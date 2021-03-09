package sms2port;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TooManyListenersException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import modem.ATCommand;
import modem.ATCommand.Mode;
import modem.ATCommand.StandardResponseCode;
import modem.ATEchoCmd;
import modem.ATVerboseCmd;
import modem.gsm.sms.ATExtendedCmd;
import modem.gsm.sms.CMSError;
import modem.gsm.sms.SMSStatus;
import modem.gsm.sms.SMSStorageArea;
import modem.gsm.sms.commands.ATSMSFormat;
import modem.gsm.sms.commands.ATSMSFormat.Format;
import modem.gsm.sms.commands.ATSMSInputCmd;
import modem.gsm.sms.notifications.NewSMSNotification;
import modem.gsm.sms.pdu.SMSDeliver;
import modem.gsm.sms.pdu.SMSStatusReport;
import util.Out;
import util.bytes.ByteArray;
import util.bytes.Convert;

/**
 * Class responsabilities:
 * 	- Ensure proper I/O management from/to serial port
 * 	- Ensure proper "event" handling and forwarding
 * 
 * @author Joel Rocha
 *
 */
public class ATSMSModem extends AbstractATSMSModem {
	public interface StandardResponseListener {
		public void standardResponseEvent(StandardResponseCode code);
	}
	
	private class ResponseHandler implements SerialPortEventListener {
		
		@Override
		public void serialEvent(SerialPortEvent spe) {
			if (spe.getEventType() != SerialPortEvent.DATA_AVAILABLE)
				return;
			
			String temp = "";
			
			try {
				temp = ATSMSModem.readData( ATSMSModem.this.sPort );
			} catch (IOException e) { }
			
			final String data = temp;

			Out.println( data );
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						new ModemReceiverParser(ATSMSModem.this, data).start();
					} catch (Exception e) {
						Out.println("Received: " + data);
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
	
	private final SerialPort sPort;
	
	public ATSMSModem(SerialPort sPort)
	throws TooManyListenersException, IOException {
		this.sPort = sPort;
		
		initModemCmds();
		
		this.sPort.addEventListener(new ResponseHandler());
		this.sPort.notifyOnDataAvailable(true);
		
		ATSMSFormat format = new ATSMSFormat(Format.Hex);
		try {
			send(format);
			
			if (format.getSRC() == StandardResponseCode.Error)
				throw new IOException(ATSMSFormat.class.getName() + " command not suported!");
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}
	
	private void initModemCmds()
	throws IOException {
		ATCommand[] cmds = new ATCommand[] {
				new ATEchoCmd( 		Mode.Off	),
				new ATVerboseCmd(	Mode.On		),
		};
		
		for (ATCommand cmd : cmds)
			this.sPort.getOutputStream().write(
					(cmd.getATCommand() + "\r\n").getBytes() );

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		
		ATSMSModem.readData(this.sPort);
	}
	
	private final ReentrantLock sendLock = new ReentrantLock();
	private final Condition cmdExecute = sendLock.newCondition();

	private boolean canSendCommand = true;
	
	/**
	 * Sends ATCommands and waits for their respective OK or ERROR code.
	 * 
	 * Warning: the error response could leave the modem unresponsive for up to
	 * 5 minutes and probably end in a '+CMS ERROR' response.
	 * 
	 * @param cmd
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void send(ATCommand cmd) throws InterruptedException, IOException {
		if (isClosed)
			throw new IOException("ATSMSModem is closed");
		
		sendLock.lock();
		try {
			while(!canSendCommand)
				cmdExecute.await();
			canSendCommand  = false;
			
			sendATCmd( cmd );
			
			if (cmd instanceof ATSMSInputCmd) {
				ATSMSInputCmd smsIn = (ATSMSInputCmd) cmd;

				this.rcvReadyMutex.awaitReceiveReady();
				this.sPort.getOutputStream().write(
						ByteArray.concat(
								Convert.byte2Hex( smsIn.getSMS() ).getBytes(),
								new byte[] { (byte) 26 } /* Ctrl + Z (Break)*/) );
			}
			
			if (cmd instanceof ATSMSInputCmd) {
				/**
				 * For the Option iCon 225 which as been used for testing purposes
				 * this is an hard command to deal with...
				 * 
				 * Here's what can happen:
				 * Command is sent:
				 * 		- Modem tries several times (it can take a while) 
				 * 	  	  to send the requested message and returns ok/error
				 * 		- Modem successfully sends the message but does not return ok
				 * 
				 * Several approaches have been tried to workaround this problem:
				 * 		- Send ^Z	 	until the modem resumes normal operation
				 * 		- Send \r\n 	until the modem resumes normal operation
				 * 		- Send AT\r\n	until the modem resumes normal operation
				 * 
				 * None of the approaches tested so far have successfully resolved this
				 * issue leaving this class in a "dead lock".
				 * 
				 * The following methods will try to solve this issue:
				 */
				int maxExpectedQueueSize = 1;
				int awaitUnits = 5;
				boolean answer = this.standardATCmd.isEmpty();
				for(int i = 0; i < 8 && !answer; i++) {
					cmdExecute.await(awaitUnits, TimeUnit.SECONDS);

					answer = this.standardATCmd.size() < maxExpectedQueueSize;
					
					if (!answer)
						switch (i) {
							case 0: //5s, Maybe the command is stuck?
								this.sPort.getOutputStream().write( new byte[] { (byte) 26 } );
								break;
							case 1: //10s, Missing CR+LF?
								this.sPort.getOutputStream().write( "\r\n".getBytes() );
								break;
							case 2: //15s, Maybe if you have something else to do?
								sendATCmd(new ATCommand());
								maxExpectedQueueSize++;
								break;
							case 3: //20s, Maybe you are getting some network troubles...
								//300seconds of penalty to you!
								awaitUnits = 300;
								break;
							case 4:
								//320s, Desperate mode: just resume operation and get on with your life!
								awaitUnits = 5;
						default:
							//325, 340s
							sendATCmd(new ATCommand());
							maxExpectedQueueSize++;
							break;
						}
				}
				
				//From now on only 2 things can happen: either the modem resumes
				//normal operation or the software shall throw an IllegalStateException
				//due to the unresponsiveness of the modem
				if (!this.standardATCmd.isEmpty()) {
					/**
					 * If some standard AT Commands were sent
					 * the worst case scenario is that a new command is issued and
					 * the modem is still responding to the previous AT command
					 * we need to synchronize...
					 */
					//Wait to see if all commands get the corresponding response
					int prevSize;
					do{
						prevSize = this.standardATCmd.size();
						cmdExecute.await(5, TimeUnit.SECONDS);
					} while(this.standardATCmd.size() < prevSize);

					//Purge all hanging commands from queue
					while (!this.standardATCmd.isEmpty())
						this.standardATCmd.remove().setSRC(StandardResponseCode.Error);

					//Send a Standard ATCommand and wait for the queue to be empty
					sendATCmd(new ATCommand());
					cmdExecute.await(5, TimeUnit.SECONDS);
					if (!this.standardATCmd.isEmpty())
						throw new IllegalStateException(
								"Unable to resume modem to normal operation");
				}
			}
				
			while(!this.standardATCmd.isEmpty())
				cmdExecute.await();
		} finally {
			canSendCommand = true;
			cmdExecute.signal();
			sendLock.unlock();
		}
	}

	/**
	 * Standard routine for sending standard AT Commands.
	 * 
	 * @param cmd
	 * @throws IOException
	 */
	private void sendATCmd(ATCommand cmd) throws IOException {
		Out.println(cmd.getATCommand());
		this.standardATCmd.add( cmd );
		this.sPort.getOutputStream().write(
				( cmd.getATCommand() + "\r\n" ).getBytes() );
	}

	private RcvReadyMutEx rcvReadyMutex = new RcvReadyMutEx();
	private static class RcvReadyMutEx {
		private final ReentrantLock rcvReadyLock = new ReentrantLock();
		private final Condition canProceed = rcvReadyLock.newCondition();
		private volatile boolean isRcvReadyReceived = false;
		
		private void awaitReceiveReady() throws InterruptedException {
			rcvReadyLock.lock();
			try {
				while(!isRcvReadyReceived)
					canProceed.await();
				isRcvReadyReceived = false;
			} finally {
				canProceed.signal();
				rcvReadyLock.unlock();
			}
		}
		
		private void setRcvReadyReceived() throws InterruptedException {
			rcvReadyLock.lock();
			try {
				while(isRcvReadyReceived)
					canProceed.await();
				isRcvReadyReceived = true;
			} finally {
				canProceed.signal();
				rcvReadyLock.unlock();
			}
		}
	}
	
	void notifyReceiveReadyListener() {
		try {
			this.rcvReadyMutex.setRcvReadyReceived();
		} catch (InterruptedException e) {}
	}
	
	private final ConcurrentLinkedQueue<ATCommand> standardATCmd =
		new ConcurrentLinkedQueue<ATCommand>();
	
	void setATCommandSRC(StandardResponseCode code) {
		sendLock.lock();
		try {
			if (!standardATCmd.isEmpty())
				standardATCmd.remove().setSRC(code);
		} finally {
			cmdExecute.signalAll();
			sendLock.unlock();
		}
	}

	void setSMSInputCmdModemRef(byte ref) {
		((ATSMSInputCmd) standardATCmd.peek()).setMsgReference(ref);
	}
	
	void setSMSInputCmdSendError(CMSError err) {
		sendLock.lock();
		try{
			((ATExtendedCmd) standardATCmd.remove()).setCMSError(err);
		} finally {
			cmdExecute.signalAll();
			sendLock.unlock();
		}
	}
	
	public interface NewMsgIndicationListener {
		public void processNMI(NewSMSNotification nmi);
	}
	private NewMsgIndicationListener newMsgIndicationListener;
	public void set(NewMsgIndicationListener nmil) {
		this.newMsgIndicationListener = nmil;
	}
	void notifyNewMsgIndicationListener(SMSStorageArea type, int index) {
		final NewSMSNotification n = new NewSMSNotification(type, index);
		
		new Thread( new Runnable() {
			@Override
			public void run() {
				if (ATSMSModem.this.newMsgIndicationListener != null)
					ATSMSModem.this.newMsgIndicationListener.processNMI(n);
			}
		}).start();
	}
	
	public static abstract class SMSRowReader extends ATExtendedCmd {
		protected abstract void processSMSRow(Integer index, SMSStatus status, SMSDeliver sms);
	}
	void notifySMSRowReader(final Integer i, final SMSStatus s, final SMSDeliver sms) {
		if (!standardATCmd.isEmpty() && standardATCmd.peek() instanceof SMSRowReader)
			((SMSRowReader) standardATCmd.peek()).processSMSRow(i, s, sms);
	}
	
	public static String readData(SerialPort sPort) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		int b = -1;
		while((b = sPort.getInputStream().read()) != -1)
			out.write(b);
		
		return new String(out.toByteArray());
	}

	private volatile boolean isClosed = false;
	public void close() {
		sPort.close();
		this.isClosed = true;
	}
	
	public interface SMSStatusReportListener { public void rcvSMSStatusReport(SMSStatusReport sr); }
	private final ArrayList<SMSStatusReportListener> smsSRListenerList = new ArrayList<>();
	public void add		(SMSStatusReportListener l) { synchronized (smsSRListenerList) { if (l != null) smsSRListenerList.add(l); } }
	public void remove	(SMSStatusReportListener l) { synchronized (smsSRListenerList) { if (l != null) smsSRListenerList.remove(l); } }
	void notifySMSStatusReportListeners(final SMSStatusReport sr) {
		synchronized (smsSRListenerList) {
			for(final SMSStatusReportListener srl : smsSRListenerList)
				new Thread(new Runnable() {
					@Override
					public void run() { srl.rcvSMSStatusReport(sr); }
				}).start();
		}
	}
}

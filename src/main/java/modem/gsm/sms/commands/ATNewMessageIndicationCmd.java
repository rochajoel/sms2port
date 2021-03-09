package modem.gsm.sms.commands;

import modem.ATCommand;
import modem.common.ATParam;
import util.Enums.NamedInt.IInt;

public class ATNewMessageIndicationCmd extends ATCommand {
	public enum NotificationMode implements IInt {
		BufferInTA				(0),
		RejectSMSIfLinkReserved	(1),
		BufferSMSIfLinkReserved	(2),
		ForwardDirectly			(3),
		;
		
		private final int mode;

		private NotificationMode(int mode) { this.mode = mode; }

		@Override
		public int getInt() {
			return this.mode;
		}
	}
	
	/**
	 * Flag to enable or disable message Routing to the host.
	 * 
	 * Classes identify the message's importance as well as the location
	 * where it should be stored. There are 4 message classes.
	 * Class 0:
	 * 	Indicates that this message is to be displayed on the MS immediately
	 * 	and a message delivery report is to be sent back to the SC. The message
	 * 	does not have to be saved in the MS or on the SIM card (unless selected
	 * 	to do so by the mobile user).
	 * Class 1:
	 * 	Indicates that this message is to be stored in the MS memory or the SIM
	 * 	card (depending on memory availability).
	 * Class 2:
	 * 	This message class is Phase 2 specific and carries SIM card data.
	 * 	The SIM card data must be successfully transferred prior to sending
	 * 	acknowledgement to the SC. An error message will be sent to the SC
	 * 	if this transmission is not possible.
	 * Class 3:
	 * 	Indicates that this message will be forwarded from the receiving entity
	 * 	to an external device. The delivery acknowledgement will be sent to the
	 * 	SC regardless of whether or not the message was forwarded to the external
	 * 	device.  
	 * 
	 * @author Joel Rocha
	 *
	 */
	public enum RouteMsg implements IInt {
		Disabled			(0),
		Enabled				(1),
		EnabledExceptClass2	(2),
		EnabledExceptClass3	(3),
		;
		
		private final int mode;

		private RouteMsg(int mode) { this.mode = mode; }
		
		@Override
		public int getInt() { return this.mode; }
	}
	
	public enum RouteStatusReport implements IInt {
		Disabled	(0),
		Enabled		(1),
		;
		
		private final int mode;

		private RouteStatusReport(int mode) { this.mode = mode; }
		
		@Override
		public int getInt() { return this.mode; }
	}
	
	public enum TABuffer implements IInt {
		/** TA buffer is flushed to TE (if Mode != BufferInTA) **/
		Flushed	(0),
		/** TA buffer is cleared (if Mode != BufferInTA) */
		Cleared (1),
		;
		
		private final int mode;

		private TABuffer(int mode) { this.mode = mode; }
		
		@Override
		public int getInt() { return this.mode; }
	}

	private static final String extCmdLabel = "+CNMI";
	private final String cmd;
	
	/**
	 * 
	 * @param mode - Mandatory
	 * @param sms - Optional
	 * @param cbs - Optional
	 * @param ds - Optional
	 * @param bfr - Optional
	 * @return
	 */
	public ATNewMessageIndicationCmd(NotificationMode mode, RouteMsg sms, RouteMsg cbs, RouteStatusReport ds, TABuffer bfr) {
		String tempCmd = extCmdLabel + "=" + ((mode != null) ? mode.getInt() : "");
		
		IInt[] param = new IInt[] {sms, cbs, ds, bfr};

		for (IInt p : param)
			tempCmd += ATParam.add(p);
		
		cmd = tempCmd;
	}
	
	public ATNewMessageIndicationCmd(NotificationMode mode, RouteMsg sms) {
		this(mode, sms, null, null, null);
	}

	@Override
	public String getATCommand() {
		return super.getATCommand() + cmd;
	}
}

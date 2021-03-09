package sms2port;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import modem.ATCommand.StandardResponseCode;
import modem.gsm.sms.CMSError;
import modem.gsm.sms.SMSStatus;
import modem.gsm.sms.SMSStorageArea;
import modem.gsm.sms.commands.ATSMSInputCmd.SMSOperation;
import modem.gsm.sms.pdu.SMSDeliver;
import modem.gsm.sms.pdu.SMSStatusReport;
import modem.gsm.sms.pdu.SMSStatusReport.DeliveredStatus;
import modem.gsm.sms.pdu.SMSStatusReport.DeliveredStatus.DeliveredReason;
import modem.gsm.sms.pdu.SMSStatusReport.SMSStatusReportHeader.SMSStatusReportOption;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;

import util.Enums.Flag;


public class ModemReceiverParserTest {
	private abstract class TestATSMSModem extends AbstractATSMSModem {
		protected boolean isMethodInvoked = false;
		
		@Override
		void notifyNewMsgIndicationListener(SMSStorageArea sa, int index)	{ parserProductionMismatch(); }
		@Override
		void setSMSInputCmdModemRef(byte ref) 						{ parserProductionMismatch(); }
		@Override
		void setSMSInputCmdSendError(CMSError err)					{ parserProductionMismatch(); }
		@Override
		void notifySMSRowReader(Integer i, SMSStatus s, SMSDeliver sms)	{ parserProductionMismatch(); }
		@Override
		void setATCommandSRC(StandardResponseCode code)				{ parserProductionMismatch(); }
		@Override
		void notifyReceiveReadyListener()							{ parserProductionMismatch(); }
		@Override
		void notifySMSStatusReportListeners(SMSStatusReport sr)					{ parserProductionMismatch(); }
		
		private void parserProductionMismatch() {
			fail("Parser is invoking the wrong method to be tested");
		}
	}
	
	@Test
	public void testNewSMSNotification() throws RecognitionException {
		TestATSMSModem testInterface = new TestATSMSModem() {
			@Override
			void notifyNewMsgIndicationListener(SMSStorageArea sa, int index) {
				this.isMethodInvoked = true;
				assertEquals(SMSStorageArea.SIMCard, sa);
				assertEquals(0, index);
			}
		};
		
		ModemReceiverParser parser = new ModemReceiverParser(
				testInterface,
				"+CMTI: \"SM\",0");
		parser.start();
		
		assertTrue(testInterface.isMethodInvoked);
	}
	
	@Test
	public void testNotifySMSInputCmd() throws RecognitionException {
		for(SMSOperation op : SMSOperation.values())
			for(int i = 0; i < 256; i++) {
				final int currIndex = i;
				
				TestATSMSModem testInterface = new TestATSMSModem() {
					@Override
					void setSMSInputCmdModemRef(byte ref) {
						this.isMethodInvoked = true;
						assertEquals((byte)currIndex, ref);
					}
				};
				
				ModemReceiverParser parser = new ModemReceiverParser(
						testInterface,
						"+CMG" + op + ": " + i);
				parser.start();
				
				assertTrue(testInterface.isMethodInvoked);
			}
	}
	
	@Test
	public void testNotifySMSInputCmdCMSError() throws RecognitionException {
		for(final CMSError expErr : CMSError.values()) {
			TestATSMSModem testInterface = new TestATSMSModem() {
				@Override
				void setSMSInputCmdSendError(CMSError err) {
					this.isMethodInvoked = true;
					assertEquals(expErr, err);
				}
			};
			
			ModemReceiverParser parser = new ModemReceiverParser(
					testInterface,
					"+CMS ERROR: " + expErr.getInt());
			parser.start();
			
			assertTrue(testInterface.isMethodInvoked);
		}
	}
	
	@Test
	public void testProcessSMS() throws RecognitionException {
		TestATSMSModem testInterface = new TestATSMSModem() {
			@Override
			void notifySMSRowReader(Integer i, SMSStatus s, SMSDeliver sms) {
				this.isMethodInvoked = true;
			}
		};
		
		ModemReceiverParser parser = new ModemReceiverParser(
				testInterface,
				"\n" +
				"+CMGR: 0,,154\n" +
				"0791539126010000240C915391961466" +
				"740000015080328290400F09D01F5400" +
				"1D4008501F6400F901\n");
		parser.start();
		
		assertTrue(testInterface.isMethodInvoked);
	}
	
	@Test
	public void testNotifySRCListener() throws RecognitionException {
		for (final StandardResponseCode expSRC : StandardResponseCode.values()) {
			TestATSMSModem testInterface = new TestATSMSModem() {
				@Override
				void setATCommandSRC(StandardResponseCode code) {
					this.isMethodInvoked = true;
					assertEquals(expSRC, code);
				}
			};
			
			ModemReceiverParser parser = new ModemReceiverParser(
					testInterface,
					expSRC.name().toUpperCase());
			parser.start();
			
			assertTrue(testInterface.isMethodInvoked);
		}
	}
	
	@Test
	public void testNotifyReceiveReadyListener() throws RecognitionException {
		TestATSMSModem testInterface = new TestATSMSModem() {
			@Override
			void notifyReceiveReadyListener() {
				this.isMethodInvoked = true;
			}
		};
		
		ModemReceiverParser parser = new ModemReceiverParser(
				testInterface,
				">");
		parser.start();
		
		assertTrue(testInterface.isMethodInvoked);
	}
	
	@Test
	public void testRcvSMSStatusReport() throws RecognitionException {
		TestATSMSModem testInterface = new TestATSMSModem() {
			@Override
			void notifySMSStatusReportListeners(SMSStatusReport sr) {
				this.isMethodInvoked = true;
				assertEquals("+35193121314", sr.getSMSCAddr().getNumber());
				assertEquals("+351933313161", sr.getRecipientAddr().getNumber());
				assertEquals(3, sr.getModemSentId());
				assertTrue(Flag.isSet(SMSStatusReportOption.NoMoreMessages, sr.getSMSStatusReportHeader().getOptions()));
				assertTrue(sr.getStatus() instanceof DeliveredStatus);
				assertEquals(((DeliveredStatus)sr.getStatus()).getReason(), DeliveredReason.SME_Delivered);
			}
		};
		
		ModemReceiverParser parser = new ModemReceiverParser(
				testInterface,
				"+CDS: 25\n" +
				"07915391131213F406030C91539133131316218030612015402180306120654000\n");
		parser.start();
		
		assertTrue(testInterface.isMethodInvoked);
	}
	
	@Test
	public void testRcvMultipleSMSStatusReport() throws RecognitionException {
		class TestInterface extends TestATSMSModem {
			int srCalls = 0;
			@Override
			void notifySMSStatusReportListeners(SMSStatusReport sr) {
				srCalls++;
				this.isMethodInvoked = true;
				assertEquals("+35193121314", sr.getSMSCAddr().getNumber());
				assertEquals("+351933313161", sr.getRecipientAddr().getNumber());
				assertEquals(3, sr.getModemSentId());
				assertTrue(Flag.isSet(SMSStatusReportOption.NoMoreMessages, sr.getSMSStatusReportHeader().getOptions()));
				assertTrue(sr.getStatus() instanceof DeliveredStatus);
				assertEquals(((DeliveredStatus)sr.getStatus()).getReason(), DeliveredReason.SME_Delivered);
			}
		};
		TestInterface testInterface = new TestInterface();
		
		ModemReceiverParser parser = new ModemReceiverParser(
				testInterface,
				"\n+CDS: 25\n" +
				"07915391131213F406030C91539133131316218030612015402180306120654000\n" +
				"\n+CDS: 25\n" +
				"07915391131213F406030C91539133131316218030612015402180306120654000\n" +
				"\n+CDS: 25\n" +
				"07915391131213F406030C91539133131316218030612015402180306120654000\n");
		parser.start();
		
		assertTrue(testInterface.isMethodInvoked);
		assertEquals(3, testInterface.srCalls);
	}
	
	@Test
	public void testMultipleProductions() throws RecognitionException {
		class CustomTestATSMSModem extends TestATSMSModem {
			@Override
			void notifySMSStatusReportListeners(SMSStatusReport sr) {
				this.isMethodInvoked = true;
			}

			private boolean isRRNotificationRcvd = false;
			@Override
			void notifyReceiveReadyListener() {
				this.isRRNotificationRcvd = true;
			}
		};
		CustomTestATSMSModem testInterface = new CustomTestATSMSModem();
		
		ModemReceiverParser parser = new ModemReceiverParser(
				testInterface,
				"+CDS: 25\n" +
				"07915391131213F406030C91539133131316218030612015402180306120654000\n" +
				"\n" +
				">\n");
		parser.start();
		
		assertTrue(testInterface.isMethodInvoked);
		assertTrue(testInterface.isRRNotificationRcvd);
	}
}

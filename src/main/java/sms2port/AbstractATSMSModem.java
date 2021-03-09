package sms2port;

import modem.ATCommand.StandardResponseCode;
import modem.gsm.sms.CMSError;
import modem.gsm.sms.SMSStatus;
import modem.gsm.sms.SMSStorageArea;
import modem.gsm.sms.pdu.SMSDeliver;
import modem.gsm.sms.pdu.SMSStatusReport;

abstract class AbstractATSMSModem {
	abstract void setSMSInputCmdModemRef(byte ref);
	abstract void setSMSInputCmdSendError(CMSError err);
	abstract void setATCommandSRC(StandardResponseCode code);
	
	abstract void notifyReceiveReadyListener();
	abstract void notifyNewMsgIndicationListener(SMSStorageArea sa, int index);	
	abstract void notifySMSRowReader(Integer i, SMSStatus s, SMSDeliver sms);
	abstract void notifySMSStatusReportListeners(SMSStatusReport sr);
}

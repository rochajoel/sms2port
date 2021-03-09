package modem.gsm.sms.commands;

import modem.gsm.sms.SMSStatus;
import modem.gsm.sms.notifications.NewSMSNotification;
import modem.gsm.sms.pdu.SMSDeliver;
import sms2port.ATSMSModem.SMSRowReader;

/**
 * AT+CMGR=index method.
 * 
 * This class is able to hold up to 1 {@link SMSDeliver} object.
 * Bear in mind that the index you request migth not hold any message, and this
 * case this class will return null on
 * {@link ATReadMessage#getSMSDeliver()} method.
 * 
 * @author Joel Rocha
 *
 */
public class ATReadMessage extends SMSRowReader {
	private static final String extCmdLabel = "+CMGR";
	private final String cmd;
	
	private SMSStatus status;
	private SMSDeliver sms;
	
	public ATReadMessage(NewSMSNotification nmi) {
		this.cmd = extCmdLabel + "=" + nmi.getIndex();
	}

	@Override
	public String getATCommand() {
		return super.getATCommand() + cmd;
	}
	
	@Override
	protected void processSMSRow(Integer index, SMSStatus status, SMSDeliver sms) {
		if (status == null || sms == null)
			throw new IllegalArgumentException("Arguments cannot be null");
		
		if (this.sms == null) {
			this.status = status;
			this.sms = sms;
		}
	}

	public SMSStatus getSMSStatus() {
		return this.status;
	}
	
	/**
	 * Gets the Retrieved SMS message( {@link SMSDeliver} ).
	 * 
	 * In some situations the message might not exist and this method will
	 * return null.
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public SMSDeliver getSMSDeliver() {
		return this.sms;
	}
}
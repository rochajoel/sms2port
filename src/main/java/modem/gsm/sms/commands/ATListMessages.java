package modem.gsm.sms.commands;

import java.util.AbstractMap.SimpleEntry;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;

import modem.gsm.sms.SMSStatus;
import modem.gsm.sms.pdu.SMSDeliver;
import sms2port.ATSMSModem.SMSRowReader;

public class ATListMessages extends SMSRowReader {
	public enum RcvdStatus {
		RcvdUnread	(SMSStatus.ReceivedUnread),
		RcvdRead	(SMSStatus.ReceivedRead),
		;
		
		private final SMSStatus smsStatus;
		
		private RcvdStatus(SMSStatus s) { smsStatus = s; }
	}
	
	private final RcvdStatus rcvStatus;
	
	public ATListMessages(RcvdStatus s) { this.rcvStatus = s; }
	
	@Override
	public String getATCommand() {
		return super.getATCommand() + "+CMGL=" + rcvStatus.smsStatus.getInt();
	}
	
	private Hashtable<Integer, SimpleEntry<SMSStatus, SMSDeliver>> smsTable = new Hashtable<>();
	@Override
	protected void processSMSRow(Integer index, SMSStatus status,
			SMSDeliver sms) {
		smsTable.put(index, new SimpleEntry<SMSStatus, SMSDeliver>(status, sms));
	}

	
	public Set<Entry<Integer, SimpleEntry<SMSStatus, SMSDeliver>>> getSMSSet() {
		return smsTable.entrySet();
	}
}

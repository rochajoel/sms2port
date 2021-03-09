package modem.gsm.sms.notifications;

import modem.gsm.sms.SMSStorageArea;

public class NewSMSNotification {
	private final SMSStorageArea memType;
	private final int index;
	
	public NewSMSNotification(SMSStorageArea type, int index) {
		this.memType = type;
		this.index = index;
	}

	public SMSStorageArea getMemType() {
		return memType;
	}

	public int getIndex() {
		return index;
	}
}

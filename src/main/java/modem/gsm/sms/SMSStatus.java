package modem.gsm.sms;

import util.Enums.NamedInt.IInt;

public enum SMSStatus implements IInt {
	ReceivedUnread	(0),
	ReceivedRead	(1),
	StoredUnsent	(2),
	StoredSent		(3),
	All				(4),
	;
	
	private final int mode;

	private SMSStatus(int mode) { this.mode = mode; }
	
	public int getInt() { return this.mode; }
}

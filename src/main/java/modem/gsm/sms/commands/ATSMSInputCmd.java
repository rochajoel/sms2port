package modem.gsm.sms.commands;

import modem.gsm.sms.ATExtendedCmd;
import modem.gsm.sms.pdu.SMSSubmit;


public class ATSMSInputCmd extends ATExtendedCmd {
	public enum SMSOperation {
		WriteToMemory	("W"),
		Send			("S"),
		;
		
		private final String op;

		private SMSOperation(String op) { this.op = op; }
		
		public String toString() { return this.op; }
	}

	private final SMSOperation op;
	private final SMSSubmit sms;
	
	public ATSMSInputCmd(SMSOperation op, SMSSubmit sms) {
		this.op = op;
		this.sms = sms;
	}

	@Override
	public String getATCommand() {
		return
			super.getATCommand() + "+CMG" + this.op + "=" + (sms.getPDUSize() << 0);
	}

	public byte[] getSMS() {
		return sms.toByte();
	}
	
	private Byte ref = null;
	public void setMsgReference(byte newRef) {
		this.ref = newRef;
	}
	public Byte getSMSSentReference() {
		return this.ref;
	}
}

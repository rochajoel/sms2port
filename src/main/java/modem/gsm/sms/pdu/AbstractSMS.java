package modem.gsm.sms.pdu;

import modem.gsm.sms.pdu.SMSDeliver.SMSDeliverHeader;
import modem.gsm.sms.pdu.SMSStatusReport.SMSStatusReportHeader;
import modem.gsm.sms.pdu.SMSSubmit.SMSSubmitHeader;
import util.Enums.NamedByte;
import util.Enums.NamedByte.IByte;

public class AbstractSMS {
	public static abstract class AbstractSMSHeader {
		protected enum SMSType implements IByte {
			SMS_Deliver			((byte) 0x00),
			SMS_Submit			((byte) 0x01),
			SMS_StatusReport	((byte) 0x02),
			;

			private final byte type;
			
			private SMSType(byte type) { this.type = type; }
			
			@Override
			public byte getByte() {
				return this.type;
			}
			
			private static SMSType getSMSType(byte smsHeader) {
				return NamedByte.getNamedByte(SMSType.values(), (byte)(0x03 & smsHeader));
			}
		}

		private byte smsHeader;
		
		protected AbstractSMSHeader(SMSType smsType) {
			this.smsHeader = smsType.getByte();
		}
		
		public static AbstractSMSHeader getInstance(byte smsHeader) {
			SMSType smsType = SMSType.getSMSType(smsHeader);
			
			switch (smsType) {
				case SMS_Submit:
					return new SMSSubmitHeader(smsHeader);

				case SMS_Deliver:
					return new SMSDeliverHeader(smsHeader);
				
				case SMS_StatusReport:
					return new SMSStatusReportHeader(smsHeader);
					
				default:
					throw new IllegalStateException("SMSType not expected");
			}
		}

		/**
		 * This method will reset all the flags, and replace them with given flags.
		 * (The SMSType flags are preserved)
		 * @param flags
		 */
		protected void setFlags(byte flags) {
			SMSType smsType = SMSType.getSMSType(this.smsHeader);
			
			if ((flags & 0x03) != 0x00)
				if (SMSType.getSMSType(flags) != smsType)
					throw new IllegalArgumentException("The flags given would change the current SMSType.");
			
			this.smsHeader = (byte) (flags | smsType.getByte());
		}
		
		public byte toByte() {
			return this.smsHeader;
		}
	} 
}

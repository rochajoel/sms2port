package modem.gsm.sms.pdu;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import modem.gsm.sms.pdu.SMSSubmit.SMSSubmitHeader.ValidityPeriodFormat;
import modem.gsm.sms.pdu.dcs.AbstractDataCodingScheme;
import modem.gsm.sms.pdu.dcs.GeneralDataCodingScheme;
import modem.gsm.sms.pdu.dcs.GeneralDataCodingScheme.Alphabet;
import modem.gsm.sms.pdu.dcs.MsgClassCS;
import modem.gsm.sms.pdu.dcs.MsgClassCS.MsgOption;
import modem.gsm.sms.pdu.dcs.MsgIndicationCS;
import modem.gsm.sms.pdu.dcs.MsgIndicationCS.ActionType;
import modem.gsm.sms.pdu.pid.AbstractProtocolID;
import pt.rochajoel.sms2port.presentationLayer.SMS7BitsCharset;
import pt.rochajoel.sms2port.presentationLayer.SMS7BitsCharset.EncodingResult;
import pt.rochajoel.sms2port.presentationLayer.SMS7BitsCharset.UnsupportedCharException;
import util.Enums.Flag;
import util.Enums.Flag.IFlag;
import util.Enums.NamedByte;
import util.Enums.NamedByte.IByte;
import util.Enums.NamedInt.IInt;
import util.bytes.ByteArray;

public class SMSSubmit extends AbstractSMS {
	public static class SMSSubmitHeader extends AbstractSMS.AbstractSMSHeader {
		public enum SMSSubmitOption implements IFlag {
			ReplyPathPresent		((byte) 0x80),
			UserDataHeaderPresent	((byte) 0x40),
			StatusReportRequest		((byte) 0x20),
			RejectDuplicates		((byte) 0x04),
			;
			
			private final byte option;
		
			private SMSSubmitOption(byte option) { this.option = option; }
			
			@Override
			public byte getFlag() { return this.option; }
			
			private static SMSSubmitOption[] getSMSSubmitOptions(byte smsHeader) {
				List<SMSSubmitOption> opts =
					Flag.getSelected(SMSSubmitOption.values(), (byte)(0xE4 & smsHeader));
				
				return opts.toArray(new SMSSubmitOption[opts.size()]);
			}
		}
		
		enum ValidityPeriodFormat implements IByte {
			NotPresent	((byte) 0x00),
			Enhanced	((byte) 0x08),
			Relative	((byte) 0x10),
			Absolute	((byte) 0x18),
			;
			
			private final byte vpf;
			
			private ValidityPeriodFormat(byte vpf) { this.vpf = vpf; }
	
			@Override
			public byte getByte() { return this.vpf; }
	
			private static ValidityPeriodFormat getValidityPeriodFormat(byte smsHeader) {
				return NamedByte.getNamedByte(ValidityPeriodFormat.values(), (byte) (smsHeader & 0x18));
			}
		}

		protected SMSSubmitHeader(byte smsHeader) {
			super(SMSType.SMS_Submit);
			
			SMSSubmitOption.getSMSSubmitOptions(smsHeader);
			ValidityPeriodFormat.getValidityPeriodFormat(smsHeader);
			
			this.setFlags(smsHeader);
		}
		
		public SMSSubmitHeader(SMSSubmitOption[] options) {
			this( Flag.getByte(options) );
		}
		
		protected void setValidityPeriodFormat(ValidityPeriodFormat vpf) {
			super.setFlags( (byte) (super.toByte() | vpf.getByte()) );
		}
		
		public SMSSubmitOption[] getOptions() {
			return SMSSubmitOption.getSMSSubmitOptions(this.toByte());
		}
		
		public ValidityPeriodFormat getValidityPeriodFormat() {
			return ValidityPeriodFormat.getValidityPeriodFormat(this.toByte());
		}
	}
	
	public static abstract class AbstractValidationPeriod {
		private final ValidityPeriodFormat vpType;

		public AbstractValidationPeriod(ValidityPeriodFormat vpType) {
			this.vpType = vpType;
		}
		
		public ValidityPeriodFormat getValidityPeriodFormat() {
			return this.vpType;
		}
		
		public abstract byte[] toBytes();
	}
	
	public static class RelativeValidationPeriod extends AbstractValidationPeriod {
		public enum TimePeriod implements IInt {
			FiveMinutes	(5),
			HalfHour	(30),
			Day			(1440),
			Week		(10080),
			;

			private final int minutes;
			
			private TimePeriod(int minutes) { this.minutes = minutes; }
			
			@Override
			public int getInt() {
				return this.minutes;
			}
		}
		
		private final byte[] vp;
		
		public RelativeValidationPeriod(TimePeriod tp, int multiplier) {
			this(tp.getInt() * multiplier);
		}
		
		/**
		 * Creates a new Relative Validation Period.
		 * 
		 * This will convert minutes to a byte
		 * using the following approximations:
		 * 
		 * 0 to 143	(TP-VP + 1) * 5 minutes (i.e. 5 minutes intervals up to 12 hours)
		 * 144 to 167	12 hours + ((TP-VP - 143) * 30 minutes)
		 * 168 to 196	(TP-VP - 166) * 1 day
		 * 197 to 255	(TP-VP - 192) * 1 week
		 * 
		 * @param minutes
		 */
		public RelativeValidationPeriod(int minutes) {
			super(ValidityPeriodFormat.Relative);
			
			this.vp = new byte[1];
			if (minutes <= 5)
				return;
			
			int temp = (minutes / TimePeriod.FiveMinutes.getInt()) - 1;
			
			if (temp > 143)
				temp = 143 + ((minutes - 720) / TimePeriod.HalfHour.getInt());
			
			if (temp > 167)
				temp = 166 + (minutes / TimePeriod.Day.getInt());
			
			if (temp > 196)
				temp = 192 + (minutes / TimePeriod.Week.getInt());
			
			if (temp > 255)
				temp = 255;
				
			this.vp[0] = (byte) temp;
		}

		@Override
		public byte[] toBytes() {
			return Arrays.copyOf(this.vp, this.vp.length);
		}
		
	}
	
	private final byte[] smsSubmit;
	
	public static class Text {
		private final byte[] text;
		private final int numOfSymbols;
		
		private Text(int numOfSymbols, byte[] text) {
			this.text = text;
			this.numOfSymbols = numOfSymbols;
		}
		
		public static Text getInstance(AbstractDataCodingScheme dcs, String text) {
			if (dcs instanceof GeneralDataCodingScheme) {
				GeneralDataCodingScheme gDCS = (GeneralDataCodingScheme) dcs;
				
				return getInstance(gDCS.getAlphabet(), text);
			}
			
			if (dcs instanceof MsgClassCS) {
				MsgClassCS mDCS = (MsgClassCS) dcs;
				
				if (!Flag.isSet(MsgOption._8BitData, mDCS.getMsgOptions()))
						return getInstance(Alphabet.Default, text);
				
				return getInstance(Alphabet._8Bit, text);
			}
			
			if (dcs instanceof MsgIndicationCS) {
				MsgIndicationCS mDCS = (MsgIndicationCS) dcs;
				
				if (mDCS.getActionType() == ActionType.SaveMessage_UCS2)
					return getInstance(Alphabet.UCS2, text);
				
				return getInstance(Alphabet.Default, text); 
			}
			
			throw new IllegalArgumentException(
					"Unexpected Coding Scheme: " + dcs.getClass());
		}
		
		private static Text getInstance(Alphabet alpha, String text) {
			switch (alpha) {
				case Default:
					EncodingResult er = null;
					while(er == null)
						try {
							er = SMS7BitsCharset.encode(text);
						} catch (UnsupportedCharException e) {
							System.out.println(" -- Warning -- Unsupported char: " + e.getChar());
							System.out.println("Original: " + text);
							text = text.replace(e.getChar(), ' ');
							System.out.println("Result: " + text);
						}
					
					return new Text(er.getTotalNumOfSeptets(), er.getEncodedText());
					
				case _8Bit:
					return new Text(text.length(), text.getBytes());
					
				case UCS2:
					int tLen = text.length();
					byte[] eUTF16 = Charset.forName("UTF-16").encode(text).array();
					
					return new Text(
							tLen << 1,
							eUTF16.length > 2 ?
									Arrays.copyOfRange(eUTF16, 2, ++tLen << 1) :
									new byte[0]);

				default:
					throw new IllegalArgumentException(
							"Alphabet not supported: " + alpha);
			}
		}
		
		public byte[] getText() {
			return text;
		}

		public int getNumOfSymbols() {
			return numOfSymbols;
		}
	}
	
	public SMSSubmit(
			Address smsc,
			SMSSubmitHeader header,
			Address toME,
			AbstractProtocolID pid,
			AbstractDataCodingScheme dcs,
			AbstractValidationPeriod period,
			String text) {
		this(smsc, header, toME, pid, dcs, period, Text.getInstance(dcs, text));
	}
	
	private SMSSubmit(
			Address smsc,
			SMSSubmitHeader header,
			Address toME,
			AbstractProtocolID pid,
			AbstractDataCodingScheme dcs,
			AbstractValidationPeriod period,
			Text text) {
		this(smsc, header, toME, pid, dcs, period,
				(byte)text.getNumOfSymbols(), text.getText());
	}
	
	/**
	 * Using this constructor enables the user to disregard the DCS byte and
	 * force the use of the desired userData. The user is responsible for
	 * setting the correct number of symbols(a standard Text SMS can have
	 * 160symbols encoded in 140bytes or 70symbols encoded in UTF-16). 
	 * 
	 * @param smsc
	 * @param header
	 * @param toME
	 * @param pid
	 * @param dcs
	 * @param period
	 * @param userSymbols
	 * @param userData
	 */
	private SMSSubmit(
			Address smsc,
			SMSSubmitHeader header,
			Address toME,
			AbstractProtocolID pid,
			AbstractDataCodingScheme dcs,
			AbstractValidationPeriod period,
			byte userSymbols, byte[] userData) {
		if (toME.getNumber().length() == 0)
			throw new IllegalArgumentException("The 'send to' Address shall not be empty!");
		
		if (userData != null && userData.length > 140)
			throw new IllegalArgumentException("Each message can only carry 140bytes");
		
		byte[] smsSubmit = new byte[1];
		
		if (smsc != null) {
			byte[] smscBytes = smsc.toBytes();
			smsSubmit[0] = (byte) smscBytes.length;			
			smsSubmit = ByteArray.concat(smsSubmit, smscBytes);
		}
		
		header.setValidityPeriodFormat(period.getValidityPeriodFormat());
		
		byte[] toMEBytes = toME.toBytes();
		
		smsSubmit = ByteArray.concat(
				smsSubmit, new byte[] { header.toByte(), 0x00,
				(byte) ((toMEBytes.length - 1) << 1 ) });
		
		smsSubmit = ByteArray.concat(
				smsSubmit, toMEBytes);
		smsSubmit = ByteArray.concat(
				smsSubmit, new byte[] {
						pid.toByte(),
						dcs.toByte() });
		smsSubmit = ByteArray.concat(
				smsSubmit, period.toBytes() );
		
//TODO:	if (Flag.isSet(SMSSubmitOption.UserDataHeaderPresent, header.getOptions()))
		
		smsSubmit = ByteArray.concat(
				smsSubmit,
				new byte[] { (byte) userSymbols });
		
		this.smsSubmit = ByteArray.concat(
				smsSubmit,
				userData);
	}
	
	public byte[] toByte() {
		return ByteArray.copyOf( this.smsSubmit );
	}
	
	public int getPDUSize() {
		return this.smsSubmit.length - 1 - this.smsSubmit[0];
	}
}

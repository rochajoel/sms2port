package modem.gsm.sms.pdu;

import java.util.Arrays;
import java.util.List;

import modem.gsm.sms.pdu.dcs.AbstractDataCodingScheme;
import modem.gsm.sms.pdu.pid.AbstractProtocolID;
import util.Enums.Flag;
import util.Enums.Flag.IFlag;

public class SMSDeliver extends AbstractSMS {
	public static class SMSDeliverHeader extends AbstractSMSHeader {
		public enum SMSDeliverOption implements IFlag {
			ReplyPathPresent		((byte) 0x80),
			UserDataHeaderPresent	((byte) 0x40),
			StatusReportIndication	((byte) 0x20),
			NoMoreMessages			((byte) 0x04),
			;
			
			private final byte option;
		
			private SMSDeliverOption(byte option) { this.option = option; }
			
			@Override
			public byte getFlag() { return this.option; }
			
			private static SMSDeliverOption[] getSMSDeliverOptions(byte smsHeader) {
				List<SMSDeliverOption> opts =
					Flag.getSelected(SMSDeliverOption.values(), (byte)(0xE4 & smsHeader));
				
				return opts.toArray(new SMSDeliverOption[opts.size()]);
			}
		}
		
		protected SMSDeliverHeader(byte smsHeader) {
			super(SMSType.SMS_Deliver);
			
			SMSDeliverOption.getSMSDeliverOptions(smsHeader);
			
			this.setFlags( smsHeader );
		}

		public SMSDeliverHeader(SMSDeliverOption[] opts) {
			this( toByte(opts) );
		}

		private static byte toByte(SMSDeliverOption[] opts) {
			byte temp = 0;
			
			for(SMSDeliverOption o : opts)
				temp = Flag.setOn(o, temp);
			
			return temp;
		}
		
		public SMSDeliverOption[] getOptions() {
			return SMSDeliverOption.getSMSDeliverOptions(this.toByte());
		}
	}
	
	private final Address smscAddr;
	private final Address senderAddr;
	
	private final SMSDeliverHeader smsHeader;
	private final AbstractProtocolID pid;
	private final AbstractDataCodingScheme dcs;
	private final ServiceCenterTimeStamp scts;
	
	private final byte numberOfSymbols;
	private final byte[] payload, pdu;

	public SMSDeliver(byte[] pdu) {
		int currIndex = 0;
		
		this.pdu	= Arrays.copyOf(pdu, pdu.length);
		smscAddr	= new Address( Arrays.copyOfRange(pdu, currIndex + 1, currIndex += pdu[currIndex] + 1) );
		smsHeader	= (SMSDeliverHeader) AbstractSMSHeader.getInstance( pdu[currIndex++] );
		senderAddr	= new Address( Arrays.copyOfRange(pdu, currIndex + 1, currIndex += ((pdu[currIndex]+1) >> 1) + 2) );
		pid			= AbstractProtocolID.getProtocolID( pdu[currIndex++] );
		dcs			= AbstractDataCodingScheme.getDataCodingScheme( pdu[currIndex++] );
		scts		= new ServiceCenterTimeStamp( Arrays.copyOfRange(pdu, currIndex, currIndex += 7) );
		
		numberOfSymbols = pdu[currIndex++];
		payload		= Arrays.copyOfRange(pdu, currIndex, pdu.length);
	}
	
	public Address getSMSCAddr() {
		return smscAddr;
	}
	public AbstractProtocolID getPId() {
		return pid;
	}

	public AbstractDataCodingScheme getDCS() {
		return dcs;
	}

	public ServiceCenterTimeStamp getSCTS() {
		return scts;
	}

	/**
	 * Gets the number of Symbols encoded in the SMS.
	 * A standard Text SMS can contain 140bytes. The number of symbols depends
	 * on the encoding:
	 *  - 160symbols (7bits encoding) or
	 *  - 140symbols (8bits encoding) or
	 *  - 70symbols encoded in UTF-16.
	 * 
	 * @return
	 */
	public int getNumberOfSymbols() {
		return numberOfSymbols & 0xFF;
	}

	public byte[] getPayload() {
		return payload;
	}

	public Address getSenderAddr() {
		return senderAddr;
	}
	public SMSDeliverHeader getSMSHeader() {
		return smsHeader;
	}
	
	/**
	 * Returns a copy of the pdu object that origined this SMSDeliver object.
	 * 
	 * Created this method so it would be easier to save this object on file or on databases.
	 * 
	 * @return
	 */
	public byte[] getPDU() {
		return Arrays.copyOf(pdu, pdu.length);
	}
}

package modem.gsm.sms.pdu;

import java.util.Arrays;
import java.util.List;

import modem.gsm.sms.pdu.AbstractSMS.AbstractSMSHeader;
import util.Enums.Flag;
import util.Enums.Flag.IFlag;
import util.Enums.NamedByte;
import util.Enums.NamedByte.IByte;

public class SMSStatusReport {
	public static class SMSStatusReportHeader extends AbstractSMSHeader {
		public enum SMSStatusReportOption implements IFlag {
			UserDataHeaderPresent	((byte) 0x40),
			StatusReportQualifier	((byte) 0x20),
			LoopPrevention			((byte) 0x08),
			NoMoreMessages			((byte) 0x04),
			;
			
			private final byte flag;
			
			private SMSStatusReportOption(byte flag) { this.flag = flag; }
	
			@Override
			public byte getFlag() { return this.flag; }
			
			private static SMSStatusReportOption[] getSMSStatusReportOptions(byte smsHeader) {
				List<SMSStatusReportOption> opts =
					Flag.getSelected(SMSStatusReportOption.values(), (byte)(0x6C & smsHeader));
				
				return opts.toArray(new SMSStatusReportOption[opts.size()]);
			}
		}
		
		protected SMSStatusReportHeader(byte smsHeader) {
			super(SMSType.SMS_StatusReport);
			
			SMSStatusReportOption.getSMSStatusReportOptions(smsHeader);
			
			this.setFlags( smsHeader );
		}

		public SMSStatusReportHeader(SMSStatusReportOption[] opts) {
			this( toByte(opts) );
		}

		private static byte toByte(SMSStatusReportOption[] opts) {
			byte temp = 0;
			
			for(SMSStatusReportOption o : opts)
				temp = Flag.setOn(o, temp);
			
			return temp;
		}
		
		public SMSStatusReportOption[] getOptions() {
			return SMSStatusReportOption.getSMSStatusReportOptions(this.toByte());
		}
	}
	
	/**
	 * 23040 - TP-ST (pag. 64)
	 * @author Joel Rocha
	 */
	public static abstract class AbstractStatus {
		private enum StatusType implements IByte {
			Delivered	((byte)0x00),
			Pending		((byte)0x20),
			Failed		((byte)0x40),
			PendingFail	((byte)0x60),
			;
			
			private final byte type;
			
			private StatusType(byte type) { this.type = type; }

			@Override
			public byte getByte() { return type; }
			
			private static StatusType getStatusType(byte sr) {
				return NamedByte.getNamedByte(StatusType.values(), (byte)(sr & 0x60));
			}
		}
		
		protected final byte status;
		
		private AbstractStatus(byte b) {
			this.status = b;
		}
		
		private static AbstractStatus getStatus(byte sr) {
			StatusType srt = StatusType.getStatusType(sr);
			
			switch(srt) {
				case Delivered:
					return new DeliveredStatus(sr);
				case Pending:
				case PendingFail:
					return new PendingStatus(sr);
				case Failed:
					return new FailedStatus(sr);
				default:
					throw new IllegalArgumentException("Unknown Status Type");
			}
		}
		
		public final byte getByte() { return this.status; }
	}
	public static class DeliveredStatus extends AbstractStatus {
		public enum DeliveredReason implements IByte {
			SME_Delivered						((byte) 0x00),
			ForwardedButUnknownIfDelivered		((byte) 0x01),
			SM_ReplacedBySC						((byte) 0x02),
			Reserved							((byte) 0x03),
			SC_Specific							((byte) 0x10),
			;
			
			private final byte info;
			
			private DeliveredReason(byte info) { this.info = info; }

			@Override
			public byte getByte() { return this.info; }
			
			private static DeliveredReason getDeliveredInfo(byte sr) {
				if ((sr & SC_Specific.getByte()) == SC_Specific.getByte())
					return SC_Specific;
				
				byte infoBits = (byte)(sr & 0x0F);
				
				if (infoBits >= Reserved.getByte())
					return Reserved;
				
				return NamedByte.getNamedByte(DeliveredReason.values(), infoBits);
			}
		}
		
		private DeliveredStatus(byte sr) {
			super(sr);
		}
		
		public DeliveredReason getReason() { return DeliveredReason.getDeliveredInfo(this.status); }
	}
	
	public static class PendingStatus extends AbstractStatus {
		public enum PendingReason implements IByte {
			Congestion							((byte) 0x00),
			SME_Busy							((byte) 0x01),
			SME_NoAnswer						((byte) 0x02),
			ServiceRejected						((byte) 0x03),
			QoSNotAvailable						((byte) 0x04),
			SME_Error							((byte) 0x05),
			Reserved							((byte) 0x06),
			SC_Specific							((byte) 0x10),
			;
			
			private final byte info;
			
			private PendingReason(byte info) { this.info = info; }

			@Override
			public byte getByte() { return this.info; }
			
			private static PendingReason getPendingInfo(byte sr) {
				if ((sr & SC_Specific.getByte()) == SC_Specific.getByte())
					return SC_Specific;
				
				byte infoBits = (byte)(sr & 0x0F);
				
				if (infoBits >= Reserved.getByte())
					return Reserved;
				
				return NamedByte.getNamedByte(PendingReason.values(), infoBits);
			}
		}
		
		private PendingStatus(byte sr) {
			super(sr);
		}
		
		public PendingReason getReason() { return PendingReason.getPendingInfo(this.status); }
		public boolean isPermanent() { return (this.status & 0x40) == 0x40; }
	}
	
	public static class FailedStatus extends AbstractStatus {
		public enum FailedReason implements IByte {
			RemoteProcedureError				((byte) 0x00),
			IncompatibleDestination				((byte) 0x01),
			SME_Rejected						((byte) 0x02),
			NotObtainable						((byte) 0x03),
			QoSNotAvailable						((byte) 0x04),
			NoInterworkingAvailable				((byte) 0x05),
			SM_VPExpired						((byte) 0x06),
			SM_DeletedByOriginatingSME			((byte) 0x07),
			SM_DeletedBySCAdmin					((byte) 0x08),
			SM_DoesNotExist						((byte) 0x09),
			Reserved							((byte) 0x0A),
			SC_Specific							((byte) 0x10),
			;
			
			private final byte info;
			
			private FailedReason(byte info) { this.info = info; }

			@Override
			public byte getByte() { return this.info; }
			
			private static FailedReason getFailureInfo(byte sr) {
				if ((sr & SC_Specific.getByte()) == SC_Specific.getByte())
					return SC_Specific;
				
				byte infoBits = (byte)(sr & 0x0F);
				
				if (infoBits >= Reserved.getByte())
					return Reserved;
				
				return NamedByte.getNamedByte(FailedReason.values(), infoBits);
			}
		}
		
		private FailedStatus(byte sr) {
			super(sr);
		}
		
		public FailedReason getReason() { return FailedReason.getFailureInfo(this.status); }
	}
	
	private final Address smscAddr;
	private final SMSStatusReportHeader srHeader;
	private final byte modemSentId;
	private final Address recpAddr;
	private final ServiceCenterTimeStamp scts, statusAt;
	private final AbstractStatus status;
	
	private final byte[] pdu;
	
	public SMSStatusReport(byte[] pdu) {
		int currIndex = 0;
		
		this.pdu	= Arrays.copyOf(pdu, pdu.length);
		smscAddr	= new Address( Arrays.copyOfRange(pdu, currIndex + 1, currIndex += pdu[currIndex] + 1) );
		srHeader	= (SMSStatusReportHeader) AbstractSMSHeader.getInstance( pdu[currIndex++] );
		modemSentId = pdu[currIndex++];
		recpAddr	= new Address( Arrays.copyOfRange(pdu, currIndex + 1, currIndex += ((pdu[currIndex]+1) >> 1) + 2) );
		scts		= new ServiceCenterTimeStamp( Arrays.copyOfRange(pdu, currIndex, currIndex += 7) );
		statusAt	= new ServiceCenterTimeStamp( Arrays.copyOfRange(pdu, currIndex, currIndex += 7) );
		status		= AbstractStatus.getStatus( pdu[currIndex++] );
	}

	public Address getSMSCAddr() 							{ return smscAddr; }
	public SMSStatusReportHeader getSMSStatusReportHeader() { return srHeader; }
	public byte getModemSentId()							{ return modemSentId; }
	public Address getRecipientAddr() 						{ return recpAddr; }

	public ServiceCenterTimeStamp getSCTS()					{ return scts; }
	public ServiceCenterTimeStamp getDischargeTime()		{ return statusAt; }
	
	public AbstractStatus getStatus()						{ return this.status; }
	
	public byte[] getPDU() { return Arrays.copyOf(pdu, pdu.length); }
}

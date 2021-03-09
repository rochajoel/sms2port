package modem.gsm.sms.pdu.pid;

import util.Enums.NamedByte;
import util.Enums.NamedByte.IByte;

public abstract class AbstractProtocolID {
	protected enum ProtocolIDType implements IByte {
		TelematicDevice	((byte) 0x00),
		SMSMessage		((byte) 0x40),
		;
		
		private final byte type;

		private ProtocolIDType(byte pid) { this.type = pid; }
		
		@Override
		public byte getByte() {
			return this.type;
		}
		
		protected static ProtocolIDType getProtocolIDType(byte pid) {
			return NamedByte.getNamedByte(ProtocolIDType.values(), (byte)(0xC0 & pid));
		}
	}

	private byte pid;
	
	protected AbstractProtocolID(ProtocolIDType pidType) {
		this.pid = pidType.getByte();
	}

	public static AbstractProtocolID getProtocolID(byte pid) {
		ProtocolIDType pidType = ProtocolIDType.getProtocolIDType(pid);
		
		switch (pidType) {
			case TelematicDevice:
				return new TelematicDevicePID(pid);
			case SMSMessage:
				return new SMSMessagePID(pid);

			default:
				throw new IllegalArgumentException("Unknown Device Type");
		}
	}
	
	protected void setFlags(byte flags) {
		if ((flags & 0xC0) != 0x00)
			if (ProtocolIDType.getProtocolIDType(flags) != ProtocolIDType.getProtocolIDType(this.pid))
				throw new IllegalArgumentException("The flags given would change the current ProtocolIDType.");
		
		this.pid |= flags;
	}

	public byte toByte() {
		return this.pid;
	}
}

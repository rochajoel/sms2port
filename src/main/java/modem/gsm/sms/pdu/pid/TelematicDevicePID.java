package modem.gsm.sms.pdu.pid;

import util.Enums.NamedByte;
import util.Enums.NamedByte.IByte;

public class TelematicDevicePID extends AbstractProtocolID {
	public enum DeviceType implements IByte {
		Implicit					((byte) 0x00),
		Telex						((byte) 0x01),
		Group3Telefax				((byte) 0x02),
		Group4Telefax				((byte) 0x03),
		VoicePhone					((byte) 0x04),
		ERMES						((byte) 0x05),
		NationalPagingSystem		((byte) 0x06),
		Videotex					((byte) 0x07),
		TeletexUnspecified			((byte) 0x08),
		TeletexPSPDN				((byte) 0x09),
		TeletexCSPDN				((byte) 0x0A),
		TeletexPSTN					((byte) 0x0B),
		TeletexISDN					((byte) 0x0C),
		UCI							((byte) 0x0D),
		Reserved_0E					((byte) 0x0E),
		Reserved_0F					((byte) 0x0F),
		PrivateMsgHandlingFacility	((byte) 0x10),
		PublicMsgHandlingFacility	((byte) 0x11),
		InternetEMail				((byte) 0x12),
		Reserved_13					((byte) 0x13),
		Reserved_14					((byte) 0x14),
		Reserved_15					((byte) 0x15),
		Reserved_16					((byte) 0x16),
		Reserved_17					((byte) 0x17),
		SCSpecific_18				((byte) 0x18),
		SCSpecific_19				((byte) 0x19),
		SCSpecific_1A				((byte) 0x1A),
		SCSpecific_1B				((byte) 0x1B),
		SCSpecific_1C				((byte) 0x1C),
		SCSpecific_1D				((byte) 0x1D),
		SCSpecific_1E				((byte) 0x1E),
		GSM_MobileStation			((byte) 0x1F),
		;
		
		private final byte type;

		private DeviceType(byte type) { this.type = type; }
		
		@Override
		public byte getByte() { return this.type; }
		
		public static DeviceType getDeviceType(byte pid) {
			return NamedByte.getNamedByte(DeviceType.values(), (byte)(0x1F & pid));
		}
	}
	
	public TelematicDevicePID(DeviceType type) {
		super(ProtocolIDType.TelematicDevice);
		
		super.setFlags(type.getByte());
	}

	protected TelematicDevicePID(byte pid) {
		super(ProtocolIDType.getProtocolIDType(pid));

		DeviceType.getDeviceType(pid);
		
		super.setFlags(pid);
	}

	public DeviceType getDeviceType() {
		return DeviceType.getDeviceType( super.toByte() );
	}
}

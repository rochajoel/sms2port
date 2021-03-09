package modem.gsm.sms.pdu.dcs;

import util.Enums.NamedByte;
import util.Enums.NamedByte.IByte;

public abstract class AbstractDataCodingScheme {
	public enum CodingGroup implements IByte {
		GeneralData				((byte) 0x00),
		MessageWaiting_Discard	((byte) 0xC0),
		MessageWaiting_Store	((byte) 0xD0),
		MessageWaiting_StoreUCS2((byte) 0xE0),
		MessageClass			((byte) 0xF0),
		;
		
		private final byte group;

		private CodingGroup(byte group) { this.group = group; }
		
		@Override
		public byte getByte() {
			return group;
		}
		
		protected static CodingGroup getCodingGroup(byte dcs) {
			if ((dcs & 0x30) == (dcs & 0xF0))
				return CodingGroup.GeneralData;
			
			return NamedByte.getNamedByte(CodingGroup.values(), (byte)(0xF0 & dcs));
		}
	}
	
	public enum MsgClass implements IByte {
		Alert		((byte) 0x00),
		ME_Specific	((byte) 0x01),
		SIM_Specific((byte) 0x02),
		TE_Specific	((byte) 0x03),
		;
		
		private final byte clazz;
		
		private MsgClass(byte clazz) { this.clazz = clazz; }
		
		@Override
		public byte getByte() { return this.clazz; }
		
		protected static MsgClass getMsgClass(byte dcs) {
			return NamedByte.getNamedByte(MsgClass.values(), (byte)(0x03 & dcs));
		}
	}
	
	private byte dcs;

	protected AbstractDataCodingScheme(CodingGroup cg) {
		this.dcs = cg.getByte();
	}
	
	public static AbstractDataCodingScheme getDataCodingScheme(byte dcs) {
		CodingGroup cg = CodingGroup.getCodingGroup(dcs);
		
		switch(cg) {
			case GeneralData:
				return new GeneralDataCodingScheme( dcs );
			case MessageClass:
				return new MsgClassCS( dcs );
			case MessageWaiting_Discard:
			case MessageWaiting_Store:
			case MessageWaiting_StoreUCS2:
				default:
					throw new IllegalArgumentException("Not Implemented: " + cg);
		}
	}
	
	protected void setSubFlags(byte flags) {
		byte mask = 0x0F;
		
		if (getCodingGroup() == CodingGroup.GeneralData)
			mask |= 0x30;
		
		if ((mask & flags) != flags)
			throw new IllegalArgumentException("Not Allowed.");
		
		this.dcs |= (mask & flags);
	}
	
	public final CodingGroup getCodingGroup() {
		return CodingGroup.getCodingGroup( this.dcs );
	}
	
	public final byte toByte() {
		return this.dcs;
	}
}

package modem.gsm.sms.pdu.dcs;

import java.util.List;

import util.Enums.Flag;
import util.Enums.Flag.IFlag;

/**
 * Message Class Coding Scheme.
 * 
 * @author Joel Rocha
 *
 */
public class MsgClassCS extends AbstractDataCodingScheme {
	public enum MsgOption implements IFlag {
		Reserved	((byte) 0x08),
		_8BitData	((byte) 0x04),
		;
		
		private final byte flag;

		private MsgOption(byte flag) { this.flag = flag; }

		@Override
		public byte getFlag() {
			return this.flag;
		}
		
		private static MsgOption[] getMsgOptions(byte dcs) {
			List<MsgOption> selected = Flag.getSelected(MsgOption.values(), (byte) (dcs & 0x0F));
			
			return selected.toArray( new MsgOption[selected.size()] );
		}
	}
	
	public MsgClassCS(MsgOption[] options, MsgClass clazz) {
		super( CodingGroup.MessageClass );
		
		this.setSubFlags( Flag.getByte( options ) );
		this.setSubFlags( clazz.getByte() );
	}
	
	protected MsgClassCS(byte dcs) {
		super( CodingGroup.getCodingGroup(dcs) );

		this.setSubFlags( Flag.getByte( MsgOption.getMsgOptions(dcs) ) );
		this.setSubFlags( MsgClass.getMsgClass(dcs).getByte() );
	}
	
	public MsgOption[] getMsgOptions() {
		return MsgOption.getMsgOptions( this.toByte() );
	}
	
	public MsgClass getMsgClass() {
		return MsgClass.getMsgClass( this.toByte() );
	}
}

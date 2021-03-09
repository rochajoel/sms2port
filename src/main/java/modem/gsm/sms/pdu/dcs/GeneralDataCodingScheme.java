package modem.gsm.sms.pdu.dcs;

import java.util.List;

import util.Enums.Flag;
import util.Enums.NamedByte;
import util.Enums.Flag.IFlag;
import util.Enums.NamedByte.IByte;


public class GeneralDataCodingScheme extends AbstractDataCodingScheme {
	public enum MessageFlag implements IFlag {
		TextCompressed			((byte) 0x20),
		MessageClassBitsEnabled	((byte) 0x10),
		;
		
		private final byte flag;

		private MessageFlag(byte flag) { this.flag = flag; }

		@Override
		public byte getFlag() { return this.flag; }
		
		private static MessageFlag[] getMessageFlag(byte dcs) {
			List<MessageFlag> sel =
				Flag.getSelected(MessageFlag.values(), (byte)(0x30 & dcs));
			
			return sel.toArray(new MessageFlag[sel.size()]);
		}
	}
	
	public enum Alphabet implements IByte {
		/** Default is 7bits */
		Default	((byte) 0x00),
		_8Bit	((byte) 0x04),
		/** 16bits */
		UCS2	((byte) 0x08),
		Reserved((byte) 0x0C),
		;
		
		private final byte type;
		
		private Alphabet(byte type) { this.type = type; }
		
		@Override
		public byte getByte() { return this.type; }
		
		public static Alphabet getAlphabet(byte dcs) {
			return NamedByte.getNamedByte(Alphabet.values(), (byte)(0x0C & dcs));
		}
	}
	
	public GeneralDataCodingScheme(MessageFlag[] flags, Alphabet alpha, MsgClass clazz) {
		super( CodingGroup.GeneralData );
		
		this.setSubFlags( Flag.getByte(flags) );
		this.setSubFlags( alpha.getByte() );
		this.setSubFlags( clazz.getByte() );
	}
	
	protected GeneralDataCodingScheme(byte dcs) {
		super( CodingGroup.getCodingGroup(dcs) );
		
		this.setSubFlags( Flag.getByte( MessageFlag.getMessageFlag(dcs) ) );
		this.setSubFlags( Alphabet.getAlphabet(dcs).getByte() );
		this.setSubFlags( MsgClass.getMsgClass(dcs).getByte() );
	}
	
	public MessageFlag[] getMessageFlags() {
		return MessageFlag.getMessageFlag( this.toByte() );
	}
	
	public Alphabet getAlphabet() {
		return Alphabet.getAlphabet( this.toByte() );
	}
	
	public MsgClass getMsgClass() {
		return MsgClass.getMsgClass( this.toByte() );
	}
}

package modem.gsm.sms.pdu.dcs;

import java.util.List;

import util.Enums.Flag;
import util.Enums.NamedByte;
import util.Enums.Flag.IFlag;
import util.Enums.NamedByte.IByte;

/**
 * Message Indication Coding Scheme.
 * 
 * @author Joel Rocha
 *
 */
public class MsgIndicationCS extends AbstractDataCodingScheme {
	public enum ActionType {
		DiscardMessage	(CodingGroup.MessageWaiting_Discard),
		SaveMessage		(CodingGroup.MessageWaiting_Store),
		SaveMessage_UCS2(CodingGroup.MessageWaiting_StoreUCS2),
		;
		
		private final CodingGroup cGroup;

		private ActionType(CodingGroup cGroup) { this.cGroup = cGroup; }
		
		protected CodingGroup getCGroup() { return cGroup; }
	}
	
	public enum IndicationFlag implements IFlag {
		Active ((byte) 0x04),
		;
		
		private final byte flag;

		private IndicationFlag(byte flag) { this.flag = flag; }
		
		@Override
		public byte getFlag() { return this.flag; }
		
		private static IndicationFlag[] getIndicationFlag(byte dcs) {
			List<IndicationFlag> selected = Flag.getSelected(IndicationFlag.values(), (byte) (dcs & 0x04));
			
			return selected.toArray(new IndicationFlag[selected.size()]);
		}
	}
	
	public enum MessageType implements IByte {
		Voicemail	((byte) 0x00),
		Fax			((byte) 0x01),
		EMail		((byte) 0x02),
		Other		((byte) 0x03),
		;
		
		private final byte type;

		private MessageType(byte type) { this.type = type; }
		
		@Override
		public byte getByte() { return this.type; }
		
		private static MessageType getMessageType(byte dcs) {
			return NamedByte.getNamedByte(MessageType.values(), (byte) (dcs & 0x03));
		}
	}

	public MsgIndicationCS(ActionType t, IndicationFlag[] f, MessageType msgType) {
		super( t.getCGroup() );
		
		for (IndicationFlag flag : f)
			this.setSubFlags( flag.getFlag() );
		
		this.setSubFlags( msgType.getByte() );
	}
	
	public IndicationFlag[] getIndicationFlags() {
		return IndicationFlag.getIndicationFlag( this.toByte() );
	}
	
	public MessageType getMessageType() {
		return MessageType.getMessageType( this.toByte() );
	}
	
	public ActionType getActionType() {
		CodingGroup cg = this.getCodingGroup();
		
		for(ActionType at : ActionType.values())
			if (at.getCGroup() == cg)
				return at;
		
		throw new IllegalStateException("Unexpected Coding Group: " + cg);
	}
}

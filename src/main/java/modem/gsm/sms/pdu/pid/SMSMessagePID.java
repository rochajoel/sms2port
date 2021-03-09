package modem.gsm.sms.pdu.pid;

import util.Enums.NamedByte;
import util.Enums.NamedByte.IByte;

public class SMSMessagePID extends AbstractProtocolID {
	public enum MessageType implements IByte {
		/** 000000	Short Message Type 0 */
		Type0				((byte) 0x00),
		/** 000001	Replace Short Message Type 1 */
		ReplaceType1		((byte) 0x01),
		/** 000010	Replace Short Message Type 2 */
		ReplaceType2		((byte) 0x02),
		/** 000011	Replace Short Message Type 3 */
		ReplaceType3		((byte) 0x03),
		/** 000100	Replace Short Message Type 4 */
		ReplaceType4		((byte) 0x04),
		/** 000101	Replace Short Message Type 5 */
		ReplaceType5		((byte) 0x05),
		/** 000110	Replace Short Message Type 6 */
		ReplaceType6		((byte) 0x06),
		/** 000111	Replace Short Message Type 7 */
		ReplaceType7		((byte) 0x07),
		/** 001000..011110	Reserved */
		/** 011111	Return Call Message */
		ReturnCallMsg		((byte) 0x1F),
		/** 100000..111100	Reserved */
		/** 111101	Mobile Equipment Data download */
		MEDataDownload		((byte) 0x3D),
		/** 111110	Mobile Equipment De-personalization Short MEssage */
		MEPersonalization	((byte) 0x3E),
		/** 111111	SIM Data download */
		SIMDataDownload		((byte) 0x3F),
		;
		
		private final byte type;

		private MessageType(byte type) { this.type = type; }

		@Override
		public byte getByte() {
			return this.type;
		}
		
		private static MessageType getMessageType(byte msgType) {
			return NamedByte.getNamedByte(MessageType.values(), (byte)(0x3F & msgType));
		}
	}
	
	public SMSMessagePID(MessageType type) {
		super(ProtocolIDType.SMSMessage);
		
		super.setFlags(type.getByte());
	}
	
	protected SMSMessagePID(byte pid) {
		super( ProtocolIDType.getProtocolIDType(pid) );
		
		MessageType.getMessageType(pid);
		
		super.setFlags(pid);
	}
	
	public MessageType getMessageType() {
		return MessageType.getMessageType( super.toByte() );
	}
}

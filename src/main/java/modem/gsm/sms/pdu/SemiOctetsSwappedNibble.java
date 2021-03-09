package modem.gsm.sms.pdu;

public class SemiOctetsSwappedNibble {
	public static byte fromSemiOctet(byte b) {
		byte ret = 0;
		
		ret += (b & 0xF0) >> 4;
		ret += (b & 0x0F) * 10;
		
		return ret;
	}
	
	public static byte[] fromSemiOctets(byte[] bs) {
		if (bs == null)
			return bs;
		
		for(int i = 0; i < bs.length; i++)
			bs[i] = fromSemiOctet( bs[i] );
		
		return bs;
	}
	
	public static byte toSemiOctet(byte b) {
		byte ret = 0;
		
		ret += (b % 10) << 4;
		ret += b / 10;
		
		return ret;
	}
	
	public static byte[] toSemiOctets(byte[] bs) {
		if (bs == null)
			return bs;
		
		for(int i = 0; i < bs.length; i++)
			bs[i] = toSemiOctet( bs[i] );
		
		return bs;
	}
}

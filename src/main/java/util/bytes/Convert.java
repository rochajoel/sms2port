package util.bytes;


public class Convert {
	public static String byte2Hex(byte[] msg) {
		String ret = "";
		
		for(byte b : msg)
			ret += byte2Hex(b);
		
		return ret.toString();
	}
	
	public static String byte2Hex(byte b) {
		String ret = (b >= 0 && b < 16) ? "0" : "";
		
		return ret + Integer.toHexString(b & 0x00FF).toUpperCase();
	}
	
	public static String printHex(byte b) { return printHex(new byte[] { b }); }
	
	public static String printHex(byte[] bArray)
	{		
		if (bArray == null)
			return "<null>";
		
		if (bArray.length == 0)
			return "<empty>";
	
		StringBuilder ret = new StringBuilder((bArray.length << 1) + bArray.length);
		
		for(int i = 0; i < bArray.length; i++) {
			ret.append(byte2Hex(bArray[i]));
			ret.append(" ");
		}
		ret.deleteCharAt(ret.length() - 1);
		
		return ret.toString();
	}
}

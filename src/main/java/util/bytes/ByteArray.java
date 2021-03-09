package util.bytes;

import java.util.Arrays;

public class ByteArray {
	public static byte[] copyOf(byte[] a) {
		return (a == null) ?
				null :
				Arrays.copyOf(a, a.length);
	}

	public static byte[] concat(byte[] a, byte[] b) {
		if (a == null)
			return copyOf(b);
		
		if (b == null)
			return copyOf(a);
		
		int aLen = a.length;
		a = Arrays.copyOf(a, aLen + b.length);
		
		for(int i = 0; i < b.length; i++)
			a[aLen+i] = b[i];
		
		return a;
	}
}

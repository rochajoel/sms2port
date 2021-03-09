package util.Enums;

import java.util.ArrayList;
import java.util.List;

import util.bytes.Convert;

public class Flag {

	/**
	 * Bit flag enum.
	 * Please avoid using 0x00 as a flag.
	 * A flag must be one (or more non-overlapping) bits set to 1.
	 * 
	 * If a class implements this interface it will be able to use
	 * the static methods on the Flag class. 
	 * 
	 * @author joel.rocha
	 *
	 */
	public interface IFlag {
		/**
		 * Get the bit flag.
		 * 
		 * @return
		 */
		public byte getFlag();
	}

	public static class UnknownBitFlagsException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3807702239739577552L;
		
		public UnknownBitFlagsException() {
			super();
		}

		public UnknownBitFlagsException(String message, Throwable cause) {
			super(message, cause);
		}

		public UnknownBitFlagsException(Throwable cause) {
			super(cause);
		}

		public UnknownBitFlagsException(String warning) {
			super();
		}

	}

	public static final byte getByte(IFlag[] flags) {
		byte result = 0;
		
		for(IFlag flag : flags)
			result |=  flag.getFlag();
		
		return result;
	}

	/**
	 * Gets the list of some of the selected flags.
	 * 
	 * Can be useful in situations were you just
	 * want to retrieve a list of some specific known bits.
	 * 
	 * This method calls its brother getSelected(values, b, <b>false</b>).
	 * It should not throw any exception.
	 * 
	 * @param <T>
	 * @param values
	 * @param b
	 * @return
	 */
	public static final <T extends IFlag> List<T> getSelected(T[] values, byte b) {
		try {
			return getSelected(values, b, false);
		} catch (UnknownBitFlagsException e) {
			//This should never happen
			throw new IllegalStateException("Something went terribly wrong!" + e.getMessage());
		}
	}
	
	/**
	 * Gets the list of selected flags.
	 * 
	 * example: if you have a byte like 0010 0010
	 * and
	 * if 00100000 is a flag named ATR_Supported
	 * and 00010000 is a flag named STFU_Flag 
	 * then it will be returned new IFlag[] { ATR_Supported }
	 * 
	 * @param <T>
	 * @param values
	 * @param b
	 * @param findAll If true it will send an UnknownBitsException
	 * 		if there are unknown bits in the byte parameter
	 * 
	 * @return
	 * @throws UnknownBitFlagsException 
	 */
	public static final <T extends IFlag> List<T> getSelected(T[] values, byte b, boolean findAll)
	throws UnknownBitFlagsException {
		if (values == null || values.length == 0)
			return null;
		
		ArrayList<T> sFlags = new ArrayList<T>();
		
		for(T f : values)
			if (isSet(f, b)) {
				b = Flag.setOff(f, b);
				sFlags.add(f);
			}
		
		if (findAll && b != 0)
			throw new UnknownBitFlagsException(getWarning(
					Thread.currentThread().getStackTrace()[1].getClassName(),
					Thread.currentThread().getStackTrace()[1].getLineNumber(),
					"Not all flags were retrieved. Flags remaining: 0x" + Convert.printHex(b) + "\n" +
					"Enum name: " + values[0].getClass().getName()));
		
		return sFlags;
	}

	private static String getWarning(String className, int lineNum, String message) {
		return 
				"--- WARNING ---\n\n" + 
				
				"Class Name: " + className + "\n" +
				"Line:       " + lineNum + "\n\n" +
				
				"Message: " + message;
	}

	public static boolean isSet(IFlag flag, byte status) {
		return ((status & flag.getFlag()) == flag.getFlag());
	}
	
	public static boolean isSet(IFlag f, IFlag[] flags) {
		for (IFlag iFlag : flags) 
			if (f.getFlag() == iFlag.getFlag())
				return true;
		
		return false;
	}

	public static byte setOn(IFlag f, byte b) {
		return (byte) (b | f.getFlag());
	}

	public static byte setOff(IFlag f, byte b) {
		return (byte) (b & ~f.getFlag());
	}
	
}
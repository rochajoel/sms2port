package util.Enums;

/**
 * Class Named Byte.
 * The methods in this class are designed to be used with enums
 * implementing the IByte interface.
 * 
 * @author joel.rocha
 *
 */
public class NamedByte {
	/**
	 * Your enum must contain one indexable byte.
	 * (This differs from the IFlag interface because the 0x00 is also a valid value)
	 * 
	 * @author joel.rocha
	 *
	 */
	public interface IByte {
		public byte getByte();
	}
	
	/**
	 * Tries to find the corresponding byte
	 * in the collection of IInt values.
	 * 
	 * Throws an IllegalArgumentException
	 * if it could not find the corresponding value
	 * 
	 * @param <T>
	 * @param values
	 * @param b
	 * @return
	 */
	public static <T extends IByte> T getNamedByte(T[] values, byte i) {
		for(T v : values)
			if (v.getByte() == i)
				return v;
		
		throw new IllegalArgumentException(
				"Could not retrieve the Name of the byte: " + i);
	}
}

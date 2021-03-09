package util.Enums;


/**
 * Class Named Int.
 * The methods in this class are designed to be used with enums
 * implementing the IInt interface.
 * 
 * @author joel.rocha
 *
 */
public class NamedInt {
	/**
	 * Your enum must contain one indexable int.
	 * (This differs from the IFlag interface because the 0x00 is also a valid value)
	 * 
	 * @author joel.rocha
	 *
	 */
	public interface IInt {
		public int getInt();
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
	public static <T extends IInt> T getNamedInt(T[] values, int i) {
		for(T v : values)
			if (v.getInt() == i)
				return v;
		
		throw new IllegalArgumentException(
				"Could not retrieve the Name of the int: " + i);
	}
}

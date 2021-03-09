package pt.rochajoel.sms2port.presentationLayer;

import java.util.ArrayList;
import java.util.Arrays;

import pt.rochajoel.sms2port.presentationLayer.AbstractAlphabetTable.AbstractAlphabetTableSymbol;
import pt.rochajoel.sms2port.presentationLayer.AbstractAlphabetTable.AlphabetCharSymbol;
import pt.rochajoel.sms2port.presentationLayer.AbstractAlphabetTable.AlphabetEscapeSymbol;
import pt.rochajoel.sms2port.presentationLayer.SMS7BitsCharset.GSM7bitsDefaultAlphabetTable.GSM7bitsDefaultAlphabetExtensionTable;
import pt.rochajoel.sms2port.presentationLayer.UserDataHeader.AbstractInformationElement;
import pt.rochajoel.sms2port.presentationLayer.UserDataHeader.NationalLanguageLockingShiftIElement;
import pt.rochajoel.sms2port.presentationLayer.UserDataHeader.NationalLanguageSingleShiftIElement;
import util.bytes.ByteArray;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;


public class SMS7BitsCharset {
	protected static class GSM7bitsDefaultAlphabetTable extends AbstractAlphabetTable {
		protected static class GSM7bitsDefaultAlphabetExtensionTable extends AbstractAlphabetTable {
			private GSM7bitsDefaultAlphabetExtensionTable() {
				super(getAlphabetSymbols(null));
			}

			/**
			 * Classes that extend the {@link GSM7bitsDefaultAlphabetExtensionTable}
			 * shall provide their own set of alphabet symbols. Symbols provided
			 * in this constructor will prevail over the base
			 * {@link GSM7bitsDefaultAlphabetExtensionTable} symbols.
			 * 
			 * @param ext
			 */
			private GSM7bitsDefaultAlphabetExtensionTable(AbstractAlphabetTableSymbol[] ext) {
				super(getAlphabetSymbols(ext));
			}

			private static AbstractAlphabetTableSymbol[] getAlphabetSymbols(AbstractAlphabetTableSymbol[] ext) {
				ArrayList<AbstractAlphabetTableSymbol> list = new ArrayList<>();
				
				//list.add(new AlphabetCharSymbol(HexBin.decode("0A")[0], "?")); //Page Break
				list.add(new AlphabetCharSymbol(HexBin.decode("14")[0], '^'));
				
				list.add(new AlphabetCharSymbol(HexBin.decode("28")[0], '{'));
				list.add(new AlphabetCharSymbol(HexBin.decode("29")[0], '}'));
				list.add(new AlphabetCharSymbol(HexBin.decode("2F")[0], '\\'));
				
				list.add(new AlphabetCharSymbol(HexBin.decode("3C")[0], '['));
				list.add(new AlphabetCharSymbol(HexBin.decode("3D")[0], '~'));
				list.add(new AlphabetCharSymbol(HexBin.decode("3E")[0], ']'));
				
				list.add(new AlphabetCharSymbol(HexBin.decode("40")[0], '|'));
				
				list.add(new AlphabetCharSymbol(HexBin.decode("65")[0], '€'));
				
				if (ext != null)
					for(AbstractAlphabetTableSymbol s : ext)
						list.add(s);
				
				return list.toArray(new AbstractAlphabetTableSymbol[list.size()]);
			} 
		}
		
		private GSM7bitsDefaultAlphabetTable() {
			super(getAlphabetSymbols());
		}
		
		private static AbstractAlphabetTableSymbol[] getAlphabetSymbols() {
			ArrayList<AbstractAlphabetTableSymbol> list = new ArrayList<>();
			
			list.add(new AlphabetEscapeSymbol(HexBin.decode("1B")[0]));
			
			list.add(new AlphabetCharSymbol(HexBin.decode("00")[0], '@'));
			list.add(new AlphabetCharSymbol(HexBin.decode("01")[0], '£'));
			list.add(new AlphabetCharSymbol(HexBin.decode("02")[0], '$'));
			list.add(new AlphabetCharSymbol(HexBin.decode("03")[0], '¥'));
			list.add(new AlphabetCharSymbol(HexBin.decode("04")[0], 'è'));
			list.add(new AlphabetCharSymbol(HexBin.decode("05")[0], 'é'));
			list.add(new AlphabetCharSymbol(HexBin.decode("06")[0], 'ù'));
			list.add(new AlphabetCharSymbol(HexBin.decode("07")[0], 'ì'));
			list.add(new AlphabetCharSymbol(HexBin.decode("08")[0], 'ò'));
			list.add(new AlphabetCharSymbol(HexBin.decode("09")[0], 'Ç'));
			list.add(new AlphabetCharSymbol(HexBin.decode("0A")[0], '\n'));
			list.add(new AlphabetCharSymbol(HexBin.decode("0B")[0], 'Ø'));
			list.add(new AlphabetCharSymbol(HexBin.decode("0C")[0], 'ø'));
			list.add(new AlphabetCharSymbol(HexBin.decode("0D")[0], '\r'));
			list.add(new AlphabetCharSymbol(HexBin.decode("0E")[0], 'Å'));
			list.add(new AlphabetCharSymbol(HexBin.decode("0F")[0], 'å'));
			
			list.add(new AlphabetCharSymbol(HexBin.decode("10")[0], 'Δ'));
			list.add(new AlphabetCharSymbol(HexBin.decode("11")[0], '_'));
			list.add(new AlphabetCharSymbol(HexBin.decode("12")[0], 'Φ'));
			list.add(new AlphabetCharSymbol(HexBin.decode("13")[0], 'Γ'));
			list.add(new AlphabetCharSymbol(HexBin.decode("14")[0], 'Λ'));
			list.add(new AlphabetCharSymbol(HexBin.decode("15")[0], 'Ω'));
			list.add(new AlphabetCharSymbol(HexBin.decode("16")[0], 'Π'));
			list.add(new AlphabetCharSymbol(HexBin.decode("17")[0], 'Ψ'));
			list.add(new AlphabetCharSymbol(HexBin.decode("18")[0], 'Σ'));
			list.add(new AlphabetCharSymbol(HexBin.decode("19")[0], 'Θ'));
			list.add(new AlphabetCharSymbol(HexBin.decode("1A")[0], 'Ξ'));

			list.add(new AlphabetCharSymbol(HexBin.decode("1C")[0], 'Æ'));
			list.add(new AlphabetCharSymbol(HexBin.decode("1D")[0], 'æ'));
			list.add(new AlphabetCharSymbol(HexBin.decode("1E")[0], 'ß'));
			list.add(new AlphabetCharSymbol(HexBin.decode("1F")[0], 'É'));
			//' '-#
			list.add(new AlphabetCharSymbol(HexBin.decode("24")[0], '¤'));
			//%-/, 0-9, :-?
			list.add(new AlphabetCharSymbol(HexBin.decode("40")[0], '¡'));
			//A-Z
			list.add(new AlphabetCharSymbol(HexBin.decode("5B")[0], 'Ä'));
			list.add(new AlphabetCharSymbol(HexBin.decode("5C")[0], 'Ö'));
			list.add(new AlphabetCharSymbol(HexBin.decode("5D")[0], 'Ñ'));
			list.add(new AlphabetCharSymbol(HexBin.decode("5E")[0], 'Ü'));
			list.add(new AlphabetCharSymbol(HexBin.decode("5F")[0], '§'));
			
			list.add(new AlphabetCharSymbol(HexBin.decode("60")[0], '¿'));
			//a-z
			list.add(new AlphabetCharSymbol(HexBin.decode("7B")[0], 'ä'));
			list.add(new AlphabetCharSymbol(HexBin.decode("7C")[0], 'ö'));
			list.add(new AlphabetCharSymbol(HexBin.decode("7D")[0], 'ñ'));
			list.add(new AlphabetCharSymbol(HexBin.decode("7E")[0], 'ü'));
			list.add(new AlphabetCharSymbol(HexBin.decode("7F")[0], 'à'));
			
			for(char i = ' '; i <= '#'; i++)
				list.add(new AlphabetCharSymbol((byte) i, i));
			
			for(char i = '%'; i <= '?'; i++)
				list.add(new AlphabetCharSymbol((byte) i, i));
			
			for(char i = 'A'; i <= 'Z'; i++)
				list.add(new AlphabetCharSymbol((byte) i, i));
			
			for(char i = 'a'; i <= 'z'; i++)
				list.add(new AlphabetCharSymbol((byte) i, i));
			
			return list.toArray(new AbstractAlphabetTableSymbol[list.size()]);
		}
	}
	/**
	 * This table extends the {@link GSM7bitsDefaultAlphabetExtensionTable}
	 * Check document 3GPP TS 23.038 (23038-a00.pdf)
	 * 
	 * @author Joel Rocha
	 */
	private static class PortugueseAlphabetSingleShiftTable extends GSM7bitsDefaultAlphabetExtensionTable {
		private PortugueseAlphabetSingleShiftTable() {
			super(getAlphabetSymbols());
		}

		private static AlphabetCharSymbol[] getAlphabetSymbols() {
			ArrayList<AlphabetCharSymbol> list = new ArrayList<>();
			
			list.add(new AlphabetCharSymbol(HexBin.decode("05")[0], 'ê'));
			list.add(new AlphabetCharSymbol(HexBin.decode("09")[0], 'ç'));
			list.add(new AlphabetCharSymbol(HexBin.decode("0B")[0], 'Ô'));
			list.add(new AlphabetCharSymbol(HexBin.decode("0C")[0], 'ô'));
//			list.add(new AlphabetCharSymbol(HexBin.decode("0D")[0], '-->')); Control Char(Reserved)
			list.add(new AlphabetCharSymbol(HexBin.decode("0E")[0], 'Á'));
			list.add(new AlphabetCharSymbol(HexBin.decode("0F")[0], 'á'));
			
			list.add(new AlphabetCharSymbol(HexBin.decode("12")[0], 'Φ'));
			list.add(new AlphabetCharSymbol(HexBin.decode("13")[0], 'Γ'));
			list.add(new AlphabetCharSymbol(HexBin.decode("15")[0], 'Ω'));
			list.add(new AlphabetCharSymbol(HexBin.decode("16")[0], 'Π'));
			list.add(new AlphabetCharSymbol(HexBin.decode("17")[0], 'Ψ'));
			list.add(new AlphabetCharSymbol(HexBin.decode("18")[0], 'Σ'));
			list.add(new AlphabetCharSymbol(HexBin.decode("19")[0], 'Θ'));
			list.add(new AlphabetCharSymbol(HexBin.decode("1F")[0], 'Ê'));
			
			list.add(new AlphabetCharSymbol(HexBin.decode("41")[0], 'À'));
			list.add(new AlphabetCharSymbol(HexBin.decode("49")[0], 'Í'));
			list.add(new AlphabetCharSymbol(HexBin.decode("4F")[0], 'Ó'));
			
			list.add(new AlphabetCharSymbol(HexBin.decode("55")[0], 'Ú'));
			list.add(new AlphabetCharSymbol(HexBin.decode("5B")[0], 'Ã'));
			list.add(new AlphabetCharSymbol(HexBin.decode("5C")[0], 'Õ'));
			
			list.add(new AlphabetCharSymbol(HexBin.decode("61")[0], 'Â'));
			list.add(new AlphabetCharSymbol(HexBin.decode("69")[0], 'í'));
			list.add(new AlphabetCharSymbol(HexBin.decode("6F")[0], 'ó'));

			list.add(new AlphabetCharSymbol(HexBin.decode("75")[0], 'ú'));
			list.add(new AlphabetCharSymbol(HexBin.decode("7B")[0], 'ã'));
			list.add(new AlphabetCharSymbol(HexBin.decode("7C")[0], 'õ'));
			list.add(new AlphabetCharSymbol(HexBin.decode("7F")[0], 'â'));
			
			return list.toArray(new AlphabetCharSymbol[list.size()]);
		}
	}
	
	public static class EncodingResult {
		private final int totalNumOfSeptets;
		private final boolean isUDHPresent;
		private final byte[] encodedText;

		private EncodingResult(int totalNumOfSeptets, boolean isUDHPresent, byte[] encodedText) {
			this.totalNumOfSeptets = totalNumOfSeptets;
			this.isUDHPresent = isUDHPresent;
			this.encodedText = encodedText;
		}

		public int getTotalNumOfSeptets()	{ return this.totalNumOfSeptets; }
		public boolean isUDHPresent()		{ return this.isUDHPresent; }
		public byte[] getEncodedText()		{ return this.encodedText; }
	}
	
	public static EncodingResult encode(String text) throws UnsupportedCharException { return encode(null, text); }
	public static EncodingResult encode(UserDataHeader udh, String text) throws UnsupportedCharException {
		AbstractAlphabetTable baseAlphabet =
				(udh != null) ? getUDHAlphabet(udh) : getAlphabet(null, null);
		byte[] unpacked = transformTo7bits(baseAlphabet, text);

		byte[] udhByteArray =
				(udh != null) ?
					udh.toBytes() :
					new byte[0];
		if (udhByteArray.length != 0)
				udhByteArray = 
					ByteArray.concat(
							new byte[] {(byte) udhByteArray.length},
							udhByteArray);
		
		if (udhByteArray.length % 7 != 0)
			unpacked = ByteArray.concat(new byte[1], unpacked);
		
		unpacked = ByteArray.concat(udhByteArray, unpacked);
		
		return new EncodingResult(
				(udhByteArray.length / 7) + unpacked.length,
				udhByteArray.length != 0,
				alignTo7bits(udhByteArray.length, unpacked));
	}
	
	private static byte[] alignTo7bits(int udhLength, byte[] txt) {
		final byte[] flags = new byte[] {
				0x01,
				0x03,
				0x07,
				0x0F,
				0x1F,
				0x3F,
				0x7F,
		};
		
		int flagsIndex = 0;
		for(int i = udhLength; i < txt.length - 1; i++) {
			flagsIndex = i % 7;
			
			txt[i] |= (txt[i+1] & flags[flagsIndex]) << (7 - flagsIndex);
			txt[i+1] >>>= flagsIndex + 1;
			
			if (flagsIndex == 6) {
				for(int j = i + 1; j < txt.length - 1; j++)
					txt[j] = txt[j + 1];
				txt = Arrays.copyOf(txt, txt.length - 1);
			}
		}
		
		return txt;
	}

	public static class UnsupportedCharException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 7936947871351044261L;
		
		private final char unsupportedChar;

		private UnsupportedCharException(char c) {
			super("" + c);
			this.unsupportedChar = c;
		}

		public char getChar() {
			return this.unsupportedChar;
		}
	}
	
	private static byte[] transformTo7bits(
			AbstractAlphabetTable table,
			String text) throws UnsupportedCharException {
		ArrayList<Byte> unpacked = new ArrayList<>();
		
		for(int i = 0; i < text.length(); i++) {
			byte[] encoded = encodeChar(table, text.charAt(i));
			
			if (encoded == null)
				throw new UnsupportedCharException(text.charAt(i));
			
			for(byte b : encoded)
				unpacked.add(b);
		}
		
		return getByteArray(unpacked);
	}

	private static byte[] encodeChar(AbstractAlphabetTable table, char c) {
		AbstractAlphabetTableSymbol symb = table.encode(c);
		
		if (symb != null)
			return new byte[] { symb.getByte() };
		
		for (AlphabetEscapeSymbol s : table.getEscapeSymbols()) {
			byte[] tail = encodeChar(s.getAlphabetExtensionTable(), c);
			
			if (tail != null)
				return ByteArray.concat(new byte[] { s.getByte() }, tail);
		}
		
		return null;
	}
	
	public static String decode(int numOfSeptets, boolean isUDHPresent, byte[] encodedText) {
		AbstractAlphabetTable alpha = (isUDHPresent) ?
			getUDHAlphabet(Arrays.copyOfRange(encodedText, 1, encodedText[0] + 1)) :
			getAlphabet(null, null);
		
		byte[] decoded = alignTo8Bits(numOfSeptets, isUDHPresent, encodedText);
		
		StringBuilder sb = new StringBuilder();
		final AlphabetEscapeSymbol baseSymbol = new AlphabetEscapeSymbol(
				HexBin.decode("00")[0], alpha);
		for(int i = 0; i < decoded.length;) {
			AbstractAlphabetTableSymbol s = baseSymbol;
			
			int j = 0;
			do {
				s = ((AlphabetEscapeSymbol)s).getAlphabetExtensionTable().decode(decoded[i+(j++)]);
			} while(s != null && s instanceof AlphabetEscapeSymbol);
			
			sb.append(s == null ? ' ' : ((AlphabetCharSymbol)s).getValue());
			i += (s == null) ? 1 : j;
		}
		
		return sb.toString();
	}

	public static AbstractAlphabetTableSymbol decodeSymbol(AbstractAlphabetTable t, byte b) {
		AbstractAlphabetTableSymbol s = t.decode(b);
		
		if (s == null)
			s = new GSM7bitsDefaultAlphabetTable().encode(' ');
		
		return s;
	}
	
	private static byte[] alignTo8Bits(int numOfSeptets, boolean isUDHPresent, byte[] encodedText) {
		final byte[] decodeFlag = new byte[] {
				(byte) 0x80,
				(byte) 0xC0,
				(byte) 0xE0,
				(byte) 0xF0,
				(byte) 0xF8,
				(byte) 0xFC,
				(byte) 0xFE,
		};
		int startingIndex = (isUDHPresent) ? encodedText[0] + 1 : 0;

		ArrayList<Byte> decoded = new ArrayList<>();

		byte[] buffer = new byte[2];
		int i = startingIndex;
		for(; i < encodedText.length; i++) {
			int flagIndex = i % 7;
			buffer[0] = buffer[1];
			
			if (i != startingIndex && flagIndex == 0) {
				decoded.add(buffer[0]);
				buffer[0] = 0;
			}
			
			buffer[0] |= (byte) (encodedText[i] & ~decodeFlag[flagIndex]) << flagIndex;
			buffer[1] = (byte) (((encodedText[i] & decodeFlag[flagIndex]) >>> (7 - flagIndex)) & ~decodeFlag[6 - flagIndex]);
			
			decoded.add(buffer[0]);
		}

		if (i != 0 && i % 7 == 0 && decoded.size() < (numOfSeptets - (startingIndex / 7)))
			decoded.add(buffer[1]);
		
		if (startingIndex % 7 != 0)
			decoded.remove(0);
		
		return getByteArray(decoded);
	}
	private static byte[] getByteArray(ArrayList<Byte> decoded) {
		byte[] ret = new byte[decoded.size()];
		
		for(int i = 0; i < decoded.size(); i++)
			ret[i] = decoded.get(i);
		
		return ret;
	}
	private static AbstractAlphabetTable getUDHAlphabet(byte[] udh) {
		return getUDHAlphabet(new UserDataHeader(udh));
	}
	private static AbstractAlphabetTable getUDHAlphabet(UserDataHeader udhObj) {
		NationalLanguageLockingShiftIElement lockingShift = null;
		NationalLanguageSingleShiftIElement singleShift = null;
		for (AbstractInformationElement ie : udhObj.getInformationElements()) {
			if (ie instanceof NationalLanguageLockingShiftIElement)
				lockingShift = (NationalLanguageLockingShiftIElement) ie;
			
			if (ie instanceof NationalLanguageSingleShiftIElement)
				singleShift = (NationalLanguageSingleShiftIElement) ie;
		}

		return getAlphabet(lockingShift, singleShift);
	}
	private static AbstractAlphabetTable getAlphabet(
			NationalLanguageLockingShiftIElement lockingShift,
			NationalLanguageSingleShiftIElement singleShift) {
		AbstractAlphabetTable baseAlpha = getLockingShiftAlphabet(lockingShift);
		AbstractAlphabetTable extAlpha = getSingleShiftAlphabet(singleShift);

		baseAlpha.setEscapeSymbols( new AlphabetEscapeSymbol[] {
				new AlphabetEscapeSymbol(HexBin.decode("1B")[0], extAlpha),
		});
		
		return baseAlpha;
	}
	
	private static AbstractAlphabetTable getLockingShiftAlphabet(
			NationalLanguageLockingShiftIElement lockingShift) {
		if (lockingShift != null) {
			switch (lockingShift.getLanguage()) {
				case Portuguese:
					//TODO: Portuguese Locking Shift table
				default:
					break;
			}
		}
		
		return new GSM7bitsDefaultAlphabetTable();
	}
	
	private static AbstractAlphabetTable getSingleShiftAlphabet(
			NationalLanguageSingleShiftIElement singleShift) {
		if (singleShift != null) {
			switch (singleShift.getLanguage()) {
				case Portuguese:
					return new PortugueseAlphabetSingleShiftTable();
				default:
					break;
			}
		}
		
		return new GSM7bitsDefaultAlphabetExtensionTable();
	}
	
	public static String filterTo7Bits(String text) {
		if (text.length() == 0)
			return text;
		
		for(int i = 0; i < text.length(); i++)
			try {
				encode(text);
				
				return text;
			} catch (UnsupportedCharException e) {
				char ch = e.getChar();
				switch (ch) {
					case 'Á': text = text.replace(ch, 'Ä'); break;
					case 'À': text = text.replace(ch, 'Ä'); break;
					case 'á': text = text.replace(ch, 'à'); break;
					case 'â': text = text.replace(ch, 'ä'); break;
					case 'ã': text = text.replace(ch, 'ä'); break;
			
					case 'ê': text = text.replace(ch, 'e'); break;
					case 'ë': text = text.replace(ch, 'e'); break;
					
					case 'í': text = text.replace(ch, 'ì'); break;
					case 'Í': text = text.replace(ch, 'I'); break;
					
					case 'ó': text = text.replace(ch, 'ò'); break;
					case 'õ': text = text.replace(ch, 'ö'); break;
					case 'ô': text = text.replace(ch, 'ö'); break;
					
					case 'ú': text = text.replace(ch, 'ü'); break;
					
					case 'ç': text = text.replace(ch, 'Ç'); break;
					
					case '«': text = text.replace(ch, '<'); break;
					case '»': text = text.replace(ch, '>'); break;
					
					case '“': text = text.replace(ch, '"'); break;
					case '”': text = text.replace(ch, '"'); break;
					
					case '’': text = text.replace(ch, '\''); break;
					case '‘': text = text.replace(ch, '\''); break;
					
					case '`': text = text.replace(ch, '\''); break;
					case '´': text = text.replace(ch, '\''); break;
					
					case 'ª': text = text.replace(ch, 'a'); break;
					case 'º': text = text.replace(ch, 'o'); break;
					
					case '…': text = text.replace(ch, '_'); break;
					case '\u00a0': text = text.replace(ch, ' '); break;
					case '\u2013': text = text.replace(ch, '-'); break;
					default:
						System.out.println("Unknown mapping for: " + ch);
						text = text.replace(ch, ' ');
				}
			}
		
		throw new IllegalStateException(
				"Why am i trying to replace more chars than the number " +
				"of chars in the original message?");
	}
}

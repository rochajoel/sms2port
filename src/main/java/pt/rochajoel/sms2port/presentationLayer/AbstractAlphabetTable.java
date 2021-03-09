package pt.rochajoel.sms2port.presentationLayer;

import java.util.ArrayList;
import java.util.Hashtable;

import util.bytes.Convert;

abstract class AbstractAlphabetTable {
	/**
	 * All alphabet symbols must have a key in order to be found in the
	 * alphabet table.
	 * 
	 * @author Joel Rocha
	 */
	public static abstract class AbstractAlphabetTableSymbol {
		private final byte symbolKey;

		private AbstractAlphabetTableSymbol(byte b) {
			this.symbolKey = b;
		}

		public byte getByte() { return this.symbolKey; }
	}

	/**
	 * Most of the symbols represent Characters, which have a String value
	 * associated.
	 * 
	 * @author Joel Rocha
	 */
	public static class AlphabetCharSymbol extends AbstractAlphabetTableSymbol {
		private final char symbolValue;
		
		protected AlphabetCharSymbol(byte b, char value) {
			super(b);
			this.symbolValue = value;
		}
		
		public char getValue() { return this.symbolValue; }
	}

	/**
	 * At least one symbol per table represent an 'escape' to some other table
	 * 
	 * @author Joel Rocha
	 */
	public static class AlphabetEscapeSymbol extends AbstractAlphabetTableSymbol {
		private final AbstractAlphabetTable table;

		protected AlphabetEscapeSymbol(byte b) {
			this(b, null);
		}

		protected AlphabetEscapeSymbol(byte b, AbstractAlphabetTable table) {
			super(b);
			
			this.table = table;
		}
		
		public AbstractAlphabetTable getAlphabetExtensionTable() { return this.table; }
	}
	
	private final Hashtable<Byte, AbstractAlphabetTableSymbol> table = new Hashtable<>();

	/**
	 * In it's base unit, an AbstractAlphabetTable is an empty collection of
	 * {@link AbstractAlphabetTableSymbol} represented by {@link AlphabetCharSymbol}
	 * and {@link AlphabetEscapeSymbol} which might or might not contain an
	 * escape to another {@link AbstractAlphabetTable}.
	 * 
	 * Caution: when implementing Alphabets remember not to set more than one
	 * symbols to the same key number (byte number) otherwise only the last
	 * symbol of the repeated element will prevail.
	 * 
	 * @param symbols
	 */
	protected AbstractAlphabetTable(AbstractAlphabetTableSymbol[] symbols) {
		for(AbstractAlphabetTableSymbol s : symbols)
			this.table.put(s.getByte(), s);
	}

	/**
	 * Caution remember that if you enter two symbols with the same key, only
	 * the last one will be in effect.
	 * 
	 * @param symbols
	 */
	public void setEscapeSymbols(AlphabetEscapeSymbol[] symbols) {
		for(AlphabetEscapeSymbol es : symbols) {
			AbstractAlphabetTableSymbol prevSymbol = this.table.get(es.getByte());
			
			if (prevSymbol != null && prevSymbol instanceof AlphabetCharSymbol)
				throw new IllegalArgumentException(
						"Check your specification!\n" +
						"Symbol " + Convert.byte2Hex(es.getByte()) + " is already mapped to a Char");
			
			this.table.put(es.getByte(), es);
		}
	}
	
	/**
	 * Returns null if no mapping is found.
	 * 
	 * @param c
	 * @return
	 */
	public AbstractAlphabetTableSymbol encode(char c) {
		for(AbstractAlphabetTableSymbol s : table.values())
			if (s instanceof AlphabetCharSymbol)
				if (((AlphabetCharSymbol) s).getValue() == c)
					return s;
		
		return null;
	}

	/**
	 * Returns null if no mapping is found
	 * 
	 * @param b
	 * @return
	 */
	public AbstractAlphabetTableSymbol decode(byte b) {
		return table.get(b);
	}
	
	public AlphabetEscapeSymbol[] getEscapeSymbols() {
		ArrayList<AlphabetEscapeSymbol> list = new ArrayList<>();
		
		for(AbstractAlphabetTableSymbol s : table.values())
			if (s instanceof AlphabetEscapeSymbol)
				list.add((AlphabetEscapeSymbol) s);
		
		return list.toArray(new AlphabetEscapeSymbol[list.size()]);
	}
}

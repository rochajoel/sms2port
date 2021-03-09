package pt.rochajoel.sms2port.presentationLayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import pt.rochajoel.sms2port.presentationLayer.AbstractAlphabetTable.AbstractAlphabetTableSymbol;
import pt.rochajoel.sms2port.presentationLayer.AbstractAlphabetTable.AlphabetCharSymbol;
import pt.rochajoel.sms2port.presentationLayer.AbstractAlphabetTable.AlphabetEscapeSymbol;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

public class AbstractAlphabetTableTest {
	
	private static class AlphabetTableForTest extends AbstractAlphabetTable {
		private AlphabetTableForTest() {
			super(getAlphabetSymbols());
		}
		
		private static AbstractAlphabetTableSymbol[] getAlphabetSymbols() {
			ArrayList<AbstractAlphabetTableSymbol> list = new ArrayList<>();
			
			for(char i = 'a'; i <= 'z'; i++)
				list.add(new AlphabetCharSymbol((byte) i, i));
			
			for(char i = 'A'; i <= 'Z'; i++)
				list.add(new AlphabetCharSymbol((byte) i, i));

			for(char i = ' '; i <= '#'; i++)
				list.add(new AlphabetCharSymbol((byte) i, i));
			
			for(char i = '%'; i <= '?'; i++)
				list.add(new AlphabetCharSymbol((byte) i, i));
			
			list.add(new AlphabetEscapeSymbol(HexBin.decode("1B")[0]));
			
			return list.toArray(new AbstractAlphabetTableSymbol[list.size()]);
		}
	}

	@Test
	public void testInstanciation() {
		new AlphabetTableForTest();
	}

	@Test
	public void testManipulationBasic() {
		String expected = "hellohello";
		AlphabetTableForTest alphabet = new AlphabetTableForTest();
		
		for(byte i : expected.getBytes())
			Assert.assertEquals(i, alphabet.decode(i).getByte());
		
		for(int i = 0; i < expected.length(); i++)
			Assert.assertEquals(
					expected.charAt(i),
					((AlphabetCharSymbol)alphabet.encode(expected.charAt(i))).getValue()); 
	}
	
	@Test
	public void testManipulationWithEscapeChars() {
		AlphabetTableForTest alphabet = new AlphabetTableForTest();
		byte[] actual = HexBin.decode("1B3C1B3E");
		
		for(int i = 0; i < actual.length; i++) {
			AbstractAlphabetTableSymbol symb = alphabet.decode(actual[i]);
			if ((i & 0x01) == 0)
				assertTrue(symb instanceof AlphabetEscapeSymbol);
			else {
				assertTrue(symb instanceof AlphabetCharSymbol);
				assertEquals(
						i == 1 ? '<' : '>', 
						((AlphabetCharSymbol)symb).getValue());
			}
		}
	}
}

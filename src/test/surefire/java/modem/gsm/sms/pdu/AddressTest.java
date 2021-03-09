package modem.gsm.sms.pdu;

import static org.junit.Assert.assertEquals;
import modem.gsm.sms.pdu.Address.TypeOfAddress.TypeOfNumber;

import org.junit.Assert;
import org.junit.Test;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

public class AddressTest {

	@Test
	public void testAddressString1() {
		String expNumber = "+27381000015";
		Address addr = new Address(expNumber);
		String actNumber = addr.getNumber();

		byte[] actBytes = addr.toBytes();
		byte[] expBytes = new byte[] {
				(byte) 0x91, 0x72, (byte)0x83, 0x01, 0x00, 0x10, (byte) 0xF5 };
		for(int i = 0; i < expBytes.length; i++)
			assertEquals("" + i, expBytes[i], actBytes[i]);
		
		//91 72 83 01 00 10 F5
		Assert.assertEquals(expNumber, actNumber);
	}

	@Test
	public void testAddressString2() {
		String expNumber = "969416647";
		Address addr = new Address(expNumber);
		String actNumber = addr.getNumber();

		byte[] actBytes = addr.toBytes();
		byte[] expBytes = HexBin.decode("A169496146F7");
	
		for(int i = 0; i < expBytes.length; i++)
			assertEquals("" + i, expBytes[i], actBytes[i]);
		
		//91 72 83 01 00 10 F5
		Assert.assertEquals(expNumber, actNumber);
	}
	
	@Test
	public void testUnknownAddress() {
		String expNumber = "";
		Address addr = new Address(expNumber);
		String actNumber = addr.getNumber();

		byte[] actBytes = addr.toBytes();
		byte[] expBytes = HexBin.decode("80");
	
		for(int i = 0; i < expBytes.length; i++)
			assertEquals("" + i, expBytes[i], actBytes[i]);
		
		//""
		Assert.assertEquals(expNumber, actNumber);
	}	
	
	@Test
	public void testAlphanumericAddress1() {
		String expAddr = "tmn";
		Address actAddr = new Address(HexBin.decode("D0F4B61B"));
		
		assertEquals(TypeOfNumber.AlphaNumeric, actAddr.getTOA().getTON());
		assertEquals(expAddr, actAddr.getNumber());
	}
	
	@Test
	public void testAlphanumericAddress2() {
		String expAddr = "ponto t";
		Address actAddr = new Address(HexBin.decode("D0F0B79BFE06D101"));
		
		assertEquals(TypeOfNumber.AlphaNumeric, actAddr.getTOA().getTON());
		assertEquals(expAddr, actAddr.getNumber());
	}
	
	@Test
	public void testAlphanumericAddress3() {
		String expAddr = "Ponto t";
		Address actAddr = new Address(HexBin.decode("D0D0B79BFE06D101"));
		
		assertEquals(TypeOfNumber.AlphaNumeric, actAddr.getTOA().getTON());
		assertEquals(expAddr, actAddr.getNumber());
	}
}

package modem.gsm.sms.pdu;

import static org.junit.Assert.assertEquals;
import modem.gsm.sms.pdu.SemiOctetsSwappedNibble;

import org.junit.Test;


public class SemiOctetsSwappedNibbleTest {
	@Test
	public void testFromSemiOctet() {
		byte[] date = new byte[] { (byte)0x99, 0x20, 0x21, 0x50, 0x75, 0x03, 0x21 };
		byte[] expt = new byte[] { 99, 2, 12, 5, 57, 30, 12 };
		
		for(int i = 0; i < date.length; i++)
			assertEquals(expt[i], SemiOctetsSwappedNibble.fromSemiOctet( date[i] ));
	}
	
	@Test
	public void testToSemiOctet() {
		byte[] date = new byte[] { 99, 2, 12, 5, 57, 30, 12 };
		byte[] expt = new byte[] { (byte)0x99, 0x20, 0x21, 0x50, 0x75, 0x03, 0x21 };
		
		for(int i = 0; i < date.length; i++)
			assertEquals("" + i, expt[i], SemiOctetsSwappedNibble.toSemiOctet( date[i] ));
	}
}

package modem.gsm.sms.pdu.dcs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import modem.gsm.sms.pdu.dcs.AbstractDataCodingScheme.MsgClass;
import modem.gsm.sms.pdu.dcs.MsgClassCS.MsgOption;

import org.junit.Test;

import util.Enums.Flag;

public class MsgClassCSTest {

	@Test
	public void testGetMsgOptions() {
		MsgClass expClass = MsgClass.Alert;
		MsgClassCS dcs = new MsgClassCS(
				new MsgOption[] { MsgOption._8BitData }, expClass);
		
		assertTrue( Flag.isSet(MsgOption._8BitData, dcs.getMsgOptions()) );
		assertEquals(expClass, dcs.getMsgClass());
	}

	@Test
	public void testGetMsgClass() {
		MsgClass expClass = MsgClass.SIM_Specific;
		MsgClassCS dcs = new MsgClassCS(
				new MsgOption[] { MsgOption._8BitData }, expClass);
		
		assertTrue( Flag.isSet(MsgOption._8BitData, dcs.getMsgOptions()) );
		assertEquals(expClass, dcs.getMsgClass());
	}

}

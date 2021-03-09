package modem.gsm.sms.pdu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import modem.gsm.sms.pdu.SMSStatusReport.DeliveredStatus;
import modem.gsm.sms.pdu.SMSStatusReport.DeliveredStatus.DeliveredReason;
import modem.gsm.sms.pdu.SMSStatusReport.FailedStatus;
import modem.gsm.sms.pdu.SMSStatusReport.FailedStatus.FailedReason;
import modem.gsm.sms.pdu.SMSStatusReport.PendingStatus;
import modem.gsm.sms.pdu.SMSStatusReport.PendingStatus.PendingReason;
import modem.gsm.sms.pdu.SMSStatusReport.SMSStatusReportHeader.SMSStatusReportOption;

import org.junit.Test;

import util.Enums.Flag;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

public class SMSStatusReportTest {

	@Test
	public void test_Delivered() {
		final byte[] expPDU = HexBin.decode(
				"07915391131213F4" + //SMSCenter Address
				"0603" +
				"0C91539133131316" + //Destination Address
				"21803061201540" + //Sent Date
				"21803061206540" + //Delivered Date
				"00" //Protocol Identifier (Mandatory)
				);
		SMSStatusReport sr = new SMSStatusReport( expPDU );
		
		assertEquals("+35193121314", sr.getSMSCAddr().getNumber());
		assertEquals(3, sr.getModemSentId());
		assertEquals("+351933313161", sr.getRecipientAddr().getNumber());
		assertTrue(sr.getStatus() instanceof DeliveredStatus);
		assertEquals(DeliveredReason.SME_Delivered, ((DeliveredStatus)sr.getStatus()).getReason());
		assertTrue(Arrays.equals(expPDU, sr.getPDU()));
	}
	
	@Test
	public void test_DeliveredSCSpecific() {
		SMSStatusReport sr = new SMSStatusReport(
				HexBin.decode(
						"07915391131213F4" + //SMSCenter Address
						"0603" +
						"0C91539133131316" + //Destination Address
						"21803061201540" + //Sent Date
						"21803061206540" + //Delivered Date
						"10" //Protocol Identifier (Mandatory)
						));
		
		assertEquals("+35193121314", sr.getSMSCAddr().getNumber());
		assertTrue(Flag.isSet(SMSStatusReportOption.NoMoreMessages, sr.getSMSStatusReportHeader().getOptions()));
		assertEquals(3, sr.getModemSentId());
		assertEquals("+351933313161", sr.getRecipientAddr().getNumber());
		assertTrue(sr.getStatus() instanceof DeliveredStatus);
		assertEquals(DeliveredReason.SC_Specific, ((DeliveredStatus)sr.getStatus()).getReason());
	}

	@Test
	public void test_Pending() {
		SMSStatusReport sr = new SMSStatusReport(
				HexBin.decode(
						"07915391131213F4" + //SMSCenter Address
						"0603" +
						"0C91539133131316" + //Destination Address
						"21803061201540" + //Sent Date
						"21803061206540" + //Delivered Date
						"20" //Status Report
						));
		
		assertEquals("+35193121314", sr.getSMSCAddr().getNumber());
		assertEquals("+351933313161", sr.getRecipientAddr().getNumber());
		assertTrue(sr.getStatus() instanceof PendingStatus);
		assertEquals(PendingReason.Congestion, ((PendingStatus)sr.getStatus()).getReason());
		assertFalse(((PendingStatus)sr.getStatus()).isPermanent());
	}
	
	@Test
	public void test_PendingPermanently() {
		SMSStatusReport sr = new SMSStatusReport(
				HexBin.decode(
						"07915391131213F4" + //SMSCenter Address
						"0603" +
						"0C91539133131316" + //Destination Address
						"21803061201540" + //Sent Date
						"21803061206540" + //Delivered Date
						"60" //Status Report
						));
		
		assertEquals("+35193121314", sr.getSMSCAddr().getNumber());
		assertEquals("+351933313161", sr.getRecipientAddr().getNumber());
		assertTrue(sr.getStatus() instanceof PendingStatus);
		assertEquals(PendingReason.Congestion, ((PendingStatus)sr.getStatus()).getReason());
		assertTrue(((PendingStatus)sr.getStatus()).isPermanent());
	}
	
	@Test
	public void test_Failed() {
		SMSStatusReport sr = new SMSStatusReport(
				HexBin.decode(
						"07915391131213F4" + //SMSCenter Address
						"0603" +
						"0C91539133131316" + //Destination Address
						"21803061201540" + //Sent Date
						"21803061206540" + //Delivered Date
						"43" //Status Report
						));
		
		assertEquals("+35193121314", sr.getSMSCAddr().getNumber());
		assertEquals("+351933313161", sr.getRecipientAddr().getNumber());
		assertTrue(sr.getStatus() instanceof FailedStatus);
		assertEquals(FailedReason.NotObtainable, ((FailedStatus)sr.getStatus()).getReason());
	}
}

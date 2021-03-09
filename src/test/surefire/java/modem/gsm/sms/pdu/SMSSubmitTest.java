package modem.gsm.sms.pdu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import modem.gsm.sms.pdu.SMSSubmit.RelativeValidationPeriod;
import modem.gsm.sms.pdu.SMSSubmit.RelativeValidationPeriod.TimePeriod;
import modem.gsm.sms.pdu.SMSSubmit.SMSSubmitHeader;
import modem.gsm.sms.pdu.SMSSubmit.SMSSubmitHeader.SMSSubmitOption;
import modem.gsm.sms.pdu.SMSSubmit.Text;
import modem.gsm.sms.pdu.dcs.AbstractDataCodingScheme.MsgClass;
import modem.gsm.sms.pdu.dcs.GeneralDataCodingScheme;
import modem.gsm.sms.pdu.dcs.GeneralDataCodingScheme.Alphabet;
import modem.gsm.sms.pdu.dcs.GeneralDataCodingScheme.MessageFlag;
import modem.gsm.sms.pdu.pid.TelematicDevicePID;
import modem.gsm.sms.pdu.pid.TelematicDevicePID.DeviceType;
import modemConfig.ModemProp;

import org.junit.Test;

import pt.rochajoel.sms2port.presentationLayer.SMS7BitsCharset;
import util.Out;
import util.bytes.Convert;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

public class SMSSubmitTest {
	@Test
	public void testSMSSubmit() throws Exception {
		SMSSubmit smsSubmit = new SMSSubmit(
				ModemProp.smscenter,
				new SMSSubmitHeader(new SMSSubmitOption[0]),
				ModemProp.toME,
				new TelematicDevicePID(DeviceType.Implicit),
				new GeneralDataCodingScheme(new MessageFlag[0], Alphabet.Default, MsgClass.Alert),
				new RelativeValidationPeriod(TimePeriod.HalfHour, 47),
				"Afinal foi falso alarme, o tempo medio de envio de sms's continua a ser de 3.5segundos. Abraco."
				);
		
		byte[] pdu = smsSubmit.toByte();
		String actual = new String( Convert.byte2Hex(pdu) );
		Out.println( actual );
		Out.println( smsSubmit.getPDUSize() + "");

		assertTrue(actual.endsWith(
				"5F4173DA1D6683CCEF34C81C66CFDFA0" +
				"303B2C6F9759A037885E6EC3DFA07699" +
				"9C7E83C86550D96D4FBF41E43268DE9E" +
				"9FE6A0F1DB4D4FBBEB615018342FCB41" +
				"E43268E6AACDCBE7BA9BFC9EBB4041B1" +
				"3C3C7EBB00"));
	}
	
	@Test
	public void testSMSSubmit_160chars_withSpecialChars() throws Exception {
		GeneralDataCodingScheme dcs = new GeneralDataCodingScheme(new MessageFlag[0], Alphabet.Default, MsgClass.Alert);
		String msg = SMS7BitsCharset.filterTo7Bits(
				"DD|Casa parcialmente soterrada ap�s aluimento de terras|" +
				"Um aluimento de terras deixou hoje uma casa parcialmente" +
				" soterrada em Santo Ant�nio, no concelho de Portim�o");
		
		int originalLength = msg.length();
		if (originalLength > 160)
			msg = msg.substring(0, 160);
		
		Text text = SMSSubmit.Text.getInstance(dcs, msg);
		while(text.getText().length > 140)
			text = SMSSubmit.Text.getInstance(dcs, msg = msg.substring(0, msg.length() - 1));
		
		if (originalLength > 160)
			msg = msg.substring(0, msg.length() - 3) + "...";
		
		Out.println(msg.length() + ":" + msg);
		Out.println("Encoded length: " + text.getText().length);
		SMSSubmit smsSubmit = new SMSSubmit(
				ModemProp.smscenter,
				new SMSSubmitHeader(new SMSSubmitOption[0]),
				ModemProp.toME,
				new TelematicDevicePID(DeviceType.Implicit),
				dcs,
				new RelativeValidationPeriod(TimePeriod.HalfHour, 47),
				msg);
		
		byte[] pdu = smsSubmit.toByte();
		String actual = new String( Convert.byte2Hex(pdu) );
		Out.println( actual );
		Out.println( smsSubmit.getPDUSize() + "");
	}
	
	@Test
	public void testSMSSubmitSpecialChars() throws Exception {
		String txt = "ããããã!ççç?==\"!!áááá";
		String cTxt = SMS7BitsCharset.filterTo7Bits(txt);
		SMSSubmit smsSubmit = new SMSSubmit(
				ModemProp.smscenter,
				new SMSSubmitHeader(new SMSSubmitOption[0]),
				ModemProp.toME,
				new TelematicDevicePID(DeviceType.Implicit),
				new GeneralDataCodingScheme(new MessageFlag[0], Alphabet.Default, MsgClass.Alert),
				new RelativeValidationPeriod(TimePeriod.HalfHour, 47),
				cTxt
				);
		
		byte[] pdu = smsSubmit.toByte();
		String actual = new String( Convert.byte2Hex(pdu) );
		
		Out.println( actual );
		Out.println( smsSubmit.getPDUSize() + "");

		assertTrue(actual.endsWith(
				"FBFD7EBF0F2512895FAF270A85FEFFFF1F"));
	}
	
	//@Test --> Fails on Linux
	public void testSMSSubmitJAVAPort35328() throws Exception {
		SMSSubmit sms2port =
			new SMSSubmit(
				ModemProp.smscenter,
				new SMSSubmitHeader(new SMSSubmitOption[] { SMSSubmitOption.UserDataHeaderPresent }),
				ModemProp.toME,
				new TelematicDevicePID(DeviceType.Implicit),
				new GeneralDataCodingScheme(
						new MessageFlag[] {
								MessageFlag.MessageClassBitsEnabled },
						Alphabet._8Bit,
						MsgClass.ME_Specific),
				new RelativeValidationPeriod(TimePeriod.HalfHour, 47),
				new String( HexBin.decode( "0605048A008A00000A5365727669636550617900064E204175746F000531333334310000012872A2EE0000000000000531313035360009303133383830323639" ) )
				);
		
		byte[] pdu = sms2port.toByte();
		String actual = new String( Convert.byte2Hex(pdu) );
		Out.println( actual );
		Out.println( sms2port.getPDUSize() + "");

		//Note: This is sent from port 35328 to 35328
		//35328 = 8A00h
		assertTrue(actual.endsWith(
				"40" + ("06" + ("05" + ("04" + "8A008A00"))) +
				"000A536572766963" +
				"6550617900064E204175746F00053133" +
				"3334310000012872A2EE000000000000" +
				"05313130353600093031333838303236" +
				"39"));
	}
	
	@Test
	public void testRelativeTimePeriod() {
		for(int i = 0; i < 144; i++)
			assertEquals(
					(byte)i,
					new RelativeValidationPeriod(
							TimePeriod.FiveMinutes, i+1).toBytes()[0]);
		
		int j = 1;
		for(int i = 5; i < 144; i += 6)
			assertEquals(
					(byte)i,
					new RelativeValidationPeriod(
							TimePeriod.HalfHour, j++).toBytes()[0]);
		for(int i = 144; i < 168; i++)
			assertEquals(
					(byte)i,
					new RelativeValidationPeriod(
							TimePeriod.HalfHour, j++).toBytes()[0]);
		
		j = 1;
		for(int i = 167; i < 197; i++)
			assertEquals(
					(byte)i,
					new RelativeValidationPeriod(
							TimePeriod.Day, j++).toBytes()[0]);

		j = 1;
		for(int i = 173; i < 195; i += 7)
			assertEquals(
					(byte)i,
					new RelativeValidationPeriod(
							TimePeriod.Week, j++).toBytes()[0]);
		j = 5;
		for(int i = 197; i < 256; i++)
			assertEquals(
					(byte)i,
					new RelativeValidationPeriod(
							TimePeriod.Week, j++).toBytes()[0]);
	}
}

package modem.gsm.sms.pdu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Calendar;

import modem.gsm.sms.pdu.Address.TypeOfAddress.NumberingPlanId;
import modem.gsm.sms.pdu.Address.TypeOfAddress.TypeOfNumber;
import modem.gsm.sms.pdu.SMSDeliver.SMSDeliverHeader.SMSDeliverOption;
import modem.gsm.sms.pdu.dcs.GeneralDataCodingScheme;
import modem.gsm.sms.pdu.dcs.GeneralDataCodingScheme.Alphabet;

import org.junit.Assert;
import org.junit.Test;

import pt.rochajoel.sms2port.presentationLayer.SMS7BitsCharset;
import pt.rochajoel.sms2port.presentationLayer.SMS7BitsCharset.UnsupportedCharException;
import pt.rochajoel.sms2port.presentationLayer.UserDataHeader;
import pt.rochajoel.sms2port.presentationLayer.UserDataHeader.AbstractInformationElement;
import pt.rochajoel.sms2port.presentationLayer.UserDataHeader.ConcatenatedSMSIElement;
import util.Enums.Flag;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;


public class SMSDeliverTest {
	@Test
	public void testSMSDeliver() {
		SMSDeliver sms = new SMSDeliver(
				HexBin.decode(
						"0791539126010000" +
						"24" +
						"0C91539196146674" +
						"00" +
						"00" +
						"01508032829040" +
						"0F" +
						"09D01F54001D4008501F6400F901"
						)
				);
		
		Assert.assertEquals("+351962100000", sms.getSMSCAddr().getNumber());
		Assert.assertEquals("+351969416647", sms.getSenderAddr().getNumber());
		
		sms.getSMSHeader().getOptions();
		
		Calendar expCal = Calendar.getInstance();
		expCal.set(Calendar.YEAR, 10);
		expCal.set(Calendar.MONTH, Calendar.MAY);
		expCal.set(Calendar.DAY_OF_MONTH, 8);
		expCal.set(Calendar.HOUR_OF_DAY, 23);
		expCal.set(Calendar.MINUTE, 28);
		expCal.set(Calendar.SECOND, 9);
		expCal.set(Calendar.MILLISECOND, 0);
		expCal.set(Calendar.ZONE_OFFSET, new ServiceCenterTimeStamp.TimezoneCalendarToTimestampConversion().toCalendar((byte) 0x40));
		
		Assert.assertEquals(expCal, sms.getSCTS().getCalendar());
		Assert.assertEquals(15, sms.getNumberOfSymbols());
		
		assertEquals(
				"Ç à é ì ò ñ ù ü",
				new String(
					SMS7BitsCharset.decode(
							sms.getNumberOfSymbols(), false,
							sms.getPayload() )));
	}
	
	@Test
	public void testSMSDeliverNetworkSpecialSMS1() {
		SMSDeliver sms = new SMSDeliver(
				HexBin.decode(
						"0791539126010000" + //SMS Center Address
						"04" + //SMS Header
						"04856169" + //Sender number
						"39" + //PID
						"F0" + //DCS
						"017021807141405C4F" + //SMS Center time stamp
						"D0" + //Number of symbols
						//Message -->
						"BC5C07CDC36CF21B5406A5DDE6B23CFD" +
						"9683C2A062550A9AB940C3B05C5E3ED7" +
						"CBA037685EAE83E86D37081E968741E3" +
						"B79B9E76D7C3725018640EB3C3725018" +
						"647FBBE96172D9050AD2CB2075D805") );
		
		assertEquals("1696", sms.getSenderAddr().getNumber());
		assertEquals(
				"O seu saldo e inferior a EUR 3. Carregue o seu tmn para continuar a falar a vontade. Ate ja.",
				new String(
					SMS7BitsCharset.decode(
							sms.getNumberOfSymbols(), false,
							sms.getPayload() )));
	}
	
	@Test
	public void testSMSDeliverNetworkSpecialSMS2() {
		SMSDeliver sms = new SMSDeliver(
				HexBin.decode(
						"0791539126010000" +
						"04" +
						"0BD0399B4C46A301" +
						"39" +
						"00" +
						"017021902144403CC3" +
						"B0" +
						"5C5E3E87DB6537FD0D2A9BCB637A3D4C" +
						"7EEB40C5AA1424ABB1603017681A6693" +
						"DF3A50B12A05C96E2C9CCD050AD2CB20" +
						"75D805"));
		
		Assert.assertEquals(
				"Carregamento efectuado: EUR 25,00. Saldo: EUR 27,86. Ate ja.",
				SMS7BitsCharset.decode(
						sms.getNumberOfSymbols(), false,
						sms.getPayload() ) );
	}
	
	@Test
	public void testSMSDeliverNetworkSpecialSMS3() {
//		MessageId: 2012-07-07 16:01:49.0 962244
		SMSDeliver sms = new SMSDeliver(
				HexBin.decode(
					"0791539126010000440BD0399B4C46A3" +
					"0139002170706110944043050003FF01" +
					"018661B9BC7C0EB7CB6EFA1B543697C7" +
					"F47A98FCD6818A5529282663D5602ED0" +
					"34CC26BF75A062550A8AC95835980B14" +
					"A49741EAB00B"));
		
		boolean isUDHPresent = Flag.isSet(
				SMSDeliverOption.UserDataHeaderPresent,
				sms.getSMSHeader().getOptions());
		assertTrue( isUDHPresent );
		
		byte[] payload = sms.getPayload();
		UserDataHeader udh = new UserDataHeader(Arrays.copyOfRange(payload, 1, payload[0]));
		
		boolean isConcatenatedSMS = false;
		for (AbstractInformationElement e : udh.getInformationElements())
			if (e instanceof ConcatenatedSMSIElement)
				isConcatenatedSMS = true;
		
		assertTrue(isConcatenatedSMS);
		Assert.assertEquals(
				"Carregamento efectuado: EUR 12,50. Saldo: EUR 12,50. Ate ja.",
				SMS7BitsCharset.decode(
						sms.getNumberOfSymbols(), isUDHPresent,
						payload ) );
	}
	
	@Test
	public void testSMSDeliverNetworkSpecialSMS_AlphanumericSenderAddr1() {
		SMSDeliver sms = new SMSDeliver(
				HexBin.decode(
						"0791539126010000" +
						"04" +
						"06D0F4B61B" +
						"39" +
						"00" +
						"017021906140409AC1" +
						"39" +
						"E86D2ECBE9E13988FC06CDCB75103D2C" +
						"4F9BC3F2F41B340FBF41F6303B4D0ECF" +
						"41617A192483C5602D58AE1583B940D3" +
						"721B2E2F83E2F532A86C2E8FE9F5B01C" +
						"340ECBE5E573B85D76D3DF7310BD2C0F" +
						"83A6CD29E82C0FD3D3735019344687DB" +
						"6172780E6A87D37390382C0FD3C37316" +
						"081E9687416190BC4C2E83E86DB70B14" +
						"A49741EA30"));
		assertEquals(TypeOfNumber.AlphaNumeric, sms.getSenderAddr().getTOA().getTON());
		assertEquals("tmn", sms.getSenderAddr().getNumber());
		assertEquals(
				"As ofertas do seu tarifario sao validas ate 2010-09-10. " +
				"Sempre que efectuar carregamentos tera SMS gratis e " +
				"chamadas mais baratas, para a rede tmn. Ate ja",
				SMS7BitsCharset.decode( 
						sms.getNumberOfSymbols(), false,
						sms.getPayload() ) );
	}
	
	@Test
	public void testSMSDeliverNetworkSpecial_AlphanumericSenderAddress2() {
		SMSDeliver sms = new SMSDeliver(
				HexBin.decode(
						"0791539126010000" +
						"04" +
						"0DD0D0B79BFE06D101" +
						"00" +
						"01" +
						"2170400100824098D3" + //SMS Center time stamp
						"32" +
						"A80C1AB3D36537BD0C6A169F20F89B5C" +
						"069DC36E74580E9281E06F37FD3D07C1" +
						"DF72D0384C0E838A75790C5406D1E5EF" +
						"71B8C57ECF41323CA81D4ECF41E4325C" +
						"5E9ECFC3215011140685C9E579F80D2A" +
						"83E86F7A98DD2EBBE965D0591EA6D7D3" +
						"F47008C44C9FEB65903A0C3ACBC3F4F4" +
						"1C8483C172B6192C677381ACE1F67B0E" +
						"62875D"));
		
		assertEquals(TypeOfNumber.AlphaNumeric, sms.getSenderAddr().getTOA().getTON());
		assertEquals("Ponto t", sms.getSenderAddr().getNumber());
		
		Assert.assertEquals(
				"Se e cliente MEO pode ganhar 2 pontos por cada Eur1 e " +
				"troca-los 2x mais depressa! E a adesao e totalmente gratuita! " +
				"Ligue ja gratis 800963096. Vamos la.",
				SMS7BitsCharset.decode( 
						sms.getNumberOfSymbols(), false,
						sms.getPayload() ) );
	}
	
	@Test
	public void testSMSDeliver1() throws UnsupportedCharException {
		String msg = "Ola UI!";
		SMSDeliver sms = new SMSDeliver(
				HexBin.decode(
						"0791539126010000240C915391961466" +
						"74000001705102206540074F7618544D" +
						"8600") );
		
		Assert.assertEquals(
				msg,
				SMS7BitsCharset.decode(
						sms.getNumberOfSymbols(), false,
						SMS7BitsCharset.encode( msg ).getEncodedText() ) );
		Assert.assertEquals(
				msg,
				SMS7BitsCharset.decode(
						sms.getNumberOfSymbols(), false,
						sms.getPayload() ) );
	}
	
	@Test
	public void testSMSDeliver2() {
		//Message Sent from TMN to Optimus
		SMSDeliver sms = new SMSDeliver(
				HexBin.decode(
						"0791539126010000240C915391366476" +
						"5400002120928163430021D3749B052A" +
						"D741F372DA057A8AE5E973981C6681C4" +
						"E5B43AED46BFE72E"));
		
		//This is just to show how we can know from which network came the sms 
		Assert.assertEquals("+351962100000", sms.getSMSCAddr().getNumber());
	}
	
	@Test
	public void testSMSDeliver3_UDHPresent() {
		SMSDeliver sms = new SMSDeliver(
				HexBin.decode(
						"0791539126010000640C915391366476" +
						"540000214090418185401903240103F8" +
						"CC419BBA3B3D7EBB40C2725A9D76A3DF" +
						"73"));
		
		Assert.assertTrue(sms.getDCS() instanceof GeneralDataCodingScheme);
		Assert.assertEquals(Alphabet.Default, ((GeneralDataCodingScheme) sms.getDCS()).getAlphabet());
		
		boolean isUDHPresent =
				Flag.isSet(SMSDeliverOption.UserDataHeaderPresent, sms.getSMSHeader().getOptions());
		
		Assert.assertTrue(isUDHPresent);

		String text = SMS7BitsCharset.decode(
				sms.getNumberOfSymbols(),
				isUDHPresent,
				sms.getPayload());
		
		Assert.assertEquals(
				"És único. Beijinhos",
				text);
	}
	
	@Test
	public void testSMSDeliver4_UDHPresent() {
		SMSDeliver sms = new SMSDeliver(
				HexBin.decode(
						"0791539126010000640C915391366476" +
						"540000214021327584400D03240103A0" +
						"86E7A0F026FD03"));
		
		Assert.assertTrue(sms.getDCS() instanceof GeneralDataCodingScheme);
		Assert.assertEquals(Alphabet.Default, ((GeneralDataCodingScheme) sms.getDCS()).getAlphabet());
		
		boolean isUDHPresent =
				Flag.isSet(SMSDeliverOption.UserDataHeaderPresent, sms.getSMSHeader().getOptions());
		
		Assert.assertTrue(isUDHPresent);

		String text = SMS7BitsCharset.decode(
				sms.getNumberOfSymbols(),
				isUDHPresent,
				sms.getPayload());
		
		Assert.assertEquals(
				"Tas aí?",
				text);
	}
	
	@Test
	public void testSMSDeliver2UTF_16() {
		SMSDeliver sms = new SMSDeliver(
			HexBin.decode(
					"0791539126010000" +
					"240C915391961466" +
					"7400080170512221" +
					"63402A0044006900" +
					"6F0067006F002000" +
					"6300E3006F002000" +
					"6100E7006F002000" +
					"6E00E3006F002000" +
					"6D00E3006F") );
		
		Assert.assertEquals(
				Alphabet.UCS2,
				((GeneralDataCodingScheme) sms.getDCS()).getAlphabet());
		Assert.assertEquals(
				"Diogo cão aço não mão",
				new String(
						sms.getPayload(),
						Charset.forName("UTF-16")) );
	}
	
	@Test
	public void testSMSDeliver3_ServiceCenterDateMonth() {
		String msg = "jTextPane0";
		SMSDeliver sms =
				new SMSDeliver(
				HexBin.decode(
						"07915391131213F4" +
						"040C915391331313" +
						"1600001121624104" +
						"00000A6A6A194F87" +
						"86DD6518") );
		
		Assert.assertEquals(
				msg,
				SMS7BitsCharset.decode(
						sms.getNumberOfSymbols(), false,
						sms.getPayload() ) );
		Assert.assertEquals(
				Calendar.DECEMBER,
				sms.getSCTS().getCalendar().get(Calendar.MONTH));
	}
	
	@Test
	public void testSMSDeliver3_toBytes() {
		byte[] originalBytes = HexBin.decode(
				"0791539126010000" +
				"24" +
				"0C91539196146674" +
				"00" +
				"00" +
				"01508032829040" +
				"0F" +
				"09D01F54001D4008501F6400F901"
				);
		byte[] getPDU = new SMSDeliver(originalBytes).getPDU();

		Assert.assertEquals(originalBytes.length, getPDU.length);
		for(int i = 0; i < originalBytes.length; i++)
			Assert.assertEquals("[" + i + "]", originalBytes[i], getPDU[i]);
	}
	
	@Test
	public void testSMSDeliver4_PrivateNumber() {
		SMSDeliver sms = new SMSDeliver(
				HexBin.decode(
						"07915391131213F424008000002130520110244004CA263108"));
		
		Assert.assertEquals("JMDA", SMS7BitsCharset.decode(sms.getNumberOfSymbols(), false, sms.getPayload()));
		Assert.assertEquals(TypeOfNumber.Unknown, sms.getSenderAddr().getTOA().getTON());
		Assert.assertEquals(NumberingPlanId.Unknown, sms.getSenderAddr().getTOA().getNPI());
		Assert.assertEquals("", sms.getSenderAddr().getNumber());
	}
	
	@Test
	public void testSMSDeliver5_160chars() {
		SMSDeliver sms = new SMSDeliver(
				HexBin.decode(
						"07915391131213F404028139000021507081300040A0" +
						"B43728E84C5A8BD269509A7C82A8C1A30EF4049D" +
						"E575F81B440D1E41ED3A9B9E86B3D3E377BD352F" +
						"8740C62033080A82844F2933088206A54110F549" +
						"0C829E506AB2599D82C2F432C89C6E83C86F50D8" +
						"FD76818A6E7B3A0C8206A54161D13905C1C3F230" +
						"282683C568A030BD0C8AD55E3617685A6E83C6F5" +
						"39FD0D0A93CBF3F0DB054ABACD6F10357874C1E9"));

		String expectedMsg = 
				"4o ANIVERSARIO TAG: O grupo TAG multiplicou-se! FALA A BORLA " +
				"PARA TODA OPTIMUS ate fim do ano. Envia PARABENS para 12014 " +
				"ate 15/6. Sem custo adesao. Info TAG.pt";
		
		Assert.assertEquals(expectedMsg, SMS7BitsCharset.decode(sms.getNumberOfSymbols(), false, sms.getPayload()));
		Assert.assertEquals(TypeOfNumber.Unknown, sms.getSenderAddr().getTOA().getTON());
		Assert.assertEquals(NumberingPlanId.ISDN, sms.getSenderAddr().getTOA().getNPI());
		Assert.assertEquals("93", sms.getSenderAddr().getNumber());
	}
}

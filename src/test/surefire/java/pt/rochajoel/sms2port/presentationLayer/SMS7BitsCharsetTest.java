package pt.rochajoel.sms2port.presentationLayer;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import pt.rochajoel.sms2port.presentationLayer.SMS7BitsCharset.EncodingResult;
import pt.rochajoel.sms2port.presentationLayer.SMS7BitsCharset.UnsupportedCharException;
import pt.rochajoel.sms2port.presentationLayer.UserDataHeader.AbstractInformationElement;
import pt.rochajoel.sms2port.presentationLayer.UserDataHeader.NationalLanguageShiftIElement.Language;
import pt.rochajoel.sms2port.presentationLayer.UserDataHeader.NationalLanguageSingleShiftIElement;
import util.Out;
import util.bytes.Convert;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

public class SMS7BitsCharsetTest {
	@Test
	public void testTo7Bits1() throws UnsupportedCharException {
		byte[] actual = SMS7BitsCharset.encode( "hellohello" ).getEncodedText();
		byte[] expected = new byte[] {
				(byte)0xE8, 0x32,
				(byte)0x9B,
				(byte)0xFD, 0x46,
				(byte)0x97, (byte)0xD9, (byte)0xEC, 0x37};
		
		for(int i = 0; i < actual.length; i++)
			assertEquals("" + i, expected[i], actual[i]);
	}

	@Test
	public void testTo7Bits2() throws UnsupportedCharException {
		byte[] expected = HexBin.decode(
			"4173DA1D6683CCEF34C81C66CF" +
			"DFA0303B2C6F9759A037885E6EC3DFA076819C7E83C86550D96D4FBF41E43268DE9E9FE6A0F1DB4D" +
			"4FBBEB615018342FCB41E43268E6AACDCBE7BA9BFC9EBB4041B13C3C7EBB00");
			
		String txt =
			"Afinal foi falso alarme, o tempo médio " +
			"de envio de sms's continua a ser de " +
			"3.5segundos. Abraco.";

		byte[] actual = SMS7BitsCharset.encode( txt ).getEncodedText();
		
		for(int i = 0; i < actual.length; i++)
			assertEquals("" + i,
					Convert.printHex( expected[i] ),
					Convert.printHex( actual[i] ) );
	}
	
	public void testTo7Bits3() throws UnsupportedCharException {
		String msg = "Tenta guardar esta :P";
		
		byte[] actual = SMS7BitsCharset.encode( msg ).getEncodedText();
		byte[] expected = HexBin.decode(
				"D4B29B1E069DEB6139392C0795E7F430480705");
		
		Out.println( Convert.printHex( actual ) );
		Out.println( Convert.printHex( expected ) );
		
		Assert.assertTrue(Arrays.equals(expected, actual));
	}
	
	@Test
	public void testTo7Bits5() throws UnsupportedCharException {
		SMS7BitsCharset.encode(
				SMS7BitsCharset.filterTo7Bits(
						"Manifestantes atiram�ovos a institui��es banc�rias�em " +
						"Lisboa - Cerca de 150 manifestantes, entre os quais� e" +
						"lementos da plataforma 15 outubro, desfilaram hoje � t" +
						"arde pela Av. Almirante� Reis at� ao Rossio, em Lisboa," +
						" atirando ovos �s instala��es das institui��es� banc�ri" +
						"as por onde passavam."));
	}
	
	@Test
	public void testTo7Bits6() throws UnsupportedCharException {
		SMS7BitsCharset.encode(
				SMS7BitsCharset.filterTo7Bits(
						"Fecho Europa: dados macroecon�micos na China e Alemanha" +
						" penalizam �ndices europeus - Os principais �ndices eur" +
						"opeus encerraram a sess�o em terreno negativo � DAX (-1" +
						",27%), CAC (-1,56%) e AEX (-1,17%) � ap�s a divulga��o " +
						"de dados macroecon�micos menos positivos no bloco asi�t" +
						"ico, mais especificamente na China, e na Alemanha, ofus" +
						"cando os dados positivos evidenciados mais uma vez pelo" +
						" bloco norte-americano. Destaque para a underperformanc" +
						"e do sector financeiro, com os t�tulos a registarem rec" +
						"uos m�dios de cerca de 2% na sess�o.� O sector de telec" +
						"omunica��es registou a melhor performance da sess�o, em" +
						"bora tenha encerrado com perdas m�dias superiores a 0,5" +
						"5%. Uma nota para a Hermes, que contrariou a performanc" +
						"e negativa da generalidade dos restante t�tulos europeu" +
						"s, tendo valorizado 2,27%, ap�s reportar resultados rel" +
						"ativo ao exerc�cio de 2011 que superaram as estimativas" +
						" dos analistas."));
	}
	
	@Test
	public void testTo7Bits7() throws UnsupportedCharException {
		SMS7BitsCharset.encode(
				SMS7BitsCharset.filterTo7Bits(
						"\"Queremos recuperar a primeira posi��o e este � um jog" +
						"o�de grande import�ncia\", diz Jesus - O treinador do B" +
						"enfica, Jorge Jesus, afirmou� hoje que o objetivo � rec" +
						"uperar o primeiro lugar da Liga portuguesa de futebol� " +
						"e que para isso o jogo frente ao Olhanense � de \"grand" +
						"e import�ncia\""));
	}
	
	@Test
	public void testTo7Bits8() throws UnsupportedCharException {
		SMS7BitsCharset.encode(
				SMS7BitsCharset.filterTo7Bits(
						"Vodafone lan�a o novo iPad em Portugal amanh�, 23 de Ma" +
						"r�o - O lan�amento do novo iPad, no inicio deste m�s, v" +
						"eio trazer para o mundo das tecnologias mais um gadget " +
						"que todos querem ter"));
	}
	
	@Test
	public void testTo7Bits9() throws UnsupportedCharException {
		SMS7BitsCharset.encode(
				SMS7BitsCharset.filterTo7Bits(
						"�N�o � necess�rio reestruturar d�vida nem um segundo ba" +
						"ilout� - O presidente do BES diz que as descidas dos ju" +
						"ros nas �ltimas emiss�o de d�vida s�o �um bom indicador" +
						"�."));
	}
	
	@Test
	public void testTo7BitsStartingAt() throws UnsupportedCharException {
		EncodingResult er = SMS7BitsCharset.encode(
				new UserDataHeader(new AbstractInformationElement[] {
						new NationalLanguageSingleShiftIElement(Language.Portuguese),
				}),
				"És único. Beijinhos");
		byte[] actual =	er.getEncodedText();
		byte[] expected = HexBin.decode(
				"03240103" + //UDH
				"F8CC419BBA3B3D7EBB40C2725A9D76A3DF73"); 
		
		for(int i = 0; i < actual.length; i++)
			assertEquals("" + i, expected[i], actual[i]);

		assertEquals(25, er.getTotalNumOfSeptets());
		assertEquals(true, er.isUDHPresent());
	}
	
	@Test
	public void testFrom7Bits0_0() {
		byte[] expected = "".getBytes();
		//68 65 6C 6C 6F 68 65 6C 6C 6F
		
		byte[] actual = SMS7BitsCharset.decode(
				expected.length, false,
				new byte[0]).getBytes();

		Out.println( Convert.printHex( actual ) );
		Out.println( Convert.printHex( expected ) );
		
		Assert.assertEquals(expected.length, actual.length);
		
		for(int i = 0; i < expected.length; i++)
			assertEquals("" + i, expected[i], actual[i]);
	}
	
	@Test
	public void testFrom7Bits1() throws UnsupportedCharException {
		byte[] expected = "hellohello".getBytes();
		//68 65 6C 6C 6F 68 65 6C 6C 6F
		
		byte[] actual = SMS7BitsCharset.decode(
				expected.length, false,
				new byte[] {
					(byte)0xE8, 0x32,
					(byte)0x9B,
					(byte)0xFD, 0x46,
					(byte)0x97, (byte)0xD9, (byte)0xEC, 0x37 }).getBytes();
		
		for(int i = 0; i < expected.length; i++)
			assertEquals("" + i, expected[i], actual[i]);
	}
	
	@Test
	public void testFrom7BitsStartingAt() throws UnsupportedCharException {
		String expected = "És único. Beijinhos";
		String actual =
				SMS7BitsCharset.decode(
						HexBin.decode("19")[0], true,
						HexBin.decode(
						"03240103" + //UDH
						"F8CC419BBA3B3D7EBB40C2725A9D76A3DF73"));
		
		Assert.assertEquals(expected, actual);
	}
	
	public void testFrom7Bits2() {
		byte[] expected = "Com� que � Z�? Tudo em p�? �s tantas t�sse bem".getBytes();
		byte[] actual = SMS7BitsCharset.decode(
				expected.length, false,
				HexBin.decode(
					"C377BB008AD7CBA002485BF881A875F2" +
					"1B546E83E0851FE83F07D1C36E7A780E" +
					"A2FFE7F332485C6E03")).getBytes();
		
		for(int i = 0; i < expected.length; i++)
			assertEquals("" + i, expected[i], actual[i]);
	}
	
	@Test
	public void testFrom7Bits3() {
		String msg = "rochajoel@iol.pt";
		
		byte[] expected = msg.getBytes();
		byte[] actual = SMS7BitsCharset.decode(
				msg.length(), false,
				HexBin.decode(
					"F2F7181D56BFCB6C40FACD76C1E9") ).getBytes();
		
		Out.println( Convert.printHex( actual ) );
		Out.println( Convert.printHex( expected ) );
		
		for(int i = 0; i < expected.length; i++)
			assertEquals("" + i, expected[i], actual[i]);
	}
	
	@Test
	public void testFrom7Bits4_160chars() {
		String msg = 
				"4o ANIVERSARIO TAG: O grupo TAG multiplicou-se! FALA A BORLA " +
				"PARA TODA OPTIMUS ate fim do ano. Envia PARABENS para 12014 " +
				"ate 15/6. Sem custo adesao. Info TAG.pt";
		
		byte[] expected = msg.getBytes();
		byte[] actual = SMS7BitsCharset.decode(
				msg.length(), false,
				HexBin.decode(
//					"07915391131213F404028139000021507081300040A0" +
					"B43728E84C5A8BD269509A7C82A8C1A30EF4049D" +
					"E575F81B440D1E41ED3A9B9E86B3D3E377BD352F" +
					"8740C62033080A82844F2933088206A54110F549" +
					"0C829E506AB2599D82C2F432C89C6E83C86F50D8" +
					"FD76818A6E7B3A0C8206A54161D13905C1C3F230" +
					"282683C568A030BD0C8AD55E3617685A6E83C6F5" +
					"39FD0D0A93CBF3F0DB054ABACD6F10357874C1E9") ).getBytes();

		Assert.assertEquals(expected.length, actual.length);
		for(int i = 0; i < expected.length; i++)
			assertEquals("" + i, expected[i], actual[i]);
	}
	
	@Test
	public void testToFrom7Bits1() throws UnsupportedCharException {
		String msg = "Comé que é Zé? Tudo em pé? às tantas tàsse bem";
		
		byte[] expected = msg.getBytes();
		byte[] actual = SMS7BitsCharset.decode(
				msg.length(), false,
				SMS7BitsCharset.encode(msg).getEncodedText() ).getBytes();
		
		Out.println( Convert.printHex( actual ) );
		Out.println( Convert.printHex( expected ) );
		
		for(int i = 0; i < expected.length; i++)
			assertEquals("" + i, expected[i], actual[i]);
	}
	
	@Test
	public void testToFrom7Bits2() throws UnsupportedCharException {
		String msg = "@?.:;Ç!\"#$%&/()={}[]|!";
		
		byte[] expected = msg.getBytes();
		byte[] actual = SMS7BitsCharset.decode(
				msg.length(), false,
				SMS7BitsCharset.encode(msg).getEncodedText() ).getBytes();
		
		Out.println( Convert.printHex( actual ) );
		Out.println( Convert.printHex( expected ) );
		
		for(int i = 0; i < expected.length; i++)
			assertEquals("" + i, expected[i], actual[i]);
	}
	
	@Test
	public void testToFrom7Bits3() throws UnsupportedCharException {
		String msg = "{}{}";
		
		byte[] expected = msg.getBytes();
		EncodingResult er = SMS7BitsCharset.encode(msg);
		byte[] actual = SMS7BitsCharset.decode(
				er.getTotalNumOfSeptets(), false,
				er.getEncodedText() ).getBytes();
		
		Out.println( Convert.printHex( actual ) );
		Out.println( Convert.printHex( expected ) );
		
		for(int i = 0; i < expected.length; i++)
			assertEquals("" + i, expected[i], actual[i]);
	}
	
	@Test
	public void testToFrom7Bits4() throws UnsupportedCharException {
		String msg = "Ola UI!";
		byte[] encoded = HexBin.decode("4F7618544D8600");
		
		Assert.assertEquals(
				msg,
				SMS7BitsCharset.decode(
						7, false,
						SMS7BitsCharset.encode( msg ).getEncodedText() ) );
		Assert.assertEquals(msg, SMS7BitsCharset.decode(7, false, encoded ));
	}
	
	@Test
	public void testToFrom7BitsStartingAt2() throws UnsupportedCharException {
		String expectedText = "És único. Beijinhos";
		EncodingResult er = SMS7BitsCharset.encode(
				new UserDataHeader(new AbstractInformationElement[] {
						new NationalLanguageSingleShiftIElement(Language.Portuguese),
						new NationalLanguageSingleShiftIElement(Language.Portuguese),
						new NationalLanguageSingleShiftIElement(Language.Portuguese),
				}),
				expectedText);
		byte[] actual =	er.getEncodedText();
		byte[] expected = HexBin.decode(
				"09240103240103240103" + //UDH
//				"F8CC419BBA3B3D7EBB40C2725A9D76A3DF73" +
				""); 
		
		for(int i = 0; i < expected.length; i++)
			assertEquals("" + i, expected[i], actual[i]);

		assertEquals(true, er.isUDHPresent());
		assertEquals(
				expectedText,
				SMS7BitsCharset.decode(
						er.getTotalNumOfSeptets(),
						er.isUDHPresent(),
						er.getEncodedText()));
		
		assertEquals(32, er.getTotalNumOfSeptets());
	}
}

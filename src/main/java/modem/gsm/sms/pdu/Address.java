package modem.gsm.sms.pdu;

import java.util.Arrays;

import modem.gsm.sms.pdu.Address.TypeOfAddress.NumberingPlanId;
import modem.gsm.sms.pdu.Address.TypeOfAddress.TypeOfNumber;
import pt.rochajoel.sms2port.presentationLayer.SMS7BitsCharset;
import util.Enums.NamedByte;
import util.Enums.NamedByte.IByte;

public class Address {
	public static class TypeOfAddress {
		public enum TypeOfNumber implements IByte {
			/**
			 * This is used when the user or network has no a priori information about the numbering plan.
			 * In this case, the Address-Value field is organized according to the network dialling plan,
			 * e.g. prefix or escape digits might be present.
			 */
			Unknown			((byte) 0x00),
			International	((byte) 0x10),
			/** Prefix or escape digits shall not be included. */
			National		((byte) 0x20),
			/**
			 * This is used to indicate administration/service number specific to the serving network,
			 * e.g. used to access an operator.
			 */
			NetworkSpecific	((byte) 0x30),
			/**
			 * This is used when a specific short number representation is stored in one or more SCs
			 * as part of a higher layer application. (Note that "Subscriber number" shall only be used
			 * in connection with the proper PID referring to this application). 
			 */
			Subscriber		((byte) 0x40),
			/** coded according to 3GPP TS 23.038 7-bit default alphabet */
			AlphaNumeric	((byte) 0x50),
			Abbreviated		((byte) 0x60),
			/** Reserved for Extension */
			Reserved		((byte) 0x70),
			;
			
			private final byte type;

			private TypeOfNumber(byte type) { this.type = type; }
			
			@Override
			public byte getByte() { return this.type; }
			
			private static TypeOfNumber getTypeOfNumber(byte ton) {
				return NamedByte.getNamedByte(TypeOfNumber.values(), (byte)(0x70 & ton));
			}
		}
		
		public enum NumberingPlanId implements IByte {
			/** 0 0 0 0	Unknown. */
			Unknown			((byte) 0x00),
			/** 0 0 0 1	ISDN/telephone numbering plan (E.164/E.163). */
			ISDN			((byte) 0x01),
			Reserved_0x02	((byte) 0x02),
			/** 0 0 1 1	Data numbering plan (X.121). */
			Data			((byte) 0x03),
			/** 0 1 0 0	Telex numbering plan */
			Telex			((byte) 0x04),
			Reserved_0x05	((byte) 0x05),
			Reserved_0x06	((byte) 0x06),
			Reserved_0x07	((byte) 0x07),
			/** 1 0 0 0	National numbering plan */
			National		((byte) 0x08),
			/** 1 0 0 1	Private numbering plan */
			Private			((byte) 0x09),
			/** 1 0 1 0	ERMES numbering plan (ETSI DE/PS 3 01-3) */
			ERMES			((byte) 0x0A),
			Reserved_0x0B	((byte) 0x0B),
			Reserved_0x0C	((byte) 0x0C),
			Reserved_0x0D	((byte) 0x0D),
			Reserved_0x0E	((byte) 0x0E),
			/** 1 1 1 1	Reserved for extension */ 
			Reserved_0x0F	((byte) 0x0F),
			;
			
			private final byte type;

			private NumberingPlanId(byte type) { this.type = type; }
			
			@Override
			public byte getByte() { return this.type; }
			
			private static NumberingPlanId getNumberingPlanId(byte npi) {
				return NamedByte.getNamedByte(NumberingPlanId.values(), (byte)(0x0F & npi));
			}
		}

		private final byte toa;
		
		public TypeOfAddress(TypeOfNumber ton, NumberingPlanId npi) {
			this( toByte(ton, npi) );
		}

		private static byte toByte(TypeOfNumber ton, NumberingPlanId npi) {
			return (byte) (0x80 | ton.getByte() | npi.getByte());
		}
		
		public TypeOfAddress(byte toa) {
			if ((toa & 0x80) != 0x80)
				throw new IllegalArgumentException("TOA MSB must always be 1");
			
			TypeOfNumber.getTypeOfNumber(toa);
			NumberingPlanId.getNumberingPlanId(toa);
			
			this.toa = toa;
		}
		
		public TypeOfNumber getTON() {
			return TypeOfNumber.getTypeOfNumber(toa);
		}

		public NumberingPlanId getNPI() {
			return NumberingPlanId.getNumberingPlanId(toa);
		}
		
		public byte toByte() {
			return this.toa;
		}
	}
	
	private final byte[] address;
	
	public Address(String number) {
		this( toBytes( number) ); 
	}
	
	/**
	 * The byte array starts with the Type Of Address byte.
	 * 
	 * @param address
	 */
	protected Address(byte[] address) {
		new TypeOfAddress(address[0]);
		
		this.address = Arrays.copyOf(address, address.length);
	} 

	private static byte[] toBytes(String number) {
		TypeOfAddress toa = identifyTOA(number);

		if (number.length() == 0)
			return new byte[] { toa.toByte() };
		
		if (toa.getTON() == TypeOfNumber.International)
			number = number.substring(1);
		
		boolean isEven = (number.length() & 1) != 1;
		number += isEven ? "" : "0";
		
		byte[] tmp = SemiOctetsSwappedNibble.toSemiOctets( getNumberPairs( number ) );
		tmp[tmp.length - 1] |= isEven ? 0x00 : 0xF0;
		
		byte[] ret = new byte[1 + tmp.length];
		
		ret[0] = toa.toByte();
		for(int i = 0; i < tmp.length; i++)
			ret[++i] = tmp[--i];
		
		return ret;
	}
	
	private static byte[] getNumberPairs(String number) {
		String[] numberPairs = new String[number.length() >> 1];
		for(int i = 0; i < numberPairs.length;)
			numberPairs[i] = number.substring(i << 1, ++i << 1);
		
		byte[] nums = new byte[numberPairs.length];
		
		for(int i = 0; i < numberPairs.length; i++)
			nums[i] = Byte.parseByte( numberPairs[i] );
		
		return nums;
	}

	private static TypeOfAddress identifyTOA(String number) {
		if (number.startsWith("+"))
			return new TypeOfAddress( TypeOfNumber.International, NumberingPlanId.ISDN);
		
		if (number.length() == 9)
			return new TypeOfAddress( TypeOfNumber.National, NumberingPlanId.ISDN);
		
		return new TypeOfAddress(TypeOfNumber.Unknown, NumberingPlanId.Unknown);
	}
	
	public TypeOfAddress getTOA() {
		return new TypeOfAddress( this.address[0] );
	}
	
	/**
	 * Gets the value of this {@link Address}.
	 * 
	 * It can return a common Phone Number but can also return an alphanumeric
	 * address.
	 * 
	 * @return
	 */
	public String getNumber() {
		if (this.address.length == 1)
			return "";
		
		switch (getTOA().getTON()) {
			case AlphaNumeric:
				return getAlphanumericTON();
			default:
				return getNationalOrInternationalTON(); 
		}
	}

	private String getAlphanumericTON() {
		byte[] addressText = Arrays.copyOfRange(this.address, 1, this.address.length);
		int maxSymbols = (addressText.length << 3) / 7;
		String number = SMS7BitsCharset.decode(maxSymbols, false, addressText);

		if ((maxSymbols & 0x07) == 0)
			if (number.endsWith("@"))
				//For this special case, i believe that the symbol @ is never
				//part of this "Alphanumeric" address because of it's
				//designation: Alpha -> letters & Numeric -> numbers
				number = number.substring(0, maxSymbols - 1);
		
		return number;
	}

	private String getNationalOrInternationalTON() {
		byte[] rawNumber = Arrays.copyOfRange(this.address, 1, this.address.length);
		final int lastByteIndex = rawNumber.length - 1;
		final boolean isOdd = (rawNumber[lastByteIndex] & 0xF0) == 0xF0;
		
		if (isOdd)
			rawNumber[lastByteIndex] &= 0x0F;
		
		String ret = "";
		
		rawNumber = SemiOctetsSwappedNibble.fromSemiOctets( rawNumber );
		for(int i = 0; i < lastByteIndex; i++ ) {
			byte n = rawNumber[i];
			ret += (n < 10 ? "0" : "") + n;
		}

		byte lastByte = rawNumber[lastByteIndex];
		ret += isOdd ?
				lastByte / 10 :
				(lastByte < 10 ? "0" : "") + lastByte;
		
		if (getTOA().getTON() == TypeOfNumber.International)
			ret = "+" + ret;
		
		return ret;
	}
	
	protected byte[] toBytes() {
		return Arrays.copyOf(this.address, this.address.length);
	}
}

package pt.rochajoel.sms2port.presentationLayer;

import java.util.ArrayList;
import java.util.Arrays;

import pt.rochajoel.sms2port.presentationLayer.UserDataHeader.AbstractInformationElement.UnexpectedInformationElement;
import util.Out;
import util.Enums.NamedByte;
import util.Enums.NamedByte.IByte;
import util.bytes.Convert;

/**
 * The user data header is simply a collection of Information Elements
 * 
 * @author Joel Rocha
 */
public class UserDataHeader {
	public static abstract class AbstractInformationElement {
		protected enum Identifier implements IByte {
			ConcatenatedSMS				((byte) 0x00),
			NationalLanguageSingleShift	((byte) 0x24),
			NationalLanguageLockingShift((byte) 0x25),
			;
			
			private final byte id;

			private Identifier(byte id) { this.id = id; }

			@Override
			public byte getByte() {
				return this.id;
			}
		}

		private final Identifier iElemId;
		
		protected AbstractInformationElement(Identifier id) { this.iElemId = id; }
		
		public Identifier getIdentifier() { return this.iElemId; }
		
		public abstract byte[] toBytes();

		protected static class UnexpectedInformationElement extends Exception {
			private static final long serialVersionUID = 8201742912161262284L;
			
			private UnexpectedInformationElement(String msg) { super(msg); }
		}
		
		public static AbstractInformationElement getInformationElement(byte iElemId, byte[] iElemData) throws UnexpectedInformationElement {
			try {
				Identifier id = NamedByte.getNamedByte(Identifier.values(), iElemId);
				
				switch (id) {
					case ConcatenatedSMS: 
						return new ConcatenatedSMSIElement(iElemData);
					case NationalLanguageSingleShift:
						return new NationalLanguageSingleShiftIElement(iElemData);
					case NationalLanguageLockingShift:
						return new NationalLanguageLockingShiftIElement(iElemData);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
			
			throw new UnexpectedInformationElement(
					Convert.byte2Hex( new byte[] { iElemId, (byte) iElemData.length } ) +
					Convert.byte2Hex( iElemData ));
		}
	}

	public static class ConcatenatedSMSIElement extends AbstractInformationElement {
		private final byte[] data;

		private ConcatenatedSMSIElement(byte[] iElemData) {
			super(Identifier.ConcatenatedSMS);
			
			if (iElemData.length != 3)
				throw new IllegalArgumentException("iElementData must have 3 bytes");
			
			this.data = Arrays.copyOf(iElemData, iElemData.length);
		}

		public byte getSMSId() { return this.data[0]; }
		public byte getMaximum() { return this.data[1]; }
		public byte getPartNumber() { return this.data[2]; }
		
		@Override
		public byte[] toBytes() {
			byte[] ret = new byte[5];
			
			int i = 0;
			ret[i++] = this.getIdentifier().getByte();
			
			ret[i++] = (byte) this.data.length;
			for(int j = 0; j < this.data.length; j++)
				ret[i++] = this.data[j];
			
			return ret;
		}
	}
	
	public static class NationalLanguageShiftIElement extends AbstractInformationElement {
		public enum Shift {
			Single	(Identifier.NationalLanguageSingleShift),
			Locking	(Identifier.NationalLanguageLockingShift),
			;
			
			private final Identifier id;
			
			private Shift(Identifier id) { this.id = id; }
			
			private Identifier getIdentifier() { return this.id; }
		}
		
		/**
		 * National Language Identifier Code table 3GPP TS 23.038 p.23
		 * @author Joel Rocha
		 */
		public enum Language implements IByte {
			//Reserved_00	((byte) 0x00),
			Turkish		((byte) 0x01),
			Spanish		((byte) 0x02),
			Portuguese	((byte) 0x03),
			Bengali		((byte) 0x04),
			Gujarati	((byte) 0x05),
			Hindi		((byte) 0x06),
			Kannada		((byte) 0x07),
			Malayalam	((byte) 0x08),
			Oriya		((byte) 0x09),
			Punjabi		((byte) 0x0A),
			Tamil		((byte) 0x0B),
			Telugu		((byte) 0x0C),
			Urdu		((byte) 0x0D),
			//Reserved 0x0E - 0xFF
			;
			
			private final byte lang;
			
			private Language(byte lang) { this.lang = lang; }
			
			@Override
			public byte getByte() { return this.lang; }
		}
		
		private final Language language;
		
		private NationalLanguageShiftIElement(Shift type, byte[] iElemData) {
			super(type.getIdentifier());
			
			if (iElemData == null || iElemData.length != 1)
				throw new IllegalArgumentException("Unexpected Information Element Data length");
			
			this.language = NamedByte.getNamedByte(Language.values(), iElemData[0]);
		}
		
		private NationalLanguageShiftIElement(Shift type, Language language) {
			this(type, new byte[] { language.getByte() });
		}

		public Language getLanguage() { return this.language; }
		
		@Override
		public byte[] toBytes() {
			return new byte[] {
					this.getIdentifier().getByte(),
					0x01, this.language.getByte() };
		}
	}
	public static class NationalLanguageLockingShiftIElement extends NationalLanguageShiftIElement {
		private NationalLanguageLockingShiftIElement(byte[] iElemData)	{ super(Shift.Locking, iElemData); }
		public NationalLanguageLockingShiftIElement(Language l)			{ super(Shift.Locking, l); }
	}
	public static class NationalLanguageSingleShiftIElement extends NationalLanguageShiftIElement {
		private NationalLanguageSingleShiftIElement(byte[] iElemData)	{ super(Shift.Single, iElemData); }
		public NationalLanguageSingleShiftIElement(Language l)			{ super(Shift.Single, l); }
	}
	
	private final AbstractInformationElement[] ieList;
	
	public UserDataHeader(byte[] header) {
		ArrayList<AbstractInformationElement> ieArrayList =
				new ArrayList<AbstractInformationElement>();
		
		int i = 0;
		while(i < header.length)
			try {
				ieArrayList.add(
						AbstractInformationElement.getInformationElement(
								header[i++],
								Arrays.copyOfRange(header, i+1, i += header[i] + 1)));
			} catch (UnexpectedInformationElement e) {
				Out.println("Warning: " + e.getMessage());
				e.printStackTrace();
			}
		
		this.ieList = ieArrayList.toArray(
				new AbstractInformationElement[ieArrayList.size()]);
	}
	
	public UserDataHeader(AbstractInformationElement[] ieList) {
		this.ieList = ieList;
	}
	
	public byte[] toBytes() {
		ArrayList<byte[]> ieBytesList = new ArrayList<byte[]>();
		
		for (AbstractInformationElement ie : this.ieList)
			ieBytesList.add( ie.toBytes() );
		
		int totalLen = 0;
		for(byte[] elem : ieBytesList)
			totalLen += elem.length;
		
		byte[] header = new byte[totalLen];
		
		int i = 0;
		for(byte[] elem : ieBytesList)
			for(int j = 0; j < elem.length; j++)
				header[i++] = elem[j];
		
		return header;
	}
	
	public AbstractInformationElement[] getInformationElements() {
		return this.ieList;
	}
}

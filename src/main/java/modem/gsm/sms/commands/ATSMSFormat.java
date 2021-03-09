package modem.gsm.sms.commands;

import modem.ATCommand;
import util.Enums.NamedInt.IInt;

public class ATSMSFormat extends ATCommand {
	public enum Format implements IInt {
		Hex		(0),
		Text	(1),
		;
		
		private final int mode;

		private Format(int mode) { this.mode = mode; }
		
		@Override
		public int getInt() { return this.mode; }
	}

	private final Format format;

	public ATSMSFormat(Format format) {
		if (format == null)
			throw new NullPointerException("null argument");
		
		this.format = format;
	}
	
	@Override
	public String getATCommand() {
		return super.getATCommand() + "+CMGF=" + format.getInt();
	}

}

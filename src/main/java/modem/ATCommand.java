package modem;


public class ATCommand {
	public enum Mode {
		Off	(0),
		On	(1),
		;
		
		private final int mode;

		private Mode(int mode) { this.mode = mode; }

		protected int getMode() {
			return mode;
		}
	}
	
	public enum StandardResponseCode {
		Ok,
		Error,
		;
	}

	public String getATCommand() {
		return "AT";
	}
	
	private volatile StandardResponseCode src = null;
	public final void setSRC(StandardResponseCode src)	{ this.src = src; }
	public final StandardResponseCode getSRC()			{ return this.src; }
}

package modem;


public class ATVerboseCmd extends ATCommand {
	private final Mode eMode;

	public ATVerboseCmd(Mode eMode) {
		this.eMode = eMode;
	}
	
	@Override
	public String getATCommand() {
		return super.getATCommand() + "V" + eMode.getMode();
	}
}

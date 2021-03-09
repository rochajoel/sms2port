package modem;

public class ATEchoCmd extends ATCommand {
	private final Mode eMode;

	public ATEchoCmd(Mode eMode) {
		this.eMode = eMode;
	}
	
	@Override
	public String getATCommand() {
		return super.getATCommand() + "E" + eMode.getMode();
	}
}

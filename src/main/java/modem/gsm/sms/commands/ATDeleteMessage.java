package modem.gsm.sms.commands;

import modem.ATCommand;
import modem.gsm.sms.notifications.NewSMSNotification;

public class ATDeleteMessage extends ATCommand {
	private final NewSMSNotification nmi;

	public ATDeleteMessage(NewSMSNotification nmi) {
		if (nmi == null)
			throw new NullPointerException("null argument");
		
		this.nmi = nmi;
	}

	@Override
	public String getATCommand() {
		return super.getATCommand() + "+CMGD=" + this.nmi.getIndex();
	}

}

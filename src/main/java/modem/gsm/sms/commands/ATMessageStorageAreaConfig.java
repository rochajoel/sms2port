package modem.gsm.sms.commands;

import modem.ATCommand;
import modem.common.ATParam;
import modem.gsm.sms.SMSStorageArea;

public class ATMessageStorageAreaConfig extends ATCommand {
	private final SMSStorageArea[] storageAreas;

	public ATMessageStorageAreaConfig(
			SMSStorageArea readDelete,
			SMSStorageArea writingSend,
			SMSStorageArea newSMSReceived) {
		this.storageAreas = new SMSStorageArea[] {
			readDelete,
			writingSend,
			newSMSReceived
		};
	}

	@Override
	public String getATCommand() {
		String cmd = "+CPMS=";
		
		cmd += this.storageAreas[0];
		for(int i = 1; i < this.storageAreas.length; i++)
			cmd += ATParam.add( "\"" + this.storageAreas[i] + "\"" );
		
		return super.getATCommand() + cmd;
	}

}

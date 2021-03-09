package modem.gsm.sms;

import modem.ATCommand;

/**
 * Extended commands can receive extended result codes.
 * 
 * @author Joel Rocha
 *
 */
public abstract class ATExtendedCmd extends ATCommand {
	public CMSError cmsError;
	
	/**
	 * This method automatically invokes the
	 * {@link ATCommand#standardResponseEvent(StandardResponseCode)}
	 *  
	 * @param err
	 */
	public void setCMSError(CMSError err) {
		this.cmsError = err;
		
		super.setSRC(StandardResponseCode.Error);
	}
	
	public CMSError getCMSError() {
		return this.cmsError;
	}
}

package modem.gsm.sms;

public enum SMSStorageArea {
	/** SM. It refers to the message storage area on the SIM card. */
	SIMCard				("SM"),
	/** ME. It refers to the message storage area on the GSM/GPRS modem or mobile phone. Usually its storage space is larger than that of the message storage area on the SIM card. */
	MobileEquipment 	("ME"),
	/** MT. It refers to all message storage areas associated with the GSM/GPRS modem or mobile phone. For example, suppose a mobile phone can access two message storage areas: "SM" and "ME". The "MT" message storage area refers to the "SM" message storage area and the "ME" message storage area combined together. */
	AllMobileAvailable	("MT"),
	/** BM. It refers to the broadcast message storage area. It is used to store cell broadcast messages. */
	Broadcast			("BM"),
	/** SR. It refers to the status report message storage area. It is used to store status reports. */
	StatusReport		("SR"),
	/** TA. It refers to the terminal adaptor message storage area. */
	TerminalAdaptor		("TA"),
	;
	
	private final String type;

	private SMSStorageArea(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() { return type; }
}

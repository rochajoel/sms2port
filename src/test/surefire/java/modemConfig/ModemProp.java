package modemConfig;

import modem.gsm.sms.pdu.Address;

public class ModemProp {

	public static final String portName = "COM4";
	
	public static final Address smscenter 
		= new Address("+351911616161"); //Vodafone
//		= new Address("+35193121314"); //Optimus
//		= new Address("+351962100000"); //TMN
	public static final Address toME 
//		= new Address("+351933313161");
		= new Address("+351918962977");

}

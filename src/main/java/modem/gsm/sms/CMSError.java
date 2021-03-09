package modem.gsm.sms;

import util.Enums.NamedInt;
import util.Enums.NamedInt.IInt;

public enum CMSError implements IInt {
	ME_Failure				(300),
	SMSService_Reserved		(301),
	OperationNotAllowed		(302),
	OperationNotSupported	(303),
	InvalidPDUParameters	(304),
	InvalidTextParameters	(305),
	
	NoSIMCard				(310),
	PINRequired				(311),
	PH_PINRequired			(312),
	SIMCardFailure			(313),
	SIMCard_Busy			(314),
	WrongSIMCard			(315),
	PUKRequired				(316),
	
	MsgStorageFailure		(320),
	MsgStorageIndexInvalid	(321),
	MsgStorageFull			(322),
	
	SMSCAddressUnknown		(330),
	NoNetworkService		(331),
	NetworkTimeout			(332),
	
	NoMsgAckRequired		(340),
	
	UnknownError			(500),
	;
	
	private final int value;
	
	private CMSError(int val) { this.value = val; }
	
	public int getInt() {
		return this.value;
	}
	
	public static CMSError getCMSError(int err) {
		return NamedInt.getNamedInt(CMSError.values(), err);
	}
}

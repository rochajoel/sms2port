grammar ModemReceiver;


@header {
	package sms2port;
	
	import modem.gsm.sms.CMSError;
	import util.bytes.Convert;
	import util.Enums.NamedInt;
	import util.Out;
	import modem.ATCommand.StandardResponseCode;
	import modem.gsm.sms.SMSStatus;
	import modem.gsm.sms.SMSStorageArea;
	import modem.gsm.sms.pdu.SMSDeliver;
	import modem.gsm.sms.pdu.SMSStatusReport;
	
	import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
}

@lexer::header {
	package sms2port;
}

@members {
	private class SMSRow {
		private final SMSStatus status;
		private final SMSDeliver smsDeliver;
		
		private SMSRow(SMSStatus s, SMSDeliver m) {
			this.status = s;
			this.smsDeliver = m;
		}
	}
	
	private AbstractATSMSModem modem;
	
	ModemReceiverParser(AbstractATSMSModem m, String input) {
   		this(new CommonTokenStream(
 				new ModemReceiverLexer(
 		 				new ANTLRStringStream( input ) ) ) );
   		this.modem = m;
   	}
}

start
	: frame*
	;

frame
	: '>' { if (modem != null) modem.notifyReceiveReadyListener(); }
	| standardMessage
	;

standardMessage
	: extendedResult
	| standardResult
	;

standardResult
	: 'OK' 		{ if (modem != null) modem.setATCommandSRC(StandardResponseCode.Ok);	} 
	| 'ERROR'	{ if (modem != null) modem.setATCommandSRC(StandardResponseCode.Error); }
	;
	
extendedResult
	: '+' extendedResultSet ;

extendedResultSet
	: smsUnrequestedResult
	| smsRequestedResult
	;

smsRequestedResult
	: 'CMG' smsInputCmdType ':' INT { if (modem != null) modem.setSMSInputCmdModemRef( (byte)Integer.parseInt( $INT.text ) ); }
	| 'CMGR:' 		  smsRow { if (modem != null) modem.notifySMSRowReader( null, $smsRow.value.status, $smsRow.value.smsDeliver ); }
	| 'CMGL:' INT ',' smsRow { if (modem != null) modem.notifySMSRowReader( Integer.parseInt( $INT.text ), $smsRow.value.status, $smsRow.value.smsDeliver ); }
	| 'CMS ERROR:' INT {
			if (modem != null)
				modem.setSMSInputCmdSendError(
					CMSError.getCMSError(
						Integer.parseInt( $INT.text ) ) );
		}
	;

smsInputCmdType
	: 'S'
	| 'W'
	;

smsRow returns [SMSRow value]
	:	smsStatus ',' alpha=INT* ',' length=INT HEX { value = new SMSRow($smsStatus.value, new SMSDeliver(HexBin.decode($HEX.text))); }
	;
	
smsStatus returns [SMSStatus value]
	: '"REC UNREAD"'	{ value = SMSStatus.ReceivedUnread; }
	| '"REC READ"'		{ value = SMSStatus.ReceivedRead; }
	| '"STO UNSENT"'	{ value = SMSStatus.StoredUnsent; }
	| '"STO SENT"'		{ value = SMSStatus.StoredSent; }
	| '"ALL"'			{ value = SMSStatus.All; }
	| INT				{ value = NamedInt.getNamedInt(SMSStatus.values(), Integer.parseInt( $INT.text )); }
	;

smsUnrequestedResult
	: 'CMTI:' smsMemoryType ',' INT { if (modem != null) modem.notifyNewMsgIndicationListener($smsMemoryType.value, Integer.parseInt( $INT.text )); }
	| 'CDS:' INT HEX { if (modem != null) modem.notifySMSStatusReportListeners(new SMSStatusReport( HexBin.decode( $HEX.text ) ) ); }
	;
	
smsMemoryType returns [SMSStorageArea value]
	: '"SM"' { value = SMSStorageArea.SIMCard; }
	| '"ME"' { value = SMSStorageArea.MobileEquipment; }
	| '"MT"' { value = SMSStorageArea.AllMobileAvailable; }
	| '"BM"' { value = SMSStorageArea.Broadcast; }
	| '"SR"' { value = SMSStorageArea.StatusReport; }
	| '"TA"' { value = SMSStorageArea.TerminalAdaptor; }
	;

INT		: ('0'..'9')+ ;
HEX		: (('0'..'9') | ('A'..'F') | ('a'..'f'))+ ;
WS		: (' ' | '\r' | '\n' | '\t')+ { skip(); } ;

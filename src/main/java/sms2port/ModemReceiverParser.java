// $ANTLR 3.5.2 ModemReceiver.g 2017-04-30 20:58:28

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


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class ModemReceiverParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "HEX", "INT", "WS", "'\"ALL\"'", 
		"'\"BM\"'", "'\"ME\"'", "'\"MT\"'", "'\"REC READ\"'", "'\"REC UNREAD\"'", 
		"'\"SM\"'", "'\"SR\"'", "'\"STO SENT\"'", "'\"STO UNSENT\"'", "'\"TA\"'", 
		"'+'", "','", "':'", "'>'", "'CDS:'", "'CMG'", "'CMGL:'", "'CMGR:'", "'CMS ERROR:'", 
		"'CMTI:'", "'ERROR'", "'OK'", "'S'", "'W'"
	};
	public static final int EOF=-1;
	public static final int T__7=7;
	public static final int T__8=8;
	public static final int T__9=9;
	public static final int T__10=10;
	public static final int T__11=11;
	public static final int T__12=12;
	public static final int T__13=13;
	public static final int T__14=14;
	public static final int T__15=15;
	public static final int T__16=16;
	public static final int T__17=17;
	public static final int T__18=18;
	public static final int T__19=19;
	public static final int T__20=20;
	public static final int T__21=21;
	public static final int T__22=22;
	public static final int T__23=23;
	public static final int T__24=24;
	public static final int T__25=25;
	public static final int T__26=26;
	public static final int T__27=27;
	public static final int T__28=28;
	public static final int T__29=29;
	public static final int T__30=30;
	public static final int T__31=31;
	public static final int HEX=4;
	public static final int INT=5;
	public static final int WS=6;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public ModemReceiverParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public ModemReceiverParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return ModemReceiverParser.tokenNames; }
	@Override public String getGrammarFileName() { return "ModemReceiver.g"; }


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



	// $ANTLR start "start"
	// ModemReceiver.g:45:1: start : ( frame )* ;
	public final void start() throws RecognitionException {
		try {
			// ModemReceiver.g:46:2: ( ( frame )* )
			// ModemReceiver.g:46:4: ( frame )*
			{
			// ModemReceiver.g:46:4: ( frame )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==18||LA1_0==21||(LA1_0 >= 28 && LA1_0 <= 29)) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// ModemReceiver.g:46:4: frame
					{
					pushFollow(FOLLOW_frame_in_start33);
					frame();
					state._fsp--;

					}
					break;

				default :
					break loop1;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "start"



	// $ANTLR start "frame"
	// ModemReceiver.g:49:1: frame : ( '>' | standardMessage );
	public final void frame() throws RecognitionException {
		try {
			// ModemReceiver.g:50:2: ( '>' | standardMessage )
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0==21) ) {
				alt2=1;
			}
			else if ( (LA2_0==18||(LA2_0 >= 28 && LA2_0 <= 29)) ) {
				alt2=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 2, 0, input);
				throw nvae;
			}

			switch (alt2) {
				case 1 :
					// ModemReceiver.g:50:4: '>'
					{
					match(input,21,FOLLOW_21_in_frame45); 
					 if (modem != null) modem.notifyReceiveReadyListener(); 
					}
					break;
				case 2 :
					// ModemReceiver.g:51:4: standardMessage
					{
					pushFollow(FOLLOW_standardMessage_in_frame52);
					standardMessage();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "frame"



	// $ANTLR start "standardMessage"
	// ModemReceiver.g:54:1: standardMessage : ( extendedResult | standardResult );
	public final void standardMessage() throws RecognitionException {
		try {
			// ModemReceiver.g:55:2: ( extendedResult | standardResult )
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( (LA3_0==18) ) {
				alt3=1;
			}
			else if ( ((LA3_0 >= 28 && LA3_0 <= 29)) ) {
				alt3=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 3, 0, input);
				throw nvae;
			}

			switch (alt3) {
				case 1 :
					// ModemReceiver.g:55:4: extendedResult
					{
					pushFollow(FOLLOW_extendedResult_in_standardMessage63);
					extendedResult();
					state._fsp--;

					}
					break;
				case 2 :
					// ModemReceiver.g:56:4: standardResult
					{
					pushFollow(FOLLOW_standardResult_in_standardMessage68);
					standardResult();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "standardMessage"



	// $ANTLR start "standardResult"
	// ModemReceiver.g:59:1: standardResult : ( 'OK' | 'ERROR' );
	public final void standardResult() throws RecognitionException {
		try {
			// ModemReceiver.g:60:2: ( 'OK' | 'ERROR' )
			int alt4=2;
			int LA4_0 = input.LA(1);
			if ( (LA4_0==29) ) {
				alt4=1;
			}
			else if ( (LA4_0==28) ) {
				alt4=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 4, 0, input);
				throw nvae;
			}

			switch (alt4) {
				case 1 :
					// ModemReceiver.g:60:4: 'OK'
					{
					match(input,29,FOLLOW_29_in_standardResult79); 
					 if (modem != null) modem.setATCommandSRC(StandardResponseCode.Ok);	
					}
					break;
				case 2 :
					// ModemReceiver.g:61:4: 'ERROR'
					{
					match(input,28,FOLLOW_28_in_standardResult89); 
					 if (modem != null) modem.setATCommandSRC(StandardResponseCode.Error); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "standardResult"



	// $ANTLR start "extendedResult"
	// ModemReceiver.g:64:1: extendedResult : '+' extendedResultSet ;
	public final void extendedResult() throws RecognitionException {
		try {
			// ModemReceiver.g:65:2: ( '+' extendedResultSet )
			// ModemReceiver.g:65:4: '+' extendedResultSet
			{
			match(input,18,FOLLOW_18_in_extendedResult103); 
			pushFollow(FOLLOW_extendedResultSet_in_extendedResult105);
			extendedResultSet();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "extendedResult"



	// $ANTLR start "extendedResultSet"
	// ModemReceiver.g:67:1: extendedResultSet : ( smsUnrequestedResult | smsRequestedResult );
	public final void extendedResultSet() throws RecognitionException {
		try {
			// ModemReceiver.g:68:2: ( smsUnrequestedResult | smsRequestedResult )
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( (LA5_0==22||LA5_0==27) ) {
				alt5=1;
			}
			else if ( ((LA5_0 >= 23 && LA5_0 <= 26)) ) {
				alt5=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 5, 0, input);
				throw nvae;
			}

			switch (alt5) {
				case 1 :
					// ModemReceiver.g:68:4: smsUnrequestedResult
					{
					pushFollow(FOLLOW_smsUnrequestedResult_in_extendedResultSet115);
					smsUnrequestedResult();
					state._fsp--;

					}
					break;
				case 2 :
					// ModemReceiver.g:69:4: smsRequestedResult
					{
					pushFollow(FOLLOW_smsRequestedResult_in_extendedResultSet120);
					smsRequestedResult();
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "extendedResultSet"



	// $ANTLR start "smsRequestedResult"
	// ModemReceiver.g:72:1: smsRequestedResult : ( 'CMG' smsInputCmdType ':' INT | 'CMGR:' smsRow | 'CMGL:' INT ',' smsRow | 'CMS ERROR:' INT );
	public final void smsRequestedResult() throws RecognitionException {
		Token INT1=null;
		Token INT3=null;
		Token INT5=null;
		SMSRow smsRow2 =null;
		SMSRow smsRow4 =null;

		try {
			// ModemReceiver.g:73:2: ( 'CMG' smsInputCmdType ':' INT | 'CMGR:' smsRow | 'CMGL:' INT ',' smsRow | 'CMS ERROR:' INT )
			int alt6=4;
			switch ( input.LA(1) ) {
			case 23:
				{
				alt6=1;
				}
				break;
			case 25:
				{
				alt6=2;
				}
				break;
			case 24:
				{
				alt6=3;
				}
				break;
			case 26:
				{
				alt6=4;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 6, 0, input);
				throw nvae;
			}
			switch (alt6) {
				case 1 :
					// ModemReceiver.g:73:4: 'CMG' smsInputCmdType ':' INT
					{
					match(input,23,FOLLOW_23_in_smsRequestedResult131); 
					pushFollow(FOLLOW_smsInputCmdType_in_smsRequestedResult133);
					smsInputCmdType();
					state._fsp--;

					match(input,20,FOLLOW_20_in_smsRequestedResult135); 
					INT1=(Token)match(input,INT,FOLLOW_INT_in_smsRequestedResult137); 
					 if (modem != null) modem.setSMSInputCmdModemRef( (byte)Integer.parseInt( (INT1!=null?INT1.getText():null) ) ); 
					}
					break;
				case 2 :
					// ModemReceiver.g:74:4: 'CMGR:' smsRow
					{
					match(input,25,FOLLOW_25_in_smsRequestedResult144); 
					pushFollow(FOLLOW_smsRow_in_smsRequestedResult150);
					smsRow2=smsRow();
					state._fsp--;

					 if (modem != null) modem.notifySMSRowReader( null, smsRow2.status, smsRow2.smsDeliver ); 
					}
					break;
				case 3 :
					// ModemReceiver.g:75:4: 'CMGL:' INT ',' smsRow
					{
					match(input,24,FOLLOW_24_in_smsRequestedResult157); 
					INT3=(Token)match(input,INT,FOLLOW_INT_in_smsRequestedResult159); 
					match(input,19,FOLLOW_19_in_smsRequestedResult161); 
					pushFollow(FOLLOW_smsRow_in_smsRequestedResult163);
					smsRow4=smsRow();
					state._fsp--;

					 if (modem != null) modem.notifySMSRowReader( Integer.parseInt( (INT3!=null?INT3.getText():null) ), smsRow4.status, smsRow4.smsDeliver ); 
					}
					break;
				case 4 :
					// ModemReceiver.g:76:4: 'CMS ERROR:' INT
					{
					match(input,26,FOLLOW_26_in_smsRequestedResult170); 
					INT5=(Token)match(input,INT,FOLLOW_INT_in_smsRequestedResult172); 

								if (modem != null)
									modem.setSMSInputCmdSendError(
										CMSError.getCMSError(
											Integer.parseInt( (INT5!=null?INT5.getText():null) ) ) );
							
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "smsRequestedResult"



	// $ANTLR start "smsInputCmdType"
	// ModemReceiver.g:84:1: smsInputCmdType : ( 'S' | 'W' );
	public final void smsInputCmdType() throws RecognitionException {
		try {
			// ModemReceiver.g:85:2: ( 'S' | 'W' )
			// ModemReceiver.g:
			{
			if ( (input.LA(1) >= 30 && input.LA(1) <= 31) ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "smsInputCmdType"



	// $ANTLR start "smsRow"
	// ModemReceiver.g:89:1: smsRow returns [SMSRow value] : smsStatus ',' (alpha= INT )* ',' length= INT HEX ;
	public final SMSRow smsRow() throws RecognitionException {
		SMSRow value = null;


		Token alpha=null;
		Token length=null;
		Token HEX7=null;
		SMSStatus smsStatus6 =null;

		try {
			// ModemReceiver.g:90:2: ( smsStatus ',' (alpha= INT )* ',' length= INT HEX )
			// ModemReceiver.g:90:4: smsStatus ',' (alpha= INT )* ',' length= INT HEX
			{
			pushFollow(FOLLOW_smsStatus_in_smsRow205);
			smsStatus6=smsStatus();
			state._fsp--;

			match(input,19,FOLLOW_19_in_smsRow207); 
			// ModemReceiver.g:90:23: (alpha= INT )*
			loop7:
			while (true) {
				int alt7=2;
				int LA7_0 = input.LA(1);
				if ( (LA7_0==INT) ) {
					alt7=1;
				}

				switch (alt7) {
				case 1 :
					// ModemReceiver.g:90:23: alpha= INT
					{
					alpha=(Token)match(input,INT,FOLLOW_INT_in_smsRow211); 
					}
					break;

				default :
					break loop7;
				}
			}

			match(input,19,FOLLOW_19_in_smsRow214); 
			length=(Token)match(input,INT,FOLLOW_INT_in_smsRow218); 
			HEX7=(Token)match(input,HEX,FOLLOW_HEX_in_smsRow220); 
			 value = new SMSRow(smsStatus6, new SMSDeliver(HexBin.decode((HEX7!=null?HEX7.getText():null)))); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "smsRow"



	// $ANTLR start "smsStatus"
	// ModemReceiver.g:93:1: smsStatus returns [SMSStatus value] : ( '\"REC UNREAD\"' | '\"REC READ\"' | '\"STO UNSENT\"' | '\"STO SENT\"' | '\"ALL\"' | INT );
	public final SMSStatus smsStatus() throws RecognitionException {
		SMSStatus value = null;


		Token INT8=null;

		try {
			// ModemReceiver.g:94:2: ( '\"REC UNREAD\"' | '\"REC READ\"' | '\"STO UNSENT\"' | '\"STO SENT\"' | '\"ALL\"' | INT )
			int alt8=6;
			switch ( input.LA(1) ) {
			case 12:
				{
				alt8=1;
				}
				break;
			case 11:
				{
				alt8=2;
				}
				break;
			case 16:
				{
				alt8=3;
				}
				break;
			case 15:
				{
				alt8=4;
				}
				break;
			case 7:
				{
				alt8=5;
				}
				break;
			case INT:
				{
				alt8=6;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 8, 0, input);
				throw nvae;
			}
			switch (alt8) {
				case 1 :
					// ModemReceiver.g:94:4: '\"REC UNREAD\"'
					{
					match(input,12,FOLLOW_12_in_smsStatus238); 
					 value = SMSStatus.ReceivedUnread; 
					}
					break;
				case 2 :
					// ModemReceiver.g:95:4: '\"REC READ\"'
					{
					match(input,11,FOLLOW_11_in_smsStatus245); 
					 value = SMSStatus.ReceivedRead; 
					}
					break;
				case 3 :
					// ModemReceiver.g:96:4: '\"STO UNSENT\"'
					{
					match(input,16,FOLLOW_16_in_smsStatus253); 
					 value = SMSStatus.StoredUnsent; 
					}
					break;
				case 4 :
					// ModemReceiver.g:97:4: '\"STO SENT\"'
					{
					match(input,15,FOLLOW_15_in_smsStatus260); 
					 value = SMSStatus.StoredSent; 
					}
					break;
				case 5 :
					// ModemReceiver.g:98:4: '\"ALL\"'
					{
					match(input,7,FOLLOW_7_in_smsStatus268); 
					 value = SMSStatus.All; 
					}
					break;
				case 6 :
					// ModemReceiver.g:99:4: INT
					{
					INT8=(Token)match(input,INT,FOLLOW_INT_in_smsStatus277); 
					 value = NamedInt.getNamedInt(SMSStatus.values(), Integer.parseInt( (INT8!=null?INT8.getText():null) )); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "smsStatus"



	// $ANTLR start "smsUnrequestedResult"
	// ModemReceiver.g:102:1: smsUnrequestedResult : ( 'CMTI:' smsMemoryType ',' INT | 'CDS:' INT HEX );
	public final void smsUnrequestedResult() throws RecognitionException {
		Token INT10=null;
		Token HEX11=null;
		SMSStorageArea smsMemoryType9 =null;

		try {
			// ModemReceiver.g:103:2: ( 'CMTI:' smsMemoryType ',' INT | 'CDS:' INT HEX )
			int alt9=2;
			int LA9_0 = input.LA(1);
			if ( (LA9_0==27) ) {
				alt9=1;
			}
			else if ( (LA9_0==22) ) {
				alt9=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 9, 0, input);
				throw nvae;
			}

			switch (alt9) {
				case 1 :
					// ModemReceiver.g:103:4: 'CMTI:' smsMemoryType ',' INT
					{
					match(input,27,FOLLOW_27_in_smsUnrequestedResult293); 
					pushFollow(FOLLOW_smsMemoryType_in_smsUnrequestedResult295);
					smsMemoryType9=smsMemoryType();
					state._fsp--;

					match(input,19,FOLLOW_19_in_smsUnrequestedResult297); 
					INT10=(Token)match(input,INT,FOLLOW_INT_in_smsUnrequestedResult299); 
					 if (modem != null) modem.notifyNewMsgIndicationListener(smsMemoryType9, Integer.parseInt( (INT10!=null?INT10.getText():null) )); 
					}
					break;
				case 2 :
					// ModemReceiver.g:104:4: 'CDS:' INT HEX
					{
					match(input,22,FOLLOW_22_in_smsUnrequestedResult306); 
					match(input,INT,FOLLOW_INT_in_smsUnrequestedResult308); 
					HEX11=(Token)match(input,HEX,FOLLOW_HEX_in_smsUnrequestedResult310); 
					 if (modem != null) modem.notifySMSStatusReportListeners(new SMSStatusReport( HexBin.decode( (HEX11!=null?HEX11.getText():null) ) ) ); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "smsUnrequestedResult"



	// $ANTLR start "smsMemoryType"
	// ModemReceiver.g:107:1: smsMemoryType returns [SMSStorageArea value] : ( '\"SM\"' | '\"ME\"' | '\"MT\"' | '\"BM\"' | '\"SR\"' | '\"TA\"' );
	public final SMSStorageArea smsMemoryType() throws RecognitionException {
		SMSStorageArea value = null;


		try {
			// ModemReceiver.g:108:2: ( '\"SM\"' | '\"ME\"' | '\"MT\"' | '\"BM\"' | '\"SR\"' | '\"TA\"' )
			int alt10=6;
			switch ( input.LA(1) ) {
			case 13:
				{
				alt10=1;
				}
				break;
			case 9:
				{
				alt10=2;
				}
				break;
			case 10:
				{
				alt10=3;
				}
				break;
			case 8:
				{
				alt10=4;
				}
				break;
			case 14:
				{
				alt10=5;
				}
				break;
			case 17:
				{
				alt10=6;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 10, 0, input);
				throw nvae;
			}
			switch (alt10) {
				case 1 :
					// ModemReceiver.g:108:4: '\"SM\"'
					{
					match(input,13,FOLLOW_13_in_smsMemoryType328); 
					 value = SMSStorageArea.SIMCard; 
					}
					break;
				case 2 :
					// ModemReceiver.g:109:4: '\"ME\"'
					{
					match(input,9,FOLLOW_9_in_smsMemoryType335); 
					 value = SMSStorageArea.MobileEquipment; 
					}
					break;
				case 3 :
					// ModemReceiver.g:110:4: '\"MT\"'
					{
					match(input,10,FOLLOW_10_in_smsMemoryType342); 
					 value = SMSStorageArea.AllMobileAvailable; 
					}
					break;
				case 4 :
					// ModemReceiver.g:111:4: '\"BM\"'
					{
					match(input,8,FOLLOW_8_in_smsMemoryType349); 
					 value = SMSStorageArea.Broadcast; 
					}
					break;
				case 5 :
					// ModemReceiver.g:112:4: '\"SR\"'
					{
					match(input,14,FOLLOW_14_in_smsMemoryType356); 
					 value = SMSStorageArea.StatusReport; 
					}
					break;
				case 6 :
					// ModemReceiver.g:113:4: '\"TA\"'
					{
					match(input,17,FOLLOW_17_in_smsMemoryType363); 
					 value = SMSStorageArea.TerminalAdaptor; 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "smsMemoryType"

	// Delegated rules



	public static final BitSet FOLLOW_frame_in_start33 = new BitSet(new long[]{0x0000000030240002L});
	public static final BitSet FOLLOW_21_in_frame45 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_standardMessage_in_frame52 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_extendedResult_in_standardMessage63 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_standardResult_in_standardMessage68 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_29_in_standardResult79 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_28_in_standardResult89 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_18_in_extendedResult103 = new BitSet(new long[]{0x000000000FC00000L});
	public static final BitSet FOLLOW_extendedResultSet_in_extendedResult105 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_smsUnrequestedResult_in_extendedResultSet115 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_smsRequestedResult_in_extendedResultSet120 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_23_in_smsRequestedResult131 = new BitSet(new long[]{0x00000000C0000000L});
	public static final BitSet FOLLOW_smsInputCmdType_in_smsRequestedResult133 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_20_in_smsRequestedResult135 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_INT_in_smsRequestedResult137 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_25_in_smsRequestedResult144 = new BitSet(new long[]{0x00000000000198A0L});
	public static final BitSet FOLLOW_smsRow_in_smsRequestedResult150 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_24_in_smsRequestedResult157 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_INT_in_smsRequestedResult159 = new BitSet(new long[]{0x0000000000080000L});
	public static final BitSet FOLLOW_19_in_smsRequestedResult161 = new BitSet(new long[]{0x00000000000198A0L});
	public static final BitSet FOLLOW_smsRow_in_smsRequestedResult163 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_26_in_smsRequestedResult170 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_INT_in_smsRequestedResult172 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_smsStatus_in_smsRow205 = new BitSet(new long[]{0x0000000000080000L});
	public static final BitSet FOLLOW_19_in_smsRow207 = new BitSet(new long[]{0x0000000000080020L});
	public static final BitSet FOLLOW_INT_in_smsRow211 = new BitSet(new long[]{0x0000000000080020L});
	public static final BitSet FOLLOW_19_in_smsRow214 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_INT_in_smsRow218 = new BitSet(new long[]{0x0000000000000010L});
	public static final BitSet FOLLOW_HEX_in_smsRow220 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_12_in_smsStatus238 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_11_in_smsStatus245 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_16_in_smsStatus253 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_15_in_smsStatus260 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_7_in_smsStatus268 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INT_in_smsStatus277 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_27_in_smsUnrequestedResult293 = new BitSet(new long[]{0x0000000000026700L});
	public static final BitSet FOLLOW_smsMemoryType_in_smsUnrequestedResult295 = new BitSet(new long[]{0x0000000000080000L});
	public static final BitSet FOLLOW_19_in_smsUnrequestedResult297 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_INT_in_smsUnrequestedResult299 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_22_in_smsUnrequestedResult306 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_INT_in_smsUnrequestedResult308 = new BitSet(new long[]{0x0000000000000010L});
	public static final BitSet FOLLOW_HEX_in_smsUnrequestedResult310 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_13_in_smsMemoryType328 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_9_in_smsMemoryType335 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_10_in_smsMemoryType342 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_8_in_smsMemoryType349 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_14_in_smsMemoryType356 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_17_in_smsMemoryType363 = new BitSet(new long[]{0x0000000000000002L});
}

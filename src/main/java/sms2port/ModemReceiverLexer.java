// $ANTLR 3.5.2 ModemReceiver.g 2017-04-30 20:58:28

	package sms2port;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class ModemReceiverLexer extends Lexer {
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
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public ModemReceiverLexer() {} 
	public ModemReceiverLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public ModemReceiverLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "ModemReceiver.g"; }

	// $ANTLR start "T__7"
	public final void mT__7() throws RecognitionException {
		try {
			int _type = T__7;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:6:6: ( '\"ALL\"' )
			// ModemReceiver.g:6:8: '\"ALL\"'
			{
			match("\"ALL\""); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__7"

	// $ANTLR start "T__8"
	public final void mT__8() throws RecognitionException {
		try {
			int _type = T__8;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:7:6: ( '\"BM\"' )
			// ModemReceiver.g:7:8: '\"BM\"'
			{
			match("\"BM\""); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__8"

	// $ANTLR start "T__9"
	public final void mT__9() throws RecognitionException {
		try {
			int _type = T__9;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:8:6: ( '\"ME\"' )
			// ModemReceiver.g:8:8: '\"ME\"'
			{
			match("\"ME\""); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__9"

	// $ANTLR start "T__10"
	public final void mT__10() throws RecognitionException {
		try {
			int _type = T__10;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:9:7: ( '\"MT\"' )
			// ModemReceiver.g:9:9: '\"MT\"'
			{
			match("\"MT\""); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__10"

	// $ANTLR start "T__11"
	public final void mT__11() throws RecognitionException {
		try {
			int _type = T__11;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:10:7: ( '\"REC READ\"' )
			// ModemReceiver.g:10:9: '\"REC READ\"'
			{
			match("\"REC READ\""); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__11"

	// $ANTLR start "T__12"
	public final void mT__12() throws RecognitionException {
		try {
			int _type = T__12;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:11:7: ( '\"REC UNREAD\"' )
			// ModemReceiver.g:11:9: '\"REC UNREAD\"'
			{
			match("\"REC UNREAD\""); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__12"

	// $ANTLR start "T__13"
	public final void mT__13() throws RecognitionException {
		try {
			int _type = T__13;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:12:7: ( '\"SM\"' )
			// ModemReceiver.g:12:9: '\"SM\"'
			{
			match("\"SM\""); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__13"

	// $ANTLR start "T__14"
	public final void mT__14() throws RecognitionException {
		try {
			int _type = T__14;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:13:7: ( '\"SR\"' )
			// ModemReceiver.g:13:9: '\"SR\"'
			{
			match("\"SR\""); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__14"

	// $ANTLR start "T__15"
	public final void mT__15() throws RecognitionException {
		try {
			int _type = T__15;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:14:7: ( '\"STO SENT\"' )
			// ModemReceiver.g:14:9: '\"STO SENT\"'
			{
			match("\"STO SENT\""); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__15"

	// $ANTLR start "T__16"
	public final void mT__16() throws RecognitionException {
		try {
			int _type = T__16;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:15:7: ( '\"STO UNSENT\"' )
			// ModemReceiver.g:15:9: '\"STO UNSENT\"'
			{
			match("\"STO UNSENT\""); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__16"

	// $ANTLR start "T__17"
	public final void mT__17() throws RecognitionException {
		try {
			int _type = T__17;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:16:7: ( '\"TA\"' )
			// ModemReceiver.g:16:9: '\"TA\"'
			{
			match("\"TA\""); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__17"

	// $ANTLR start "T__18"
	public final void mT__18() throws RecognitionException {
		try {
			int _type = T__18;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:17:7: ( '+' )
			// ModemReceiver.g:17:9: '+'
			{
			match('+'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__18"

	// $ANTLR start "T__19"
	public final void mT__19() throws RecognitionException {
		try {
			int _type = T__19;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:18:7: ( ',' )
			// ModemReceiver.g:18:9: ','
			{
			match(','); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__19"

	// $ANTLR start "T__20"
	public final void mT__20() throws RecognitionException {
		try {
			int _type = T__20;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:19:7: ( ':' )
			// ModemReceiver.g:19:9: ':'
			{
			match(':'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__20"

	// $ANTLR start "T__21"
	public final void mT__21() throws RecognitionException {
		try {
			int _type = T__21;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:20:7: ( '>' )
			// ModemReceiver.g:20:9: '>'
			{
			match('>'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__21"

	// $ANTLR start "T__22"
	public final void mT__22() throws RecognitionException {
		try {
			int _type = T__22;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:21:7: ( 'CDS:' )
			// ModemReceiver.g:21:9: 'CDS:'
			{
			match("CDS:"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__22"

	// $ANTLR start "T__23"
	public final void mT__23() throws RecognitionException {
		try {
			int _type = T__23;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:22:7: ( 'CMG' )
			// ModemReceiver.g:22:9: 'CMG'
			{
			match("CMG"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__23"

	// $ANTLR start "T__24"
	public final void mT__24() throws RecognitionException {
		try {
			int _type = T__24;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:23:7: ( 'CMGL:' )
			// ModemReceiver.g:23:9: 'CMGL:'
			{
			match("CMGL:"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__24"

	// $ANTLR start "T__25"
	public final void mT__25() throws RecognitionException {
		try {
			int _type = T__25;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:24:7: ( 'CMGR:' )
			// ModemReceiver.g:24:9: 'CMGR:'
			{
			match("CMGR:"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__25"

	// $ANTLR start "T__26"
	public final void mT__26() throws RecognitionException {
		try {
			int _type = T__26;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:25:7: ( 'CMS ERROR:' )
			// ModemReceiver.g:25:9: 'CMS ERROR:'
			{
			match("CMS ERROR:"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__26"

	// $ANTLR start "T__27"
	public final void mT__27() throws RecognitionException {
		try {
			int _type = T__27;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:26:7: ( 'CMTI:' )
			// ModemReceiver.g:26:9: 'CMTI:'
			{
			match("CMTI:"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__27"

	// $ANTLR start "T__28"
	public final void mT__28() throws RecognitionException {
		try {
			int _type = T__28;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:27:7: ( 'ERROR' )
			// ModemReceiver.g:27:9: 'ERROR'
			{
			match("ERROR"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__28"

	// $ANTLR start "T__29"
	public final void mT__29() throws RecognitionException {
		try {
			int _type = T__29;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:28:7: ( 'OK' )
			// ModemReceiver.g:28:9: 'OK'
			{
			match("OK"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__29"

	// $ANTLR start "T__30"
	public final void mT__30() throws RecognitionException {
		try {
			int _type = T__30;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:29:7: ( 'S' )
			// ModemReceiver.g:29:9: 'S'
			{
			match('S'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__30"

	// $ANTLR start "T__31"
	public final void mT__31() throws RecognitionException {
		try {
			int _type = T__31;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:30:7: ( 'W' )
			// ModemReceiver.g:30:9: 'W'
			{
			match('W'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__31"

	// $ANTLR start "INT"
	public final void mINT() throws RecognitionException {
		try {
			int _type = INT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:116:6: ( ( '0' .. '9' )+ )
			// ModemReceiver.g:116:8: ( '0' .. '9' )+
			{
			// ModemReceiver.g:116:8: ( '0' .. '9' )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( ((LA1_0 >= '0' && LA1_0 <= '9')) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// ModemReceiver.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt1 >= 1 ) break loop1;
					EarlyExitException eee = new EarlyExitException(1, input);
					throw eee;
				}
				cnt1++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INT"

	// $ANTLR start "HEX"
	public final void mHEX() throws RecognitionException {
		try {
			int _type = HEX;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:117:6: ( ( ( '0' .. '9' ) | ( 'A' .. 'F' ) | ( 'a' .. 'f' ) )+ )
			// ModemReceiver.g:117:8: ( ( '0' .. '9' ) | ( 'A' .. 'F' ) | ( 'a' .. 'f' ) )+
			{
			// ModemReceiver.g:117:8: ( ( '0' .. '9' ) | ( 'A' .. 'F' ) | ( 'a' .. 'f' ) )+
			int cnt2=0;
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( ((LA2_0 >= '0' && LA2_0 <= '9')||(LA2_0 >= 'A' && LA2_0 <= 'F')||(LA2_0 >= 'a' && LA2_0 <= 'f')) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// ModemReceiver.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'F')||(input.LA(1) >= 'a' && input.LA(1) <= 'f') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt2 >= 1 ) break loop2;
					EarlyExitException eee = new EarlyExitException(2, input);
					throw eee;
				}
				cnt2++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "HEX"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// ModemReceiver.g:118:5: ( ( ' ' | '\\r' | '\\n' | '\\t' )+ )
			// ModemReceiver.g:118:7: ( ' ' | '\\r' | '\\n' | '\\t' )+
			{
			// ModemReceiver.g:118:7: ( ' ' | '\\r' | '\\n' | '\\t' )+
			int cnt3=0;
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( ((LA3_0 >= '\t' && LA3_0 <= '\n')||LA3_0=='\r'||LA3_0==' ') ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// ModemReceiver.g:
					{
					if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt3 >= 1 ) break loop3;
					EarlyExitException eee = new EarlyExitException(3, input);
					throw eee;
				}
				cnt3++;
			}

			 skip(); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	@Override
	public void mTokens() throws RecognitionException {
		// ModemReceiver.g:1:8: ( T__7 | T__8 | T__9 | T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | INT | HEX | WS )
		int alt4=28;
		alt4 = dfa4.predict(input);
		switch (alt4) {
			case 1 :
				// ModemReceiver.g:1:10: T__7
				{
				mT__7(); 

				}
				break;
			case 2 :
				// ModemReceiver.g:1:15: T__8
				{
				mT__8(); 

				}
				break;
			case 3 :
				// ModemReceiver.g:1:20: T__9
				{
				mT__9(); 

				}
				break;
			case 4 :
				// ModemReceiver.g:1:25: T__10
				{
				mT__10(); 

				}
				break;
			case 5 :
				// ModemReceiver.g:1:31: T__11
				{
				mT__11(); 

				}
				break;
			case 6 :
				// ModemReceiver.g:1:37: T__12
				{
				mT__12(); 

				}
				break;
			case 7 :
				// ModemReceiver.g:1:43: T__13
				{
				mT__13(); 

				}
				break;
			case 8 :
				// ModemReceiver.g:1:49: T__14
				{
				mT__14(); 

				}
				break;
			case 9 :
				// ModemReceiver.g:1:55: T__15
				{
				mT__15(); 

				}
				break;
			case 10 :
				// ModemReceiver.g:1:61: T__16
				{
				mT__16(); 

				}
				break;
			case 11 :
				// ModemReceiver.g:1:67: T__17
				{
				mT__17(); 

				}
				break;
			case 12 :
				// ModemReceiver.g:1:73: T__18
				{
				mT__18(); 

				}
				break;
			case 13 :
				// ModemReceiver.g:1:79: T__19
				{
				mT__19(); 

				}
				break;
			case 14 :
				// ModemReceiver.g:1:85: T__20
				{
				mT__20(); 

				}
				break;
			case 15 :
				// ModemReceiver.g:1:91: T__21
				{
				mT__21(); 

				}
				break;
			case 16 :
				// ModemReceiver.g:1:97: T__22
				{
				mT__22(); 

				}
				break;
			case 17 :
				// ModemReceiver.g:1:103: T__23
				{
				mT__23(); 

				}
				break;
			case 18 :
				// ModemReceiver.g:1:109: T__24
				{
				mT__24(); 

				}
				break;
			case 19 :
				// ModemReceiver.g:1:115: T__25
				{
				mT__25(); 

				}
				break;
			case 20 :
				// ModemReceiver.g:1:121: T__26
				{
				mT__26(); 

				}
				break;
			case 21 :
				// ModemReceiver.g:1:127: T__27
				{
				mT__27(); 

				}
				break;
			case 22 :
				// ModemReceiver.g:1:133: T__28
				{
				mT__28(); 

				}
				break;
			case 23 :
				// ModemReceiver.g:1:139: T__29
				{
				mT__29(); 

				}
				break;
			case 24 :
				// ModemReceiver.g:1:145: T__30
				{
				mT__30(); 

				}
				break;
			case 25 :
				// ModemReceiver.g:1:151: T__31
				{
				mT__31(); 

				}
				break;
			case 26 :
				// ModemReceiver.g:1:157: INT
				{
				mINT(); 

				}
				break;
			case 27 :
				// ModemReceiver.g:1:161: HEX
				{
				mHEX(); 

				}
				break;
			case 28 :
				// ModemReceiver.g:1:165: WS
				{
				mWS(); 

				}
				break;

		}
	}


	protected DFA4 dfa4 = new DFA4(this);
	static final String DFA4_eotS =
		"\6\uffff\2\14\3\uffff\1\27\10\uffff\1\14\12\uffff\1\46\15\uffff";
	static final String DFA4_eofS =
		"\55\uffff";
	static final String DFA4_minS =
		"\1\11\1\101\4\uffff\1\104\1\122\3\uffff\1\60\4\uffff\2\105\1\115\1\uffff"+
		"\1\123\1\107\4\uffff\1\103\2\uffff\1\117\1\uffff\1\114\2\uffff\2\40\3"+
		"\uffff\1\122\1\123\4\uffff";
	static final String DFA4_maxS =
		"\1\146\1\124\4\uffff\1\115\1\122\3\uffff\1\146\4\uffff\1\124\1\105\1\124"+
		"\1\uffff\1\123\1\124\4\uffff\1\103\2\uffff\1\117\1\uffff\1\122\2\uffff"+
		"\2\40\3\uffff\2\125\4\uffff";
	static final String DFA4_acceptS =
		"\2\uffff\1\14\1\15\1\16\1\17\2\uffff\1\27\1\30\1\31\1\uffff\1\33\1\34"+
		"\1\1\1\2\3\uffff\1\13\2\uffff\1\26\1\32\1\3\1\4\1\uffff\1\7\1\10\1\uffff"+
		"\1\20\1\uffff\1\24\1\25\2\uffff\1\22\1\23\1\21\2\uffff\1\5\1\6\1\11\1"+
		"\12";
	static final String DFA4_specialS =
		"\55\uffff}>";
	static final String[] DFA4_transitionS = {
			"\2\15\2\uffff\1\15\22\uffff\1\15\1\uffff\1\1\10\uffff\1\2\1\3\3\uffff"+
			"\12\13\1\4\3\uffff\1\5\2\uffff\2\14\1\6\1\14\1\7\1\14\10\uffff\1\10\3"+
			"\uffff\1\11\3\uffff\1\12\11\uffff\6\14",
			"\1\16\1\17\12\uffff\1\20\4\uffff\1\21\1\22\1\23",
			"",
			"",
			"",
			"",
			"\1\24\10\uffff\1\25",
			"\1\26",
			"",
			"",
			"",
			"\12\13\7\uffff\6\14\32\uffff\6\14",
			"",
			"",
			"",
			"",
			"\1\30\16\uffff\1\31",
			"\1\32",
			"\1\33\4\uffff\1\34\1\uffff\1\35",
			"",
			"\1\36",
			"\1\37\13\uffff\1\40\1\41",
			"",
			"",
			"",
			"",
			"\1\42",
			"",
			"",
			"\1\43",
			"",
			"\1\44\5\uffff\1\45",
			"",
			"",
			"\1\47",
			"\1\50",
			"",
			"",
			"",
			"\1\51\2\uffff\1\52",
			"\1\53\1\uffff\1\54",
			"",
			"",
			"",
			""
	};

	static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
	static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
	static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
	static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
	static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
	static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
	static final short[][] DFA4_transition;

	static {
		int numStates = DFA4_transitionS.length;
		DFA4_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
		}
	}

	protected class DFA4 extends DFA {

		public DFA4(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 4;
			this.eot = DFA4_eot;
			this.eof = DFA4_eof;
			this.min = DFA4_min;
			this.max = DFA4_max;
			this.accept = DFA4_accept;
			this.special = DFA4_special;
			this.transition = DFA4_transition;
		}
		@Override
		public String getDescription() {
			return "1:1: Tokens : ( T__7 | T__8 | T__9 | T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | INT | HEX | WS );";
		}
	}

}

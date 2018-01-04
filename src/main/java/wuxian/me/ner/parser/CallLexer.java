package wuxian.me.ner.parser;// $ANTLR 3.5.2 /Users/dashu/Desktop/Playgroud/NER-Experiment/src/main/antlr3/Call__.g 2018-01-04 19:11:34

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class CallLexer extends Lexer {
	public static final int EOF=-1;
	public static final int T__13=13;
	public static final int T__14=14;
	public static final int CHAR=4;
	public static final int ESC=5;
	public static final int FLOAT=6;
	public static final int ID=7;
	public static final int INT=8;
	public static final int LEFT=9;
	public static final int RIGHT=10;
	public static final int STRING=11;
	public static final int WS=12;
	public static final int Tokens=15;

	// delegates
	public Call_CommonLexer gCommonLexer;
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {gCommonLexer};
	}

	public CallLexer() {} 
	public CallLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public CallLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
		gCommonLexer = new Call_CommonLexer(input, state, this);
	}
	@Override public String getGrammarFileName() { return "/Users/dashu/Desktop/Playgroud/NER-Experiment/src/main/antlr3/Call__.g"; }

	// $ANTLR start "T__13"
	public final void mT__13() throws RecognitionException {
		try {
			int _type = T__13;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/dashu/Desktop/Playgroud/NER-Experiment/src/main/antlr3/Call__.g:3:7: ( ',' )
			// /Users/dashu/Desktop/Playgroud/NER-Experiment/src/main/antlr3/Call__.g:3:9: ','
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
	// $ANTLR end "T__13"

	// $ANTLR start "T__14"
	public final void mT__14() throws RecognitionException {
		try {
			int _type = T__14;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/dashu/Desktop/Playgroud/NER-Experiment/src/main/antlr3/Call__.g:4:7: ( '=' )
			// /Users/dashu/Desktop/Playgroud/NER-Experiment/src/main/antlr3/Call__.g:4:9: '='
			{
			match('='); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__14"

	@Override
	public void mTokens() throws RecognitionException {
		// /Users/dashu/Desktop/Playgroud/NER-Experiment/src/main/antlr3/Call__.g:1:8: ( T__13 | T__14 | CommonLexer. Tokens )
		int alt1=3;
		switch ( input.LA(1) ) {
		case ',':
			{
			alt1=1;
			}
			break;
		case '=':
			{
			alt1=2;
			}
			break;
		case '\t':
		case '\n':
		case '\r':
		case ' ':
		case '\"':
		case '\'':
		case '(':
		case ')':
		case '.':
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
		case 'A':
		case 'B':
		case 'C':
		case 'D':
		case 'E':
		case 'F':
		case 'G':
		case 'H':
		case 'I':
		case 'J':
		case 'K':
		case 'L':
		case 'M':
		case 'N':
		case 'O':
		case 'P':
		case 'Q':
		case 'R':
		case 'S':
		case 'T':
		case 'U':
		case 'V':
		case 'W':
		case 'X':
		case 'Y':
		case 'Z':
		case '_':
		case 'a':
		case 'b':
		case 'c':
		case 'd':
		case 'e':
		case 'f':
		case 'g':
		case 'h':
		case 'i':
		case 'j':
		case 'k':
		case 'l':
		case 'm':
		case 'n':
		case 'o':
		case 'p':
		case 'q':
		case 'r':
		case 's':
		case 't':
		case 'u':
		case 'v':
		case 'w':
		case 'x':
		case 'y':
		case 'z':
			{
			alt1=3;
			}
			break;
		default:
			NoViableAltException nvae =
				new NoViableAltException("", 1, 0, input);
			throw nvae;
		}
		switch (alt1) {
			case 1 :
				// /Users/dashu/Desktop/Playgroud/NER-Experiment/src/main/antlr3/Call__.g:1:10: T__13
				{
				mT__13(); 

				}
				break;
			case 2 :
				// /Users/dashu/Desktop/Playgroud/NER-Experiment/src/main/antlr3/Call__.g:1:16: T__14
				{
				mT__14(); 

				}
				break;
			case 3 :
				// /Users/dashu/Desktop/Playgroud/NER-Experiment/src/main/antlr3/Call__.g:1:22: CommonLexer. Tokens
				{
				gCommonLexer.mTokens(); 

				}
				break;

		}
	}



}

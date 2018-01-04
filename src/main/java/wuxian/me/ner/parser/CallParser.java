// $ANTLR 3.5.2 Call.g 2018-01-04 19:11:34

package wuxian.me.ner.parser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings("all")
public class CallParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "CHAR", "ESC", "FLOAT", "ID", 
		"INT", "LEFT", "RIGHT", "STRING", "WS", "','", "'='"
	};
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

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public CallParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public CallParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	protected TreeAdaptor adaptor = new CommonTreeAdaptor();

	public void setTreeAdaptor(TreeAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	public TreeAdaptor getTreeAdaptor() {
		return adaptor;
	}
	@Override public String[] getTokenNames() { return CallParser.tokenNames; }
	@Override public String getGrammarFileName() { return "Call.g"; }


	public static class call_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "call"
	// Call.g:14:1: call : ( ID LEFT params RIGHT -> ^( ID params ) | ID ( WS )* params -> ^( ID params ) );
	public final CallParser.call_return call() throws RecognitionException {
		CallParser.call_return retval = new CallParser.call_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token ID1=null;
		Token LEFT2=null;
		Token RIGHT4=null;
		Token ID5=null;
		Token WS6=null;
		ParserRuleReturnScope params3 =null;
		ParserRuleReturnScope params7 =null;

		Object ID1_tree=null;
		Object LEFT2_tree=null;
		Object RIGHT4_tree=null;
		Object ID5_tree=null;
		Object WS6_tree=null;
		RewriteRuleTokenStream stream_LEFT=new RewriteRuleTokenStream(adaptor,"token LEFT");
		RewriteRuleTokenStream stream_RIGHT=new RewriteRuleTokenStream(adaptor,"token RIGHT");
		RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
		RewriteRuleTokenStream stream_WS=new RewriteRuleTokenStream(adaptor,"token WS");
		RewriteRuleSubtreeStream stream_params=new RewriteRuleSubtreeStream(adaptor,"rule params");

		try {
			// Call.g:14:6: ( ID LEFT params RIGHT -> ^( ID params ) | ID ( WS )* params -> ^( ID params ) )
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0==ID) ) {
				int LA2_1 = input.LA(2);
				if ( (LA2_1==LEFT) ) {
					alt2=1;
				}
				else if ( (LA2_1==CHAR||(LA2_1 >= FLOAT && LA2_1 <= INT)||(LA2_1 >= STRING && LA2_1 <= WS)) ) {
					alt2=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 2, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 2, 0, input);
				throw nvae;
			}

			switch (alt2) {
				case 1 :
					// Call.g:14:8: ID LEFT params RIGHT
					{
						ID1 = (Token) match(input, ID, FOLLOW_ID_in_call51);
					stream_ID.add(ID1);

						LEFT2 = (Token) match(input, LEFT, FOLLOW_LEFT_in_call53);
					stream_LEFT.add(LEFT2);

						pushFollow(FOLLOW_params_in_call55);
					params3=params();
					state._fsp--;

					stream_params.add(params3.getTree());
						RIGHT4 = (Token) match(input, RIGHT, FOLLOW_RIGHT_in_call57);
					stream_RIGHT.add(RIGHT4);

					// AST REWRITE
					// elements: ID, params
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (Object)adaptor.nil();
						// 14:29: -> ^( ID params )
					{
						// Call.g:14:32: ^( ID params )
						{
						Object root_1 = (Object)adaptor.nil();
						root_1 = (Object)adaptor.becomeRoot(stream_ID.nextNode(), root_1);
						adaptor.addChild(root_1, stream_params.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;

					}
					break;
				case 2 :
					// Call.g:14:47: ID ( WS )* params
					{
						ID5 = (Token) match(input, ID, FOLLOW_ID_in_call69);
					stream_ID.add(ID5);

						// Call.g:14:50: ( WS )*
					loop1:
					while (true) {
						int alt1=2;
						int LA1_0 = input.LA(1);
						if ( (LA1_0==WS) ) {
							alt1=1;
						}

						switch (alt1) {
						case 1 :
							// Call.g:14:50: WS
							{
								WS6 = (Token) match(input, WS, FOLLOW_WS_in_call71);
							stream_WS.add(WS6);

							}
							break;

						default :
							break loop1;
						}
					}

						pushFollow(FOLLOW_params_in_call74);
					params7=params();
					state._fsp--;

					stream_params.add(params7.getTree());
					// AST REWRITE
					// elements: params, ID
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (Object)adaptor.nil();
						// 14:61: -> ^( ID params )
					{
						// Call.g:14:64: ^( ID params )
						{
						Object root_1 = (Object)adaptor.nil();
						root_1 = (Object)adaptor.becomeRoot(stream_ID.nextNode(), root_1);
						adaptor.addChild(root_1, stream_params.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "call"


	public static class params_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "params"
	// Call.g:16:1: params : param ( ( ',' | ( WS )* ) param )* ;
	public final CallParser.params_return params() throws RecognitionException {
		CallParser.params_return retval = new CallParser.params_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token char_literal9=null;
		Token WS10=null;
		ParserRuleReturnScope param8 =null;
		ParserRuleReturnScope param11 =null;

		Object char_literal9_tree=null;
		Object WS10_tree=null;

		try {
			// Call.g:16:8: ( param ( ( ',' | ( WS )* ) param )* )
			// Call.g:16:10: param ( ( ',' | ( WS )* ) param )*
			{
			root_0 = (Object)adaptor.nil();


				pushFollow(FOLLOW_param_in_params91);
			param8=param();
			state._fsp--;

			adaptor.addChild(root_0, param8.getTree());

				// Call.g:16:16: ( ( ',' | ( WS )* ) param )*
			loop5:
			while (true) {
				int alt5=2;
				int LA5_0 = input.LA(1);
				if ( (LA5_0==CHAR||(LA5_0 >= FLOAT && LA5_0 <= INT)||(LA5_0 >= STRING && LA5_0 <= 13)) ) {
					alt5=1;
				}

				switch (alt5) {
				case 1 :
					// Call.g:16:17: ( ',' | ( WS )* ) param
					{
						// Call.g:16:17: ( ',' | ( WS )* )
					int alt4=2;
					int LA4_0 = input.LA(1);
					if ( (LA4_0==13) ) {
						alt4=1;
					}
					else if ( (LA4_0==CHAR||(LA4_0 >= FLOAT && LA4_0 <= INT)||(LA4_0 >= STRING && LA4_0 <= WS)) ) {
						alt4=2;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 4, 0, input);
						throw nvae;
					}

					switch (alt4) {
						case 1 :
							// Call.g:16:18: ','
							{
								char_literal9 = (Token) match(input, 13, FOLLOW_13_in_params95);
							char_literal9_tree = (Object)adaptor.create(char_literal9);
							adaptor.addChild(root_0, char_literal9_tree);

							}
							break;
						case 2 :
							// Call.g:16:24: ( WS )*
							{
								// Call.g:16:24: ( WS )*
							loop3:
							while (true) {
								int alt3=2;
								int LA3_0 = input.LA(1);
								if ( (LA3_0==WS) ) {
									alt3=1;
								}

								switch (alt3) {
								case 1 :
									// Call.g:16:24: WS
									{
										WS10 = (Token) match(input, WS, FOLLOW_WS_in_params99);
									WS10_tree = (Object)adaptor.create(WS10);
									adaptor.addChild(root_0, WS10_tree);

									}
									break;

								default :
									break loop3;
								}
							}

							}
							break;

					}

						pushFollow(FOLLOW_param_in_params103);
					param11=param();
					state._fsp--;

					adaptor.addChild(root_0, param11.getTree());

					}
					break;

				default :
					break loop5;
				}
			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "params"


	public static class param_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "param"
	// Call.g:18:1: param : ( ID '=' value -> ^( ID value ) | value '=' ID -> ^( ID value ) );
	public final CallParser.param_return param() throws RecognitionException {
		CallParser.param_return retval = new CallParser.param_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token ID12=null;
		Token char_literal13=null;
		Token char_literal16=null;
		Token ID17=null;
		ParserRuleReturnScope value14 =null;
		ParserRuleReturnScope value15 =null;

		Object ID12_tree=null;
		Object char_literal13_tree=null;
		Object char_literal16_tree=null;
		Object ID17_tree=null;
		RewriteRuleTokenStream stream_14 = new RewriteRuleTokenStream(adaptor, "token 14");
		RewriteRuleTokenStream stream_ID = new RewriteRuleTokenStream(adaptor, "token ID");
		RewriteRuleSubtreeStream stream_value = new RewriteRuleSubtreeStream(adaptor, "rule value");

		try {
			// Call.g:18:7: ( ID '=' value -> ^( ID value ) | value '=' ID -> ^( ID value ) )
			int alt6=2;
			int LA6_0 = input.LA(1);
			if ( (LA6_0==ID) ) {
				alt6=1;
			}
			else if ( (LA6_0==CHAR||LA6_0==FLOAT||LA6_0==INT||LA6_0==STRING) ) {
				alt6=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 6, 0, input);
				throw nvae;
			}

			switch (alt6) {
				case 1 :
					// Call.g:18:9: ID '=' value
					{
						ID12 = (Token) match(input, ID, FOLLOW_ID_in_param114);
						stream_ID.add(ID12);

						char_literal13 = (Token) match(input, 14, FOLLOW_14_in_param116);
						stream_14.add(char_literal13);

						pushFollow(FOLLOW_value_in_param118);
					value14=value();
					state._fsp--;

						stream_value.add(value14.getTree());
						// AST REWRITE
						// elements: value, ID
						// token labels:
						// rule labels: retval
						// token list labels:
						// rule list labels:
						// wildcard labels:
						retval.tree = root_0;
						RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(adaptor, "rule retval", retval != null ? retval.getTree() : null);

						root_0 = (Object) adaptor.nil();
						// 18:22: -> ^( ID value )
						{
							// Call.g:18:25: ^( ID value )
							{
								Object root_1 = (Object) adaptor.nil();
								root_1 = (Object) adaptor.becomeRoot(stream_ID.nextNode(), root_1);
								adaptor.addChild(root_1, stream_value.nextTree());
								adaptor.addChild(root_0, root_1);
							}

						}


						retval.tree = root_0;

					}
					break;
				case 2 :
					// Call.g:18:39: value '=' ID
					{
						pushFollow(FOLLOW_value_in_param130);
					value15=value();
					state._fsp--;

						stream_value.add(value15.getTree());
						char_literal16 = (Token) match(input, 14, FOLLOW_14_in_param132);
						stream_14.add(char_literal16);

						ID17 = (Token) match(input, ID, FOLLOW_ID_in_param134);
						stream_ID.add(ID17);

						// AST REWRITE
						// elements: value, ID
						// token labels:
						// rule labels: retval
						// token list labels:
						// rule list labels:
						// wildcard labels:
						retval.tree = root_0;
						RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(adaptor, "rule retval", retval != null ? retval.getTree() : null);

						root_0 = (Object) adaptor.nil();
						// 18:52: -> ^( ID value )
						{
							// Call.g:18:55: ^( ID value )
							{
								Object root_1 = (Object) adaptor.nil();
								root_1 = (Object) adaptor.becomeRoot(stream_ID.nextNode(), root_1);
								adaptor.addChild(root_1, stream_value.nextTree());
								adaptor.addChild(root_0, root_1);
							}

						}


						retval.tree = root_0;

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "param"


	public static class value_return extends ParserRuleReturnScope {
		Object tree;
		@Override
		public Object getTree() { return tree; }
	};


	// $ANTLR start "value"
	// Call.g:21:1: fragment value : ( INT | FLOAT | CHAR | STRING );
	public final CallParser.value_return value() throws RecognitionException {
		CallParser.value_return retval = new CallParser.value_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token set18=null;

		Object set18_tree=null;

		try {
			// Call.g:21:7: ( INT | FLOAT | CHAR | STRING )
			// Call.g:
			{
			root_0 = (Object)adaptor.nil();


			set18=input.LT(1);
			if ( input.LA(1)==CHAR||input.LA(1)==FLOAT||input.LA(1)==INT||input.LA(1)==STRING ) {
				input.consume();
				adaptor.addChild(root_0, (Object)adaptor.create(set18));
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

			retval.stop = input.LT(-1);

			retval.tree = (Object)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "value"

	// Delegated rules


	public static final BitSet FOLLOW_ID_in_call51 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_LEFT_in_call53 = new BitSet(new long[]{0x00000000000009D0L});
	public static final BitSet FOLLOW_params_in_call55 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_RIGHT_in_call57 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_call69 = new BitSet(new long[]{0x00000000000019D0L});
	public static final BitSet FOLLOW_WS_in_call71 = new BitSet(new long[]{0x00000000000019D0L});
	public static final BitSet FOLLOW_params_in_call74 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_param_in_params91 = new BitSet(new long[]{0x00000000000039D2L});
	public static final BitSet FOLLOW_13_in_params95 = new BitSet(new long[]{0x00000000000009D0L});
	public static final BitSet FOLLOW_WS_in_params99 = new BitSet(new long[]{0x00000000000019D0L});
	public static final BitSet FOLLOW_param_in_params103 = new BitSet(new long[]{0x00000000000039D2L});
	public static final BitSet FOLLOW_ID_in_param114 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_14_in_param116 = new BitSet(new long[]{0x0000000000000950L});
	public static final BitSet FOLLOW_value_in_param118 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_value_in_param130 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_14_in_param132 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_ID_in_param134 = new BitSet(new long[]{0x0000000000000002L});
}

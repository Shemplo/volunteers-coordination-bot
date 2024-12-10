// Generated from InlineQueryGrammar.g4 by ANTLR 4.13.1

package ru.itmo.nerc.vcb.bot.chat.antlr;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class InlineQueryGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, WS=31, ANY=32;
	public static final int
		RULE_inlineQuery = 0, RULE_anyParameter = 1, RULE_taskParameter = 2, RULE_taskOperator = 3, 
		RULE_groupsParameter = 4, RULE_groupsOperator = 5, RULE_typeParameter = 6, 
		RULE_typeOperator = 7, RULE_idParameter = 8, RULE_idOperator = 9, RULE_answerParameter = 10, 
		RULE_answerOperator = 11, RULE_keyParameter = 12, RULE_keyOperator = 13, 
		RULE_valueParameter = 14, RULE_valueOperator = 15, RULE_ignoreParameter = 16, 
		RULE_sentencesArray = 17, RULE_sentenceWithComma = 18, RULE_sentence = 19;
	private static String[] makeRuleNames() {
		return new String[] {
			"inlineQuery", "anyParameter", "taskParameter", "taskOperator", "groupsParameter", 
			"groupsOperator", "typeParameter", "typeOperator", "idParameter", "idOperator", 
			"answerParameter", "answerOperator", "keyParameter", "keyOperator", "valueParameter", 
			"valueOperator", "ignoreParameter", "sentencesArray", "sentenceWithComma", 
			"sentence"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", "'???'", "'t'", "'task'", "'Task'", "'\\u0437\\u0430\\u0434\\u0430\\u0447\\u0430'", 
			"'\\u0417\\u0430\\u0434\\u0430\\u0447\\u0430'", "'h'", "'g'", "'halls'", 
			"'groups'", "'Halls'", "'Groups'", "'\\u0445\\u043E\\u043B\\u043B\\u044B'", 
			"'\\u0425\\u043E\\u043B\\u043B\\u044B'", "'type'", "'\\u0442\\u0438\\u043F'", 
			"'id'", "'ID'", "'answer'", "'\\u043E\\u0442\\u0432\\u0435\\u0442'", 
			"'key'", "'Key'", "'\\u043A\\u043B\\u044E\\u0447'", "'\\u041A\\u043B\\u044E\\u0447'", 
			"'value'", "'Value'", "'\\u0437\\u043D\\u0430\\u0447\\u0435\\u043D\\u0438\\u0435'", 
			"'\\u0417\\u043D\\u0430\\u0447\\u0435\\u043D\\u0438\\u0435'", "','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, "WS", "ANY"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "InlineQueryGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public InlineQueryGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InlineQueryContext extends ParserRuleContext {
		public String json = "{";;
		public AnyParameterContext anyParameter;
		public List<IgnoreParameterContext> ignoreParameter() {
			return getRuleContexts(IgnoreParameterContext.class);
		}
		public IgnoreParameterContext ignoreParameter(int i) {
			return getRuleContext(IgnoreParameterContext.class,i);
		}
		public List<TerminalNode> WS() { return getTokens(InlineQueryGrammarParser.WS); }
		public TerminalNode WS(int i) {
			return getToken(InlineQueryGrammarParser.WS, i);
		}
		public List<AnyParameterContext> anyParameter() {
			return getRuleContexts(AnyParameterContext.class);
		}
		public AnyParameterContext anyParameter(int i) {
			return getRuleContext(AnyParameterContext.class,i);
		}
		public InlineQueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inlineQuery; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).enterInlineQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).exitInlineQuery(this);
		}
	}

	public final InlineQueryContext inlineQuery() throws RecognitionException {
		InlineQueryContext _localctx = new InlineQueryContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_inlineQuery);
		int _la;
		try {
			int _alt;
			setState(169);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(50);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(40);
						ignoreParameter();
						setState(41);
						match(T__0);
						setState(45);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
						while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
							if ( _alt==1 ) {
								{
								{
								setState(42);
								match(WS);
								}
								} 
							}
							setState(47);
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
						}
						}
						} 
					}
					setState(52);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
				}
				setState(53);
				ignoreParameter();
				_localctx.json += "}";
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(80);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(66);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 6442450946L) != 0)) {
							{
							{
							setState(56);
							ignoreParameter();
							setState(57);
							match(T__0);
							setState(61);
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
							while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
								if ( _alt==1 ) {
									{
									{
									setState(58);
									match(WS);
									}
									} 
								}
								setState(63);
								_errHandler.sync(this);
								_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
							}
							}
							}
							setState(68);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(69);
						((InlineQueryContext)_localctx).anyParameter = anyParameter();
						_localctx.json += ((InlineQueryContext)_localctx).anyParameter.json + ",";
						setState(71);
						match(T__0);
						setState(75);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
						while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
							if ( _alt==1 ) {
								{
								{
								setState(72);
								match(WS);
								}
								} 
							}
							setState(77);
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
						}
						}
						} 
					}
					setState(82);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
				}
				setState(93);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 6442450946L) != 0)) {
					{
					{
					setState(83);
					ignoreParameter();
					setState(84);
					match(T__0);
					setState(88);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
							{
							setState(85);
							match(WS);
							}
							} 
						}
						setState(90);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
					}
					}
					}
					setState(95);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(96);
				((InlineQueryContext)_localctx).anyParameter = anyParameter();
				_localctx.json += ((InlineQueryContext)_localctx).anyParameter.json + "}";
				setState(99);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__0) {
					{
					setState(98);
					match(T__0);
					}
				}

				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(125);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(111);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 6442450946L) != 0)) {
							{
							{
							setState(101);
							ignoreParameter();
							setState(102);
							match(T__0);
							setState(106);
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
							while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
								if ( _alt==1 ) {
									{
									{
									setState(103);
									match(WS);
									}
									} 
								}
								setState(108);
								_errHandler.sync(this);
								_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
							}
							}
							}
							setState(113);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(114);
						((InlineQueryContext)_localctx).anyParameter = anyParameter();
						_localctx.json += ((InlineQueryContext)_localctx).anyParameter.json + ",";
						setState(116);
						match(T__0);
						setState(120);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
						while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
							if ( _alt==1 ) {
								{
								{
								setState(117);
								match(WS);
								}
								} 
							}
							setState(122);
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
						}
						}
						} 
					}
					setState(127);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				}
				setState(138);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 6442450946L) != 0)) {
					{
					{
					setState(128);
					ignoreParameter();
					setState(129);
					match(T__0);
					setState(133);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
							{
							setState(130);
							match(WS);
							}
							} 
						}
						setState(135);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
					}
					}
					}
					setState(140);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(141);
				((InlineQueryContext)_localctx).anyParameter = anyParameter();
				_localctx.json += ((InlineQueryContext)_localctx).anyParameter.json;
				setState(143);
				match(T__0);
				setState(147);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(144);
						match(WS);
						}
						} 
					}
					setState(149);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
				}
				setState(160);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(150);
						ignoreParameter();
						setState(151);
						match(T__0);
						setState(155);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
						while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
							if ( _alt==1 ) {
								{
								{
								setState(152);
								match(WS);
								}
								} 
							}
							setState(157);
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
						}
						}
						} 
					}
					setState(162);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
				}
				setState(163);
				ignoreParameter();
				setState(165);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__0) {
					{
					setState(164);
					match(T__0);
					}
				}

				_localctx.json += "}";
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AnyParameterContext extends ParserRuleContext {
		public String json = "";;
		public TaskParameterContext taskParameter;
		public GroupsParameterContext groupsParameter;
		public TypeParameterContext typeParameter;
		public IdParameterContext idParameter;
		public AnswerParameterContext answerParameter;
		public KeyParameterContext keyParameter;
		public ValueParameterContext valueParameter;
		public TaskParameterContext taskParameter() {
			return getRuleContext(TaskParameterContext.class,0);
		}
		public GroupsParameterContext groupsParameter() {
			return getRuleContext(GroupsParameterContext.class,0);
		}
		public TypeParameterContext typeParameter() {
			return getRuleContext(TypeParameterContext.class,0);
		}
		public IdParameterContext idParameter() {
			return getRuleContext(IdParameterContext.class,0);
		}
		public AnswerParameterContext answerParameter() {
			return getRuleContext(AnswerParameterContext.class,0);
		}
		public KeyParameterContext keyParameter() {
			return getRuleContext(KeyParameterContext.class,0);
		}
		public ValueParameterContext valueParameter() {
			return getRuleContext(ValueParameterContext.class,0);
		}
		public AnyParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_anyParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).enterAnyParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).exitAnyParameter(this);
		}
	}

	public final AnyParameterContext anyParameter() throws RecognitionException {
		AnyParameterContext _localctx = new AnyParameterContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_anyParameter);
		try {
			setState(192);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__1:
			case T__2:
			case T__3:
			case T__4:
			case T__5:
			case T__6:
				enterOuterAlt(_localctx, 1);
				{
				setState(171);
				((AnyParameterContext)_localctx).taskParameter = taskParameter();

				        ((AnyParameterContext)_localctx).json =  ((AnyParameterContext)_localctx).taskParameter.json;
				    
				}
				break;
			case T__7:
			case T__8:
			case T__9:
			case T__10:
			case T__11:
			case T__12:
			case T__13:
			case T__14:
				enterOuterAlt(_localctx, 2);
				{
				setState(174);
				((AnyParameterContext)_localctx).groupsParameter = groupsParameter();

				        ((AnyParameterContext)_localctx).json =  ((AnyParameterContext)_localctx).groupsParameter.json;
				    
				}
				break;
			case T__15:
			case T__16:
				enterOuterAlt(_localctx, 3);
				{
				setState(177);
				((AnyParameterContext)_localctx).typeParameter = typeParameter();

				        ((AnyParameterContext)_localctx).json =  ((AnyParameterContext)_localctx).typeParameter.json;
				    
				}
				break;
			case T__17:
			case T__18:
				enterOuterAlt(_localctx, 4);
				{
				setState(180);
				((AnyParameterContext)_localctx).idParameter = idParameter();

				        ((AnyParameterContext)_localctx).json =  ((AnyParameterContext)_localctx).idParameter.json;
				    
				}
				break;
			case T__19:
			case T__20:
				enterOuterAlt(_localctx, 5);
				{
				setState(183);
				((AnyParameterContext)_localctx).answerParameter = answerParameter();

				        ((AnyParameterContext)_localctx).json =  ((AnyParameterContext)_localctx).answerParameter.json;
				    
				}
				break;
			case T__21:
			case T__22:
			case T__23:
			case T__24:
				enterOuterAlt(_localctx, 6);
				{
				setState(186);
				((AnyParameterContext)_localctx).keyParameter = keyParameter();

				        ((AnyParameterContext)_localctx).json =  ((AnyParameterContext)_localctx).keyParameter.json;
				    
				}
				break;
			case T__25:
			case T__26:
			case T__27:
			case T__28:
				enterOuterAlt(_localctx, 7);
				{
				setState(189);
				((AnyParameterContext)_localctx).valueParameter = valueParameter();

				        ((AnyParameterContext)_localctx).json =  ((AnyParameterContext)_localctx).valueParameter.json;
				    
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TaskParameterContext extends ParserRuleContext {
		public String json;
		public SentenceWithCommaContext sentenceWithComma;
		public TaskOperatorContext taskOperator() {
			return getRuleContext(TaskOperatorContext.class,0);
		}
		public SentenceWithCommaContext sentenceWithComma() {
			return getRuleContext(SentenceWithCommaContext.class,0);
		}
		public TaskParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_taskParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).enterTaskParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).exitTaskParameter(this);
		}
	}

	public final TaskParameterContext taskParameter() throws RecognitionException {
		TaskParameterContext _localctx = new TaskParameterContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_taskParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			taskOperator();
			setState(196);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8589934588L) != 0)) {
				{
				setState(195);
				((TaskParameterContext)_localctx).sentenceWithComma = sentenceWithComma();
				}
			}


			        ((TaskParameterContext)_localctx).json =  "\"task\":\"" + ((((TaskParameterContext)_localctx).sentenceWithComma!=null?_input.getText(((TaskParameterContext)_localctx).sentenceWithComma.start,((TaskParameterContext)_localctx).sentenceWithComma.stop):null) != null ? (((TaskParameterContext)_localctx).sentenceWithComma!=null?_input.getText(((TaskParameterContext)_localctx).sentenceWithComma.start,((TaskParameterContext)_localctx).sentenceWithComma.stop):null).trim () : "") + "\"";
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TaskOperatorContext extends ParserRuleContext {
		public TaskOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_taskOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).enterTaskOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).exitTaskOperator(this);
		}
	}

	public final TaskOperatorContext taskOperator() throws RecognitionException {
		TaskOperatorContext _localctx = new TaskOperatorContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_taskOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 252L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GroupsParameterContext extends ParserRuleContext {
		public String json;
		public SentencesArrayContext sentencesArray;
		public GroupsOperatorContext groupsOperator() {
			return getRuleContext(GroupsOperatorContext.class,0);
		}
		public SentencesArrayContext sentencesArray() {
			return getRuleContext(SentencesArrayContext.class,0);
		}
		public GroupsParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupsParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).enterGroupsParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).exitGroupsParameter(this);
		}
	}

	public final GroupsParameterContext groupsParameter() throws RecognitionException {
		GroupsParameterContext _localctx = new GroupsParameterContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_groupsParameter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(202);
			groupsOperator();

			        ((GroupsParameterContext)_localctx).json =  "\"groups\":";
			    
			setState(207);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				{
				setState(204);
				((GroupsParameterContext)_localctx).sentencesArray = sentencesArray();
				_localctx.json += ((GroupsParameterContext)_localctx).sentencesArray.array;
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GroupsOperatorContext extends ParserRuleContext {
		public GroupsOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupsOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).enterGroupsOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).exitGroupsOperator(this);
		}
	}

	public final GroupsOperatorContext groupsOperator() throws RecognitionException {
		GroupsOperatorContext _localctx = new GroupsOperatorContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_groupsOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 65280L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeParameterContext extends ParserRuleContext {
		public String json;
		public SentenceContext sentence;
		public TypeOperatorContext typeOperator() {
			return getRuleContext(TypeOperatorContext.class,0);
		}
		public SentenceContext sentence() {
			return getRuleContext(SentenceContext.class,0);
		}
		public TypeParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).enterTypeParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).exitTypeParameter(this);
		}
	}

	public final TypeParameterContext typeParameter() throws RecognitionException {
		TypeParameterContext _localctx = new TypeParameterContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_typeParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(211);
			typeOperator();
			setState(213);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 7516192764L) != 0)) {
				{
				setState(212);
				((TypeParameterContext)_localctx).sentence = sentence();
				}
			}


			        ((TypeParameterContext)_localctx).json =  "\"type\":\"" + ((((TypeParameterContext)_localctx).sentence!=null?_input.getText(((TypeParameterContext)_localctx).sentence.start,((TypeParameterContext)_localctx).sentence.stop):null) != null ? (((TypeParameterContext)_localctx).sentence!=null?_input.getText(((TypeParameterContext)_localctx).sentence.start,((TypeParameterContext)_localctx).sentence.stop):null).trim () : "") + "\"";
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeOperatorContext extends ParserRuleContext {
		public TypeOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).enterTypeOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).exitTypeOperator(this);
		}
	}

	public final TypeOperatorContext typeOperator() throws RecognitionException {
		TypeOperatorContext _localctx = new TypeOperatorContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_typeOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(217);
			_la = _input.LA(1);
			if ( !(_la==T__15 || _la==T__16) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IdParameterContext extends ParserRuleContext {
		public String json;
		public SentenceContext sentence;
		public IdOperatorContext idOperator() {
			return getRuleContext(IdOperatorContext.class,0);
		}
		public SentenceContext sentence() {
			return getRuleContext(SentenceContext.class,0);
		}
		public IdParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_idParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).enterIdParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).exitIdParameter(this);
		}
	}

	public final IdParameterContext idParameter() throws RecognitionException {
		IdParameterContext _localctx = new IdParameterContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_idParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(219);
			idOperator();
			setState(221);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 7516192764L) != 0)) {
				{
				setState(220);
				((IdParameterContext)_localctx).sentence = sentence();
				}
			}


			        ((IdParameterContext)_localctx).json =  "\"id\":" + ((((IdParameterContext)_localctx).sentence!=null?_input.getText(((IdParameterContext)_localctx).sentence.start,((IdParameterContext)_localctx).sentence.stop):null) != null ? (((IdParameterContext)_localctx).sentence!=null?_input.getText(((IdParameterContext)_localctx).sentence.start,((IdParameterContext)_localctx).sentence.stop):null).trim () : "");
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IdOperatorContext extends ParserRuleContext {
		public IdOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_idOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).enterIdOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).exitIdOperator(this);
		}
	}

	public final IdOperatorContext idOperator() throws RecognitionException {
		IdOperatorContext _localctx = new IdOperatorContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_idOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(225);
			_la = _input.LA(1);
			if ( !(_la==T__17 || _la==T__18) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AnswerParameterContext extends ParserRuleContext {
		public String json;
		public SentenceWithCommaContext sentenceWithComma;
		public AnswerOperatorContext answerOperator() {
			return getRuleContext(AnswerOperatorContext.class,0);
		}
		public SentenceWithCommaContext sentenceWithComma() {
			return getRuleContext(SentenceWithCommaContext.class,0);
		}
		public AnswerParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_answerParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).enterAnswerParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).exitAnswerParameter(this);
		}
	}

	public final AnswerParameterContext answerParameter() throws RecognitionException {
		AnswerParameterContext _localctx = new AnswerParameterContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_answerParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(227);
			answerOperator();
			setState(229);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8589934588L) != 0)) {
				{
				setState(228);
				((AnswerParameterContext)_localctx).sentenceWithComma = sentenceWithComma();
				}
			}


			        ((AnswerParameterContext)_localctx).json =  "\"answer\":\"" + ((((AnswerParameterContext)_localctx).sentenceWithComma!=null?_input.getText(((AnswerParameterContext)_localctx).sentenceWithComma.start,((AnswerParameterContext)_localctx).sentenceWithComma.stop):null) != null ? (((AnswerParameterContext)_localctx).sentenceWithComma!=null?_input.getText(((AnswerParameterContext)_localctx).sentenceWithComma.start,((AnswerParameterContext)_localctx).sentenceWithComma.stop):null).trim () : "") + "\"";
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AnswerOperatorContext extends ParserRuleContext {
		public AnswerOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_answerOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).enterAnswerOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).exitAnswerOperator(this);
		}
	}

	public final AnswerOperatorContext answerOperator() throws RecognitionException {
		AnswerOperatorContext _localctx = new AnswerOperatorContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_answerOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(233);
			_la = _input.LA(1);
			if ( !(_la==T__19 || _la==T__20) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class KeyParameterContext extends ParserRuleContext {
		public String json;
		public SentenceWithCommaContext sentenceWithComma;
		public KeyOperatorContext keyOperator() {
			return getRuleContext(KeyOperatorContext.class,0);
		}
		public SentenceWithCommaContext sentenceWithComma() {
			return getRuleContext(SentenceWithCommaContext.class,0);
		}
		public KeyParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).enterKeyParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).exitKeyParameter(this);
		}
	}

	public final KeyParameterContext keyParameter() throws RecognitionException {
		KeyParameterContext _localctx = new KeyParameterContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_keyParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(235);
			keyOperator();
			setState(237);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8589934588L) != 0)) {
				{
				setState(236);
				((KeyParameterContext)_localctx).sentenceWithComma = sentenceWithComma();
				}
			}


			        ((KeyParameterContext)_localctx).json =  "\"key\":\"" + ((((KeyParameterContext)_localctx).sentenceWithComma!=null?_input.getText(((KeyParameterContext)_localctx).sentenceWithComma.start,((KeyParameterContext)_localctx).sentenceWithComma.stop):null) != null ? (((KeyParameterContext)_localctx).sentenceWithComma!=null?_input.getText(((KeyParameterContext)_localctx).sentenceWithComma.start,((KeyParameterContext)_localctx).sentenceWithComma.stop):null).trim () : "") + "\"";
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class KeyOperatorContext extends ParserRuleContext {
		public KeyOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).enterKeyOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).exitKeyOperator(this);
		}
	}

	public final KeyOperatorContext keyOperator() throws RecognitionException {
		KeyOperatorContext _localctx = new KeyOperatorContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_keyOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(241);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 62914560L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ValueParameterContext extends ParserRuleContext {
		public String json;
		public SentenceWithCommaContext sentenceWithComma;
		public ValueOperatorContext valueOperator() {
			return getRuleContext(ValueOperatorContext.class,0);
		}
		public SentenceWithCommaContext sentenceWithComma() {
			return getRuleContext(SentenceWithCommaContext.class,0);
		}
		public ValueParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).enterValueParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).exitValueParameter(this);
		}
	}

	public final ValueParameterContext valueParameter() throws RecognitionException {
		ValueParameterContext _localctx = new ValueParameterContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_valueParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(243);
			valueOperator();
			setState(245);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8589934588L) != 0)) {
				{
				setState(244);
				((ValueParameterContext)_localctx).sentenceWithComma = sentenceWithComma();
				}
			}


			        ((ValueParameterContext)_localctx).json =  "\"value\":\"" + ((((ValueParameterContext)_localctx).sentenceWithComma!=null?_input.getText(((ValueParameterContext)_localctx).sentenceWithComma.start,((ValueParameterContext)_localctx).sentenceWithComma.stop):null) != null ? (((ValueParameterContext)_localctx).sentenceWithComma!=null?_input.getText(((ValueParameterContext)_localctx).sentenceWithComma.start,((ValueParameterContext)_localctx).sentenceWithComma.stop):null).trim () : "") + "\"";
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ValueOperatorContext extends ParserRuleContext {
		public ValueOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).enterValueOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).exitValueOperator(this);
		}
	}

	public final ValueOperatorContext valueOperator() throws RecognitionException {
		ValueOperatorContext _localctx = new ValueOperatorContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_valueOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(249);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1006632960L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IgnoreParameterContext extends ParserRuleContext {
		public List<TerminalNode> ANY() { return getTokens(InlineQueryGrammarParser.ANY); }
		public TerminalNode ANY(int i) {
			return getToken(InlineQueryGrammarParser.ANY, i);
		}
		public List<TerminalNode> WS() { return getTokens(InlineQueryGrammarParser.WS); }
		public TerminalNode WS(int i) {
			return getToken(InlineQueryGrammarParser.WS, i);
		}
		public IgnoreParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ignoreParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).enterIgnoreParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).exitIgnoreParameter(this);
		}
	}

	public final IgnoreParameterContext ignoreParameter() throws RecognitionException {
		IgnoreParameterContext _localctx = new IgnoreParameterContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_ignoreParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(254);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WS || _la==ANY) {
				{
				{
				setState(251);
				_la = _input.LA(1);
				if ( !(_la==WS || _la==ANY) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				}
				setState(256);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SentencesArrayContext extends ParserRuleContext {
		public String array = "[";;
		public SentenceContext sentence;
		public List<SentenceContext> sentence() {
			return getRuleContexts(SentenceContext.class);
		}
		public SentenceContext sentence(int i) {
			return getRuleContext(SentenceContext.class,i);
		}
		public TerminalNode EOF() { return getToken(InlineQueryGrammarParser.EOF, 0); }
		public List<TerminalNode> WS() { return getTokens(InlineQueryGrammarParser.WS); }
		public TerminalNode WS(int i) {
			return getToken(InlineQueryGrammarParser.WS, i);
		}
		public SentencesArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sentencesArray; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).enterSentencesArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).exitSentencesArray(this);
		}
	}

	public final SentencesArrayContext sentencesArray() throws RecognitionException {
		SentencesArrayContext _localctx = new SentencesArrayContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_sentencesArray);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(263);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(257);
					((SentencesArrayContext)_localctx).sentence = sentence();
					_localctx.array += "\"" + (((SentencesArrayContext)_localctx).sentence!=null?_input.getText(((SentencesArrayContext)_localctx).sentence.start,((SentencesArrayContext)_localctx).sentence.stop):null).trim () + "\",";
					setState(259);
					match(T__29);
					}
					} 
				}
				setState(265);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			}
			setState(277);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				{
				setState(266);
				((SentencesArrayContext)_localctx).sentence = sentence();
				_localctx.array += "\"" + (((SentencesArrayContext)_localctx).sentence!=null?_input.getText(((SentencesArrayContext)_localctx).sentence.start,((SentencesArrayContext)_localctx).sentence.stop):null).trim () + "\"]";
				}
				break;
			case 2:
				{
				setState(272);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==WS) {
					{
					{
					setState(269);
					match(WS);
					}
					}
					setState(274);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(275);
				match(EOF);
				_localctx.array += "]";
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SentenceWithCommaContext extends ParserRuleContext {
		public List<SentenceContext> sentence() {
			return getRuleContexts(SentenceContext.class);
		}
		public SentenceContext sentence(int i) {
			return getRuleContext(SentenceContext.class,i);
		}
		public SentenceWithCommaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sentenceWithComma; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).enterSentenceWithComma(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).exitSentenceWithComma(this);
		}
	}

	public final SentenceWithCommaContext sentenceWithComma() throws RecognitionException {
		SentenceWithCommaContext _localctx = new SentenceWithCommaContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_sentenceWithComma);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(281); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(281);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__1:
				case T__2:
				case T__3:
				case T__4:
				case T__5:
				case T__6:
				case T__7:
				case T__8:
				case T__9:
				case T__10:
				case T__11:
				case T__12:
				case T__13:
				case T__14:
				case T__15:
				case T__16:
				case T__17:
				case T__18:
				case T__19:
				case T__20:
				case T__21:
				case T__22:
				case T__23:
				case T__24:
				case T__25:
				case T__26:
				case T__27:
				case T__28:
				case WS:
				case ANY:
					{
					setState(279);
					sentence();
					}
					break;
				case T__29:
					{
					setState(280);
					match(T__29);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(283); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 8589934588L) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SentenceContext extends ParserRuleContext {
		public List<TaskOperatorContext> taskOperator() {
			return getRuleContexts(TaskOperatorContext.class);
		}
		public TaskOperatorContext taskOperator(int i) {
			return getRuleContext(TaskOperatorContext.class,i);
		}
		public List<GroupsOperatorContext> groupsOperator() {
			return getRuleContexts(GroupsOperatorContext.class);
		}
		public GroupsOperatorContext groupsOperator(int i) {
			return getRuleContext(GroupsOperatorContext.class,i);
		}
		public List<TypeOperatorContext> typeOperator() {
			return getRuleContexts(TypeOperatorContext.class);
		}
		public TypeOperatorContext typeOperator(int i) {
			return getRuleContext(TypeOperatorContext.class,i);
		}
		public List<IdOperatorContext> idOperator() {
			return getRuleContexts(IdOperatorContext.class);
		}
		public IdOperatorContext idOperator(int i) {
			return getRuleContext(IdOperatorContext.class,i);
		}
		public List<AnswerOperatorContext> answerOperator() {
			return getRuleContexts(AnswerOperatorContext.class);
		}
		public AnswerOperatorContext answerOperator(int i) {
			return getRuleContext(AnswerOperatorContext.class,i);
		}
		public List<KeyOperatorContext> keyOperator() {
			return getRuleContexts(KeyOperatorContext.class);
		}
		public KeyOperatorContext keyOperator(int i) {
			return getRuleContext(KeyOperatorContext.class,i);
		}
		public List<ValueOperatorContext> valueOperator() {
			return getRuleContexts(ValueOperatorContext.class);
		}
		public ValueOperatorContext valueOperator(int i) {
			return getRuleContext(ValueOperatorContext.class,i);
		}
		public List<TerminalNode> WS() { return getTokens(InlineQueryGrammarParser.WS); }
		public TerminalNode WS(int i) {
			return getToken(InlineQueryGrammarParser.WS, i);
		}
		public List<TerminalNode> ANY() { return getTokens(InlineQueryGrammarParser.ANY); }
		public TerminalNode ANY(int i) {
			return getToken(InlineQueryGrammarParser.ANY, i);
		}
		public SentenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sentence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).enterSentence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InlineQueryGrammarListener ) ((InlineQueryGrammarListener)listener).exitSentence(this);
		}
	}

	public final SentenceContext sentence() throws RecognitionException {
		SentenceContext _localctx = new SentenceContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_sentence);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(302); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					setState(302);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case WS:
						{
						setState(286); 
						_errHandler.sync(this);
						_alt = 1;
						do {
							switch (_alt) {
							case 1:
								{
								{
								setState(285);
								match(WS);
								}
								}
								break;
							default:
								throw new NoViableAltException(this);
							}
							setState(288); 
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
						} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
						}
						break;
					case ANY:
						{
						setState(291); 
						_errHandler.sync(this);
						_alt = 1;
						do {
							switch (_alt) {
							case 1:
								{
								{
								setState(290);
								match(ANY);
								}
								}
								break;
							default:
								throw new NoViableAltException(this);
							}
							setState(293); 
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
						} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
						}
						break;
					case T__1:
					case T__2:
					case T__3:
					case T__4:
					case T__5:
					case T__6:
						{
						setState(295);
						taskOperator();
						}
						break;
					case T__7:
					case T__8:
					case T__9:
					case T__10:
					case T__11:
					case T__12:
					case T__13:
					case T__14:
						{
						setState(296);
						groupsOperator();
						}
						break;
					case T__15:
					case T__16:
						{
						setState(297);
						typeOperator();
						}
						break;
					case T__17:
					case T__18:
						{
						setState(298);
						idOperator();
						}
						break;
					case T__19:
					case T__20:
						{
						setState(299);
						answerOperator();
						}
						break;
					case T__21:
					case T__22:
					case T__23:
					case T__24:
						{
						setState(300);
						keyOperator();
						}
						break;
					case T__25:
					case T__26:
					case T__27:
					case T__28:
						{
						setState(301);
						valueOperator();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(304); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001 \u0133\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000"+
		",\b\u0000\n\u0000\f\u0000/\t\u0000\u0005\u00001\b\u0000\n\u0000\f\u0000"+
		"4\t\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0005\u0000<\b\u0000\n\u0000\f\u0000?\t\u0000\u0005\u0000"+
		"A\b\u0000\n\u0000\f\u0000D\t\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0005\u0000J\b\u0000\n\u0000\f\u0000M\t\u0000\u0005\u0000"+
		"O\b\u0000\n\u0000\f\u0000R\t\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0005\u0000W\b\u0000\n\u0000\f\u0000Z\t\u0000\u0005\u0000\\\b\u0000\n"+
		"\u0000\f\u0000_\t\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0003\u0000"+
		"d\b\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000i\b\u0000\n\u0000"+
		"\f\u0000l\t\u0000\u0005\u0000n\b\u0000\n\u0000\f\u0000q\t\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000w\b\u0000\n\u0000"+
		"\f\u0000z\t\u0000\u0005\u0000|\b\u0000\n\u0000\f\u0000\u007f\t\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0005\u0000\u0084\b\u0000\n\u0000\f\u0000"+
		"\u0087\t\u0000\u0005\u0000\u0089\b\u0000\n\u0000\f\u0000\u008c\t\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000\u0092\b\u0000"+
		"\n\u0000\f\u0000\u0095\t\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0005"+
		"\u0000\u009a\b\u0000\n\u0000\f\u0000\u009d\t\u0000\u0005\u0000\u009f\b"+
		"\u0000\n\u0000\f\u0000\u00a2\t\u0000\u0001\u0000\u0001\u0000\u0003\u0000"+
		"\u00a6\b\u0000\u0001\u0000\u0001\u0000\u0003\u0000\u00aa\b\u0000\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0003\u0001\u00c1\b\u0001\u0001\u0002\u0001"+
		"\u0002\u0003\u0002\u00c5\b\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001"+
		"\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003"+
		"\u0004\u00d0\b\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0003"+
		"\u0006\u00d6\b\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001"+
		"\b\u0001\b\u0003\b\u00de\b\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001"+
		"\n\u0003\n\u00e6\b\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001"+
		"\f\u0003\f\u00ee\b\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\u000e\u0001"+
		"\u000e\u0003\u000e\u00f6\b\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001"+
		"\u000f\u0001\u0010\u0005\u0010\u00fd\b\u0010\n\u0010\f\u0010\u0100\t\u0010"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0005\u0011\u0106\b\u0011"+
		"\n\u0011\f\u0011\u0109\t\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0005\u0011\u010f\b\u0011\n\u0011\f\u0011\u0112\t\u0011\u0001\u0011"+
		"\u0001\u0011\u0003\u0011\u0116\b\u0011\u0001\u0012\u0001\u0012\u0004\u0012"+
		"\u011a\b\u0012\u000b\u0012\f\u0012\u011b\u0001\u0013\u0004\u0013\u011f"+
		"\b\u0013\u000b\u0013\f\u0013\u0120\u0001\u0013\u0004\u0013\u0124\b\u0013"+
		"\u000b\u0013\f\u0013\u0125\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0004\u0013\u012f\b\u0013\u000b\u0013"+
		"\f\u0013\u0130\u0001\u0013\u0000\u0000\u0014\u0000\u0002\u0004\u0006\b"+
		"\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&\u0000"+
		"\b\u0001\u0000\u0002\u0007\u0001\u0000\b\u000f\u0001\u0000\u0010\u0011"+
		"\u0001\u0000\u0012\u0013\u0001\u0000\u0014\u0015\u0001\u0000\u0016\u0019"+
		"\u0001\u0000\u001a\u001d\u0001\u0000\u001f \u0151\u0000\u00a9\u0001\u0000"+
		"\u0000\u0000\u0002\u00c0\u0001\u0000\u0000\u0000\u0004\u00c2\u0001\u0000"+
		"\u0000\u0000\u0006\u00c8\u0001\u0000\u0000\u0000\b\u00ca\u0001\u0000\u0000"+
		"\u0000\n\u00d1\u0001\u0000\u0000\u0000\f\u00d3\u0001\u0000\u0000\u0000"+
		"\u000e\u00d9\u0001\u0000\u0000\u0000\u0010\u00db\u0001\u0000\u0000\u0000"+
		"\u0012\u00e1\u0001\u0000\u0000\u0000\u0014\u00e3\u0001\u0000\u0000\u0000"+
		"\u0016\u00e9\u0001\u0000\u0000\u0000\u0018\u00eb\u0001\u0000\u0000\u0000"+
		"\u001a\u00f1\u0001\u0000\u0000\u0000\u001c\u00f3\u0001\u0000\u0000\u0000"+
		"\u001e\u00f9\u0001\u0000\u0000\u0000 \u00fe\u0001\u0000\u0000\u0000\""+
		"\u0107\u0001\u0000\u0000\u0000$\u0119\u0001\u0000\u0000\u0000&\u012e\u0001"+
		"\u0000\u0000\u0000()\u0003 \u0010\u0000)-\u0005\u0001\u0000\u0000*,\u0005"+
		"\u001f\u0000\u0000+*\u0001\u0000\u0000\u0000,/\u0001\u0000\u0000\u0000"+
		"-+\u0001\u0000\u0000\u0000-.\u0001\u0000\u0000\u0000.1\u0001\u0000\u0000"+
		"\u0000/-\u0001\u0000\u0000\u00000(\u0001\u0000\u0000\u000014\u0001\u0000"+
		"\u0000\u000020\u0001\u0000\u0000\u000023\u0001\u0000\u0000\u000035\u0001"+
		"\u0000\u0000\u000042\u0001\u0000\u0000\u000056\u0003 \u0010\u000067\u0006"+
		"\u0000\uffff\uffff\u00007\u00aa\u0001\u0000\u0000\u000089\u0003 \u0010"+
		"\u00009=\u0005\u0001\u0000\u0000:<\u0005\u001f\u0000\u0000;:\u0001\u0000"+
		"\u0000\u0000<?\u0001\u0000\u0000\u0000=;\u0001\u0000\u0000\u0000=>\u0001"+
		"\u0000\u0000\u0000>A\u0001\u0000\u0000\u0000?=\u0001\u0000\u0000\u0000"+
		"@8\u0001\u0000\u0000\u0000AD\u0001\u0000\u0000\u0000B@\u0001\u0000\u0000"+
		"\u0000BC\u0001\u0000\u0000\u0000CE\u0001\u0000\u0000\u0000DB\u0001\u0000"+
		"\u0000\u0000EF\u0003\u0002\u0001\u0000FG\u0006\u0000\uffff\uffff\u0000"+
		"GK\u0005\u0001\u0000\u0000HJ\u0005\u001f\u0000\u0000IH\u0001\u0000\u0000"+
		"\u0000JM\u0001\u0000\u0000\u0000KI\u0001\u0000\u0000\u0000KL\u0001\u0000"+
		"\u0000\u0000LO\u0001\u0000\u0000\u0000MK\u0001\u0000\u0000\u0000NB\u0001"+
		"\u0000\u0000\u0000OR\u0001\u0000\u0000\u0000PN\u0001\u0000\u0000\u0000"+
		"PQ\u0001\u0000\u0000\u0000Q]\u0001\u0000\u0000\u0000RP\u0001\u0000\u0000"+
		"\u0000ST\u0003 \u0010\u0000TX\u0005\u0001\u0000\u0000UW\u0005\u001f\u0000"+
		"\u0000VU\u0001\u0000\u0000\u0000WZ\u0001\u0000\u0000\u0000XV\u0001\u0000"+
		"\u0000\u0000XY\u0001\u0000\u0000\u0000Y\\\u0001\u0000\u0000\u0000ZX\u0001"+
		"\u0000\u0000\u0000[S\u0001\u0000\u0000\u0000\\_\u0001\u0000\u0000\u0000"+
		"][\u0001\u0000\u0000\u0000]^\u0001\u0000\u0000\u0000^`\u0001\u0000\u0000"+
		"\u0000_]\u0001\u0000\u0000\u0000`a\u0003\u0002\u0001\u0000ac\u0006\u0000"+
		"\uffff\uffff\u0000bd\u0005\u0001\u0000\u0000cb\u0001\u0000\u0000\u0000"+
		"cd\u0001\u0000\u0000\u0000d\u00aa\u0001\u0000\u0000\u0000ef\u0003 \u0010"+
		"\u0000fj\u0005\u0001\u0000\u0000gi\u0005\u001f\u0000\u0000hg\u0001\u0000"+
		"\u0000\u0000il\u0001\u0000\u0000\u0000jh\u0001\u0000\u0000\u0000jk\u0001"+
		"\u0000\u0000\u0000kn\u0001\u0000\u0000\u0000lj\u0001\u0000\u0000\u0000"+
		"me\u0001\u0000\u0000\u0000nq\u0001\u0000\u0000\u0000om\u0001\u0000\u0000"+
		"\u0000op\u0001\u0000\u0000\u0000pr\u0001\u0000\u0000\u0000qo\u0001\u0000"+
		"\u0000\u0000rs\u0003\u0002\u0001\u0000st\u0006\u0000\uffff\uffff\u0000"+
		"tx\u0005\u0001\u0000\u0000uw\u0005\u001f\u0000\u0000vu\u0001\u0000\u0000"+
		"\u0000wz\u0001\u0000\u0000\u0000xv\u0001\u0000\u0000\u0000xy\u0001\u0000"+
		"\u0000\u0000y|\u0001\u0000\u0000\u0000zx\u0001\u0000\u0000\u0000{o\u0001"+
		"\u0000\u0000\u0000|\u007f\u0001\u0000\u0000\u0000}{\u0001\u0000\u0000"+
		"\u0000}~\u0001\u0000\u0000\u0000~\u008a\u0001\u0000\u0000\u0000\u007f"+
		"}\u0001\u0000\u0000\u0000\u0080\u0081\u0003 \u0010\u0000\u0081\u0085\u0005"+
		"\u0001\u0000\u0000\u0082\u0084\u0005\u001f\u0000\u0000\u0083\u0082\u0001"+
		"\u0000\u0000\u0000\u0084\u0087\u0001\u0000\u0000\u0000\u0085\u0083\u0001"+
		"\u0000\u0000\u0000\u0085\u0086\u0001\u0000\u0000\u0000\u0086\u0089\u0001"+
		"\u0000\u0000\u0000\u0087\u0085\u0001\u0000\u0000\u0000\u0088\u0080\u0001"+
		"\u0000\u0000\u0000\u0089\u008c\u0001\u0000\u0000\u0000\u008a\u0088\u0001"+
		"\u0000\u0000\u0000\u008a\u008b\u0001\u0000\u0000\u0000\u008b\u008d\u0001"+
		"\u0000\u0000\u0000\u008c\u008a\u0001\u0000\u0000\u0000\u008d\u008e\u0003"+
		"\u0002\u0001\u0000\u008e\u008f\u0006\u0000\uffff\uffff\u0000\u008f\u0093"+
		"\u0005\u0001\u0000\u0000\u0090\u0092\u0005\u001f\u0000\u0000\u0091\u0090"+
		"\u0001\u0000\u0000\u0000\u0092\u0095\u0001\u0000\u0000\u0000\u0093\u0091"+
		"\u0001\u0000\u0000\u0000\u0093\u0094\u0001\u0000\u0000\u0000\u0094\u00a0"+
		"\u0001\u0000\u0000\u0000\u0095\u0093\u0001\u0000\u0000\u0000\u0096\u0097"+
		"\u0003 \u0010\u0000\u0097\u009b\u0005\u0001\u0000\u0000\u0098\u009a\u0005"+
		"\u001f\u0000\u0000\u0099\u0098\u0001\u0000\u0000\u0000\u009a\u009d\u0001"+
		"\u0000\u0000\u0000\u009b\u0099\u0001\u0000\u0000\u0000\u009b\u009c\u0001"+
		"\u0000\u0000\u0000\u009c\u009f\u0001\u0000\u0000\u0000\u009d\u009b\u0001"+
		"\u0000\u0000\u0000\u009e\u0096\u0001\u0000\u0000\u0000\u009f\u00a2\u0001"+
		"\u0000\u0000\u0000\u00a0\u009e\u0001\u0000\u0000\u0000\u00a0\u00a1\u0001"+
		"\u0000\u0000\u0000\u00a1\u00a3\u0001\u0000\u0000\u0000\u00a2\u00a0\u0001"+
		"\u0000\u0000\u0000\u00a3\u00a5\u0003 \u0010\u0000\u00a4\u00a6\u0005\u0001"+
		"\u0000\u0000\u00a5\u00a4\u0001\u0000\u0000\u0000\u00a5\u00a6\u0001\u0000"+
		"\u0000\u0000\u00a6\u00a7\u0001\u0000\u0000\u0000\u00a7\u00a8\u0006\u0000"+
		"\uffff\uffff\u0000\u00a8\u00aa\u0001\u0000\u0000\u0000\u00a92\u0001\u0000"+
		"\u0000\u0000\u00a9P\u0001\u0000\u0000\u0000\u00a9}\u0001\u0000\u0000\u0000"+
		"\u00aa\u0001\u0001\u0000\u0000\u0000\u00ab\u00ac\u0003\u0004\u0002\u0000"+
		"\u00ac\u00ad\u0006\u0001\uffff\uffff\u0000\u00ad\u00c1\u0001\u0000\u0000"+
		"\u0000\u00ae\u00af\u0003\b\u0004\u0000\u00af\u00b0\u0006\u0001\uffff\uffff"+
		"\u0000\u00b0\u00c1\u0001\u0000\u0000\u0000\u00b1\u00b2\u0003\f\u0006\u0000"+
		"\u00b2\u00b3\u0006\u0001\uffff\uffff\u0000\u00b3\u00c1\u0001\u0000\u0000"+
		"\u0000\u00b4\u00b5\u0003\u0010\b\u0000\u00b5\u00b6\u0006\u0001\uffff\uffff"+
		"\u0000\u00b6\u00c1\u0001\u0000\u0000\u0000\u00b7\u00b8\u0003\u0014\n\u0000"+
		"\u00b8\u00b9\u0006\u0001\uffff\uffff\u0000\u00b9\u00c1\u0001\u0000\u0000"+
		"\u0000\u00ba\u00bb\u0003\u0018\f\u0000\u00bb\u00bc\u0006\u0001\uffff\uffff"+
		"\u0000\u00bc\u00c1\u0001\u0000\u0000\u0000\u00bd\u00be\u0003\u001c\u000e"+
		"\u0000\u00be\u00bf\u0006\u0001\uffff\uffff\u0000\u00bf\u00c1\u0001\u0000"+
		"\u0000\u0000\u00c0\u00ab\u0001\u0000\u0000\u0000\u00c0\u00ae\u0001\u0000"+
		"\u0000\u0000\u00c0\u00b1\u0001\u0000\u0000\u0000\u00c0\u00b4\u0001\u0000"+
		"\u0000\u0000\u00c0\u00b7\u0001\u0000\u0000\u0000\u00c0\u00ba\u0001\u0000"+
		"\u0000\u0000\u00c0\u00bd\u0001\u0000\u0000\u0000\u00c1\u0003\u0001\u0000"+
		"\u0000\u0000\u00c2\u00c4\u0003\u0006\u0003\u0000\u00c3\u00c5\u0003$\u0012"+
		"\u0000\u00c4\u00c3\u0001\u0000\u0000\u0000\u00c4\u00c5\u0001\u0000\u0000"+
		"\u0000\u00c5\u00c6\u0001\u0000\u0000\u0000\u00c6\u00c7\u0006\u0002\uffff"+
		"\uffff\u0000\u00c7\u0005\u0001\u0000\u0000\u0000\u00c8\u00c9\u0007\u0000"+
		"\u0000\u0000\u00c9\u0007\u0001\u0000\u0000\u0000\u00ca\u00cb\u0003\n\u0005"+
		"\u0000\u00cb\u00cf\u0006\u0004\uffff\uffff\u0000\u00cc\u00cd\u0003\"\u0011"+
		"\u0000\u00cd\u00ce\u0006\u0004\uffff\uffff\u0000\u00ce\u00d0\u0001\u0000"+
		"\u0000\u0000\u00cf\u00cc\u0001\u0000\u0000\u0000\u00cf\u00d0\u0001\u0000"+
		"\u0000\u0000\u00d0\t\u0001\u0000\u0000\u0000\u00d1\u00d2\u0007\u0001\u0000"+
		"\u0000\u00d2\u000b\u0001\u0000\u0000\u0000\u00d3\u00d5\u0003\u000e\u0007"+
		"\u0000\u00d4\u00d6\u0003&\u0013\u0000\u00d5\u00d4\u0001\u0000\u0000\u0000"+
		"\u00d5\u00d6\u0001\u0000\u0000\u0000\u00d6\u00d7\u0001\u0000\u0000\u0000"+
		"\u00d7\u00d8\u0006\u0006\uffff\uffff\u0000\u00d8\r\u0001\u0000\u0000\u0000"+
		"\u00d9\u00da\u0007\u0002\u0000\u0000\u00da\u000f\u0001\u0000\u0000\u0000"+
		"\u00db\u00dd\u0003\u0012\t\u0000\u00dc\u00de\u0003&\u0013\u0000\u00dd"+
		"\u00dc\u0001\u0000\u0000\u0000\u00dd\u00de\u0001\u0000\u0000\u0000\u00de"+
		"\u00df\u0001\u0000\u0000\u0000\u00df\u00e0\u0006\b\uffff\uffff\u0000\u00e0"+
		"\u0011\u0001\u0000\u0000\u0000\u00e1\u00e2\u0007\u0003\u0000\u0000\u00e2"+
		"\u0013\u0001\u0000\u0000\u0000\u00e3\u00e5\u0003\u0016\u000b\u0000\u00e4"+
		"\u00e6\u0003$\u0012\u0000\u00e5\u00e4\u0001\u0000\u0000\u0000\u00e5\u00e6"+
		"\u0001\u0000\u0000\u0000\u00e6\u00e7\u0001\u0000\u0000\u0000\u00e7\u00e8"+
		"\u0006\n\uffff\uffff\u0000\u00e8\u0015\u0001\u0000\u0000\u0000\u00e9\u00ea"+
		"\u0007\u0004\u0000\u0000\u00ea\u0017\u0001\u0000\u0000\u0000\u00eb\u00ed"+
		"\u0003\u001a\r\u0000\u00ec\u00ee\u0003$\u0012\u0000\u00ed\u00ec\u0001"+
		"\u0000\u0000\u0000\u00ed\u00ee\u0001\u0000\u0000\u0000\u00ee\u00ef\u0001"+
		"\u0000\u0000\u0000\u00ef\u00f0\u0006\f\uffff\uffff\u0000\u00f0\u0019\u0001"+
		"\u0000\u0000\u0000\u00f1\u00f2\u0007\u0005\u0000\u0000\u00f2\u001b\u0001"+
		"\u0000\u0000\u0000\u00f3\u00f5\u0003\u001e\u000f\u0000\u00f4\u00f6\u0003"+
		"$\u0012\u0000\u00f5\u00f4\u0001\u0000\u0000\u0000\u00f5\u00f6\u0001\u0000"+
		"\u0000\u0000\u00f6\u00f7\u0001\u0000\u0000\u0000\u00f7\u00f8\u0006\u000e"+
		"\uffff\uffff\u0000\u00f8\u001d\u0001\u0000\u0000\u0000\u00f9\u00fa\u0007"+
		"\u0006\u0000\u0000\u00fa\u001f\u0001\u0000\u0000\u0000\u00fb\u00fd\u0007"+
		"\u0007\u0000\u0000\u00fc\u00fb\u0001\u0000\u0000\u0000\u00fd\u0100\u0001"+
		"\u0000\u0000\u0000\u00fe\u00fc\u0001\u0000\u0000\u0000\u00fe\u00ff\u0001"+
		"\u0000\u0000\u0000\u00ff!\u0001\u0000\u0000\u0000\u0100\u00fe\u0001\u0000"+
		"\u0000\u0000\u0101\u0102\u0003&\u0013\u0000\u0102\u0103\u0006\u0011\uffff"+
		"\uffff\u0000\u0103\u0104\u0005\u001e\u0000\u0000\u0104\u0106\u0001\u0000"+
		"\u0000\u0000\u0105\u0101\u0001\u0000\u0000\u0000\u0106\u0109\u0001\u0000"+
		"\u0000\u0000\u0107\u0105\u0001\u0000\u0000\u0000\u0107\u0108\u0001\u0000"+
		"\u0000\u0000\u0108\u0115\u0001\u0000\u0000\u0000\u0109\u0107\u0001\u0000"+
		"\u0000\u0000\u010a\u010b\u0003&\u0013\u0000\u010b\u010c\u0006\u0011\uffff"+
		"\uffff\u0000\u010c\u0116\u0001\u0000\u0000\u0000\u010d\u010f\u0005\u001f"+
		"\u0000\u0000\u010e\u010d\u0001\u0000\u0000\u0000\u010f\u0112\u0001\u0000"+
		"\u0000\u0000\u0110\u010e\u0001\u0000\u0000\u0000\u0110\u0111\u0001\u0000"+
		"\u0000\u0000\u0111\u0113\u0001\u0000\u0000\u0000\u0112\u0110\u0001\u0000"+
		"\u0000\u0000\u0113\u0114\u0005\u0000\u0000\u0001\u0114\u0116\u0006\u0011"+
		"\uffff\uffff\u0000\u0115\u010a\u0001\u0000\u0000\u0000\u0115\u0110\u0001"+
		"\u0000\u0000\u0000\u0116#\u0001\u0000\u0000\u0000\u0117\u011a\u0003&\u0013"+
		"\u0000\u0118\u011a\u0005\u001e\u0000\u0000\u0119\u0117\u0001\u0000\u0000"+
		"\u0000\u0119\u0118\u0001\u0000\u0000\u0000\u011a\u011b\u0001\u0000\u0000"+
		"\u0000\u011b\u0119\u0001\u0000\u0000\u0000\u011b\u011c\u0001\u0000\u0000"+
		"\u0000\u011c%\u0001\u0000\u0000\u0000\u011d\u011f\u0005\u001f\u0000\u0000"+
		"\u011e\u011d\u0001\u0000\u0000\u0000\u011f\u0120\u0001\u0000\u0000\u0000"+
		"\u0120\u011e\u0001\u0000\u0000\u0000\u0120\u0121\u0001\u0000\u0000\u0000"+
		"\u0121\u012f\u0001\u0000\u0000\u0000\u0122\u0124\u0005 \u0000\u0000\u0123"+
		"\u0122\u0001\u0000\u0000\u0000\u0124\u0125\u0001\u0000\u0000\u0000\u0125"+
		"\u0123\u0001\u0000\u0000\u0000\u0125\u0126\u0001\u0000\u0000\u0000\u0126"+
		"\u012f\u0001\u0000\u0000\u0000\u0127\u012f\u0003\u0006\u0003\u0000\u0128"+
		"\u012f\u0003\n\u0005\u0000\u0129\u012f\u0003\u000e\u0007\u0000\u012a\u012f"+
		"\u0003\u0012\t\u0000\u012b\u012f\u0003\u0016\u000b\u0000\u012c\u012f\u0003"+
		"\u001a\r\u0000\u012d\u012f\u0003\u001e\u000f\u0000\u012e\u011e\u0001\u0000"+
		"\u0000\u0000\u012e\u0123\u0001\u0000\u0000\u0000\u012e\u0127\u0001\u0000"+
		"\u0000\u0000\u012e\u0128\u0001\u0000\u0000\u0000\u012e\u0129\u0001\u0000"+
		"\u0000\u0000\u012e\u012a\u0001\u0000\u0000\u0000\u012e\u012b\u0001\u0000"+
		"\u0000\u0000\u012e\u012c\u0001\u0000\u0000\u0000\u012e\u012d\u0001\u0000"+
		"\u0000\u0000\u012f\u0130\u0001\u0000\u0000\u0000\u0130\u012e\u0001\u0000"+
		"\u0000\u0000\u0130\u0131\u0001\u0000\u0000\u0000\u0131\'\u0001\u0000\u0000"+
		"\u0000&-2=BKPX]cjox}\u0085\u008a\u0093\u009b\u00a0\u00a5\u00a9\u00c0\u00c4"+
		"\u00cf\u00d5\u00dd\u00e5\u00ed\u00f5\u00fe\u0107\u0110\u0115\u0119\u011b"+
		"\u0120\u0125\u012e\u0130";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
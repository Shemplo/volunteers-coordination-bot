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
		T__17=18, T__18=19, T__19=20, T__20=21, WS=22, ANY=23;
	public static final int
		RULE_inlineQuery = 0, RULE_anyParameter = 1, RULE_taskParameter = 2, RULE_taskOperator = 3, 
		RULE_groupsParameter = 4, RULE_groupsOperator = 5, RULE_typeParameter = 6, 
		RULE_typeOperator = 7, RULE_idParameter = 8, RULE_idOperator = 9, RULE_answerParameter = 10, 
		RULE_answerOperator = 11, RULE_ignoreParameter = 12, RULE_sentencesArray = 13, 
		RULE_sentenceWithComma = 14, RULE_sentence = 15;
	private static String[] makeRuleNames() {
		return new String[] {
			"inlineQuery", "anyParameter", "taskParameter", "taskOperator", "groupsParameter", 
			"groupsOperator", "typeParameter", "typeOperator", "idParameter", "idOperator", 
			"answerParameter", "answerOperator", "ignoreParameter", "sentencesArray", 
			"sentenceWithComma", "sentence"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", "'???'", "'t'", "'task'", "'Task'", "'\\u0437\\u0430\\u0434\\u0430\\u0447\\u0430'", 
			"'\\u0417\\u0430\\u0434\\u0430\\u0447\\u0430'", "'h'", "'g'", "'halls'", 
			"'groups'", "'Halls'", "'Groups'", "'\\u0445\\u043E\\u043B\\u043B\\u044B'", 
			"'\\u0425\\u043E\\u043B\\u043B\\u044B'", "'type'", "'\\u0442\\u0438\\u043F'", 
			"'id'", "'answer'", "'\\u043E\\u0442\\u0432\\u0435\\u0442'", "','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, "WS", "ANY"
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
			setState(161);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(42);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(32);
						ignoreParameter();
						setState(33);
						match(T__0);
						setState(37);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
						while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
							if ( _alt==1 ) {
								{
								{
								setState(34);
								match(WS);
								}
								} 
							}
							setState(39);
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
						}
						}
						} 
					}
					setState(44);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
				}
				setState(45);
				ignoreParameter();
				_localctx.json += "}";
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(72);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(58);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 12582914L) != 0)) {
							{
							{
							setState(48);
							ignoreParameter();
							setState(49);
							match(T__0);
							setState(53);
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
							while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
								if ( _alt==1 ) {
									{
									{
									setState(50);
									match(WS);
									}
									} 
								}
								setState(55);
								_errHandler.sync(this);
								_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
							}
							}
							}
							setState(60);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(61);
						((InlineQueryContext)_localctx).anyParameter = anyParameter();
						_localctx.json += ((InlineQueryContext)_localctx).anyParameter.json + ",";
						setState(63);
						match(T__0);
						setState(67);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
						while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
							if ( _alt==1 ) {
								{
								{
								setState(64);
								match(WS);
								}
								} 
							}
							setState(69);
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
						}
						}
						} 
					}
					setState(74);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
				}
				setState(85);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 12582914L) != 0)) {
					{
					{
					setState(75);
					ignoreParameter();
					setState(76);
					match(T__0);
					setState(80);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
							{
							setState(77);
							match(WS);
							}
							} 
						}
						setState(82);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
					}
					}
					}
					setState(87);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(88);
				((InlineQueryContext)_localctx).anyParameter = anyParameter();
				_localctx.json += ((InlineQueryContext)_localctx).anyParameter.json + "}";
				setState(91);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__0) {
					{
					setState(90);
					match(T__0);
					}
				}

				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(117);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(103);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 12582914L) != 0)) {
							{
							{
							setState(93);
							ignoreParameter();
							setState(94);
							match(T__0);
							setState(98);
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
							while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
								if ( _alt==1 ) {
									{
									{
									setState(95);
									match(WS);
									}
									} 
								}
								setState(100);
								_errHandler.sync(this);
								_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
							}
							}
							}
							setState(105);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(106);
						((InlineQueryContext)_localctx).anyParameter = anyParameter();
						_localctx.json += ((InlineQueryContext)_localctx).anyParameter.json + ",";
						setState(108);
						match(T__0);
						setState(112);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
						while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
							if ( _alt==1 ) {
								{
								{
								setState(109);
								match(WS);
								}
								} 
							}
							setState(114);
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
						}
						}
						} 
					}
					setState(119);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				}
				setState(130);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 12582914L) != 0)) {
					{
					{
					setState(120);
					ignoreParameter();
					setState(121);
					match(T__0);
					setState(125);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
							{
							setState(122);
							match(WS);
							}
							} 
						}
						setState(127);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
					}
					}
					}
					setState(132);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(133);
				((InlineQueryContext)_localctx).anyParameter = anyParameter();
				_localctx.json += ((InlineQueryContext)_localctx).anyParameter.json;
				setState(135);
				match(T__0);
				setState(139);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(136);
						match(WS);
						}
						} 
					}
					setState(141);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
				}
				setState(152);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(142);
						ignoreParameter();
						setState(143);
						match(T__0);
						setState(147);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
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
							_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
						}
						}
						} 
					}
					setState(154);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
				}
				setState(155);
				ignoreParameter();
				setState(157);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__0) {
					{
					setState(156);
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
			setState(178);
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
				setState(163);
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
				setState(166);
				((AnyParameterContext)_localctx).groupsParameter = groupsParameter();

				        ((AnyParameterContext)_localctx).json =  ((AnyParameterContext)_localctx).groupsParameter.json;
				    
				}
				break;
			case T__15:
			case T__16:
				enterOuterAlt(_localctx, 3);
				{
				setState(169);
				((AnyParameterContext)_localctx).typeParameter = typeParameter();

				        ((AnyParameterContext)_localctx).json =  ((AnyParameterContext)_localctx).typeParameter.json;
				    
				}
				break;
			case T__17:
				enterOuterAlt(_localctx, 4);
				{
				setState(172);
				((AnyParameterContext)_localctx).idParameter = idParameter();

				        ((AnyParameterContext)_localctx).json =  ((AnyParameterContext)_localctx).idParameter.json;
				    
				}
				break;
			case T__18:
			case T__19:
				enterOuterAlt(_localctx, 5);
				{
				setState(175);
				((AnyParameterContext)_localctx).answerParameter = answerParameter();

				        ((AnyParameterContext)_localctx).json =  ((AnyParameterContext)_localctx).answerParameter.json;
				    
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
			setState(180);
			taskOperator();
			setState(182);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 16777212L) != 0)) {
				{
				setState(181);
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
			setState(186);
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
			setState(188);
			groupsOperator();

			        ((GroupsParameterContext)_localctx).json =  "\"groups\":";
			    
			setState(193);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				{
				setState(190);
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
			setState(195);
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
			setState(197);
			typeOperator();
			setState(199);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 14680060L) != 0)) {
				{
				setState(198);
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
			setState(203);
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
			setState(205);
			idOperator();
			setState(207);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 14680060L) != 0)) {
				{
				setState(206);
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
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(211);
			match(T__17);
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
			setState(213);
			answerOperator();
			setState(215);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 16777212L) != 0)) {
				{
				setState(214);
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
			setState(219);
			_la = _input.LA(1);
			if ( !(_la==T__18 || _la==T__19) ) {
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
		enterRule(_localctx, 24, RULE_ignoreParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(224);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WS || _la==ANY) {
				{
				{
				setState(221);
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
				setState(226);
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
		enterRule(_localctx, 26, RULE_sentencesArray);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(233);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(227);
					((SentencesArrayContext)_localctx).sentence = sentence();
					_localctx.array += "\"" + (((SentencesArrayContext)_localctx).sentence!=null?_input.getText(((SentencesArrayContext)_localctx).sentence.start,((SentencesArrayContext)_localctx).sentence.stop):null).trim () + "\",";
					setState(229);
					match(T__20);
					}
					} 
				}
				setState(235);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			}
			setState(247);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				{
				setState(236);
				((SentencesArrayContext)_localctx).sentence = sentence();
				_localctx.array += "\"" + (((SentencesArrayContext)_localctx).sentence!=null?_input.getText(((SentencesArrayContext)_localctx).sentence.start,((SentencesArrayContext)_localctx).sentence.stop):null).trim () + "\"]";
				}
				break;
			case 2:
				{
				setState(242);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==WS) {
					{
					{
					setState(239);
					match(WS);
					}
					}
					setState(244);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(245);
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
		enterRule(_localctx, 28, RULE_sentenceWithComma);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(251); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(251);
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
				case WS:
				case ANY:
					{
					setState(249);
					sentence();
					}
					break;
				case T__20:
					{
					setState(250);
					match(T__20);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(253); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 16777212L) != 0) );
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
		enterRule(_localctx, 30, RULE_sentence);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(270); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					setState(270);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case WS:
						{
						setState(256); 
						_errHandler.sync(this);
						_alt = 1;
						do {
							switch (_alt) {
							case 1:
								{
								{
								setState(255);
								match(WS);
								}
								}
								break;
							default:
								throw new NoViableAltException(this);
							}
							setState(258); 
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
						} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
						}
						break;
					case ANY:
						{
						setState(261); 
						_errHandler.sync(this);
						_alt = 1;
						do {
							switch (_alt) {
							case 1:
								{
								{
								setState(260);
								match(ANY);
								}
								}
								break;
							default:
								throw new NoViableAltException(this);
							}
							setState(263); 
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
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
						setState(265);
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
						setState(266);
						groupsOperator();
						}
						break;
					case T__15:
					case T__16:
						{
						setState(267);
						typeOperator();
						}
						break;
					case T__17:
						{
						setState(268);
						idOperator();
						}
						break;
					case T__18:
					case T__19:
						{
						setState(269);
						answerOperator();
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
				setState(272); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
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
		"\u0004\u0001\u0017\u0113\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007"+
		"\u000f\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000$\b\u0000\n\u0000"+
		"\f\u0000\'\t\u0000\u0005\u0000)\b\u0000\n\u0000\f\u0000,\t\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0005"+
		"\u00004\b\u0000\n\u0000\f\u00007\t\u0000\u0005\u00009\b\u0000\n\u0000"+
		"\f\u0000<\t\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0005"+
		"\u0000B\b\u0000\n\u0000\f\u0000E\t\u0000\u0005\u0000G\b\u0000\n\u0000"+
		"\f\u0000J\t\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000O\b\u0000"+
		"\n\u0000\f\u0000R\t\u0000\u0005\u0000T\b\u0000\n\u0000\f\u0000W\t\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0003\u0000\\\b\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0005\u0000a\b\u0000\n\u0000\f\u0000d\t\u0000"+
		"\u0005\u0000f\b\u0000\n\u0000\f\u0000i\t\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0005\u0000o\b\u0000\n\u0000\f\u0000r\t\u0000"+
		"\u0005\u0000t\b\u0000\n\u0000\f\u0000w\t\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0005\u0000|\b\u0000\n\u0000\f\u0000\u007f\t\u0000\u0005"+
		"\u0000\u0081\b\u0000\n\u0000\f\u0000\u0084\t\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0005\u0000\u008a\b\u0000\n\u0000\f\u0000\u008d"+
		"\t\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000\u0092\b\u0000"+
		"\n\u0000\f\u0000\u0095\t\u0000\u0005\u0000\u0097\b\u0000\n\u0000\f\u0000"+
		"\u009a\t\u0000\u0001\u0000\u0001\u0000\u0003\u0000\u009e\b\u0000\u0001"+
		"\u0000\u0001\u0000\u0003\u0000\u00a2\b\u0000\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0003\u0001\u00b3\b\u0001\u0001\u0002\u0001\u0002\u0003\u0002\u00b7"+
		"\b\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004\u00c2\b\u0004\u0001"+
		"\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0003\u0006\u00c8\b\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0003\b\u00d0"+
		"\b\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0003\n\u00d8\b\n"+
		"\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0005\f\u00df\b\f\n\f"+
		"\f\f\u00e2\t\f\u0001\r\u0001\r\u0001\r\u0001\r\u0005\r\u00e8\b\r\n\r\f"+
		"\r\u00eb\t\r\u0001\r\u0001\r\u0001\r\u0001\r\u0005\r\u00f1\b\r\n\r\f\r"+
		"\u00f4\t\r\u0001\r\u0001\r\u0003\r\u00f8\b\r\u0001\u000e\u0001\u000e\u0004"+
		"\u000e\u00fc\b\u000e\u000b\u000e\f\u000e\u00fd\u0001\u000f\u0004\u000f"+
		"\u0101\b\u000f\u000b\u000f\f\u000f\u0102\u0001\u000f\u0004\u000f\u0106"+
		"\b\u000f\u000b\u000f\f\u000f\u0107\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0004\u000f\u010f\b\u000f\u000b\u000f\f\u000f"+
		"\u0110\u0001\u000f\u0000\u0000\u0010\u0000\u0002\u0004\u0006\b\n\f\u000e"+
		"\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e\u0000\u0005\u0001\u0000"+
		"\u0002\u0007\u0001\u0000\b\u000f\u0001\u0000\u0010\u0011\u0001\u0000\u0013"+
		"\u0014\u0001\u0000\u0016\u0017\u012f\u0000\u00a1\u0001\u0000\u0000\u0000"+
		"\u0002\u00b2\u0001\u0000\u0000\u0000\u0004\u00b4\u0001\u0000\u0000\u0000"+
		"\u0006\u00ba\u0001\u0000\u0000\u0000\b\u00bc\u0001\u0000\u0000\u0000\n"+
		"\u00c3\u0001\u0000\u0000\u0000\f\u00c5\u0001\u0000\u0000\u0000\u000e\u00cb"+
		"\u0001\u0000\u0000\u0000\u0010\u00cd\u0001\u0000\u0000\u0000\u0012\u00d3"+
		"\u0001\u0000\u0000\u0000\u0014\u00d5\u0001\u0000\u0000\u0000\u0016\u00db"+
		"\u0001\u0000\u0000\u0000\u0018\u00e0\u0001\u0000\u0000\u0000\u001a\u00e9"+
		"\u0001\u0000\u0000\u0000\u001c\u00fb\u0001\u0000\u0000\u0000\u001e\u010e"+
		"\u0001\u0000\u0000\u0000 !\u0003\u0018\f\u0000!%\u0005\u0001\u0000\u0000"+
		"\"$\u0005\u0016\u0000\u0000#\"\u0001\u0000\u0000\u0000$\'\u0001\u0000"+
		"\u0000\u0000%#\u0001\u0000\u0000\u0000%&\u0001\u0000\u0000\u0000&)\u0001"+
		"\u0000\u0000\u0000\'%\u0001\u0000\u0000\u0000( \u0001\u0000\u0000\u0000"+
		"),\u0001\u0000\u0000\u0000*(\u0001\u0000\u0000\u0000*+\u0001\u0000\u0000"+
		"\u0000+-\u0001\u0000\u0000\u0000,*\u0001\u0000\u0000\u0000-.\u0003\u0018"+
		"\f\u0000./\u0006\u0000\uffff\uffff\u0000/\u00a2\u0001\u0000\u0000\u0000"+
		"01\u0003\u0018\f\u000015\u0005\u0001\u0000\u000024\u0005\u0016\u0000\u0000"+
		"32\u0001\u0000\u0000\u000047\u0001\u0000\u0000\u000053\u0001\u0000\u0000"+
		"\u000056\u0001\u0000\u0000\u000069\u0001\u0000\u0000\u000075\u0001\u0000"+
		"\u0000\u000080\u0001\u0000\u0000\u00009<\u0001\u0000\u0000\u0000:8\u0001"+
		"\u0000\u0000\u0000:;\u0001\u0000\u0000\u0000;=\u0001\u0000\u0000\u0000"+
		"<:\u0001\u0000\u0000\u0000=>\u0003\u0002\u0001\u0000>?\u0006\u0000\uffff"+
		"\uffff\u0000?C\u0005\u0001\u0000\u0000@B\u0005\u0016\u0000\u0000A@\u0001"+
		"\u0000\u0000\u0000BE\u0001\u0000\u0000\u0000CA\u0001\u0000\u0000\u0000"+
		"CD\u0001\u0000\u0000\u0000DG\u0001\u0000\u0000\u0000EC\u0001\u0000\u0000"+
		"\u0000F:\u0001\u0000\u0000\u0000GJ\u0001\u0000\u0000\u0000HF\u0001\u0000"+
		"\u0000\u0000HI\u0001\u0000\u0000\u0000IU\u0001\u0000\u0000\u0000JH\u0001"+
		"\u0000\u0000\u0000KL\u0003\u0018\f\u0000LP\u0005\u0001\u0000\u0000MO\u0005"+
		"\u0016\u0000\u0000NM\u0001\u0000\u0000\u0000OR\u0001\u0000\u0000\u0000"+
		"PN\u0001\u0000\u0000\u0000PQ\u0001\u0000\u0000\u0000QT\u0001\u0000\u0000"+
		"\u0000RP\u0001\u0000\u0000\u0000SK\u0001\u0000\u0000\u0000TW\u0001\u0000"+
		"\u0000\u0000US\u0001\u0000\u0000\u0000UV\u0001\u0000\u0000\u0000VX\u0001"+
		"\u0000\u0000\u0000WU\u0001\u0000\u0000\u0000XY\u0003\u0002\u0001\u0000"+
		"Y[\u0006\u0000\uffff\uffff\u0000Z\\\u0005\u0001\u0000\u0000[Z\u0001\u0000"+
		"\u0000\u0000[\\\u0001\u0000\u0000\u0000\\\u00a2\u0001\u0000\u0000\u0000"+
		"]^\u0003\u0018\f\u0000^b\u0005\u0001\u0000\u0000_a\u0005\u0016\u0000\u0000"+
		"`_\u0001\u0000\u0000\u0000ad\u0001\u0000\u0000\u0000b`\u0001\u0000\u0000"+
		"\u0000bc\u0001\u0000\u0000\u0000cf\u0001\u0000\u0000\u0000db\u0001\u0000"+
		"\u0000\u0000e]\u0001\u0000\u0000\u0000fi\u0001\u0000\u0000\u0000ge\u0001"+
		"\u0000\u0000\u0000gh\u0001\u0000\u0000\u0000hj\u0001\u0000\u0000\u0000"+
		"ig\u0001\u0000\u0000\u0000jk\u0003\u0002\u0001\u0000kl\u0006\u0000\uffff"+
		"\uffff\u0000lp\u0005\u0001\u0000\u0000mo\u0005\u0016\u0000\u0000nm\u0001"+
		"\u0000\u0000\u0000or\u0001\u0000\u0000\u0000pn\u0001\u0000\u0000\u0000"+
		"pq\u0001\u0000\u0000\u0000qt\u0001\u0000\u0000\u0000rp\u0001\u0000\u0000"+
		"\u0000sg\u0001\u0000\u0000\u0000tw\u0001\u0000\u0000\u0000us\u0001\u0000"+
		"\u0000\u0000uv\u0001\u0000\u0000\u0000v\u0082\u0001\u0000\u0000\u0000"+
		"wu\u0001\u0000\u0000\u0000xy\u0003\u0018\f\u0000y}\u0005\u0001\u0000\u0000"+
		"z|\u0005\u0016\u0000\u0000{z\u0001\u0000\u0000\u0000|\u007f\u0001\u0000"+
		"\u0000\u0000}{\u0001\u0000\u0000\u0000}~\u0001\u0000\u0000\u0000~\u0081"+
		"\u0001\u0000\u0000\u0000\u007f}\u0001\u0000\u0000\u0000\u0080x\u0001\u0000"+
		"\u0000\u0000\u0081\u0084\u0001\u0000\u0000\u0000\u0082\u0080\u0001\u0000"+
		"\u0000\u0000\u0082\u0083\u0001\u0000\u0000\u0000\u0083\u0085\u0001\u0000"+
		"\u0000\u0000\u0084\u0082\u0001\u0000\u0000\u0000\u0085\u0086\u0003\u0002"+
		"\u0001\u0000\u0086\u0087\u0006\u0000\uffff\uffff\u0000\u0087\u008b\u0005"+
		"\u0001\u0000\u0000\u0088\u008a\u0005\u0016\u0000\u0000\u0089\u0088\u0001"+
		"\u0000\u0000\u0000\u008a\u008d\u0001\u0000\u0000\u0000\u008b\u0089\u0001"+
		"\u0000\u0000\u0000\u008b\u008c\u0001\u0000\u0000\u0000\u008c\u0098\u0001"+
		"\u0000\u0000\u0000\u008d\u008b\u0001\u0000\u0000\u0000\u008e\u008f\u0003"+
		"\u0018\f\u0000\u008f\u0093\u0005\u0001\u0000\u0000\u0090\u0092\u0005\u0016"+
		"\u0000\u0000\u0091\u0090\u0001\u0000\u0000\u0000\u0092\u0095\u0001\u0000"+
		"\u0000\u0000\u0093\u0091\u0001\u0000\u0000\u0000\u0093\u0094\u0001\u0000"+
		"\u0000\u0000\u0094\u0097\u0001\u0000\u0000\u0000\u0095\u0093\u0001\u0000"+
		"\u0000\u0000\u0096\u008e\u0001\u0000\u0000\u0000\u0097\u009a\u0001\u0000"+
		"\u0000\u0000\u0098\u0096\u0001\u0000\u0000\u0000\u0098\u0099\u0001\u0000"+
		"\u0000\u0000\u0099\u009b\u0001\u0000\u0000\u0000\u009a\u0098\u0001\u0000"+
		"\u0000\u0000\u009b\u009d\u0003\u0018\f\u0000\u009c\u009e\u0005\u0001\u0000"+
		"\u0000\u009d\u009c\u0001\u0000\u0000\u0000\u009d\u009e\u0001\u0000\u0000"+
		"\u0000\u009e\u009f\u0001\u0000\u0000\u0000\u009f\u00a0\u0006\u0000\uffff"+
		"\uffff\u0000\u00a0\u00a2\u0001\u0000\u0000\u0000\u00a1*\u0001\u0000\u0000"+
		"\u0000\u00a1H\u0001\u0000\u0000\u0000\u00a1u\u0001\u0000\u0000\u0000\u00a2"+
		"\u0001\u0001\u0000\u0000\u0000\u00a3\u00a4\u0003\u0004\u0002\u0000\u00a4"+
		"\u00a5\u0006\u0001\uffff\uffff\u0000\u00a5\u00b3\u0001\u0000\u0000\u0000"+
		"\u00a6\u00a7\u0003\b\u0004\u0000\u00a7\u00a8\u0006\u0001\uffff\uffff\u0000"+
		"\u00a8\u00b3\u0001\u0000\u0000\u0000\u00a9\u00aa\u0003\f\u0006\u0000\u00aa"+
		"\u00ab\u0006\u0001\uffff\uffff\u0000\u00ab\u00b3\u0001\u0000\u0000\u0000"+
		"\u00ac\u00ad\u0003\u0010\b\u0000\u00ad\u00ae\u0006\u0001\uffff\uffff\u0000"+
		"\u00ae\u00b3\u0001\u0000\u0000\u0000\u00af\u00b0\u0003\u0014\n\u0000\u00b0"+
		"\u00b1\u0006\u0001\uffff\uffff\u0000\u00b1\u00b3\u0001\u0000\u0000\u0000"+
		"\u00b2\u00a3\u0001\u0000\u0000\u0000\u00b2\u00a6\u0001\u0000\u0000\u0000"+
		"\u00b2\u00a9\u0001\u0000\u0000\u0000\u00b2\u00ac\u0001\u0000\u0000\u0000"+
		"\u00b2\u00af\u0001\u0000\u0000\u0000\u00b3\u0003\u0001\u0000\u0000\u0000"+
		"\u00b4\u00b6\u0003\u0006\u0003\u0000\u00b5\u00b7\u0003\u001c\u000e\u0000"+
		"\u00b6\u00b5\u0001\u0000\u0000\u0000\u00b6\u00b7\u0001\u0000\u0000\u0000"+
		"\u00b7\u00b8\u0001\u0000\u0000\u0000\u00b8\u00b9\u0006\u0002\uffff\uffff"+
		"\u0000\u00b9\u0005\u0001\u0000\u0000\u0000\u00ba\u00bb\u0007\u0000\u0000"+
		"\u0000\u00bb\u0007\u0001\u0000\u0000\u0000\u00bc\u00bd\u0003\n\u0005\u0000"+
		"\u00bd\u00c1\u0006\u0004\uffff\uffff\u0000\u00be\u00bf\u0003\u001a\r\u0000"+
		"\u00bf\u00c0\u0006\u0004\uffff\uffff\u0000\u00c0\u00c2\u0001\u0000\u0000"+
		"\u0000\u00c1\u00be\u0001\u0000\u0000\u0000\u00c1\u00c2\u0001\u0000\u0000"+
		"\u0000\u00c2\t\u0001\u0000\u0000\u0000\u00c3\u00c4\u0007\u0001\u0000\u0000"+
		"\u00c4\u000b\u0001\u0000\u0000\u0000\u00c5\u00c7\u0003\u000e\u0007\u0000"+
		"\u00c6\u00c8\u0003\u001e\u000f\u0000\u00c7\u00c6\u0001\u0000\u0000\u0000"+
		"\u00c7\u00c8\u0001\u0000\u0000\u0000\u00c8\u00c9\u0001\u0000\u0000\u0000"+
		"\u00c9\u00ca\u0006\u0006\uffff\uffff\u0000\u00ca\r\u0001\u0000\u0000\u0000"+
		"\u00cb\u00cc\u0007\u0002\u0000\u0000\u00cc\u000f\u0001\u0000\u0000\u0000"+
		"\u00cd\u00cf\u0003\u0012\t\u0000\u00ce\u00d0\u0003\u001e\u000f\u0000\u00cf"+
		"\u00ce\u0001\u0000\u0000\u0000\u00cf\u00d0\u0001\u0000\u0000\u0000\u00d0"+
		"\u00d1\u0001\u0000\u0000\u0000\u00d1\u00d2\u0006\b\uffff\uffff\u0000\u00d2"+
		"\u0011\u0001\u0000\u0000\u0000\u00d3\u00d4\u0005\u0012\u0000\u0000\u00d4"+
		"\u0013\u0001\u0000\u0000\u0000\u00d5\u00d7\u0003\u0016\u000b\u0000\u00d6"+
		"\u00d8\u0003\u001c\u000e\u0000\u00d7\u00d6\u0001\u0000\u0000\u0000\u00d7"+
		"\u00d8\u0001\u0000\u0000\u0000\u00d8\u00d9\u0001\u0000\u0000\u0000\u00d9"+
		"\u00da\u0006\n\uffff\uffff\u0000\u00da\u0015\u0001\u0000\u0000\u0000\u00db"+
		"\u00dc\u0007\u0003\u0000\u0000\u00dc\u0017\u0001\u0000\u0000\u0000\u00dd"+
		"\u00df\u0007\u0004\u0000\u0000\u00de\u00dd\u0001\u0000\u0000\u0000\u00df"+
		"\u00e2\u0001\u0000\u0000\u0000\u00e0\u00de\u0001\u0000\u0000\u0000\u00e0"+
		"\u00e1\u0001\u0000\u0000\u0000\u00e1\u0019\u0001\u0000\u0000\u0000\u00e2"+
		"\u00e0\u0001\u0000\u0000\u0000\u00e3\u00e4\u0003\u001e\u000f\u0000\u00e4"+
		"\u00e5\u0006\r\uffff\uffff\u0000\u00e5\u00e6\u0005\u0015\u0000\u0000\u00e6"+
		"\u00e8\u0001\u0000\u0000\u0000\u00e7\u00e3\u0001\u0000\u0000\u0000\u00e8"+
		"\u00eb\u0001\u0000\u0000\u0000\u00e9\u00e7\u0001\u0000\u0000\u0000\u00e9"+
		"\u00ea\u0001\u0000\u0000\u0000\u00ea\u00f7\u0001\u0000\u0000\u0000\u00eb"+
		"\u00e9\u0001\u0000\u0000\u0000\u00ec\u00ed\u0003\u001e\u000f\u0000\u00ed"+
		"\u00ee\u0006\r\uffff\uffff\u0000\u00ee\u00f8\u0001\u0000\u0000\u0000\u00ef"+
		"\u00f1\u0005\u0016\u0000\u0000\u00f0\u00ef\u0001\u0000\u0000\u0000\u00f1"+
		"\u00f4\u0001\u0000\u0000\u0000\u00f2\u00f0\u0001\u0000\u0000\u0000\u00f2"+
		"\u00f3\u0001\u0000\u0000\u0000\u00f3\u00f5\u0001\u0000\u0000\u0000\u00f4"+
		"\u00f2\u0001\u0000\u0000\u0000\u00f5\u00f6\u0005\u0000\u0000\u0001\u00f6"+
		"\u00f8\u0006\r\uffff\uffff\u0000\u00f7\u00ec\u0001\u0000\u0000\u0000\u00f7"+
		"\u00f2\u0001\u0000\u0000\u0000\u00f8\u001b\u0001\u0000\u0000\u0000\u00f9"+
		"\u00fc\u0003\u001e\u000f\u0000\u00fa\u00fc\u0005\u0015\u0000\u0000\u00fb"+
		"\u00f9\u0001\u0000\u0000\u0000\u00fb\u00fa\u0001\u0000\u0000\u0000\u00fc"+
		"\u00fd\u0001\u0000\u0000\u0000\u00fd\u00fb\u0001\u0000\u0000\u0000\u00fd"+
		"\u00fe\u0001\u0000\u0000\u0000\u00fe\u001d\u0001\u0000\u0000\u0000\u00ff"+
		"\u0101\u0005\u0016\u0000\u0000\u0100\u00ff\u0001\u0000\u0000\u0000\u0101"+
		"\u0102\u0001\u0000\u0000\u0000\u0102\u0100\u0001\u0000\u0000\u0000\u0102"+
		"\u0103\u0001\u0000\u0000\u0000\u0103\u010f\u0001\u0000\u0000\u0000\u0104"+
		"\u0106\u0005\u0017\u0000\u0000\u0105\u0104\u0001\u0000\u0000\u0000\u0106"+
		"\u0107\u0001\u0000\u0000\u0000\u0107\u0105\u0001\u0000\u0000\u0000\u0107"+
		"\u0108\u0001\u0000\u0000\u0000\u0108\u010f\u0001\u0000\u0000\u0000\u0109"+
		"\u010f\u0003\u0006\u0003\u0000\u010a\u010f\u0003\n\u0005\u0000\u010b\u010f"+
		"\u0003\u000e\u0007\u0000\u010c\u010f\u0003\u0012\t\u0000\u010d\u010f\u0003"+
		"\u0016\u000b\u0000\u010e\u0100\u0001\u0000\u0000\u0000\u010e\u0105\u0001"+
		"\u0000\u0000\u0000\u010e\u0109\u0001\u0000\u0000\u0000\u010e\u010a\u0001"+
		"\u0000\u0000\u0000\u010e\u010b\u0001\u0000\u0000\u0000\u010e\u010c\u0001"+
		"\u0000\u0000\u0000\u010e\u010d\u0001\u0000\u0000\u0000\u010f\u0110\u0001"+
		"\u0000\u0000\u0000\u0110\u010e\u0001\u0000\u0000\u0000\u0110\u0111\u0001"+
		"\u0000\u0000\u0000\u0111\u001f\u0001\u0000\u0000\u0000$%*5:CHPU[bgpu}"+
		"\u0082\u008b\u0093\u0098\u009d\u00a1\u00b2\u00b6\u00c1\u00c7\u00cf\u00d7"+
		"\u00e0\u00e9\u00f2\u00f7\u00fb\u00fd\u0102\u0107\u010e\u0110";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
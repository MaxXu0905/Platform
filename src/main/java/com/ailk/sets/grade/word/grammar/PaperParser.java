// Generated from Paper.g4 by ANTLR 4.2.2
package com.ailk.sets.grade.word.grammar;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PaperParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		UNIT_HOUR=1, UNIT_MINUTE=2, UNIT_SECOND=3, PROMPT_PAPER_NAME=4, PROMPT_PAPER_DESC=5, 
		PROMPT_TIME_LIMIT=6, UNIT_POINT=7, PROMPT_POINT=8, PROMPT_SKILL_REORDER=9, 
		PROMPT_QUESTION_REORDER=10, PROMPT_CHOICE_REORDER=11, PROMPT_BACKWARD=12, 
		PROMPT_YES=13, PROMPT_NO=14, HINT_SCQ=15, HINT_MCQS=16, HINT_VERY_EASY=17, 
		HINT_EASY=18, HINT_MODERATE=19, HINT_DIFFICULT=20, HINT_VERY_DIFFICULT=21, 
		HINT_SAMPLE=22, PROMPT_ANSWER=23, PROMPT_SUPPLEMENT=24, CHOICE_PREFIX=25, 
		DIGIT_PREFIX=26, COMMA=27, COLON=28, LBRACE=29, RBRACE=30, HINT_LANGUAGE=31, 
		INT=32, NL=33, WS=34, WORD=35;
	public static final String[] tokenNames = {
		"<INVALID>", "'小时'", "'分钟'", "'秒'", "PROMPT_PAPER_NAME", "PROMPT_PAPER_DESC", 
		"PROMPT_TIME_LIMIT", "'分'", "PROMPT_POINT", "PROMPT_SKILL_REORDER", "PROMPT_QUESTION_REORDER", 
		"PROMPT_CHOICE_REORDER", "PROMPT_BACKWARD", "PROMPT_YES", "PROMPT_NO", 
		"HINT_SCQ", "HINT_MCQS", "'极易'", "'易'", "'中'", "'难'", "'极难'", "'样例'", 
		"PROMPT_ANSWER", "PROMPT_SUPPLEMENT", "CHOICE_PREFIX", "DIGIT_PREFIX", 
		"COMMA", "COLON", "LBRACE", "RBRACE", "HINT_LANGUAGE", "INT", "'\n'", 
		"WS", "WORD"
	};
	public static final int
		RULE_description = 0, RULE_paperName = 1, RULE_paperDesc = 2, RULE_descTime = 3, 
		RULE_unitTime = 4, RULE_descPoint = 5, RULE_skillReorder = 6, RULE_questionReorder = 7, 
		RULE_choiceReorder = 8, RULE_backward = 9, RULE_yesOrNo = 10, RULE_questionWithoutChoices = 11, 
		RULE_question = 12, RULE_title = 13, RULE_choices = 14, RULE_choice = 15, 
		RULE_answer = 16, RULE_supplement = 17, RULE_sentence = 18, RULE_skill = 19, 
		RULE_words = 20, RULE_word = 21, RULE_punctuation = 22, RULE_unit = 23, 
		RULE_hint = 24, RULE_prompt = 25;
	public static final String[] ruleNames = {
		"description", "paperName", "paperDesc", "descTime", "unitTime", "descPoint", 
		"skillReorder", "questionReorder", "choiceReorder", "backward", "yesOrNo", 
		"questionWithoutChoices", "question", "title", "choices", "choice", "answer", 
		"supplement", "sentence", "skill", "words", "word", "punctuation", "unit", 
		"hint", "prompt"
	};

	@Override
	public String getGrammarFileName() { return "Paper.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public PaperParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class DescriptionContext extends ParserRuleContext {
		public List<PaperNameContext> paperName() {
			return getRuleContexts(PaperNameContext.class);
		}
		public List<DescTimeContext> descTime() {
			return getRuleContexts(DescTimeContext.class);
		}
		public List<TerminalNode> NL() { return getTokens(PaperParser.NL); }
		public PaperDescContext paperDesc(int i) {
			return getRuleContext(PaperDescContext.class,i);
		}
		public List<QuestionReorderContext> questionReorder() {
			return getRuleContexts(QuestionReorderContext.class);
		}
		public PaperNameContext paperName(int i) {
			return getRuleContext(PaperNameContext.class,i);
		}
		public List<PaperDescContext> paperDesc() {
			return getRuleContexts(PaperDescContext.class);
		}
		public WordsContext words(int i) {
			return getRuleContext(WordsContext.class,i);
		}
		public DescTimeContext descTime(int i) {
			return getRuleContext(DescTimeContext.class,i);
		}
		public BackwardContext backward(int i) {
			return getRuleContext(BackwardContext.class,i);
		}
		public List<ChoiceReorderContext> choiceReorder() {
			return getRuleContexts(ChoiceReorderContext.class);
		}
		public List<DescPointContext> descPoint() {
			return getRuleContexts(DescPointContext.class);
		}
		public QuestionReorderContext questionReorder(int i) {
			return getRuleContext(QuestionReorderContext.class,i);
		}
		public SkillReorderContext skillReorder(int i) {
			return getRuleContext(SkillReorderContext.class,i);
		}
		public TerminalNode NL(int i) {
			return getToken(PaperParser.NL, i);
		}
		public List<WordsContext> words() {
			return getRuleContexts(WordsContext.class);
		}
		public ChoiceReorderContext choiceReorder(int i) {
			return getRuleContext(ChoiceReorderContext.class,i);
		}
		public List<BackwardContext> backward() {
			return getRuleContexts(BackwardContext.class);
		}
		public List<SkillReorderContext> skillReorder() {
			return getRuleContexts(SkillReorderContext.class);
		}
		public DescPointContext descPoint(int i) {
			return getRuleContext(DescPointContext.class,i);
		}
		public DescriptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_description; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterDescription(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitDescription(this);
		}
	}

	public final DescriptionContext description() throws RecognitionException {
		DescriptionContext _localctx = new DescriptionContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_description);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(53);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(52); words();
					}
					break;
				}
				setState(69);
				switch (_input.LA(1)) {
				case PROMPT_PAPER_NAME:
					{
					setState(55); paperName();
					}
					break;
				case PROMPT_PAPER_DESC:
					{
					setState(56); paperDesc();
					}
					break;
				case UNIT_HOUR:
				case UNIT_MINUTE:
				case UNIT_SECOND:
				case PROMPT_TIME_LIMIT:
				case UNIT_POINT:
				case PROMPT_POINT:
				case PROMPT_SKILL_REORDER:
				case PROMPT_QUESTION_REORDER:
				case PROMPT_CHOICE_REORDER:
				case PROMPT_BACKWARD:
				case PROMPT_YES:
				case PROMPT_NO:
				case HINT_SCQ:
				case HINT_MCQS:
				case HINT_VERY_EASY:
				case HINT_EASY:
				case HINT_MODERATE:
				case HINT_DIFFICULT:
				case HINT_VERY_DIFFICULT:
				case HINT_SAMPLE:
				case CHOICE_PREFIX:
				case DIGIT_PREFIX:
				case COMMA:
				case COLON:
				case LBRACE:
				case RBRACE:
				case HINT_LANGUAGE:
				case INT:
				case NL:
				case WORD:
					{
					setState(64);
					switch (_input.LA(1)) {
					case PROMPT_TIME_LIMIT:
						{
						setState(57); descTime();
						}
						break;
					case PROMPT_POINT:
						{
						setState(58); descPoint();
						}
						break;
					case PROMPT_SKILL_REORDER:
						{
						setState(59); skillReorder();
						}
						break;
					case PROMPT_QUESTION_REORDER:
						{
						setState(60); questionReorder();
						}
						break;
					case PROMPT_CHOICE_REORDER:
						{
						setState(61); choiceReorder();
						}
						break;
					case PROMPT_BACKWARD:
						{
						setState(62); backward();
						}
						break;
					case UNIT_HOUR:
					case UNIT_MINUTE:
					case UNIT_SECOND:
					case UNIT_POINT:
					case PROMPT_YES:
					case PROMPT_NO:
					case HINT_SCQ:
					case HINT_MCQS:
					case HINT_VERY_EASY:
					case HINT_EASY:
					case HINT_MODERATE:
					case HINT_DIFFICULT:
					case HINT_VERY_DIFFICULT:
					case HINT_SAMPLE:
					case CHOICE_PREFIX:
					case DIGIT_PREFIX:
					case COMMA:
					case COLON:
					case LBRACE:
					case RBRACE:
					case HINT_LANGUAGE:
					case INT:
					case NL:
					case WORD:
						{
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(67);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << UNIT_HOUR) | (1L << UNIT_MINUTE) | (1L << UNIT_SECOND) | (1L << UNIT_POINT) | (1L << PROMPT_YES) | (1L << PROMPT_NO) | (1L << HINT_SCQ) | (1L << HINT_MCQS) | (1L << HINT_VERY_EASY) | (1L << HINT_EASY) | (1L << HINT_MODERATE) | (1L << HINT_DIFFICULT) | (1L << HINT_VERY_DIFFICULT) | (1L << HINT_SAMPLE) | (1L << CHOICE_PREFIX) | (1L << DIGIT_PREFIX) | (1L << COMMA) | (1L << COLON) | (1L << LBRACE) | (1L << RBRACE) | (1L << HINT_LANGUAGE) | (1L << INT) | (1L << WORD))) != 0)) {
						{
						setState(66); words();
						}
					}

					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(71); match(NL);
				}
				}
				setState(74); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << UNIT_HOUR) | (1L << UNIT_MINUTE) | (1L << UNIT_SECOND) | (1L << PROMPT_PAPER_NAME) | (1L << PROMPT_PAPER_DESC) | (1L << PROMPT_TIME_LIMIT) | (1L << UNIT_POINT) | (1L << PROMPT_POINT) | (1L << PROMPT_SKILL_REORDER) | (1L << PROMPT_QUESTION_REORDER) | (1L << PROMPT_CHOICE_REORDER) | (1L << PROMPT_BACKWARD) | (1L << PROMPT_YES) | (1L << PROMPT_NO) | (1L << HINT_SCQ) | (1L << HINT_MCQS) | (1L << HINT_VERY_EASY) | (1L << HINT_EASY) | (1L << HINT_MODERATE) | (1L << HINT_DIFFICULT) | (1L << HINT_VERY_DIFFICULT) | (1L << HINT_SAMPLE) | (1L << CHOICE_PREFIX) | (1L << DIGIT_PREFIX) | (1L << COMMA) | (1L << COLON) | (1L << LBRACE) | (1L << RBRACE) | (1L << HINT_LANGUAGE) | (1L << INT) | (1L << NL) | (1L << WORD))) != 0) );
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

	public static class PaperNameContext extends ParserRuleContext {
		public TerminalNode PROMPT_PAPER_NAME() { return getToken(PaperParser.PROMPT_PAPER_NAME, 0); }
		public WordsContext words() {
			return getRuleContext(WordsContext.class,0);
		}
		public PaperNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paperName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterPaperName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitPaperName(this);
		}
	}

	public final PaperNameContext paperName() throws RecognitionException {
		PaperNameContext _localctx = new PaperNameContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_paperName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76); match(PROMPT_PAPER_NAME);
			setState(77); words();
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

	public static class PaperDescContext extends ParserRuleContext {
		public WordsContext words() {
			return getRuleContext(WordsContext.class,0);
		}
		public TerminalNode PROMPT_PAPER_DESC() { return getToken(PaperParser.PROMPT_PAPER_DESC, 0); }
		public PaperDescContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paperDesc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterPaperDesc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitPaperDesc(this);
		}
	}

	public final PaperDescContext paperDesc() throws RecognitionException {
		PaperDescContext _localctx = new PaperDescContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_paperDesc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79); match(PROMPT_PAPER_DESC);
			setState(80); words();
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

	public static class DescTimeContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(PaperParser.INT, 0); }
		public UnitTimeContext unitTime() {
			return getRuleContext(UnitTimeContext.class,0);
		}
		public TerminalNode PROMPT_TIME_LIMIT() { return getToken(PaperParser.PROMPT_TIME_LIMIT, 0); }
		public DescTimeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_descTime; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterDescTime(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitDescTime(this);
		}
	}

	public final DescTimeContext descTime() throws RecognitionException {
		DescTimeContext _localctx = new DescTimeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_descTime);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82); match(PROMPT_TIME_LIMIT);
			setState(83); match(INT);
			setState(84); unitTime();
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

	public static class UnitTimeContext extends ParserRuleContext {
		public TerminalNode UNIT_HOUR() { return getToken(PaperParser.UNIT_HOUR, 0); }
		public TerminalNode UNIT_MINUTE() { return getToken(PaperParser.UNIT_MINUTE, 0); }
		public TerminalNode UNIT_SECOND() { return getToken(PaperParser.UNIT_SECOND, 0); }
		public UnitTimeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unitTime; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterUnitTime(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitUnitTime(this);
		}
	}

	public final UnitTimeContext unitTime() throws RecognitionException {
		UnitTimeContext _localctx = new UnitTimeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_unitTime);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << UNIT_HOUR) | (1L << UNIT_MINUTE) | (1L << UNIT_SECOND))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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

	public static class DescPointContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(PaperParser.INT, 0); }
		public TerminalNode PROMPT_POINT() { return getToken(PaperParser.PROMPT_POINT, 0); }
		public TerminalNode UNIT_POINT() { return getToken(PaperParser.UNIT_POINT, 0); }
		public DescPointContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_descPoint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterDescPoint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitDescPoint(this);
		}
	}

	public final DescPointContext descPoint() throws RecognitionException {
		DescPointContext _localctx = new DescPointContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_descPoint);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88); match(PROMPT_POINT);
			setState(89); match(INT);
			setState(90); match(UNIT_POINT);
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

	public static class SkillReorderContext extends ParserRuleContext {
		public YesOrNoContext yesOrNo() {
			return getRuleContext(YesOrNoContext.class,0);
		}
		public TerminalNode PROMPT_SKILL_REORDER() { return getToken(PaperParser.PROMPT_SKILL_REORDER, 0); }
		public SkillReorderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_skillReorder; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterSkillReorder(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitSkillReorder(this);
		}
	}

	public final SkillReorderContext skillReorder() throws RecognitionException {
		SkillReorderContext _localctx = new SkillReorderContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_skillReorder);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92); match(PROMPT_SKILL_REORDER);
			setState(93); yesOrNo();
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

	public static class QuestionReorderContext extends ParserRuleContext {
		public TerminalNode PROMPT_QUESTION_REORDER() { return getToken(PaperParser.PROMPT_QUESTION_REORDER, 0); }
		public YesOrNoContext yesOrNo() {
			return getRuleContext(YesOrNoContext.class,0);
		}
		public QuestionReorderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_questionReorder; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterQuestionReorder(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitQuestionReorder(this);
		}
	}

	public final QuestionReorderContext questionReorder() throws RecognitionException {
		QuestionReorderContext _localctx = new QuestionReorderContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_questionReorder);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95); match(PROMPT_QUESTION_REORDER);
			setState(96); yesOrNo();
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

	public static class ChoiceReorderContext extends ParserRuleContext {
		public YesOrNoContext yesOrNo() {
			return getRuleContext(YesOrNoContext.class,0);
		}
		public TerminalNode PROMPT_CHOICE_REORDER() { return getToken(PaperParser.PROMPT_CHOICE_REORDER, 0); }
		public ChoiceReorderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_choiceReorder; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterChoiceReorder(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitChoiceReorder(this);
		}
	}

	public final ChoiceReorderContext choiceReorder() throws RecognitionException {
		ChoiceReorderContext _localctx = new ChoiceReorderContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_choiceReorder);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98); match(PROMPT_CHOICE_REORDER);
			setState(99); yesOrNo();
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

	public static class BackwardContext extends ParserRuleContext {
		public YesOrNoContext yesOrNo() {
			return getRuleContext(YesOrNoContext.class,0);
		}
		public TerminalNode PROMPT_BACKWARD() { return getToken(PaperParser.PROMPT_BACKWARD, 0); }
		public BackwardContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_backward; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterBackward(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitBackward(this);
		}
	}

	public final BackwardContext backward() throws RecognitionException {
		BackwardContext _localctx = new BackwardContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_backward);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(101); match(PROMPT_BACKWARD);
			setState(102); yesOrNo();
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

	public static class YesOrNoContext extends ParserRuleContext {
		public TerminalNode PROMPT_YES() { return getToken(PaperParser.PROMPT_YES, 0); }
		public TerminalNode PROMPT_NO() { return getToken(PaperParser.PROMPT_NO, 0); }
		public YesOrNoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_yesOrNo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterYesOrNo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitYesOrNo(this);
		}
	}

	public final YesOrNoContext yesOrNo() throws RecognitionException {
		YesOrNoContext _localctx = new YesOrNoContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_yesOrNo);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			_la = _input.LA(1);
			if ( !(_la==PROMPT_YES || _la==PROMPT_NO) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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

	public static class QuestionWithoutChoicesContext extends ParserRuleContext {
		public List<TerminalNode> NL() { return getTokens(PaperParser.NL); }
		public SupplementContext supplement(int i) {
			return getRuleContext(SupplementContext.class,i);
		}
		public TerminalNode NL(int i) {
			return getToken(PaperParser.NL, i);
		}
		public AnswerContext answer(int i) {
			return getRuleContext(AnswerContext.class,i);
		}
		public List<AnswerContext> answer() {
			return getRuleContexts(AnswerContext.class);
		}
		public TerminalNode DIGIT_PREFIX() { return getToken(PaperParser.DIGIT_PREFIX, 0); }
		public TitleContext title() {
			return getRuleContext(TitleContext.class,0);
		}
		public List<SupplementContext> supplement() {
			return getRuleContexts(SupplementContext.class);
		}
		public QuestionWithoutChoicesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_questionWithoutChoices; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterQuestionWithoutChoices(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitQuestionWithoutChoices(this);
		}
	}

	public final QuestionWithoutChoicesContext questionWithoutChoices() throws RecognitionException {
		QuestionWithoutChoicesContext _localctx = new QuestionWithoutChoicesContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_questionWithoutChoices);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(106); match(DIGIT_PREFIX);
				}
				break;
			}
			setState(109); title();
			setState(117);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(110); match(NL);
				setState(113);
				switch (_input.LA(1)) {
				case PROMPT_ANSWER:
					{
					setState(111); answer();
					}
					break;
				case PROMPT_SUPPLEMENT:
					{
					setState(112); supplement();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				}
				setState(119);
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

	public static class QuestionContext extends ParserRuleContext {
		public List<TerminalNode> NL() { return getTokens(PaperParser.NL); }
		public SupplementContext supplement(int i) {
			return getRuleContext(SupplementContext.class,i);
		}
		public TerminalNode NL(int i) {
			return getToken(PaperParser.NL, i);
		}
		public AnswerContext answer(int i) {
			return getRuleContext(AnswerContext.class,i);
		}
		public List<AnswerContext> answer() {
			return getRuleContexts(AnswerContext.class);
		}
		public TerminalNode DIGIT_PREFIX() { return getToken(PaperParser.DIGIT_PREFIX, 0); }
		public TitleContext title() {
			return getRuleContext(TitleContext.class,0);
		}
		public ChoicesContext choices() {
			return getRuleContext(ChoicesContext.class,0);
		}
		public List<SupplementContext> supplement() {
			return getRuleContexts(SupplementContext.class);
		}
		public QuestionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_question; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterQuestion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitQuestion(this);
		}
	}

	public final QuestionContext question() throws RecognitionException {
		QuestionContext _localctx = new QuestionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_question);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				setState(120); match(DIGIT_PREFIX);
				}
				break;
			}
			setState(123); title();
			setState(126);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				setState(124); match(NL);
				setState(125); choices();
				}
				break;
			}
			setState(135);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(128); match(NL);
				setState(131);
				switch (_input.LA(1)) {
				case PROMPT_ANSWER:
					{
					setState(129); answer();
					}
					break;
				case PROMPT_SUPPLEMENT:
					{
					setState(130); supplement();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				}
				setState(137);
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

	public static class TitleContext extends ParserRuleContext {
		public List<TerminalNode> NL() { return getTokens(PaperParser.NL); }
		public TerminalNode RBRACE(int i) {
			return getToken(PaperParser.RBRACE, i);
		}
		public List<TerminalNode> LBRACE() { return getTokens(PaperParser.LBRACE); }
		public List<HintContext> hint() {
			return getRuleContexts(HintContext.class);
		}
		public SentenceContext sentence() {
			return getRuleContext(SentenceContext.class,0);
		}
		public TerminalNode NL(int i) {
			return getToken(PaperParser.NL, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(PaperParser.COMMA); }
		public SkillContext skill() {
			return getRuleContext(SkillContext.class,0);
		}
		public List<TerminalNode> RBRACE() { return getTokens(PaperParser.RBRACE); }
		public HintContext hint(int i) {
			return getRuleContext(HintContext.class,i);
		}
		public TerminalNode LBRACE(int i) {
			return getToken(PaperParser.LBRACE, i);
		}
		public TerminalNode COMMA(int i) {
			return getToken(PaperParser.COMMA, i);
		}
		public TitleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_title; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterTitle(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitTitle(this);
		}
	}

	public final TitleContext title() throws RecognitionException {
		TitleContext _localctx = new TitleContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_title);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(154);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				{
				setState(138); match(LBRACE);
				setState(139); hint();
				setState(144);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(140); match(COMMA);
					setState(141); hint();
					}
					}
					setState(146);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(147); match(RBRACE);
				setState(152);
				switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
				case 1:
					{
					setState(148); match(LBRACE);
					setState(149); skill();
					setState(150); match(RBRACE);
					}
					break;
				}
				}
				break;
			}
			setState(159);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(156); match(NL);
				}
				}
				setState(161);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(162); sentence();
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

	public static class ChoicesContext extends ParserRuleContext {
		public List<TerminalNode> NL() { return getTokens(PaperParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(PaperParser.NL, i);
		}
		public ChoiceContext choice(int i) {
			return getRuleContext(ChoiceContext.class,i);
		}
		public List<ChoiceContext> choice() {
			return getRuleContexts(ChoiceContext.class);
		}
		public ChoicesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_choices; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterChoices(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitChoices(this);
		}
	}

	public final ChoicesContext choices() throws RecognitionException {
		ChoicesContext _localctx = new ChoicesContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_choices);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(164); choice();
			setState(169); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(166);
					_la = _input.LA(1);
					if (_la==NL) {
						{
						setState(165); match(NL);
						}
					}

					setState(168); choice();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(171); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			} while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER );
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

	public static class ChoiceContext extends ParserRuleContext {
		public SentenceContext sentence() {
			return getRuleContext(SentenceContext.class,0);
		}
		public TerminalNode CHOICE_PREFIX() { return getToken(PaperParser.CHOICE_PREFIX, 0); }
		public ChoiceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_choice; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterChoice(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitChoice(this);
		}
	}

	public final ChoiceContext choice() throws RecognitionException {
		ChoiceContext _localctx = new ChoiceContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_choice);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173); match(CHOICE_PREFIX);
			setState(174); sentence();
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

	public static class AnswerContext extends ParserRuleContext {
		public SentenceContext sentence() {
			return getRuleContext(SentenceContext.class,0);
		}
		public TerminalNode PROMPT_ANSWER() { return getToken(PaperParser.PROMPT_ANSWER, 0); }
		public AnswerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_answer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterAnswer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitAnswer(this);
		}
	}

	public final AnswerContext answer() throws RecognitionException {
		AnswerContext _localctx = new AnswerContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_answer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(176); match(PROMPT_ANSWER);
			setState(177); sentence();
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

	public static class SupplementContext extends ParserRuleContext {
		public SentenceContext sentence() {
			return getRuleContext(SentenceContext.class,0);
		}
		public TerminalNode PROMPT_SUPPLEMENT() { return getToken(PaperParser.PROMPT_SUPPLEMENT, 0); }
		public SupplementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_supplement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterSupplement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitSupplement(this);
		}
	}

	public final SupplementContext supplement() throws RecognitionException {
		SupplementContext _localctx = new SupplementContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_supplement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(179); match(PROMPT_SUPPLEMENT);
			setState(180); sentence();
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

	public static class SentenceContext extends ParserRuleContext {
		public List<TerminalNode> NL() { return getTokens(PaperParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(PaperParser.NL, i);
		}
		public List<WordsContext> words() {
			return getRuleContexts(WordsContext.class);
		}
		public WordsContext words(int i) {
			return getRuleContext(WordsContext.class,i);
		}
		public SentenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sentence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterSentence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitSentence(this);
		}
	}

	public final SentenceContext sentence() throws RecognitionException {
		SentenceContext _localctx = new SentenceContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_sentence);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(182); words();
			setState(189);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=1 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(183); match(NL);
					setState(185);
					switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
					case 1:
						{
						setState(184); words();
						}
						break;
					}
					}
					} 
				}
				setState(191);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
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

	public static class SkillContext extends ParserRuleContext {
		public List<PromptContext> prompt() {
			return getRuleContexts(PromptContext.class);
		}
		public List<TerminalNode> WORD() { return getTokens(PaperParser.WORD); }
		public List<HintContext> hint() {
			return getRuleContexts(HintContext.class);
		}
		public List<TerminalNode> INT() { return getTokens(PaperParser.INT); }
		public List<UnitContext> unit() {
			return getRuleContexts(UnitContext.class);
		}
		public HintContext hint(int i) {
			return getRuleContext(HintContext.class,i);
		}
		public UnitContext unit(int i) {
			return getRuleContext(UnitContext.class,i);
		}
		public TerminalNode INT(int i) {
			return getToken(PaperParser.INT, i);
		}
		public PromptContext prompt(int i) {
			return getRuleContext(PromptContext.class,i);
		}
		public TerminalNode WORD(int i) {
			return getToken(PaperParser.WORD, i);
		}
		public SkillContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_skill; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterSkill(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitSkill(this);
		}
	}

	public final SkillContext skill() throws RecognitionException {
		SkillContext _localctx = new SkillContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_skill);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(197);
				switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
				case 1:
					{
					setState(192); match(INT);
					}
					break;

				case 2:
					{
					setState(193); match(WORD);
					}
					break;

				case 3:
					{
					setState(194); unit();
					}
					break;

				case 4:
					{
					setState(195); hint();
					}
					break;

				case 5:
					{
					setState(196); prompt();
					}
					break;
				}
				}
				setState(199); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << UNIT_HOUR) | (1L << UNIT_MINUTE) | (1L << UNIT_SECOND) | (1L << UNIT_POINT) | (1L << PROMPT_YES) | (1L << PROMPT_NO) | (1L << HINT_SCQ) | (1L << HINT_MCQS) | (1L << HINT_VERY_EASY) | (1L << HINT_EASY) | (1L << HINT_MODERATE) | (1L << HINT_DIFFICULT) | (1L << HINT_VERY_DIFFICULT) | (1L << HINT_SAMPLE) | (1L << HINT_LANGUAGE) | (1L << INT) | (1L << WORD))) != 0) );
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

	public static class WordsContext extends ParserRuleContext {
		public List<WordContext> word() {
			return getRuleContexts(WordContext.class);
		}
		public TerminalNode DIGIT_PREFIX(int i) {
			return getToken(PaperParser.DIGIT_PREFIX, i);
		}
		public List<TerminalNode> DIGIT_PREFIX() { return getTokens(PaperParser.DIGIT_PREFIX); }
		public List<TerminalNode> CHOICE_PREFIX() { return getTokens(PaperParser.CHOICE_PREFIX); }
		public TerminalNode CHOICE_PREFIX(int i) {
			return getToken(PaperParser.CHOICE_PREFIX, i);
		}
		public WordContext word(int i) {
			return getRuleContext(WordContext.class,i);
		}
		public WordsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_words; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterWords(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitWords(this);
		}
	}

	public final WordsContext words() throws RecognitionException {
		WordsContext _localctx = new WordsContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_words);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(204); 
			_errHandler.sync(this);
			_alt = 1+1;
			do {
				switch (_alt) {
				case 1+1:
					{
					setState(204);
					switch (_input.LA(1)) {
					case UNIT_HOUR:
					case UNIT_MINUTE:
					case UNIT_SECOND:
					case UNIT_POINT:
					case PROMPT_YES:
					case PROMPT_NO:
					case HINT_SCQ:
					case HINT_MCQS:
					case HINT_VERY_EASY:
					case HINT_EASY:
					case HINT_MODERATE:
					case HINT_DIFFICULT:
					case HINT_VERY_DIFFICULT:
					case HINT_SAMPLE:
					case COMMA:
					case COLON:
					case LBRACE:
					case RBRACE:
					case HINT_LANGUAGE:
					case INT:
					case WORD:
						{
						setState(201); word();
						}
						break;
					case DIGIT_PREFIX:
						{
						setState(202); match(DIGIT_PREFIX);
						}
						break;
					case CHOICE_PREFIX:
						{
						setState(203); match(CHOICE_PREFIX);
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
				setState(206); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			} while ( _alt!=1 && _alt!=ATN.INVALID_ALT_NUMBER );
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

	public static class WordContext extends ParserRuleContext {
		public PromptContext prompt() {
			return getRuleContext(PromptContext.class,0);
		}
		public TerminalNode WORD() { return getToken(PaperParser.WORD, 0); }
		public HintContext hint() {
			return getRuleContext(HintContext.class,0);
		}
		public TerminalNode INT() { return getToken(PaperParser.INT, 0); }
		public UnitContext unit() {
			return getRuleContext(UnitContext.class,0);
		}
		public PunctuationContext punctuation() {
			return getRuleContext(PunctuationContext.class,0);
		}
		public WordContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_word; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterWord(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitWord(this);
		}
	}

	public final WordContext word() throws RecognitionException {
		WordContext _localctx = new WordContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_word);
		try {
			setState(214);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(208); match(INT);
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(209); match(WORD);
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(210); punctuation();
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(211); unit();
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(212); hint();
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(213); prompt();
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

	public static class PunctuationContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(PaperParser.LBRACE, 0); }
		public TerminalNode COMMA() { return getToken(PaperParser.COMMA, 0); }
		public TerminalNode RBRACE() { return getToken(PaperParser.RBRACE, 0); }
		public TerminalNode COLON() { return getToken(PaperParser.COLON, 0); }
		public PunctuationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_punctuation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterPunctuation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitPunctuation(this);
		}
	}

	public final PunctuationContext punctuation() throws RecognitionException {
		PunctuationContext _localctx = new PunctuationContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_punctuation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COMMA) | (1L << COLON) | (1L << LBRACE) | (1L << RBRACE))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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

	public static class UnitContext extends ParserRuleContext {
		public TerminalNode UNIT_HOUR() { return getToken(PaperParser.UNIT_HOUR, 0); }
		public TerminalNode UNIT_POINT() { return getToken(PaperParser.UNIT_POINT, 0); }
		public TerminalNode UNIT_MINUTE() { return getToken(PaperParser.UNIT_MINUTE, 0); }
		public TerminalNode UNIT_SECOND() { return getToken(PaperParser.UNIT_SECOND, 0); }
		public UnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitUnit(this);
		}
	}

	public final UnitContext unit() throws RecognitionException {
		UnitContext _localctx = new UnitContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_unit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << UNIT_HOUR) | (1L << UNIT_MINUTE) | (1L << UNIT_SECOND) | (1L << UNIT_POINT))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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

	public static class HintContext extends ParserRuleContext {
		public TerminalNode HINT_EASY() { return getToken(PaperParser.HINT_EASY, 0); }
		public TerminalNode INT() { return getToken(PaperParser.INT, 0); }
		public TerminalNode HINT_MCQS() { return getToken(PaperParser.HINT_MCQS, 0); }
		public TerminalNode UNIT_POINT() { return getToken(PaperParser.UNIT_POINT, 0); }
		public TerminalNode HINT_VERY_EASY() { return getToken(PaperParser.HINT_VERY_EASY, 0); }
		public UnitTimeContext unitTime() {
			return getRuleContext(UnitTimeContext.class,0);
		}
		public TerminalNode HINT_VERY_DIFFICULT() { return getToken(PaperParser.HINT_VERY_DIFFICULT, 0); }
		public TerminalNode HINT_SAMPLE() { return getToken(PaperParser.HINT_SAMPLE, 0); }
		public TerminalNode HINT_SCQ() { return getToken(PaperParser.HINT_SCQ, 0); }
		public TerminalNode HINT_DIFFICULT() { return getToken(PaperParser.HINT_DIFFICULT, 0); }
		public TerminalNode HINT_MODERATE() { return getToken(PaperParser.HINT_MODERATE, 0); }
		public TerminalNode HINT_LANGUAGE() { return getToken(PaperParser.HINT_LANGUAGE, 0); }
		public HintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterHint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitHint(this);
		}
	}

	public final HintContext hint() throws RecognitionException {
		HintContext _localctx = new HintContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_hint);
		try {
			setState(233);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(220); match(HINT_SCQ);
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(221); match(HINT_MCQS);
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(222); match(HINT_LANGUAGE);
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(223); match(HINT_VERY_EASY);
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(224); match(HINT_EASY);
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(225); match(HINT_MODERATE);
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(226); match(HINT_DIFFICULT);
				}
				break;

			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(227); match(HINT_VERY_DIFFICULT);
				}
				break;

			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(228); match(HINT_SAMPLE);
				}
				break;

			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(229); match(INT);
				setState(230); match(UNIT_POINT);
				}
				break;

			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(231); match(INT);
				setState(232); unitTime();
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

	public static class PromptContext extends ParserRuleContext {
		public TerminalNode PROMPT_YES() { return getToken(PaperParser.PROMPT_YES, 0); }
		public TerminalNode PROMPT_NO() { return getToken(PaperParser.PROMPT_NO, 0); }
		public PromptContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prompt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).enterPrompt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PaperListener ) ((PaperListener)listener).exitPrompt(this);
		}
	}

	public final PromptContext prompt() throws RecognitionException {
		PromptContext _localctx = new PromptContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_prompt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(235);
			_la = _input.LA(1);
			if ( !(_la==PROMPT_YES || _la==PROMPT_NO) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3%\u00f0\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\3\2\5\28\n\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\5\2C\n\2\3\2\5\2F\n\2\5\2H\n\2\3\2\6\2K\n\2\r\2\16\2L\3\3\3\3\3\3\3\4"+
		"\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3"+
		"\t\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\r\5\rn\n\r\3\r\3\r\3\r\3\r\5\r"+
		"t\n\r\7\rv\n\r\f\r\16\ry\13\r\3\16\5\16|\n\16\3\16\3\16\3\16\5\16\u0081"+
		"\n\16\3\16\3\16\3\16\5\16\u0086\n\16\7\16\u0088\n\16\f\16\16\16\u008b"+
		"\13\16\3\17\3\17\3\17\3\17\7\17\u0091\n\17\f\17\16\17\u0094\13\17\3\17"+
		"\3\17\3\17\3\17\3\17\5\17\u009b\n\17\5\17\u009d\n\17\3\17\7\17\u00a0\n"+
		"\17\f\17\16\17\u00a3\13\17\3\17\3\17\3\20\3\20\5\20\u00a9\n\20\3\20\6"+
		"\20\u00ac\n\20\r\20\16\20\u00ad\3\21\3\21\3\21\3\22\3\22\3\22\3\23\3\23"+
		"\3\23\3\24\3\24\3\24\5\24\u00bc\n\24\7\24\u00be\n\24\f\24\16\24\u00c1"+
		"\13\24\3\25\3\25\3\25\3\25\3\25\6\25\u00c8\n\25\r\25\16\25\u00c9\3\26"+
		"\3\26\3\26\6\26\u00cf\n\26\r\26\16\26\u00d0\3\27\3\27\3\27\3\27\3\27\3"+
		"\27\5\27\u00d9\n\27\3\30\3\30\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\3\32\3\32\3\32\3\32\3\32\3\32\5\32\u00ec\n\32\3\33\3\33\3\33\4\u00bf"+
		"\u00d0\2\34\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\2"+
		"\6\3\2\3\5\3\2\17\20\3\2\35 \4\2\3\5\t\t\u0106\2J\3\2\2\2\4N\3\2\2\2\6"+
		"Q\3\2\2\2\bT\3\2\2\2\nX\3\2\2\2\fZ\3\2\2\2\16^\3\2\2\2\20a\3\2\2\2\22"+
		"d\3\2\2\2\24g\3\2\2\2\26j\3\2\2\2\30m\3\2\2\2\32{\3\2\2\2\34\u009c\3\2"+
		"\2\2\36\u00a6\3\2\2\2 \u00af\3\2\2\2\"\u00b2\3\2\2\2$\u00b5\3\2\2\2&\u00b8"+
		"\3\2\2\2(\u00c7\3\2\2\2*\u00ce\3\2\2\2,\u00d8\3\2\2\2.\u00da\3\2\2\2\60"+
		"\u00dc\3\2\2\2\62\u00eb\3\2\2\2\64\u00ed\3\2\2\2\668\5*\26\2\67\66\3\2"+
		"\2\2\678\3\2\2\28G\3\2\2\29H\5\4\3\2:H\5\6\4\2;C\5\b\5\2<C\5\f\7\2=C\5"+
		"\16\b\2>C\5\20\t\2?C\5\22\n\2@C\5\24\13\2AC\3\2\2\2B;\3\2\2\2B<\3\2\2"+
		"\2B=\3\2\2\2B>\3\2\2\2B?\3\2\2\2B@\3\2\2\2BA\3\2\2\2CE\3\2\2\2DF\5*\26"+
		"\2ED\3\2\2\2EF\3\2\2\2FH\3\2\2\2G9\3\2\2\2G:\3\2\2\2GB\3\2\2\2HI\3\2\2"+
		"\2IK\7#\2\2J\67\3\2\2\2KL\3\2\2\2LJ\3\2\2\2LM\3\2\2\2M\3\3\2\2\2NO\7\6"+
		"\2\2OP\5*\26\2P\5\3\2\2\2QR\7\7\2\2RS\5*\26\2S\7\3\2\2\2TU\7\b\2\2UV\7"+
		"\"\2\2VW\5\n\6\2W\t\3\2\2\2XY\t\2\2\2Y\13\3\2\2\2Z[\7\n\2\2[\\\7\"\2\2"+
		"\\]\7\t\2\2]\r\3\2\2\2^_\7\13\2\2_`\5\26\f\2`\17\3\2\2\2ab\7\f\2\2bc\5"+
		"\26\f\2c\21\3\2\2\2de\7\r\2\2ef\5\26\f\2f\23\3\2\2\2gh\7\16\2\2hi\5\26"+
		"\f\2i\25\3\2\2\2jk\t\3\2\2k\27\3\2\2\2ln\7\34\2\2ml\3\2\2\2mn\3\2\2\2"+
		"no\3\2\2\2ow\5\34\17\2ps\7#\2\2qt\5\"\22\2rt\5$\23\2sq\3\2\2\2sr\3\2\2"+
		"\2tv\3\2\2\2up\3\2\2\2vy\3\2\2\2wu\3\2\2\2wx\3\2\2\2x\31\3\2\2\2yw\3\2"+
		"\2\2z|\7\34\2\2{z\3\2\2\2{|\3\2\2\2|}\3\2\2\2}\u0080\5\34\17\2~\177\7"+
		"#\2\2\177\u0081\5\36\20\2\u0080~\3\2\2\2\u0080\u0081\3\2\2\2\u0081\u0089"+
		"\3\2\2\2\u0082\u0085\7#\2\2\u0083\u0086\5\"\22\2\u0084\u0086\5$\23\2\u0085"+
		"\u0083\3\2\2\2\u0085\u0084\3\2\2\2\u0086\u0088\3\2\2\2\u0087\u0082\3\2"+
		"\2\2\u0088\u008b\3\2\2\2\u0089\u0087\3\2\2\2\u0089\u008a\3\2\2\2\u008a"+
		"\33\3\2\2\2\u008b\u0089\3\2\2\2\u008c\u008d\7\37\2\2\u008d\u0092\5\62"+
		"\32\2\u008e\u008f\7\35\2\2\u008f\u0091\5\62\32\2\u0090\u008e\3\2\2\2\u0091"+
		"\u0094\3\2\2\2\u0092\u0090\3\2\2\2\u0092\u0093\3\2\2\2\u0093\u0095\3\2"+
		"\2\2\u0094\u0092\3\2\2\2\u0095\u009a\7 \2\2\u0096\u0097\7\37\2\2\u0097"+
		"\u0098\5(\25\2\u0098\u0099\7 \2\2\u0099\u009b\3\2\2\2\u009a\u0096\3\2"+
		"\2\2\u009a\u009b\3\2\2\2\u009b\u009d\3\2\2\2\u009c\u008c\3\2\2\2\u009c"+
		"\u009d\3\2\2\2\u009d\u00a1\3\2\2\2\u009e\u00a0\7#\2\2\u009f\u009e\3\2"+
		"\2\2\u00a0\u00a3\3\2\2\2\u00a1\u009f\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2"+
		"\u00a4\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a4\u00a5\5&\24\2\u00a5\35\3\2\2"+
		"\2\u00a6\u00ab\5 \21\2\u00a7\u00a9\7#\2\2\u00a8\u00a7\3\2\2\2\u00a8\u00a9"+
		"\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\u00ac\5 \21\2\u00ab\u00a8\3\2\2\2\u00ac"+
		"\u00ad\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\37\3\2\2"+
		"\2\u00af\u00b0\7\33\2\2\u00b0\u00b1\5&\24\2\u00b1!\3\2\2\2\u00b2\u00b3"+
		"\7\31\2\2\u00b3\u00b4\5&\24\2\u00b4#\3\2\2\2\u00b5\u00b6\7\32\2\2\u00b6"+
		"\u00b7\5&\24\2\u00b7%\3\2\2\2\u00b8\u00bf\5*\26\2\u00b9\u00bb\7#\2\2\u00ba"+
		"\u00bc\5*\26\2\u00bb\u00ba\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00be\3\2"+
		"\2\2\u00bd\u00b9\3\2\2\2\u00be\u00c1\3\2\2\2\u00bf\u00c0\3\2\2\2\u00bf"+
		"\u00bd\3\2\2\2\u00c0\'\3\2\2\2\u00c1\u00bf\3\2\2\2\u00c2\u00c8\7\"\2\2"+
		"\u00c3\u00c8\7%\2\2\u00c4\u00c8\5\60\31\2\u00c5\u00c8\5\62\32\2\u00c6"+
		"\u00c8\5\64\33\2\u00c7\u00c2\3\2\2\2\u00c7\u00c3\3\2\2\2\u00c7\u00c4\3"+
		"\2\2\2\u00c7\u00c5\3\2\2\2\u00c7\u00c6\3\2\2\2\u00c8\u00c9\3\2\2\2\u00c9"+
		"\u00c7\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca)\3\2\2\2\u00cb\u00cf\5,\27\2"+
		"\u00cc\u00cf\7\34\2\2\u00cd\u00cf\7\33\2\2\u00ce\u00cb\3\2\2\2\u00ce\u00cc"+
		"\3\2\2\2\u00ce\u00cd\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d0"+
		"\u00ce\3\2\2\2\u00d1+\3\2\2\2\u00d2\u00d9\7\"\2\2\u00d3\u00d9\7%\2\2\u00d4"+
		"\u00d9\5.\30\2\u00d5\u00d9\5\60\31\2\u00d6\u00d9\5\62\32\2\u00d7\u00d9"+
		"\5\64\33\2\u00d8\u00d2\3\2\2\2\u00d8\u00d3\3\2\2\2\u00d8\u00d4\3\2\2\2"+
		"\u00d8\u00d5\3\2\2\2\u00d8\u00d6\3\2\2\2\u00d8\u00d7\3\2\2\2\u00d9-\3"+
		"\2\2\2\u00da\u00db\t\4\2\2\u00db/\3\2\2\2\u00dc\u00dd\t\5\2\2\u00dd\61"+
		"\3\2\2\2\u00de\u00ec\7\21\2\2\u00df\u00ec\7\22\2\2\u00e0\u00ec\7!\2\2"+
		"\u00e1\u00ec\7\23\2\2\u00e2\u00ec\7\24\2\2\u00e3\u00ec\7\25\2\2\u00e4"+
		"\u00ec\7\26\2\2\u00e5\u00ec\7\27\2\2\u00e6\u00ec\7\30\2\2\u00e7\u00e8"+
		"\7\"\2\2\u00e8\u00ec\7\t\2\2\u00e9\u00ea\7\"\2\2\u00ea\u00ec\5\n\6\2\u00eb"+
		"\u00de\3\2\2\2\u00eb\u00df\3\2\2\2\u00eb\u00e0\3\2\2\2\u00eb\u00e1\3\2"+
		"\2\2\u00eb\u00e2\3\2\2\2\u00eb\u00e3\3\2\2\2\u00eb\u00e4\3\2\2\2\u00eb"+
		"\u00e5\3\2\2\2\u00eb\u00e6\3\2\2\2\u00eb\u00e7\3\2\2\2\u00eb\u00e9\3\2"+
		"\2\2\u00ec\63\3\2\2\2\u00ed\u00ee\t\3\2\2\u00ee\65\3\2\2\2\34\67BEGLm"+
		"sw{\u0080\u0085\u0089\u0092\u009a\u009c\u00a1\u00a8\u00ad\u00bb\u00bf"+
		"\u00c7\u00c9\u00ce\u00d0\u00d8\u00eb";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
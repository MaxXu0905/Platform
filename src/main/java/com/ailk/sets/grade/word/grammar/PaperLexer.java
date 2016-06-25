// Generated from Paper.g4 by ANTLR 4.2.2
package com.ailk.sets.grade.word.grammar;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PaperLexer extends Lexer {
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
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'小时'", "'分钟'", "'秒'", "PROMPT_PAPER_NAME", "PROMPT_PAPER_DESC", "PROMPT_TIME_LIMIT", 
		"'分'", "PROMPT_POINT", "PROMPT_SKILL_REORDER", "PROMPT_QUESTION_REORDER", 
		"PROMPT_CHOICE_REORDER", "PROMPT_BACKWARD", "PROMPT_YES", "PROMPT_NO", 
		"HINT_SCQ", "HINT_MCQS", "'极易'", "'易'", "'中'", "'难'", "'极难'", "'样例'", 
		"PROMPT_ANSWER", "PROMPT_SUPPLEMENT", "CHOICE_PREFIX", "DIGIT_PREFIX", 
		"COMMA", "COLON", "LBRACE", "RBRACE", "HINT_LANGUAGE", "INT", "'\n'", 
		"WS", "WORD"
	};
	public static final String[] ruleNames = {
		"UNIT_HOUR", "UNIT_MINUTE", "UNIT_SECOND", "PROMPT_PAPER_NAME", "PROMPT_PAPER_DESC", 
		"PROMPT_TIME_LIMIT", "UNIT_POINT", "PROMPT_POINT", "PROMPT_SKILL_REORDER", 
		"PROMPT_QUESTION_REORDER", "PROMPT_CHOICE_REORDER", "PROMPT_BACKWARD", 
		"PROMPT_YES", "PROMPT_NO", "HINT_SCQ", "HINT_MCQS", "HINT_VERY_EASY", 
		"HINT_EASY", "HINT_MODERATE", "HINT_DIFFICULT", "HINT_VERY_DIFFICULT", 
		"HINT_SAMPLE", "PROMPT_ANSWER", "PROMPT_SUPPLEMENT", "PROMPT_SEPARATOR", 
		"CHOICE_SEPARATOR", "CHOICE_PREFIX", "DIGIT_PREFIX", "COMMA", "COLON", 
		"LBRACE", "RBRACE", "HINT_LANGUAGE", "INT", "NL", "WS", "WORD"
	};


		public static final int WHITESPACE = 1;


	public PaperLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Paper.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 35: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: _channel = WHITESPACE; break;
		}
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2%\u0148\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\3\2\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\5\7j\n\7\3\7\3\7\3\b\3\b\3\t\3\t\5\tr\n\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\5\16\u0097"+
		"\n\16\3\17\3\17\3\17\5\17\u009c\n\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\5\20\u00a6\n\20\3\20\5\20\u00a9\n\20\3\21\3\21\3\21\3\21\3\21\3"+
		"\21\5\21\u00b1\n\21\3\21\5\21\u00b4\n\21\3\22\3\22\3\22\3\23\3\23\3\24"+
		"\3\24\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30"+
		"\3\31\3\31\3\31\3\31\5\31\u00ce\n\31\3\31\3\31\3\31\3\31\5\31\u00d4\n"+
		"\31\3\31\3\31\3\32\3\32\5\32\u00da\n\32\3\33\3\33\3\34\3\34\3\34\3\35"+
		"\6\35\u00e2\n\35\r\35\16\35\u00e3\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 "+
		"\3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\""+
		"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3"+
		"\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\""+
		"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\5\"\u012f\n\"\3#\6#\u0132"+
		"\n#\r#\16#\u0133\3$\3$\3%\6%\u0139\n%\r%\16%\u013a\3%\3%\3&\3&\7&\u0141"+
		"\n&\f&\16&\u0144\13&\3&\5&\u0147\n&\2\2\'\3\3\5\4\7\5\t\6\13\7\r\b\17"+
		"\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+"+
		"\27-\30/\31\61\32\63\2\65\2\67\339\34;\35=\36?\37A C!E\"G#I$K%\3\2\"\4"+
		"\2\u5bfb\u5bfb\u6631\u6631\4\2\u5428\u5428\u951b\u951b\5\2\60\60\u3003"+
		"\u3003\uff10\uff10\4\2C\\c|\3\2\62;\7\2..==\u3003\u3003\uff0e\uff0e\uff1d"+
		"\uff1d\4\2<<\uff1c\uff1c\7\2**]^}}\u3012\u3012\uff0a\uff0a\7\2++^_\177"+
		"\177\u3013\u3013\uff0b\uff0b\4\2LLll\4\2CCcc\4\2XXxx\4\2EEee\4\2QQqq\4"+
		"\2DDdd\4\2GGgg\4\2VVvv\4\2KKkk\4\2RRrr\4\2[[{{\4\2JJjj\4\2PPpp\4\2UUu"+
		"u\4\2NNnn\4\2TTtt\4\2WWww\4\2SSuu\4\2SSss\4\2YYyy\5\2\13\13\16\17\"\""+
		"\5\2C\\aac|\6\2\62;C\\aac|\u0165\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2"+
		"\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2"+
		"\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2"+
		"\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2"+
		"\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2"+
		"\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G"+
		"\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\3M\3\2\2\2\5P\3\2\2\2\7S\3\2\2\2\tU\3\2"+
		"\2\2\13\\\3\2\2\2\ri\3\2\2\2\17m\3\2\2\2\21q\3\2\2\2\23x\3\2\2\2\25\177"+
		"\3\2\2\2\27\u0086\3\2\2\2\31\u008d\3\2\2\2\33\u0096\3\2\2\2\35\u009b\3"+
		"\2\2\2\37\u00a5\3\2\2\2!\u00b0\3\2\2\2#\u00b5\3\2\2\2%\u00b8\3\2\2\2\'"+
		"\u00ba\3\2\2\2)\u00bc\3\2\2\2+\u00be\3\2\2\2-\u00c1\3\2\2\2/\u00c4\3\2"+
		"\2\2\61\u00cd\3\2\2\2\63\u00d9\3\2\2\2\65\u00db\3\2\2\2\67\u00dd\3\2\2"+
		"\29\u00e1\3\2\2\2;\u00e7\3\2\2\2=\u00e9\3\2\2\2?\u00eb\3\2\2\2A\u00ed"+
		"\3\2\2\2C\u012e\3\2\2\2E\u0131\3\2\2\2G\u0135\3\2\2\2I\u0138\3\2\2\2K"+
		"\u0146\3\2\2\2MN\7\u5c11\2\2NO\7\u65f8\2\2O\4\3\2\2\2PQ\7\u5208\2\2QR"+
		"\7\u94a1\2\2R\6\3\2\2\2ST\7\u79d4\2\2T\b\3\2\2\2UV\7\u8bd7\2\2VW\7\u5379"+
		"\2\2WX\7\u540f\2\2XY\7\u79f2\2\2YZ\3\2\2\2Z[\5\63\32\2[\n\3\2\2\2\\]\7"+
		"\u8bd7\2\2]^\7\u5379\2\2^_\7\u7b82\2\2_`\7\u4ecd\2\2`a\3\2\2\2ab\5\63"+
		"\32\2b\f\3\2\2\2cd\7\u9652\2\2dj\7\u65f8\2\2ef\7\u65f8\2\2fj\7\u9652\2"+
		"\2gh\7\u65f8\2\2hj\7\u95f6\2\2ic\3\2\2\2ie\3\2\2\2ig\3\2\2\2jk\3\2\2\2"+
		"kl\5\63\32\2l\16\3\2\2\2mn\7\u5208\2\2n\20\3\2\2\2op\7\u6bd1\2\2pr\7\u989a"+
		"\2\2qo\3\2\2\2qr\3\2\2\2rs\3\2\2\2st\7\u5208\2\2tu\7\u503e\2\2uv\3\2\2"+
		"\2vw\5\63\32\2w\22\3\2\2\2xy\7\u8005\2\2yz\7\u70bb\2\2z{\7\u4e73\2\2{"+
		"|\7\u5e91\2\2|}\3\2\2\2}~\5\63\32\2~\24\3\2\2\2\177\u0080\7\u989a\2\2"+
		"\u0080\u0081\7\u76f0\2\2\u0081\u0082\7\u4e73\2\2\u0082\u0083\7\u5e91\2"+
		"\2\u0083\u0084\3\2\2\2\u0084\u0085\5\63\32\2\u0085\26\3\2\2\2\u0086\u0087"+
		"\7\u900b\2\2\u0087\u0088\7\u987b\2\2\u0088\u0089\7\u4e73\2\2\u0089\u008a"+
		"\7\u5e91\2\2\u008a\u008b\3\2\2\2\u008b\u008c\5\63\32\2\u008c\30\3\2\2"+
		"\2\u008d\u008e\7\u53f1\2\2\u008e\u008f\7\u56e0\2\2\u008f\u0090\7\u6eb1"+
		"\2\2\u0090\u0091\3\2\2\2\u0091\u0092\5\63\32\2\u0092\32\3\2\2\2\u0093"+
		"\u0097\t\2\2\2\u0094\u0095\7\u6b65\2\2\u0095\u0097\7\u7870\2\2\u0096\u0093"+
		"\3\2\2\2\u0096\u0094\3\2\2\2\u0097\34\3\2\2\2\u0098\u009c\t\3\2\2\u0099"+
		"\u009a\7\u951b\2\2\u009a\u009c\7\u8bf1\2\2\u009b\u0098\3\2\2\2\u009b\u0099"+
		"\3\2\2\2\u009c\36\3\2\2\2\u009d\u009e\7\u5357\2\2\u009e\u009f\7\u987b"+
		"\2\2\u009f\u00a0\7\u900b\2\2\u00a0\u00a6\7\u62eb\2\2\u00a1\u00a2\7\u5357"+
		"\2\2\u00a2\u00a6\7\u900b\2\2\u00a3\u00a4\7\u900b\2\2\u00a4\u00a6\7\u62eb"+
		"\2\2\u00a5\u009d\3\2\2\2\u00a5\u00a1\3\2\2\2\u00a5\u00a3\3\2\2\2\u00a6"+
		"\u00a8\3\2\2\2\u00a7\u00a9\7\u989a\2\2\u00a8\u00a7\3\2\2\2\u00a8\u00a9"+
		"\3\2\2\2\u00a9 \3\2\2\2\u00aa\u00ab\7\u591c\2\2\u00ab\u00ac\7\u987b\2"+
		"\2\u00ac\u00ad\7\u900b\2\2\u00ad\u00b1\7\u62eb\2\2\u00ae\u00af\7\u591c"+
		"\2\2\u00af\u00b1\7\u900b\2\2\u00b0\u00aa\3\2\2\2\u00b0\u00ae\3\2\2\2\u00b1"+
		"\u00b3\3\2\2\2\u00b2\u00b4\7\u989a\2\2\u00b3\u00b2\3\2\2\2\u00b3\u00b4"+
		"\3\2\2\2\u00b4\"\3\2\2\2\u00b5\u00b6\7\u6783\2\2\u00b6\u00b7\7\u6615\2"+
		"\2\u00b7$\3\2\2\2\u00b8\u00b9\7\u6615\2\2\u00b9&\3\2\2\2\u00ba\u00bb\7"+
		"\u4e2f\2\2\u00bb(\3\2\2\2\u00bc\u00bd\7\u96c0\2\2\u00bd*\3\2\2\2\u00be"+
		"\u00bf\7\u6783\2\2\u00bf\u00c0\7\u96c0\2\2\u00c0,\3\2\2\2\u00c1\u00c2"+
		"\7\u6839\2\2\u00c2\u00c3\7\u4f8d\2\2\u00c3.\3\2\2\2\u00c4\u00c5\7\u7b56"+
		"\2\2\u00c5\u00c6\7\u684a\2\2\u00c6\u00c7\3\2\2\2\u00c7\u00c8\5\63\32\2"+
		"\u00c8\60\3\2\2\2\u00c9\u00ca\7\u7b56\2\2\u00ca\u00ce\7\u684a\2\2\u00cb"+
		"\u00cc\7\u8867\2\2\u00cc\u00ce\7\u5147\2\2\u00cd\u00c9\3\2\2\2\u00cd\u00cb"+
		"\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce\u00d3\3\2\2\2\u00cf\u00d0\7\u89e5\2"+
		"\2\u00d0\u00d4\7\u91cc\2\2\u00d1\u00d2\7\u8bf6\2\2\u00d2\u00d4\7\u6610"+
		"\2\2\u00d3\u00cf\3\2\2\2\u00d3\u00d1\3\2\2\2\u00d4\u00d5\3\2\2\2\u00d5"+
		"\u00d6\5\63\32\2\u00d6\62\3\2\2\2\u00d7\u00da\5=\37\2\u00d8\u00da\5A!"+
		"\2\u00d9\u00d7\3\2\2\2\u00d9\u00d8\3\2\2\2\u00da\64\3\2\2\2\u00db\u00dc"+
		"\t\4\2\2\u00dc\66\3\2\2\2\u00dd\u00de\t\5\2\2\u00de\u00df\5\65\33\2\u00df"+
		"8\3\2\2\2\u00e0\u00e2\t\6\2\2\u00e1\u00e0\3\2\2\2\u00e2\u00e3\3\2\2\2"+
		"\u00e3\u00e1\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e6"+
		"\5\65\33\2\u00e6:\3\2\2\2\u00e7\u00e8\t\7\2\2\u00e8<\3\2\2\2\u00e9\u00ea"+
		"\t\b\2\2\u00ea>\3\2\2\2\u00eb\u00ec\t\t\2\2\u00ec@\3\2\2\2\u00ed\u00ee"+
		"\t\n\2\2\u00eeB\3\2\2\2\u00ef\u00f0\t\13\2\2\u00f0\u00f1\t\f\2\2\u00f1"+
		"\u00f2\t\r\2\2\u00f2\u012f\t\f\2\2\u00f3\u012f\t\16\2\2\u00f4\u00f5\t"+
		"\16\2\2\u00f5\u00f6\7-\2\2\u00f6\u012f\7-\2\2\u00f7\u00f8\t\17\2\2\u00f8"+
		"\u00f9\t\20\2\2\u00f9\u00fa\t\13\2\2\u00fa\u00fb\t\21\2\2\u00fb\u00fc"+
		"\t\16\2\2\u00fc\u00fd\t\22\2\2\u00fd\u00fe\t\23\2\2\u00fe\u00ff\t\r\2"+
		"\2\u00ff\u0100\t\21\2\2\u0100\u012f\t\16\2\2\u0101\u0102\t\24\2\2\u0102"+
		"\u0103\t\25\2\2\u0103\u0104\t\22\2\2\u0104\u0105\t\26\2\2\u0105\u0106"+
		"\t\17\2\2\u0106\u012f\t\27\2\2\u0107\u0108\t\30\2\2\u0108\u0109\t\26\2"+
		"\2\u0109\u010a\t\21\2\2\u010a\u010b\t\31\2\2\u010b\u012f\t\31\2\2\u010c"+
		"\u010d\t\13\2\2\u010d\u010e\t\f\2\2\u010e\u010f\t\r\2\2\u010f\u0110\t"+
		"\f\2\2\u0110\u0111\t\30\2\2\u0111\u0112\t\16\2\2\u0112\u0113\t\32\2\2"+
		"\u0113\u0114\t\23\2\2\u0114\u0115\t\24\2\2\u0115\u012f\t\22\2\2\u0116"+
		"\u0117\t\24\2\2\u0117\u0118\t\26\2\2\u0118\u012f\t\24\2\2\u0119\u011a"+
		"\t\24\2\2\u011a\u011b\t\21\2\2\u011b\u011c\t\32\2\2\u011c\u012f\t\31\2"+
		"\2\u011d\u011e\t\32\2\2\u011e\u011f\t\33\2\2\u011f\u0120\t\20\2\2\u0120"+
		"\u012f\t\25\2\2\u0121\u0122\t\16\2\2\u0122\u012f\7%\2\2\u0123\u0124\t"+
		"\34\2\2\u0124\u0125\t\35\2\2\u0125\u012f\t\31\2\2\u0126\u0127\t\36\2\2"+
		"\u0127\u0128\t\21\2\2\u0128\u012f\t\20\2\2\u0129\u012a\t\17\2\2\u012a"+
		"\u012b\t\22\2\2\u012b\u012c\t\26\2\2\u012c\u012d\t\21\2\2\u012d\u012f"+
		"\t\32\2\2\u012e\u00ef\3\2\2\2\u012e\u00f3\3\2\2\2\u012e\u00f4\3\2\2\2"+
		"\u012e\u00f7\3\2\2\2\u012e\u0101\3\2\2\2\u012e\u0107\3\2\2\2\u012e\u010c"+
		"\3\2\2\2\u012e\u0116\3\2\2\2\u012e\u0119\3\2\2\2\u012e\u011d\3\2\2\2\u012e"+
		"\u0121\3\2\2\2\u012e\u0123\3\2\2\2\u012e\u0126\3\2\2\2\u012e\u0129\3\2"+
		"\2\2\u012fD\3\2\2\2\u0130\u0132\t\6\2\2\u0131\u0130\3\2\2\2\u0132\u0133"+
		"\3\2\2\2\u0133\u0131\3\2\2\2\u0133\u0134\3\2\2\2\u0134F\3\2\2\2\u0135"+
		"\u0136\7\f\2\2\u0136H\3\2\2\2\u0137\u0139\t\37\2\2\u0138\u0137\3\2\2\2"+
		"\u0139\u013a\3\2\2\2\u013a\u0138\3\2\2\2\u013a\u013b\3\2\2\2\u013b\u013c"+
		"\3\2\2\2\u013c\u013d\b%\2\2\u013dJ\3\2\2\2\u013e\u0142\t \2\2\u013f\u0141"+
		"\t!\2\2\u0140\u013f\3\2\2\2\u0141\u0144\3\2\2\2\u0142\u0140\3\2\2\2\u0142"+
		"\u0143\3\2\2\2\u0143\u0147\3\2\2\2\u0144\u0142\3\2\2\2\u0145\u0147\13"+
		"\2\2\2\u0146\u013e\3\2\2\2\u0146\u0145\3\2\2\2\u0147L\3\2\2\2\24\2iq\u0096"+
		"\u009b\u00a5\u00a8\u00b0\u00b3\u00cd\u00d3\u00d9\u00e3\u012e\u0133\u013a"+
		"\u0142\u0146\3\3%\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
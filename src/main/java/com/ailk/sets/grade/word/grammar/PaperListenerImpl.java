package com.ailk.sets.grade.word.grammar;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.log4j.Logger;

import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.word.grammar.PaperParser.AnswerContext;
import com.ailk.sets.grade.word.grammar.PaperParser.BackwardContext;
import com.ailk.sets.grade.word.grammar.PaperParser.ChoiceContext;
import com.ailk.sets.grade.word.grammar.PaperParser.ChoiceReorderContext;
import com.ailk.sets.grade.word.grammar.PaperParser.DescPointContext;
import com.ailk.sets.grade.word.grammar.PaperParser.DescTimeContext;
import com.ailk.sets.grade.word.grammar.PaperParser.HintContext;
import com.ailk.sets.grade.word.grammar.PaperParser.PaperDescContext;
import com.ailk.sets.grade.word.grammar.PaperParser.PaperNameContext;
import com.ailk.sets.grade.word.grammar.PaperParser.QuestionReorderContext;
import com.ailk.sets.grade.word.grammar.PaperParser.SentenceContext;
import com.ailk.sets.grade.word.grammar.PaperParser.SkillContext;
import com.ailk.sets.grade.word.grammar.PaperParser.SkillReorderContext;
import com.ailk.sets.grade.word.grammar.PaperParser.SupplementContext;
import com.ailk.sets.grade.word.grammar.PaperParser.TitleContext;
import com.ailk.sets.grade.word.grammar.PaperParser.UnitTimeContext;
import com.ailk.sets.grade.word.grammar.PaperParser.WordsContext;
import com.ailk.sets.grade.word.grammar.PaperParser.YesOrNoContext;

public class PaperListenerImpl extends PaperBaseListener {

	private static final Logger logger = Logger
			.getLogger(PaperListenerImpl.class);

	private BufferedTokenStream tokens;

	private String paperName;
	private String paperDesc;
	private int timeVolume;
	private int timeUnit;
	private int point;
	private boolean skillReorder;
	private boolean questionReorder;
	private boolean choiceReorder;
	private boolean backward;
	private int questionType;
	private int programLanguage;
	private String skillName;
	private String title;
	private List<String> options;
	private String answer;
	private String supplement;
	private int difficulty;
	private boolean sample;

	public PaperListenerImpl(BufferedTokenStream tokens) {
		this.tokens = tokens;

		questionType = GradeConst.QUESTION_TYPE_UNKNOWN;
		difficulty = 2;
	}

	public String getPaperName() {
		return paperName;
	}

	public String getPaperDesc() {
		return paperDesc;
	}

	public int getTimeLimit() {
		return timeVolume * timeUnit;
	}

	public int getPoint() {
		return point;
	}

	public boolean isSkillReorder() {
		return skillReorder;
	}

	public boolean isQuestionReorder() {
		return questionReorder;
	}

	public boolean isChoiceReorder() {
		return choiceReorder;
	}

	public boolean isBackward() {
		return backward;
	}

	public int getQuestionType() {
		return questionType;
	}

	public int getProgramLanguage() {
		return programLanguage;
	}

	public String getSkillName() {
		return skillName;
	}

	public String getTitle() {
		return title;
	}

	public List<String> getOptions() {
		return options;
	}

	public String getAnswer() {
		return answer;
	}

	public String getSupplement() {
		return supplement;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public boolean isSample() {
		return sample;
	}

	@Override
	public String toString() {
		return "PaperListenerImpl [paperName=" + paperName + ", paperDesc="
				+ paperDesc + ", timeVolume=" + timeVolume + ", timeUnit="
				+ timeUnit + ", point=" + point + ", skillReorder="
				+ skillReorder + ", questionReorder=" + questionReorder
				+ ", choiceReorder=" + choiceReorder + ", backward=" + backward
				+ ", questionType=" + questionType + ", programLanguage="
				+ programLanguage + ", skillName=" + skillName + ", title="
				+ title + ", options=" + options + ", answer=" + answer
				+ ", supplement=" + supplement + ", difficulty=" + difficulty
				+ ", sample=" + sample + "]";
	}

	@Override
	public void exitHint(HintContext ctx) {
		if (!(ctx.getParent() instanceof TitleContext))
			return;

		Token token = ctx.getStart();
		switch (token.getType()) {
		case PaperLexer.HINT_SCQ:
			questionType = GradeConst.QUESTION_TYPE_S_CHOICE;
			break;
		case PaperLexer.HINT_MCQS:
			questionType = GradeConst.QUESTION_TYPE_M_CHOICE;
			break;
		case PaperLexer.HINT_LANGUAGE:
			try {
				programLanguage = GradeConst.toModeInt(token.getText());
			} catch (Exception e) {
				logger.error("编程语言错误：" + token.getText(), e);
				programLanguage = GradeConst.MODE_UNKNOWN;
			}
			break;
		case PaperLexer.HINT_VERY_EASY:
			difficulty = 1;
			break;
		case PaperLexer.HINT_EASY:
			difficulty = 2;
			break;
		case PaperLexer.HINT_MODERATE:
			difficulty = 4;
			break;
		case PaperLexer.HINT_DIFFICULT:
			difficulty = 6;
			break;
		case PaperLexer.HINT_VERY_DIFFICULT:
			difficulty = 7;
			break;
		case PaperLexer.HINT_SAMPLE:
			sample = true;
			break;
		case PaperLexer.INT:
			if (ctx.getToken(PaperLexer.UNIT_POINT, 0) != null)
				point = Integer.parseInt(token.getText());
			else
				timeVolume = Integer.parseInt(token.getText());
			break;
		}
	}

	@Override
	public void exitSkill(SkillContext ctx) {
		skillName = ctx.getText();
	}

	@Override
	public void exitTitle(TitleContext ctx) {
		title = standardPre(getRawText(ctx.getRuleContext(
				SentenceContext.class, 0)));
	}

	@Override
	public void exitChoice(ChoiceContext ctx) {
		if (options == null)
			options = new ArrayList<String>();

		options.add(standardPre(getRawText(ctx.getRuleContext(
				SentenceContext.class, 0))));
	}

	@Override
	public void exitAnswer(AnswerContext ctx) {
		answer = standardPre(getRawText(ctx.getRuleContext(
				SentenceContext.class, 0)));
	}

	@Override
	public void exitSupplement(SupplementContext ctx) {
		supplement = standardPre(getRawText(ctx.getRuleContext(
				SentenceContext.class, 0)));
	}

	@Override
	public void exitPaperName(PaperNameContext ctx) {
		paperName = getRawText(ctx.getRuleContext(WordsContext.class, 0));
	}

	@Override
	public void exitPaperDesc(PaperDescContext ctx) {
		paperDesc = getRawText(ctx.getRuleContext(WordsContext.class, 0));
	}

	@Override
	public void exitDescTime(DescTimeContext ctx) {
		Token token = ctx.getToken(PaperLexer.INT, 0).getSymbol();
		timeVolume = Integer.parseInt(token.getText());
	}

	@Override
	public void exitUnitTime(UnitTimeContext ctx) {
		TerminalNode node = ctx.getToken(PaperLexer.UNIT_HOUR, 0);
		if (node != null) {
			timeUnit = 3600;
			return;
		}

		node = ctx.getToken(PaperLexer.UNIT_MINUTE, 0);
		if (node != null) {
			timeUnit = 60;
			return;
		}

		timeUnit = 1;
	}

	@Override
	public void exitDescPoint(DescPointContext ctx) {
		Token token = ctx.getToken(PaperLexer.INT, 0).getSymbol();
		point = Integer.parseInt(token.getText());
	}

	@Override
	public void exitSkillReorder(SkillReorderContext ctx) {
		YesOrNoContext yesOrNoCtx = ctx.getRuleContext(YesOrNoContext.class, 0);
		if (yesOrNoCtx.getToken(PaperLexer.PROMPT_YES, 0) != null)
			skillReorder = true;
		else
			skillReorder = false;
	}

	@Override
	public void exitQuestionReorder(QuestionReorderContext ctx) {
		YesOrNoContext yesOrNoCtx = ctx.getRuleContext(YesOrNoContext.class, 0);
		if (yesOrNoCtx.getToken(PaperLexer.PROMPT_YES, 0) != null)
			questionReorder = true;
		else
			questionReorder = false;
	}

	@Override
	public void exitChoiceReorder(ChoiceReorderContext ctx) {
		YesOrNoContext yesOrNoCtx = ctx.getRuleContext(YesOrNoContext.class, 0);
		if (yesOrNoCtx.getToken(PaperLexer.PROMPT_YES, 0) != null)
			choiceReorder = true;
		else
			choiceReorder = false;
	}

	@Override
	public void exitBackward(BackwardContext ctx) {
		YesOrNoContext yesOrNoCtx = ctx.getRuleContext(YesOrNoContext.class, 0);
		if (yesOrNoCtx.getToken(PaperLexer.PROMPT_YES, 0) != null)
			backward = true;
		else
			backward = false;
	}

	private String getRawText(ParserRuleContext ctx) {
		try {
			Token start = ctx.getStart();
			Token stop = ctx.getStop();
			List<Token> region = tokens.get(start.getTokenIndex(),
					stop.getTokenIndex());

			StringBuilder builder = new StringBuilder();
			for (Token token : region) {
				builder.append(token.getText());
			}

			// 去掉末尾的换行符
			int i;
			for (i = builder.length() - 1; i >= 0; i--) {
				if (builder.charAt(i) != '\n')
					break;
			}
			builder.setLength(i + 1);

			return builder.toString();
		} catch (NullPointerException e) {
			return "";
		}

	}

	/**
	 * 规整成pre格式的字符串
	 * 
	 * @param data
	 *            转换前字符串
	 * @return 转换后字符串
	 */
	private static String standardPre(String data) {
		if (data == null)
			return null;

		return "<pre>" + data.replace("</pre><pre>", "") + "</pre>";
	}

}

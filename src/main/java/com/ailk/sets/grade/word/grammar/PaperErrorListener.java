package com.ailk.sets.grade.word.grammar;

import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import com.ailk.sets.grade.word.grammar.PaperParser.AnswerContext;
import com.ailk.sets.grade.word.grammar.PaperParser.BackwardContext;
import com.ailk.sets.grade.word.grammar.PaperParser.ChoiceContext;
import com.ailk.sets.grade.word.grammar.PaperParser.ChoiceReorderContext;
import com.ailk.sets.grade.word.grammar.PaperParser.DescPointContext;
import com.ailk.sets.grade.word.grammar.PaperParser.DescTimeContext;
import com.ailk.sets.grade.word.grammar.PaperParser.DescriptionContext;
import com.ailk.sets.grade.word.grammar.PaperParser.HintContext;
import com.ailk.sets.grade.word.grammar.PaperParser.PaperNameContext;
import com.ailk.sets.grade.word.grammar.PaperParser.PromptContext;
import com.ailk.sets.grade.word.grammar.PaperParser.PunctuationContext;
import com.ailk.sets.grade.word.grammar.PaperParser.QuestionContext;
import com.ailk.sets.grade.word.grammar.PaperParser.QuestionReorderContext;
import com.ailk.sets.grade.word.grammar.PaperParser.SentenceContext;
import com.ailk.sets.grade.word.grammar.PaperParser.SkillContext;
import com.ailk.sets.grade.word.grammar.PaperParser.SkillReorderContext;
import com.ailk.sets.grade.word.grammar.PaperParser.SupplementContext;
import com.ailk.sets.grade.word.grammar.PaperParser.TitleContext;
import com.ailk.sets.grade.word.grammar.PaperParser.UnitContext;
import com.ailk.sets.grade.word.grammar.PaperParser.UnitTimeContext;
import com.ailk.sets.grade.word.grammar.PaperParser.WordContext;
import com.ailk.sets.grade.word.grammar.PaperParser.WordsContext;
import com.ailk.sets.grade.word.grammar.PaperParser.YesOrNoContext;

public class PaperErrorListener implements ANTLRErrorListener {

	private Map<Integer, StringBuilder> errorMap;
	private Set<RequiredRuleEnum> requiredRuleSet;

	public PaperErrorListener() {
		errorMap = new HashMap<Integer, StringBuilder>();
		requiredRuleSet = new HashSet<RequiredRuleEnum>();
	}

	public Map<Integer, StringBuilder> getErrorMap() {
		return errorMap;
	}

	public boolean isReported(RequiredRuleEnum requiredRule) {
		return (requiredRuleSet.contains(requiredRule));
	}

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer,
			Object offendingSymbol, int line, int charPositionInLine,
			String msg, RecognitionException e) {
		PaperParser parser = (PaperParser) recognizer;
		ParserRuleContext ctx = parser.getContext();

		while (true) {
			if (ctx instanceof DescriptionContext) {
				addError(line, "【试卷、部分】的描述格式错误");
				requiredRuleSet.add(RequiredRuleEnum.DESCRIPTION);
			} else if (ctx instanceof PaperNameContext) {
				Token token = ctx.getToken(PaperLexer.PROMPT_PAPER_NAME, 0)
						.getSymbol();
				addError(line, token.getText() + "缺少值");
				requiredRuleSet.add(RequiredRuleEnum.PAPER_NAME);
			} else if (ctx instanceof DescTimeContext) {
				Token token = ctx.getToken(PaperLexer.PROMPT_TIME_LIMIT, 0)
						.getSymbol();
				addError(line, token.getText() + "缺少值");
				requiredRuleSet.add(RequiredRuleEnum.DESC_TIME);
			} else if (ctx instanceof UnitTimeContext) {
				ctx = ctx.getParent();
				continue;
			} else if (ctx instanceof DescPointContext) {
				Token token = ctx.getToken(PaperLexer.PROMPT_POINT, 0)
						.getSymbol();
				if (ctx.getToken(PaperLexer.INT, 0) == null)
					addError(line, token.getText() + "缺少数值");
				else if (ctx.getRuleContext(UnitTimeContext.class, 0) == null)
					addError(line, token.getText() + "缺少单位（小时、分钟、秒）");
				else
					addError(line, token.getText() + "格式错误");
				requiredRuleSet.add(RequiredRuleEnum.DESC_POINT);
			} else if (ctx instanceof SkillReorderContext) {
				Token token = ctx.getToken(PaperLexer.PROMPT_SKILL_REORDER, 0)
						.getSymbol();
				if (ctx.getRuleContext(YesOrNoContext.class, 0) == null)
					addError(line, token.getText() + "缺少值（是、否）");
				else
					addError(line, token.getText() + "格式错误");
				requiredRuleSet.add(RequiredRuleEnum.SKILL_REORDER);
			} else if (ctx instanceof QuestionReorderContext) {
				Token token = ctx.getToken(PaperLexer.PROMPT_QUESTION_REORDER,
						0).getSymbol();
				if (ctx.getRuleContext(YesOrNoContext.class, 0) == null)
					addError(line, token.getText() + "缺少值（是、否）");
				else
					addError(line, token.getText() + "格式错误");
				requiredRuleSet.add(RequiredRuleEnum.QUESTION_REORDER);
			} else if (ctx instanceof ChoiceReorderContext) {
				Token token = ctx.getToken(PaperLexer.PROMPT_CHOICE_REORDER, 0)
						.getSymbol();
				if (ctx.getRuleContext(YesOrNoContext.class, 0) == null)
					addError(line, token.getText() + "缺少值（是、否）");
				else
					addError(line, token.getText() + "格式错误");
				requiredRuleSet.add(RequiredRuleEnum.CHOICE_REORDER);
			} else if (ctx instanceof BackwardContext) {
				Token token = ctx.getToken(PaperLexer.PROMPT_BACKWARD, 0)
						.getSymbol();
				if (ctx.getRuleContext(YesOrNoContext.class, 0) == null)
					addError(line, token.getText() + "缺少值（是、否）");
				else
					addError(line, token.getText() + "格式错误");
				requiredRuleSet.add(RequiredRuleEnum.BACKWARD);
			} else if (ctx instanceof YesOrNoContext) {
				ctx = ctx.getParent();
				continue;
			} else if (ctx instanceof QuestionContext) {
				addError(line, "【题目】格式错误");
			} else if (ctx instanceof TitleContext) {
				addError(line, "【标题】格式错误");
			} else if (ctx instanceof ChoiceContext) {
				addError(line, "【选项】格式错误");
			} else if (ctx instanceof AnswerContext) {
				addError(line, "【答案】格式错误");
			} else if (ctx instanceof SupplementContext) {
				addError(line, "【补充解释】格式错误");
			} else if (ctx instanceof SentenceContext) {
				ctx = ctx.getParent();
				continue;
			} else if (ctx instanceof SkillContext) {
				addError(line, "【技能】格式错误");
			} else if (ctx instanceof WordsContext) {
				ctx = ctx.getParent();
				continue;
			} else if (ctx instanceof WordContext) {
				ctx = ctx.getParent();
				continue;
			} else if (ctx instanceof PunctuationContext) {
				ctx = ctx.getParent();
				continue;
			} else if (ctx instanceof UnitContext) {
				ctx = ctx.getParent();
				continue;
			} else if (ctx instanceof HintContext) {
				addError(line, "【题目设置项】格式错误");
			} else if (ctx instanceof PromptContext) {
				ctx = ctx.getParent();
				continue;
			} else {
				addError(line, "未知错误");
			}

			break;
		}
	}

	@Override
	public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex,
			int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {
	}

	@Override
	public void reportAttemptingFullContext(Parser recognizer, DFA dfa,
			int startIndex, int stopIndex, BitSet conflictingAlts,
			ATNConfigSet configs) {
	}

	@Override
	public void reportContextSensitivity(Parser recognizer, DFA dfa,
			int startIndex, int stopIndex, int prediction, ATNConfigSet configs) {
	}

	private void addError(int line, String message) {
		StringBuilder builder = errorMap.get(line);
		if (builder == null) {
			builder = new StringBuilder();
			errorMap.put(line, builder);
		}

		if (builder.length() > 0)
			builder.append("，");
		builder.append(message);
	}

}

// Generated from Paper.g4 by ANTLR 4.2.2
package com.ailk.sets.grade.word.grammar;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PaperParser}.
 */
public interface PaperListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PaperParser#paperName}.
	 * @param ctx the parse tree
	 */
	void enterPaperName(@NotNull PaperParser.PaperNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#paperName}.
	 * @param ctx the parse tree
	 */
	void exitPaperName(@NotNull PaperParser.PaperNameContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#backward}.
	 * @param ctx the parse tree
	 */
	void enterBackward(@NotNull PaperParser.BackwardContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#backward}.
	 * @param ctx the parse tree
	 */
	void exitBackward(@NotNull PaperParser.BackwardContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#choice}.
	 * @param ctx the parse tree
	 */
	void enterChoice(@NotNull PaperParser.ChoiceContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#choice}.
	 * @param ctx the parse tree
	 */
	void exitChoice(@NotNull PaperParser.ChoiceContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#skillReorder}.
	 * @param ctx the parse tree
	 */
	void enterSkillReorder(@NotNull PaperParser.SkillReorderContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#skillReorder}.
	 * @param ctx the parse tree
	 */
	void exitSkillReorder(@NotNull PaperParser.SkillReorderContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#sentence}.
	 * @param ctx the parse tree
	 */
	void enterSentence(@NotNull PaperParser.SentenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#sentence}.
	 * @param ctx the parse tree
	 */
	void exitSentence(@NotNull PaperParser.SentenceContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#unitTime}.
	 * @param ctx the parse tree
	 */
	void enterUnitTime(@NotNull PaperParser.UnitTimeContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#unitTime}.
	 * @param ctx the parse tree
	 */
	void exitUnitTime(@NotNull PaperParser.UnitTimeContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#title}.
	 * @param ctx the parse tree
	 */
	void enterTitle(@NotNull PaperParser.TitleContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#title}.
	 * @param ctx the parse tree
	 */
	void exitTitle(@NotNull PaperParser.TitleContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#words}.
	 * @param ctx the parse tree
	 */
	void enterWords(@NotNull PaperParser.WordsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#words}.
	 * @param ctx the parse tree
	 */
	void exitWords(@NotNull PaperParser.WordsContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#choiceReorder}.
	 * @param ctx the parse tree
	 */
	void enterChoiceReorder(@NotNull PaperParser.ChoiceReorderContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#choiceReorder}.
	 * @param ctx the parse tree
	 */
	void exitChoiceReorder(@NotNull PaperParser.ChoiceReorderContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#description}.
	 * @param ctx the parse tree
	 */
	void enterDescription(@NotNull PaperParser.DescriptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#description}.
	 * @param ctx the parse tree
	 */
	void exitDescription(@NotNull PaperParser.DescriptionContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#supplement}.
	 * @param ctx the parse tree
	 */
	void enterSupplement(@NotNull PaperParser.SupplementContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#supplement}.
	 * @param ctx the parse tree
	 */
	void exitSupplement(@NotNull PaperParser.SupplementContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#questionReorder}.
	 * @param ctx the parse tree
	 */
	void enterQuestionReorder(@NotNull PaperParser.QuestionReorderContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#questionReorder}.
	 * @param ctx the parse tree
	 */
	void exitQuestionReorder(@NotNull PaperParser.QuestionReorderContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#descPoint}.
	 * @param ctx the parse tree
	 */
	void enterDescPoint(@NotNull PaperParser.DescPointContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#descPoint}.
	 * @param ctx the parse tree
	 */
	void exitDescPoint(@NotNull PaperParser.DescPointContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#question}.
	 * @param ctx the parse tree
	 */
	void enterQuestion(@NotNull PaperParser.QuestionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#question}.
	 * @param ctx the parse tree
	 */
	void exitQuestion(@NotNull PaperParser.QuestionContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#choices}.
	 * @param ctx the parse tree
	 */
	void enterChoices(@NotNull PaperParser.ChoicesContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#choices}.
	 * @param ctx the parse tree
	 */
	void exitChoices(@NotNull PaperParser.ChoicesContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#answer}.
	 * @param ctx the parse tree
	 */
	void enterAnswer(@NotNull PaperParser.AnswerContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#answer}.
	 * @param ctx the parse tree
	 */
	void exitAnswer(@NotNull PaperParser.AnswerContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#hint}.
	 * @param ctx the parse tree
	 */
	void enterHint(@NotNull PaperParser.HintContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#hint}.
	 * @param ctx the parse tree
	 */
	void exitHint(@NotNull PaperParser.HintContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#paperDesc}.
	 * @param ctx the parse tree
	 */
	void enterPaperDesc(@NotNull PaperParser.PaperDescContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#paperDesc}.
	 * @param ctx the parse tree
	 */
	void exitPaperDesc(@NotNull PaperParser.PaperDescContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#descTime}.
	 * @param ctx the parse tree
	 */
	void enterDescTime(@NotNull PaperParser.DescTimeContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#descTime}.
	 * @param ctx the parse tree
	 */
	void exitDescTime(@NotNull PaperParser.DescTimeContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#unit}.
	 * @param ctx the parse tree
	 */
	void enterUnit(@NotNull PaperParser.UnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#unit}.
	 * @param ctx the parse tree
	 */
	void exitUnit(@NotNull PaperParser.UnitContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#questionWithoutChoices}.
	 * @param ctx the parse tree
	 */
	void enterQuestionWithoutChoices(@NotNull PaperParser.QuestionWithoutChoicesContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#questionWithoutChoices}.
	 * @param ctx the parse tree
	 */
	void exitQuestionWithoutChoices(@NotNull PaperParser.QuestionWithoutChoicesContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#skill}.
	 * @param ctx the parse tree
	 */
	void enterSkill(@NotNull PaperParser.SkillContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#skill}.
	 * @param ctx the parse tree
	 */
	void exitSkill(@NotNull PaperParser.SkillContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#yesOrNo}.
	 * @param ctx the parse tree
	 */
	void enterYesOrNo(@NotNull PaperParser.YesOrNoContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#yesOrNo}.
	 * @param ctx the parse tree
	 */
	void exitYesOrNo(@NotNull PaperParser.YesOrNoContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#word}.
	 * @param ctx the parse tree
	 */
	void enterWord(@NotNull PaperParser.WordContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#word}.
	 * @param ctx the parse tree
	 */
	void exitWord(@NotNull PaperParser.WordContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#prompt}.
	 * @param ctx the parse tree
	 */
	void enterPrompt(@NotNull PaperParser.PromptContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#prompt}.
	 * @param ctx the parse tree
	 */
	void exitPrompt(@NotNull PaperParser.PromptContext ctx);

	/**
	 * Enter a parse tree produced by {@link PaperParser#punctuation}.
	 * @param ctx the parse tree
	 */
	void enterPunctuation(@NotNull PaperParser.PunctuationContext ctx);
	/**
	 * Exit a parse tree produced by {@link PaperParser#punctuation}.
	 * @param ctx the parse tree
	 */
	void exitPunctuation(@NotNull PaperParser.PunctuationContext ctx);
}
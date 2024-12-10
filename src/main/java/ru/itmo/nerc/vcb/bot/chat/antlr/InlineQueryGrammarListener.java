// Generated from InlineQueryGrammar.g4 by ANTLR 4.13.1

package ru.itmo.nerc.vcb.bot.chat.antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link InlineQueryGrammarParser}.
 */
public interface InlineQueryGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link InlineQueryGrammarParser#inlineQuery}.
	 * @param ctx the parse tree
	 */
	void enterInlineQuery(InlineQueryGrammarParser.InlineQueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link InlineQueryGrammarParser#inlineQuery}.
	 * @param ctx the parse tree
	 */
	void exitInlineQuery(InlineQueryGrammarParser.InlineQueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link InlineQueryGrammarParser#anyParameter}.
	 * @param ctx the parse tree
	 */
	void enterAnyParameter(InlineQueryGrammarParser.AnyParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link InlineQueryGrammarParser#anyParameter}.
	 * @param ctx the parse tree
	 */
	void exitAnyParameter(InlineQueryGrammarParser.AnyParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link InlineQueryGrammarParser#taskParameter}.
	 * @param ctx the parse tree
	 */
	void enterTaskParameter(InlineQueryGrammarParser.TaskParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link InlineQueryGrammarParser#taskParameter}.
	 * @param ctx the parse tree
	 */
	void exitTaskParameter(InlineQueryGrammarParser.TaskParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link InlineQueryGrammarParser#taskOperator}.
	 * @param ctx the parse tree
	 */
	void enterTaskOperator(InlineQueryGrammarParser.TaskOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link InlineQueryGrammarParser#taskOperator}.
	 * @param ctx the parse tree
	 */
	void exitTaskOperator(InlineQueryGrammarParser.TaskOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link InlineQueryGrammarParser#groupsParameter}.
	 * @param ctx the parse tree
	 */
	void enterGroupsParameter(InlineQueryGrammarParser.GroupsParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link InlineQueryGrammarParser#groupsParameter}.
	 * @param ctx the parse tree
	 */
	void exitGroupsParameter(InlineQueryGrammarParser.GroupsParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link InlineQueryGrammarParser#groupsOperator}.
	 * @param ctx the parse tree
	 */
	void enterGroupsOperator(InlineQueryGrammarParser.GroupsOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link InlineQueryGrammarParser#groupsOperator}.
	 * @param ctx the parse tree
	 */
	void exitGroupsOperator(InlineQueryGrammarParser.GroupsOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link InlineQueryGrammarParser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameter(InlineQueryGrammarParser.TypeParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link InlineQueryGrammarParser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameter(InlineQueryGrammarParser.TypeParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link InlineQueryGrammarParser#typeOperator}.
	 * @param ctx the parse tree
	 */
	void enterTypeOperator(InlineQueryGrammarParser.TypeOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link InlineQueryGrammarParser#typeOperator}.
	 * @param ctx the parse tree
	 */
	void exitTypeOperator(InlineQueryGrammarParser.TypeOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link InlineQueryGrammarParser#idParameter}.
	 * @param ctx the parse tree
	 */
	void enterIdParameter(InlineQueryGrammarParser.IdParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link InlineQueryGrammarParser#idParameter}.
	 * @param ctx the parse tree
	 */
	void exitIdParameter(InlineQueryGrammarParser.IdParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link InlineQueryGrammarParser#idOperator}.
	 * @param ctx the parse tree
	 */
	void enterIdOperator(InlineQueryGrammarParser.IdOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link InlineQueryGrammarParser#idOperator}.
	 * @param ctx the parse tree
	 */
	void exitIdOperator(InlineQueryGrammarParser.IdOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link InlineQueryGrammarParser#answerParameter}.
	 * @param ctx the parse tree
	 */
	void enterAnswerParameter(InlineQueryGrammarParser.AnswerParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link InlineQueryGrammarParser#answerParameter}.
	 * @param ctx the parse tree
	 */
	void exitAnswerParameter(InlineQueryGrammarParser.AnswerParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link InlineQueryGrammarParser#answerOperator}.
	 * @param ctx the parse tree
	 */
	void enterAnswerOperator(InlineQueryGrammarParser.AnswerOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link InlineQueryGrammarParser#answerOperator}.
	 * @param ctx the parse tree
	 */
	void exitAnswerOperator(InlineQueryGrammarParser.AnswerOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link InlineQueryGrammarParser#keyParameter}.
	 * @param ctx the parse tree
	 */
	void enterKeyParameter(InlineQueryGrammarParser.KeyParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link InlineQueryGrammarParser#keyParameter}.
	 * @param ctx the parse tree
	 */
	void exitKeyParameter(InlineQueryGrammarParser.KeyParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link InlineQueryGrammarParser#keyOperator}.
	 * @param ctx the parse tree
	 */
	void enterKeyOperator(InlineQueryGrammarParser.KeyOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link InlineQueryGrammarParser#keyOperator}.
	 * @param ctx the parse tree
	 */
	void exitKeyOperator(InlineQueryGrammarParser.KeyOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link InlineQueryGrammarParser#valueParameter}.
	 * @param ctx the parse tree
	 */
	void enterValueParameter(InlineQueryGrammarParser.ValueParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link InlineQueryGrammarParser#valueParameter}.
	 * @param ctx the parse tree
	 */
	void exitValueParameter(InlineQueryGrammarParser.ValueParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link InlineQueryGrammarParser#valueOperator}.
	 * @param ctx the parse tree
	 */
	void enterValueOperator(InlineQueryGrammarParser.ValueOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link InlineQueryGrammarParser#valueOperator}.
	 * @param ctx the parse tree
	 */
	void exitValueOperator(InlineQueryGrammarParser.ValueOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link InlineQueryGrammarParser#ignoreParameter}.
	 * @param ctx the parse tree
	 */
	void enterIgnoreParameter(InlineQueryGrammarParser.IgnoreParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link InlineQueryGrammarParser#ignoreParameter}.
	 * @param ctx the parse tree
	 */
	void exitIgnoreParameter(InlineQueryGrammarParser.IgnoreParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link InlineQueryGrammarParser#sentencesArray}.
	 * @param ctx the parse tree
	 */
	void enterSentencesArray(InlineQueryGrammarParser.SentencesArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link InlineQueryGrammarParser#sentencesArray}.
	 * @param ctx the parse tree
	 */
	void exitSentencesArray(InlineQueryGrammarParser.SentencesArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link InlineQueryGrammarParser#sentenceWithComma}.
	 * @param ctx the parse tree
	 */
	void enterSentenceWithComma(InlineQueryGrammarParser.SentenceWithCommaContext ctx);
	/**
	 * Exit a parse tree produced by {@link InlineQueryGrammarParser#sentenceWithComma}.
	 * @param ctx the parse tree
	 */
	void exitSentenceWithComma(InlineQueryGrammarParser.SentenceWithCommaContext ctx);
	/**
	 * Enter a parse tree produced by {@link InlineQueryGrammarParser#sentence}.
	 * @param ctx the parse tree
	 */
	void enterSentence(InlineQueryGrammarParser.SentenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link InlineQueryGrammarParser#sentence}.
	 * @param ctx the parse tree
	 */
	void exitSentence(InlineQueryGrammarParser.SentenceContext ctx);
}
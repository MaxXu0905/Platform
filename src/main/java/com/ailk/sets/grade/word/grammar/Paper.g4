grammar Paper;

@lexer::members {
	public static final int WHITESPACE = 1;
}

description
:
	(
		words?
		(
			paperName
			| paperDesc
			|
			(
				descTime
				| descPoint
				| skillReorder
				| questionReorder
				| choiceReorder
				| backward
				|
			) words?
		) NL
	)+
;

paperName
:
	PROMPT_PAPER_NAME words
;

paperDesc
:
	PROMPT_PAPER_DESC words
;

descTime
:
	PROMPT_TIME_LIMIT INT unitTime
;

unitTime
:
	UNIT_HOUR
	| UNIT_MINUTE
	| UNIT_SECOND
;

descPoint
:
	PROMPT_POINT INT UNIT_POINT
;

skillReorder
:
	PROMPT_SKILL_REORDER yesOrNo
;

questionReorder
:
	PROMPT_QUESTION_REORDER yesOrNo
;

choiceReorder
:
	PROMPT_CHOICE_REORDER yesOrNo
;

backward
:
	PROMPT_BACKWARD yesOrNo
;

yesOrNo
:
	PROMPT_YES
	| PROMPT_NO
;

questionWithoutChoices
:
	DIGIT_PREFIX? title
	(
		NL
		(
			answer
			| supplement
		)
	)*
;

question
:
	DIGIT_PREFIX? title
	(
		NL choices
	)?
	(
		NL
		(
			answer
			| supplement
		)
	)*
;

title
:
	(
		LBRACE hint
		(
			COMMA hint
		)* RBRACE
		(
			LBRACE skill RBRACE
		)?
	)? NL* sentence
;

choices
:
	choice
	(
		NL? choice
	)+
;

choice
:
	CHOICE_PREFIX sentence
;

answer
:
	PROMPT_ANSWER sentence
;

supplement
:
	PROMPT_SUPPLEMENT sentence
;

sentence
:
	words
	(
		NL words?
	)*?
;

skill
:
	(
		INT
		| WORD
		| unit
		| hint
		| prompt
	)+
;

words
:
	(
		word
		| DIGIT_PREFIX
		| CHOICE_PREFIX
	)+?
;

word
:
	INT
	| WORD
	| punctuation
	| unit
	| hint
	| prompt
;

punctuation
:
	COMMA
	| COLON
	| LBRACE
	| RBRACE
;

unit
:
	UNIT_HOUR
	| UNIT_MINUTE
	| UNIT_SECOND
	| UNIT_POINT
;

hint
:
	HINT_SCQ
	| HINT_MCQS
	| HINT_LANGUAGE
	| HINT_VERY_EASY
	| HINT_EASY
	| HINT_MODERATE
	| HINT_DIFFICULT
	| HINT_VERY_DIFFICULT
	| HINT_SAMPLE
	| INT UNIT_POINT
	| INT unitTime
;

prompt
:
	PROMPT_YES
	| PROMPT_NO
;

UNIT_HOUR
:
	'小时'
;

UNIT_MINUTE
:
	'分钟'
;

UNIT_SECOND
:
	'秒'
;

PROMPT_PAPER_NAME
:
	'试卷名称' PROMPT_SEPARATOR
;

PROMPT_PAPER_DESC
:
	'试卷简介' PROMPT_SEPARATOR
;

PROMPT_TIME_LIMIT
:
	(
		'限时'
		| '时限'
		| '时间'
	) PROMPT_SEPARATOR
;

UNIT_POINT
:
	'分'
;

PROMPT_POINT
:
	'每题'? '分值' PROMPT_SEPARATOR
;

PROMPT_SKILL_REORDER
:
	'考点乱序' PROMPT_SEPARATOR
;

PROMPT_QUESTION_REORDER
:
	'题目乱序' PROMPT_SEPARATOR
;

PROMPT_CHOICE_REORDER
:
	'选项乱序' PROMPT_SEPARATOR
;

PROMPT_BACKWARD
:
	'可回溯' PROMPT_SEPARATOR
;

PROMPT_YES
:
	'是'
	| '对'
	| '正确'
;

PROMPT_NO
:
	'否'
	| '错'
	| '错误'
;

HINT_SCQ
:
	(
		'单项选择'
		| '单选'
		| '选择'
	) '题'?
;

HINT_MCQS
:
	(
		'多项选择'
		| '多选'
	) '题'?
;

HINT_VERY_EASY
:
	'极易'
;

HINT_EASY
:
	'易'
;

HINT_MODERATE
:
	'中'
;

HINT_DIFFICULT
:
	'难'
;

HINT_VERY_DIFFICULT
:
	'极难'
;

HINT_SAMPLE
:
	'样例'
;

PROMPT_ANSWER
:
	'答案' PROMPT_SEPARATOR
;

PROMPT_SUPPLEMENT
:
	(
		'答案'
		| '补充'
	)?
	(
		'解释'
		| '说明'
	) PROMPT_SEPARATOR
;

fragment
PROMPT_SEPARATOR
:
	COLON
	| RBRACE
;

fragment
CHOICE_SEPARATOR
:
	[.．、]
;

CHOICE_PREFIX
:
	[a-zA-Z] CHOICE_SEPARATOR
;

DIGIT_PREFIX
:
	[0-9]+ CHOICE_SEPARATOR
;

COMMA
:
	[,，;；、]
;

COLON
:
	[:：]
;

LBRACE
:
	[(\[{（【]
;

RBRACE
:
	[)\]}）】]
;

HINT_LANGUAGE
:
	[jJ] [aA] [vV] [aA]
	| [cC]
	| [cC] '++'
	| [oO] [bB] [jJ] [eE] [cC] [tT] [iI] [vV] [eE] [cC]
	| [pP] [yY] [tT] [hH] [oO] [nN]
	| [sS] [hH] [eE] [lL] [lL]
	| [jJ] [aA] [vV] [aA] [sS] [cC] [rR] [iI] [pP] [tT]
	| [pP] [hH] [pP]
	| [pP] [eE] [rR] [lL]
	| [rR] [uU] [bB] [yY]
	| [cC] '#'
	| [sQ] [qQ] [lL]
	| [wW] [eE] [bB]
	| [oO] [tT] [hH] [eE] [rR]
;

INT
:
	[0-9]+
;

NL
:
	'\n'
;

WS
:
	[ \t\r\u000C]+ -> channel ( WHITESPACE )
;

WORD
:
	[a-zA-Z_] [0-9a-zA-Z_]*
	| .
;

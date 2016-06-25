package com.ailk.sets.grade.word;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyle;
import org.apache.poi.xwpf.usermodel.XWPFStyles;

import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.service.IAliyunService;
import com.ailk.sets.grade.word.PaperException.Level;
import com.ailk.sets.grade.word.grammar.PaperErrorListener;
import com.ailk.sets.grade.word.grammar.PaperLexer;
import com.ailk.sets.grade.word.grammar.PaperListenerImpl;
import com.ailk.sets.grade.word.grammar.PaperParser;
import com.ailk.sets.grade.word.grammar.RequiredRuleEnum;

class WordImpl implements IWord {

	private static final Logger logger = Logger.getLogger(WordImpl.class);

	public static final String BUCKET_QUESTION = "qbank-self";

	private enum ParseType {
		DESCRIPTION, QUESTION, QUESTION_WITHOUT_CHOICES
	}

	private enum NodeType {
		PAPER, PART, SKILL, QUESTION, CHOICE, SUB_CHOICE
	};

	private IAliyunService aliyunService; // 阿里云服务

	private PaperTree paperTree; // 试卷分析树
	private PaperTree.Part currentPart; // 当前部分
	private PaperTree.Skill currentSkill; // 当前技能
	private PaperTree.Question currentQuestion; // 当前题目
	private PaperTree.Choice currentChoice; // 当选选项
	private PaperTree.Choice currentSubChoice; // 当前子选项
	private NodeType nodeType; // 当前类型
	private List<String> numFmts; // List Paragraph的格式列表

	private PaperWord paperWord; // 试卷处理结果

	/**
	 * 构造函数
	 */
	public WordImpl(IAliyunService aliyunService) {
		this.aliyunService = aliyunService;
		paperTree = new PaperTree();
		nodeType = NodeType.PAPER;
		numFmts = new ArrayList<String>();
		paperWord = new PaperWord();
	}

	/**
	 * 执行文档
	 * 
	 * @param doc
	 *            文档
	 * @return 试卷处理结果
	 */
	public PaperWord execute(XWPFDocument doc) {
		removeHints(doc);
		buildTree(doc);
		doPaper();

		// 统计题目数
		if (paperWord.getParts() != null) {
			for (PaperWord.Part part : paperWord.getParts()) {
				int questions = 0;
				if (part.getSkills() != null) {
					for (PaperWord.Skill skill : part.getSkills()) {
						if (skill.getQuestions() == null)
							continue;

						questions += skill.getQuestions().size();
					}
				}

				part.setQuestions(questions);
			}
		}

		if (paperWord.getErrors() > 0)
			return paperWord;

		// 继承默认值
		for (PaperWord.Part part : paperWord.getParts()) {
			part.inherit(paperWord);
			int questions = 0;

			for (PaperWord.Skill skill : part.getSkills()) {
				skill.inherit(part);

				for (PaperWord.Question question : skill.getQuestions()) {
					question.inherit(skill);
					questions++;
				}
			}

			int perTimeLimit = part.getTimeLimit() / questions;
			boolean first = true;
			for (PaperWord.Skill skill : part.getSkills()) {
				for (PaperWord.Question question : skill.getQuestions()) {
					if (first) {
						first = false;
						question.setTimeLimit((part.getTimeLimit() % questions)
								+ perTimeLimit);
					} else {
						question.setTimeLimit(perTimeLimit);
					}
				}
			}
		}

		return paperWord;
	}

	/**
	 * 处理试卷
	 */
	private void doPaper() {
		// 获取试卷描述
		if (paperTree.getDescriptions() != null) {
			XWPFParagraph paragraph = (XWPFParagraph) paperTree
					.getDescriptions().get(0);
			String text = getText(paperTree.getDescriptions(), false, false);
			try {
				PaperErrorListener errorListener = new PaperErrorListener();
				PaperListenerImpl listener = parse(text, ParseType.DESCRIPTION,
						errorListener);

				// 添加错误
				addHints(paperTree.getDescriptions(),
						errorListener.getErrorMap());

				if (StringUtils.isEmpty(listener.getPaperName())) {
					RequiredRuleEnum requiredRule = RequiredRuleEnum.PAPER_NAME;
					if (!errorListener.isReported(requiredRule))
						addHint(paragraph, requiredRule.getMessage());
				} else {
					paperWord.setPaperName(listener.getPaperName());
				}

				paperWord.setTimeLimit(listener.getTimeLimit());
			} catch (PaperException e) {
				addHint(paragraph, e.getDesc());
			}
		}

		List<PaperTree.Part> parts = paperTree.getParts();
		if (parts == null) {
			XWPFParagraph paragraph = (XWPFParagraph) paperTree
					.getDescriptions().get(0);
			addHint(paragraph, "缺少【部分】");
		}

		for (PaperTree.Part part : parts) {
			PaperWord.Part partWord = doPart(part);
			if (partWord != null)
				paperWord.add(partWord);
		}

		if (paperWord.getParts() == null) {
			XWPFParagraph paragraph = (XWPFParagraph) paperTree
					.getDescriptions().get(0);
			addHint(paragraph, "缺少【部分】");
		}
	}

	/**
	 * 处理部分
	 * 
	 * @param part
	 *            部分树
	 * @return 部分结果
	 */
	private PaperWord.Part doPart(PaperTree.Part part) {
		PaperWord.Part partWord = new PaperWord.Part();
		if (StringUtils.isEmpty(part.getPartName())) {
			if (part.getPart() != null)
				addHint((XWPFParagraph) part.getPart(), "缺少【部分名称】");
			return null;
		} else {
			partWord.setPartName(part.getPartName());
		}

		String partName = partWord.getPartName();
		if (partName.equals("技能类选择题")) {
			partWord.setPartId(PaperWord.PART_ID_TECH_CHOICE);
			partWord.setCategory(GradeConst.CATEGORY_TECHNOLOGY);
			partWord.setQuestionType(GradeConst.QUESTION_TYPE_UNKNOWN);
		} else if (partName.equals("编程题")) {
			partWord.setPartId(PaperWord.PART_ID_PROGRAM);
			partWord.setCategory(GradeConst.CATEGORY_TECHNOLOGY);
			partWord.setQuestionType(GradeConst.QUESTION_TYPE_EXTRA_PROGRAM);
		} else if (partName.equals("技能类问答题")) {
			partWord.setPartId(PaperWord.PART_ID_TECH_ESSAY);
			partWord.setCategory(GradeConst.CATEGORY_TECHNOLOGY);
			partWord.setQuestionType(GradeConst.QUESTION_TYPE_ESSAY);
		} else if (partName.equals("智力题")) {
			partWord.setPartId(PaperWord.PART_ID_INTEL);
			partWord.setCategory(GradeConst.CATEGORY_INTELLIGENCE);
			partWord.setQuestionType(GradeConst.QUESTION_TYPE_UNKNOWN);
		} else if (partName.equals("面试题")) {
			partWord.setPartId(PaperWord.PART_ID_INTERVIEW);
			partWord.setCategory(GradeConst.CATEGORY_INTERVIEW);
			partWord.setQuestionType(GradeConst.QUESTION_TYPE_VIDEO);
		} else {
			XWPFParagraph paragraph = (XWPFParagraph) part.getPart();
			addHint(paragraph, "标题只支持：技能类选择题、编程题、技能类问答题、智力题、面试题");
		}

		// 获取部分描述
		if (part.getDescriptions() != null) {
			String text = getText(part.getDescriptions(), false, false);
			try {
				PaperErrorListener errorListener = new PaperErrorListener();
				PaperListenerImpl listener = parse(text, ParseType.DESCRIPTION,
						errorListener);

				// 添加错误
				addHints(part.getDescriptions(), errorListener.getErrorMap());

				partWord.setTimeLimit(listener.getTimeLimit());
				partWord.setPoint(listener.getPoint());
				partWord.setSkillReorder(listener.isSkillReorder());
				partWord.setQuestionReorder(listener.isQuestionReorder());
				partWord.setChoiceReorder(listener.isChoiceReorder());
				partWord.setBackword(listener.isBackward());
			} catch (PaperException e) {
				XWPFParagraph paragraph = (XWPFParagraph) part
						.getDescriptions().get(0);
				addHint(paragraph, e.getDesc());
			}
		}

		List<PaperTree.Skill> skills = part.getSkills();
		if (skills == null) {
			XWPFParagraph paragraph = (XWPFParagraph) part.getPart();
			addHint(paragraph, "该部分缺少题目或所有题目格式都不正确");
			return null;
		}

		for (PaperTree.Skill skill : skills) {
			PaperWord.Skill skillWord = doSkill(partWord, skill);
			if (skillWord != null)
				partWord.add(skillWord);
		}

		if (partWord.getSkills() == null) {
			XWPFParagraph paragraph = (XWPFParagraph) part.getPart();
			addHint(paragraph, "该部分缺少题目或所有题目格式都不正确");
			return null;
		}

		return partWord;
	}

	/**
	 * 处理技能
	 * 
	 * @param part
	 *            部分
	 * @param skill
	 *            技能树
	 * @return 技能结果
	 */
	private PaperWord.Skill doSkill(PaperWord.Part part, PaperTree.Skill skill) {
		PaperWord.Skill skillWord = new PaperWord.Skill(part.getPartId());
		skillWord.setSkillName(skill.getSkillName());
		skillWord.setQuestionType(part.getQuestionType());

		// 获取部分描述
		if (skill.getDescriptions() != null) {
			String text = getText(skill.getDescriptions(), false, false);
			try {
				PaperErrorListener errorListener = new PaperErrorListener();
				PaperListenerImpl listener = parse(text, ParseType.DESCRIPTION,
						errorListener);

				// 添加错误
				addHints(skill.getDescriptions(), errorListener.getErrorMap());

				skillWord.setPoint(listener.getPoint());
				skillWord.setTimeLimit(listener.getTimeLimit());
				if (listener.getQuestionType() != GradeConst.QUESTION_TYPE_UNKNOWN)
					skillWord.setQuestionType(listener.getQuestionType());
				skillWord.setQuestionType(listener.getQuestionType());
			} catch (PaperException e) {
				XWPFParagraph paragraph = (XWPFParagraph) skill
						.getDescriptions().get(0);
				addHint(paragraph, e.getDesc());
			}
		}

		List<PaperTree.Question> questions = skill.getQuestions();
		if (questions == null)
			return null;

		for (PaperTree.Question question : questions) {
			PaperWord.Question questionWord = doQuestion(skillWord, question);
			if (questionWord != null)
				skillWord.add(questionWord);
		}

		if (skillWord.getQuestions() == null)
			return null;

		return skillWord;
	}

	/**
	 * 处理题目
	 *
	 * @param skill
	 *            技能
	 * @param question
	 *            题目树
	 * @return 题目结果
	 */
	private PaperWord.Question doQuestion(PaperWord.Skill skill,
			PaperTree.Question question) {
		List<IBodyElement> titles = question.getTitles();
		try {
			List<PaperTree.Choice> choices = question.getChoices();

			StringBuilder textBuilder = new StringBuilder();
			textBuilder.append(getText(titles, true, false));
			if (choices != null && choices.size() == 1) { // 如果只有一个选项，则把选项展开处理
				PaperTree.Choice choice = choices.get(0);
				textBuilder.append("A.");
				textBuilder.append(getText(choice.getElements(), true, false));
			}

			boolean withoutChoices;
			if (choices != null && choices.size() > 1)
				withoutChoices = true;
			else
				withoutChoices = false;

			String text = textBuilder.toString();
			PaperErrorListener errorListener = new PaperErrorListener();
			PaperWord.Question questionWord = parseQuestion(text,
					withoutChoices, errorListener, skill.getQuestionType());
			if (questionWord.getQuestionType() == GradeConst.QUESTION_TYPE_UNKNOWN) {
				XWPFParagraph root = (XWPFParagraph) titles.get(0);
				addHint(root, "缺少【题型】");
				return null;
			}

			if (skill.getPartId() == PaperWord.PART_ID_TECH_CHOICE) {
				if (StringUtils.isEmpty(questionWord.getSkillName())) {
					XWPFParagraph root = (XWPFParagraph) titles.get(0);
					addHint(root, "缺少【考点】");
					return null;
				}
			}

			if ((questionWord.getQuestionType() == GradeConst.QUESTION_TYPE_PROGRAM || questionWord
					.getQuestionType() == GradeConst.QUESTION_TYPE_EXTRA_PROGRAM)
					&& questionWord.getProgramLanguage() == GradeConst.MODE_UNKNOWN) {
				XWPFParagraph root = (XWPFParagraph) titles.get(0);
				addHint(root, "缺少【编程语言】，或编程语言不正确");
				return null;
			}

			// 添加错误
			Map<Integer, StringBuilder> errorMap = errorListener.getErrorMap();
			if (!MapUtils.isEmpty(errorMap)) {
				addHints(titles, errorMap);
				return null;
			}

			if (choices != null && choices.size() > 1) {
				// 设置选项
				List<String> options = new ArrayList<String>();
				questionWord.setOptions(options);

				for (int i = 0; i < choices.size(); i++) {
					PaperTree.Choice choice = choices.get(i);
					StringBuilder builder = new StringBuilder();
					List<IBodyElement> bodyElements = new ArrayList<IBodyElement>();

					builder.append(getText(choice.getElements(), true, false));
					bodyElements.addAll(choice.getElements());

					List<PaperTree.Choice> subChoices = choice.getSubChoices();
					if (subChoices != null) {
						for (PaperTree.Choice subChoice : subChoices) {
							builder.append(getText(subChoice.getElements(),
									true, false));
							bodyElements.addAll(subChoice.getElements());
						}
					}

					if (i < choices.size() - 1) {
						// 去掉末尾的换行符
						int j;
						for (j = builder.length() - 1; j >= 0; j--) {
							if (builder.charAt(j) != '\n')
								break;
						}
						builder.setLength(j + 1);

						options.add("<pre>"
								+ builder.toString().replace("</pre><pre>", "")
								+ "</pre>");
					} else {
						text = builder.toString();
						PaperErrorListener optionErrorListener = new PaperErrorListener();
						PaperListenerImpl optionListener = parse(text,
								ParseType.QUESTION, optionErrorListener);

						// 添加错误
						addHints(bodyElements, errorListener.getErrorMap());

						options.add(optionListener.getTitle());
						questionWord.setOptAnswer(optionListener.getAnswer());
						questionWord.setTextAnswer(optionListener
								.getSupplement());
					}
				}
			}

			questionWord.setEndLines(getEndLines(text));
			validate(questionWord);

			return questionWord;
		} catch (PaperException e) {
			XWPFParagraph root = (XWPFParagraph) titles.get(0);
			addHint(root, e.getDesc());
			return null;
		}
	}

	/**
	 * 分析题目
	 * 
	 * @param text
	 *            数据
	 * @param withoutChoices
	 *            不包含选项
	 * @param errorListener
	 *            错误监听器
	 * @param defaultQuestionType
	 *            默认题型
	 * @return 题目结果
	 * @throws PaperException
	 */
	private PaperWord.Question parseQuestion(String text,
			boolean withoutChoices, PaperErrorListener errorListener,
			int defaultQuestionType) throws PaperException {
		ParseType parseType;
		if (!withoutChoices)
			parseType = ParseType.QUESTION;
		else
			parseType = ParseType.QUESTION_WITHOUT_CHOICES;
		PaperListenerImpl listener = parse(text, parseType, errorListener);

		PaperWord.Question questionWord = new PaperWord.Question();
		questionWord.setQuestionType(listener.getQuestionType());
		questionWord.setProgramLanguage(listener.getProgramLanguage());
		questionWord.setSkillName(listener.getSkillName());
		questionWord.setTitle(listener.getTitle());
		questionWord.setOptions(listener.getOptions());
		questionWord.setPoint(listener.getPoint());
		questionWord.setDifficulty(listener.getDifficulty());
		questionWord.setSample(listener.isSample());
		questionWord.setTimeLimit(listener.getTimeLimit());

		if (questionWord.getQuestionType() == GradeConst.QUESTION_TYPE_UNKNOWN)
			questionWord.setQuestionType(defaultQuestionType);

		switch (questionWord.getQuestionType()) {
		case GradeConst.QUESTION_TYPE_S_CHOICE:
		case GradeConst.QUESTION_TYPE_M_CHOICE:
			questionWord.setOptAnswer(listener.getAnswer());
			questionWord.setTextAnswer(listener.getSupplement());
			break;
		case GradeConst.QUESTION_TYPE_EXTRA_PROGRAM:
		case GradeConst.QUESTION_TYPE_ESSAY:
			questionWord.setTextAnswer(listener.getAnswer());
			break;
		case GradeConst.QUESTION_TYPE_VIDEO:
			break;
		}

		return questionWord;
	}

	/**
	 * 分析数据
	 * 
	 * @param data
	 *            数据
	 * @param type
	 *            分析类型
	 * @param errorListener
	 *            错误监听器
	 * @return 分析结果
	 * @throws PaperException
	 */
	private PaperListenerImpl parse(String data, ParseType type,
			PaperErrorListener errorListener) throws PaperException {
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes());
			ANTLRInputStream input = new ANTLRInputStream(in);

			PaperLexer lexer = new PaperLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			PaperParser parser = new PaperParser(tokens);

			PaperListenerImpl paperListener = new PaperListenerImpl(tokens);
			parser.addParseListener(paperListener);

			parser.removeErrorListeners();
			parser.addErrorListener(errorListener);

			switch (type) {
			case DESCRIPTION:
				parser.description();
				break;
			case QUESTION:
				parser.question();
				break;
			case QUESTION_WITHOUT_CHOICES:
				parser.questionWithoutChoices();
				break;
			}

			return paperListener;
		} catch (Exception e) {
			logger.debug(data, e);
			throw new PaperException(Level.ERROR, "语法分析错误");
		}
	}

	/**
	 * 构造文档树
	 * 
	 * @param doc
	 *            文档
	 */
	private void buildTree(XWPFDocument doc) {
		XWPFStyles styles = doc.getStyles();
		List<IBodyElement> bodyElements = doc.getBodyElements();

		for (IBodyElement bodyElement : bodyElements) {
			String numFmt = null;
			WordStyle wordStyle = WordStyle.NOOP;
			BodyElementType bodyElementType = bodyElement.getElementType();

			switch (bodyElementType) {
			case PARAGRAPH: {
				// 获取格式
				XWPFParagraph paragraph = (XWPFParagraph) bodyElement;
				XWPFStyle style = styles.getStyle(paragraph.getStyleID());
				if (style != null)
					wordStyle = WordStyle.getWordStyle(style.getName());

				if (wordStyle != WordStyle.HEADING_1
						&& wordStyle != WordStyle.HEADING_2) {
					numFmt = paragraph.getNumFmt();
					if ("none".equals(numFmt)) // 兼容老版本Word的样式
						numFmt = "decimal";

					if (numFmt != null) {
						wordStyle = WordStyle.LIST_PARAGRAPH;
					} else if (isListParaghaph(getText(paragraph, false, true))) {
						wordStyle = WordStyle.LIST_PARAGRAPH;
						numFmt = "decimal";
					}
				}

				break;
			}
			case TABLE:
				wordStyle = WordStyle.NOOP;
				break;
			case CONTENTCONTROL:
			default:
				continue;
			}

			// 根据格式类型设置
			switch (wordStyle) {
			case HEADING_1: { // 部分
				newPart();
				currentPart.setPartName(getText(bodyElement, false, false));
				currentPart.setPart(bodyElement);
				break;
			}
			case HEADING_2: { // 技能
				newSkill();
				currentSkill.setSkillName(getText(bodyElement, false, false));
				currentSkill.setSkill(bodyElement);
				break;
			}
			case LIST_PARAGRAPH: {
				if (currentSkill == null)
					newSkill();

				int lvl;
				if (numFmt == null) {
					// 格式为空时，表示继承上一个格式
					lvl = -numFmts.size();
				} else {
					for (lvl = 0; lvl < numFmts.size(); lvl++) {
						if (numFmt.equals(numFmts.get(lvl)))
							break;
					}

					for (int index = numFmts.size() - 1; index > lvl; index--)
						numFmts.remove(index);

					if (lvl == numFmts.size() && lvl < 3)
						numFmts.add(numFmt);
				}

				switch (lvl) {
				case 0: { // 题目
					PaperTree.Question question = currentSkill.newQuestion();
					question.addTitle(bodyElement);
					currentQuestion = question;
					nodeType = NodeType.QUESTION;
					break;
				}
				case 1: { // 选项/子题目
					PaperTree.Choice choice = currentQuestion.newChoice();
					choice.addElement(bodyElement);
					currentChoice = choice;
					nodeType = NodeType.CHOICE;
					break;
				}
				case 2: { // 子题目选项
					PaperTree.Choice subChoice = currentChoice.newSubChoice();
					subChoice.addElement(bodyElement);
					currentSubChoice = subChoice;
					nodeType = NodeType.SUB_CHOICE;
					break;
				}
				case -1: // 题目
					currentQuestion.addTitle(bodyElement);
					break;
				case -2: // 选项/子题目
					currentChoice.addElement(bodyElement);
					break;
				case -3: // 子题目选项
					currentSubChoice.addElement(bodyElement);
					break;
				default:
					currentSubChoice.addElement(bodyElement);
					break;
				}
				break;
			}
			default:
				switch (nodeType) {
				case PAPER:
					paperTree.addDescription(bodyElement);
					break;
				case PART:
					currentPart.addDescription(bodyElement);
					break;
				case SKILL:
					currentSkill.addDescription(bodyElement);
					break;
				case QUESTION:
					currentQuestion.addTitle(bodyElement);
					break;
				case CHOICE:
					currentChoice.addElement(bodyElement);
					break;
				case SUB_CHOICE:
					currentSubChoice.addElement(bodyElement);
					break;
				}
				break;
			}
		}
	}

	/**
	 * 获取文本
	 * 
	 * @param bodyElements
	 *            文档元素列表
	 * @param html
	 *             是否html格式
	 * @param skipError
	 *            忽略错误
	 * @return 文本
	 */
	private String getText(List<IBodyElement> bodyElements, boolean html,
			boolean skipError) {
		StringBuilder builder = new StringBuilder();

		for (IBodyElement bodyElement : bodyElements) {
			builder.append(getText(bodyElement, html, skipError));
			builder.append("\n");
		}

		return builder.toString();
	}

	/**
	 * 获取文本
	 * 
	 * @param bodyElement
	 *            文档元素
	 * @param html
	 *             是否html格式
	 * @param skipError
	 *            忽略错误
	 * @return 文本
	 */
	private String getText(IBodyElement bodyElement, boolean html,
			boolean skipError) {
		if (!bodyElement.getElementType().equals(BodyElementType.PARAGRAPH))
			return "";

		boolean mediaError = false;
		StringBuilder builder = new StringBuilder();
		XWPFParagraph paragraph = (XWPFParagraph) bodyElement;
		List<XWPFRun> runs = paragraph.getRuns();
		for (XWPFRun run : runs) {
			if (html)
				builder.append(StringEscapeUtils.escapeXml(run.toString()));
			else
				builder.append(run.toString());

			// 添加图片
			List<XWPFPicture> pictures = run.getEmbeddedPictures();
			if (!html && !pictures.isEmpty()) {
				mediaError = true;
				continue;
			}

			for (XWPFPicture picture : pictures) {
				String suffix;
				XWPFPictureData pictureData = picture.getPictureData();
				switch (pictureData.getPictureType()) {
				case Document.PICTURE_TYPE_JPEG:
					suffix = "jpeg";
					break;
				case Document.PICTURE_TYPE_BMP:
					suffix = "bmp";
					break;
				case Document.PICTURE_TYPE_GIF:
					suffix = "gif";
					break;
				case Document.PICTURE_TYPE_PNG:
					suffix = "png";
					break;
				case Document.PICTURE_TYPE_TIFF:
					suffix = "tiff";
					break;
				default:
					addHint(paragraph, "不支持的图片格式，请转换成以下之一：JPG、BMP、GIF、PNG、TIFF");
					continue;
				}

				String key = aliyunService.putObject(BUCKET_QUESTION, suffix,
						pictureData.getData());
				String url = aliyunService.getUrl(BUCKET_QUESTION, key);
				builder.append("</pre><img src=\"" + url + "\"/><pre>");
			}
		}

		if (!skipError) {
			if (mediaError)
				addHint(paragraph, "该位置不支持多媒体");
		}

		return builder.toString();
	}

	/**
	 * 验证题目一致性
	 * 
	 * @param questionWord
	 *            题目
	 * @throws PaperException
	 */
	private void validate(PaperWord.Question questionWord)
			throws PaperException {
		List<String> options = questionWord.getOptions();
		int questionType = questionWord.getQuestionType();

		switch (questionType) {
		case GradeConst.QUESTION_TYPE_S_CHOICE:
		case GradeConst.QUESTION_TYPE_M_CHOICE: {
			if (options == null) {
				throw new PaperException(Level.ERROR, "选择题缺少选项");
			}

			String optAnswer = standardOptAnswer(questionWord.getOptAnswer(),
					options.size());
			switch (questionType) {
			case GradeConst.QUESTION_TYPE_S_CHOICE:
				if (optAnswer.length() != 1)
					throw new PaperException(Level.ERROR, "单选题只能有一个答案");
				break;
			case GradeConst.QUESTION_TYPE_M_CHOICE:
				if (optAnswer.length() <= 1)
					throw new PaperException(Level.ERROR, "多选题至少有两个答案");
				break;
			}

			questionWord.setOptAnswer(optAnswer);

			if (!StringUtils.isEmpty(questionWord.getTextAnswer())) {
				switch (questionType) {
				case GradeConst.QUESTION_TYPE_S_CHOICE:
					questionWord
							.setQuestionType(GradeConst.QUESTION_TYPE_S_CHOICE_PLUS);
					break;
				case GradeConst.QUESTION_TYPE_M_CHOICE:
					questionWord
							.setQuestionType(GradeConst.QUESTION_TYPE_M_CHOICE_PLUS);
					break;
				default:
					break;
				}
			}
			break;
		}
		case GradeConst.QUESTION_TYPE_EXTRA_PROGRAM:
			if (options != null) {
				throw new PaperException(Level.ERROR, "编程题不能有选项");
			}
			break;
		case GradeConst.QUESTION_TYPE_ESSAY:
			if (options != null) {
				throw new PaperException(Level.ERROR, "问答题不能有选项");
			}
			break;
		case GradeConst.QUESTION_TYPE_VIDEO:
			if (options != null) {
				throw new PaperException(Level.ERROR, "视频题不能有选项");
			}

			if (!StringUtils.isEmpty(questionWord.getTextAnswer())) {
				throw new PaperException(Level.ERROR, "视频题不能有答案");
			}
			break;
		}
	}

	/**
	 * 规整选项
	 * 
	 * @param data
	 *            规整前选项
	 * @param count
	 *            选项个数
	 * @return 规整后选项
	 * @throws PaperException
	 */
	private String standardOptAnswer(String data, int count)
			throws PaperException {
		if (data == null || data.length() <= 11)
			throw new PaperException(Level.ERROR, "选择题缺少答案");

		// 去掉前后的<pre></pre>
		data = data.substring(5, data.length() - 6);
		if (StringUtils.isEmpty(data))
			throw new PaperException(Level.ERROR, "选择题缺少答案");

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < data.length(); i++) {
			char ch = data.charAt(i);
			if (Character.isSpaceChar(ch))
				continue;

			if (ch >= '1' && ch < '1' + count)
				ch = (char) ('A' + (ch - '1'));
			else if (ch >= 'a' && ch < 'a' + count)
				ch = (char) ('A' + (ch - 'a'));
			else if (ch >= 'A' && ch < 'A' + count)
				;
			else
				throw new PaperException(Level.ERROR, "选择题选项答案超出范围");

			builder.append(ch);
		}

		return builder.toString();
	}

	/**
	 * 在段落前添加文本
	 * 
	 * @param paragraph
	 *            段落
	 * @param text
	 *            文本
	 */
	private void addHint(XWPFParagraph paragraph, String text) {
		boolean hasHint = false;
		for (XWPFRun run : paragraph.getRuns()) {
			if (!isHintRun(run))
				break;

			// 相同错误只报告一次
			if (text.equals(run.toString()))
				return;

			hasHint = true;
		}

		if (hasHint)
			text = text + "；";

		XWPFRun run = paragraph.insertNewRun(0);
		run.setText(text);
		run.setBold(true);
		run.setColor("FF0000");
		run.setFontFamily("Courier");
		run.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
		paragraph.addRun(run);

		paperWord.setErrors(paperWord.getErrors() + 1);
	}

	private boolean isHintRun(XWPFRun run) {
		if (run.getUnderline() == UnderlinePatterns.DOT_DOT_DASH
				&& "FF0000".equals(run.getColor()))
			return true;
		else
			return false;
	}

	private int getEndLines(String text) {
		if (text == null)
			return 0;

		int endLines = 0;
		for (int i = text.length() - 1; i >= 0; i--) {
			if (text.charAt(i) != '\n')
				break;

			endLines++;
		}

		return endLines;
	}

	/**
	 * 新建部分
	 */
	private void newPart() {
		PaperTree.Part part = paperTree.newPart();
		part.setPartName(null);
		part.setPart(null);
		currentPart = part;
		nodeType = NodeType.PART;
		numFmts.clear();

		currentSkill = null;
		currentQuestion = null;
		currentChoice = null;
		currentSubChoice = null;
	}

	/**
	 * 新建技能
	 */
	private void newSkill() {
		if (currentPart == null)
			newPart();

		PaperTree.Skill skill = currentPart.newSkill();
		skill.setSkillName(null);
		skill.setSkill(null);
		currentSkill = skill;
		nodeType = NodeType.SKILL;
		numFmts.clear();

		currentQuestion = null;
		currentChoice = null;
		currentSubChoice = null;
	}

	/**
	 * 删除所有注释
	 * 
	 * @param doc
	 *            文档
	 */
	private void removeHints(XWPFDocument doc) {
		List<IBodyElement> bodyElements = doc.getBodyElements();

		for (int pos = bodyElements.size() - 1; pos >= 0; pos--) {
			IBodyElement bodyElement = bodyElements.get(pos);
			BodyElementType bodyElementType = bodyElement.getElementType();

			// 忽略系统插入的注解
			if (bodyElementType == BodyElementType.PARAGRAPH) {
				XWPFParagraph paragraph = (XWPFParagraph) bodyElement;
				List<XWPFRun> runs = paragraph.getRuns();
				for (int i = runs.size() - 1; i >= 0; i--) {
					XWPFRun run = runs.get(i);
					if (isHintRun(run)) {
						paragraph.removeRun(i);
						continue;
					}
				}

				if (runs.isEmpty()) {
					doc.removeBodyElement(pos);
					continue;
				}
			}
		}
	}

	/**
	 * 批量在段落前添加注释
	 * 
	 * @param bodyElements
	 *            段落列表
	 * @param errorMap
	 *            错误映射
	 */
	private void addHints(List<IBodyElement> bodyElements,
			Map<Integer, StringBuilder> errorMap) {
		if (errorMap == null)
			return;

		for (Entry<Integer, StringBuilder> entry : errorMap.entrySet()) {
			int index = entry.getKey() - 1;
			if (index >= bodyElements.size())
				index = bodyElements.size() - 1;
			IBodyElement bodyElement = bodyElements.get(index);
			XWPFParagraph paragraph = (XWPFParagraph) bodyElement;

			addHint(paragraph, entry.getValue().toString());
		}
	}

	/**
	 * 根据文本的开始部分判断是否为列表
	 * 
	 * @param text
	 *            文本
	 * @return 是否为列表
	 */
	private static boolean isListParaghaph(String text) {
		return text.matches("^(\\s)*[0-9]+[.．、].*$");
	}

}

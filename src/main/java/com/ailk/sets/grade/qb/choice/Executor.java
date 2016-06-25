package com.ailk.sets.grade.qb.choice;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import com.ailk.sets.grade.grade.config.ParamElement;
import com.ailk.sets.grade.grade.config.QuestionConf;
import com.ailk.sets.grade.grade.config.QuestionContent;
import com.ailk.sets.grade.grade.execute.ResultHolder;
import com.ailk.sets.grade.qb.IExecutor;
import com.ailk.sets.grade.utils.Env;

/**
 * 根据配置，实例化、编译、运行等操作
 * 
 * @author xugq
 * 
 */
public class Executor implements IExecutor {

	private static int choiceExecutorIndex = 0;

	@Override
	public void instantiate(QuestionContent questionContent) throws Exception {
		QuestionConf questionConf = questionContent.getQuestionConf();
		List<ParamElement> params = questionConf.getParams();
		Map<String, String> map = new HashMap<String, String>();
		List<String> optionValues;
		Random random = new Random(System.currentTimeMillis());

		if (params != null && !params.isEmpty()) {
			Object[] args = new Object[params.size()];
			for (int i = 0; i < params.size(); i++) {
				ParamElement param = params.get(i);

				args[i] = param.newInstance(random);
				map.put(param.getName(), args[i].toString());
			}

			// 获取选项值
			optionValues = getOptions(questionConf.getOptions(), params, args);
		} else {
			optionValues = questionConf.getOptions();
		}

		if (!map.isEmpty())
			questionConf.setTitle(Env.expandVar(questionConf.getTitle(), map));

		List<String> options = questionConf.getOptions();
		List<Integer> indeces = new ArrayList<Integer>();
		for (int i = 0; i < options.size(); i++) {
			indeces.add(i);
		}

		Collections.shuffle(indeces);

		List<String> newOptions = new ArrayList<String>();
		questionConf.setOptions(newOptions);
		for (int index : indeces) {
			newOptions.add(optionValues.get(index));
		}

		if (questionConf.getAnswer() <= 0)
			questionConf.setAnswer(1);

		String answerStr = "";
		for (int i = 0; i < indeces.size(); i++) {
			int idx = indeces.get(i);
			if (idx < questionConf.getAnswer())
				answerStr += (char) ('A' + i);
		}
		questionConf.setOptAnswers(answerStr);
	}

	@Override
	public ResultHolder compile(String root, int stage) throws Exception {
		return new ResultHolder();
	}

	@Override
	public ResultHolder init(String root, int stage) throws Exception {
		return new ResultHolder();
	}

	@Override
	public ResultHolder deploy(String root, int stage) throws Exception {
		return new ResultHolder();
	}

	@Override
	public Map<String, String> getenv() throws Exception {
		return null;
	}

	@Override
	public ResultHolder destroy(String root, int stage) throws Exception {
		return new ResultHolder();
	}

	@Override
	public ResultHolder execute(String root, int stage, int mode, int seq)
			throws Exception {
		return new ResultHolder();
	}

	private List<String> getOptions(List<String> options,
			List<ParamElement> params, Object[] args) throws Exception {
		ClassPool pool = ClassPool.getDefault();
		pool.importPackage("java.io");
		pool.importPackage("java.net");
		pool.importPackage("java.math");
		pool.importPackage("java.util");

		String className;
		synchronized (Executor.class) {
			choiceExecutorIndex++;
			className = "ChoiceExecutor" + choiceExecutorIndex;
		}
		CtClass cc = pool.makeClass(className);

		for (int i = 0; i < options.size(); i++) {
			StringBuilder builder = new StringBuilder();
			boolean first = true;

			builder.append("public static String fun" + i + "(");
			for (int j = 0; j < params.size(); j++) {
				ParamElement holder = params.get(j);

				if (first)
					first = false;
				else
					builder.append(", ");

				switch (holder.getSubType()) {
				case ParamElement.SUB_TYPE_ATOMIC:
				case ParamElement.SUB_TYPE_ARRAY:
					switch (holder.getType()) {
					case ParamElement.TYPE_BOOLEAN:
						builder.append("boolean");
						break;
					case ParamElement.TYPE_BYTE:
						builder.append("byte");
						break;
					case ParamElement.TYPE_CHAR:
						builder.append("char");
						break;
					case ParamElement.TYPE_SHORT:
						builder.append("short");
						break;
					case ParamElement.TYPE_INT:
						builder.append("int");
						break;
					case ParamElement.TYPE_LONG:
						builder.append("long");
						break;
					case ParamElement.TYPE_FLOAT:
						builder.append("float");
						break;
					case ParamElement.TYPE_DOUBLE:
						builder.append("double");
						break;
					case ParamElement.TYPE_STRING:
						builder.append("String");
						break;
					}

					if (holder.getSubType() == ParamElement.SUB_TYPE_ARRAY)
						builder.append("[] ");
					else
						builder.append(" ");
					break;
				case ParamElement.SUB_TYPE_LIST:
					builder.append("java.util.List ");
					break;
				}

				builder.append(holder.getName());
			}

			builder.append(") {");
			builder.append(options.get(i));
			builder.append("}");

			CtMethod method = CtMethod.make(builder.toString(), cc);
			cc.addMethod(method);
		}

		Class<?> claz = cc.toClass();
		cc.detach();
		List<String> result = new ArrayList<String>();
		Method[] methods = claz.getMethods();

		for (int i = 0; i < options.size(); i++) {
			String methodName = "fun" + i;
			for (Method method : methods) {
				if (!method.getName().equals(methodName))
					continue;

				Object retval = null;
				switch (args.length) {
				case 0:
					retval = method.invoke(null);
					break;
				case 1:
					retval = method.invoke(null, args[0]);
					break;
				case 2:
					retval = method.invoke(null, args[0], args[1]);
					break;
				case 3:
					retval = method.invoke(null, args[0], args[1], args[2]);
					break;
				case 4:
					retval = method.invoke(null, args[0], args[1], args[2],
							args[3]);
					break;
				case 5:
					retval = method.invoke(null, args[0], args[1], args[2],
							args[3], args[4]);
					break;
				case 6:
					retval = method.invoke(null, args[0], args[1], args[2],
							args[3], args[4], args[5]);
					break;
				case 7:
					retval = method.invoke(null, args[0], args[1], args[2],
							args[3], args[4], args[5], args[6]);
					break;
				case 8:
					retval = method.invoke(null, args[0], args[1], args[2],
							args[3], args[4], args[5], args[6], args[7]);
					break;
				case 9:
					retval = method.invoke(null, args[0], args[1], args[2],
							args[3], args[4], args[5], args[6], args[7],
							args[8]);
					break;
				case 10:
					retval = method.invoke(null, args[0], args[1], args[2],
							args[3], args[4], args[5], args[6], args[7],
							args[8], args[9]);
					break;
				case 11:
					retval = method.invoke(null, args[0], args[1], args[2],
							args[3], args[4], args[5], args[6], args[7],
							args[8], args[9], args[10]);
					break;
				case 12:
					retval = method.invoke(null, args[0], args[1], args[2],
							args[3], args[4], args[5], args[6], args[7],
							args[8], args[9], args[10], args[11]);
					break;
				case 13:
					retval = method.invoke(null, args[0], args[1], args[2],
							args[3], args[4], args[5], args[6], args[7],
							args[8], args[9], args[10], args[11], args[12]);
					break;
				case 14:
					retval = method.invoke(null, args[0], args[1], args[2],
							args[3], args[4], args[5], args[6], args[7],
							args[8], args[9], args[10], args[11], args[12],
							args[13]);
					break;
				case 15:
					retval = method.invoke(null, args[0], args[1], args[2],
							args[3], args[4], args[5], args[6], args[7],
							args[8], args[9], args[10], args[11], args[12],
							args[13], args[14]);
					break;
				case 16:
					retval = method.invoke(null, args[0], args[1], args[2],
							args[3], args[4], args[5], args[6], args[7],
							args[8], args[9], args[10], args[11], args[12],
							args[13], args[14], args[15]);
					break;
				case 17:
					retval = method.invoke(null, args[0], args[1], args[2],
							args[3], args[4], args[5], args[6], args[7],
							args[8], args[9], args[10], args[11], args[12],
							args[13], args[14], args[15], args[16]);
					break;
				case 18:
					retval = method.invoke(null, args[0], args[1], args[2],
							args[3], args[4], args[5], args[6], args[7],
							args[8], args[9], args[10], args[11], args[12],
							args[13], args[14], args[15], args[16], args[17]);
					break;
				case 19:
					retval = method.invoke(null, args[0], args[1], args[2],
							args[3], args[4], args[5], args[6], args[7],
							args[8], args[9], args[10], args[11], args[12],
							args[13], args[14], args[15], args[16], args[17],
							args[18]);
					break;
				case 20:
					retval = method.invoke(null, args[0], args[1], args[2],
							args[3], args[4], args[5], args[6], args[7],
							args[8], args[9], args[10], args[11], args[12],
							args[13], args[14], args[15], args[16], args[17],
							args[18], args[19]);
					break;
				}

				result.add((String) retval);
			}
		}

		return result;
	}

	@Override
	public ResultHolder test(String root, int stage, int mode, String arg)
			throws Exception {
		return new ResultHolder();
	}

	@Override
	public ResultHolder testSample(String root, int stage, int mode,
			int sampleId) throws Exception {
		return new ResultHolder();
	}

}

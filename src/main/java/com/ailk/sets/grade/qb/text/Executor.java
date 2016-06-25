package com.ailk.sets.grade.qb.text;

import java.util.Map;

import com.ailk.sets.grade.grade.config.QuestionContent;
import com.ailk.sets.grade.grade.execute.ResultHolder;
import com.ailk.sets.grade.qb.IExecutor;

/**
 * 根据配置，实例化、编译、运行等操作
 * 
 * @author xugq
 * 
 */
public class Executor implements IExecutor {

	@Override
	public void instantiate(QuestionContent questionContent) throws Exception {
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

	@Override
	public ResultHolder test(String root, int stage, int mode, String arg)
			throws Exception {
		return new ResultHolder();
	}

	@Override
	public ResultHolder testSample(String root, int stage, int mode, int sampleId)
			throws Exception {
		return new ResultHolder();
	}

}

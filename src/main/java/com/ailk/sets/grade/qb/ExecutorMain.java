package com.ailk.sets.grade.qb;

import com.ailk.sets.grade.grade.common.GradeConst;
import com.ailk.sets.grade.grade.common.TraceManager;
import com.ailk.sets.grade.grade.execute.ResultHolder;

public class ExecutorMain {

	private static final int TYPE_UNKNOWN = -1;
	private static final int TYPE_VALIDATE = 0;
	private static final int TYPE_TEST = 1;
	private static final int TYPE_TEST_SAMPLE = 2;

	public static void main(String[] args) {
		try {
			int type = TYPE_UNKNOWN;
			String executorClass = null;
			String qRoot = null;
			int stage = -1;
			int mode = -1;
			int seq = -1;
			String arg = null;
			int sampleId = -1;

			for (int i = 0; i < args.length; i += 2) {
				String key = args[i];
				String value = args[i + 1];

				if (key.equals("-t")) {
					if (value.equals("validate")) {
						type = TYPE_VALIDATE;
					} else if (value.equals("test")) {
						type = TYPE_TEST;
					} else if (value.equals("testSample")) {
						type = TYPE_TEST_SAMPLE;
					} else {
						System.out.println("type类型不正确");
						System.exit(1);
					}
				} else if (key.equals("-e")) {
					executorClass = value;
				} else if (key.equals("-q")) {
					qRoot = value;
				} else if (key.equals("-s")) {
					stage = Integer.parseInt(value);
				} else if (key.equals("-m")) {
					mode = Integer.parseInt(value);
				} else if (key.equals("-S")) {
					seq = Integer.parseInt(value);
				} else if (key.equals("-a")) {
					arg = value;
				} else if (key.equals("-i")) {
					sampleId = Integer.parseInt(value);
				}
			}

			if (executorClass == null) {
				System.out.println("-e选项未指定");
				usage();
				System.exit(1);
			}

			if (qRoot == null) {
				System.out.println("-q选项未指定");
				usage();
				System.exit(1);
			}

			if (stage == -1) {
				System.out.println("-s选项未指定");
				usage();
				System.exit(1);
			}

			if (mode == -1) {
				System.out.println("-m选项未指定");
				usage();
				System.exit(1);
			}

			IExecutor executor = (IExecutor) Class.forName(executorClass)
					.newInstance();
			ResultHolder result = null;

			switch (type) {
			case TYPE_VALIDATE:
				if (seq == -1) {
					System.out.println("-S选项未指定");
					usage();
					System.exit(1);
				}
				result = executor.execute(qRoot, stage, mode, seq);
				break;
			case TYPE_TEST:
				if (arg == null || arg.isEmpty()) {
					System.out.println("-a选项未指定");
					usage();
					System.exit(1);
				}
				result = executor.test(qRoot, stage, mode, arg);
				break;
			case TYPE_TEST_SAMPLE:
				if (sampleId == -1) {
					System.out.println("-i选项未指定");
					usage();
					System.exit(1);
				}
				result = executor.testSample(qRoot, stage, mode, sampleId);
				break;
			case TYPE_UNKNOWN:
				System.out.println("type未指定");
				usage();
				System.exit(1);
			}

			if (result.getOut() != null)
				System.out.print(result.getOut());
			if (result.getErr() != null)
				System.err.print(result.getErr());

			if (type == TYPE_VALIDATE) {
				if (result.getCheck() != null) {
					System.out.println(GradeConst.GRADE_RESULT_BEGIN);
					System.out.println(result.getCheck());
					System.out.println(GradeConst.GRADE_RESULT_END);
				}

				System.out.println(GradeConst.GRADE_RESULT_ELAPSED
						+ result.getElapsed());
				System.out.println(GradeConst.GRADE_RESULT_MEM_BYTES
						+ result.getMemBytes());
			}

			System.exit(result.getExitValue());
		} catch (Throwable e) {
			System.out.println(TraceManager.getTrace(e));
			System.exit(1);
		}
	}

	private static void usage() {
		System.out
				.println("Usage: "
						+ ExecutorMain.class.getName()
						+ " -t [validate|test|testSample] -e executorClass -q qRoot -s stage -m mode [-S seq | -a arg | -i sampleId]");
	}

}

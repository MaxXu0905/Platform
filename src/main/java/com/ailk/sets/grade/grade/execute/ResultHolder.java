package com.ailk.sets.grade.grade.execute;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.util.IOUtils;

import com.ailk.sets.grade.grade.common.GradeConst;

public class ResultHolder {

	private int exitValue; // 退出码
	private String out; // 标准输出
	private String err; // 标准错误
	private String check; // 正确性检查串
	private long elapsed; // 经过时间
	private long memBytes; // 使用内存（字节）

	public ResultHolder() {
		exitValue = 0;
		elapsed = 0;
		memBytes = 0;
	}

	public void removeDirectory(String parent) {
		String prefix = parent + File.separator;
		int begin = prefix.length();

		if (out != null) {
			StringBuilder builder = new StringBuilder();
			String[] fields = out.split("\n");

			for (String field : fields) {
				if (field.startsWith(prefix))
					builder.append(field.substring(begin, field.length()));
				else
					builder.append(field);

				builder.append("\n");
			}

			if (!out.endsWith("\n") && builder.length() > 0)
				builder.setLength(builder.length() - 1);

			out = builder.toString();
		}

		if (err != null) {
			StringBuilder builder = new StringBuilder();
			String[] fields = err.split("\n");

			for (String field : fields) {
				if (field.startsWith(prefix))
					builder.append(field.substring(begin, field.length()));
				else
					builder.append(field);

				if (!err.endsWith("\n") && builder.length() > 0)
					builder.setLength(builder.length() - 1);

				builder.append("\n");
			}

			err = builder.toString();
		}
	}

	public int getExitValue() {
		return exitValue;
	}

	public void setExitValue(int exitValue) {
		this.exitValue = exitValue;
	}

	public String getOut() {
		return out;
	}

	public void setOut(File file) {
		getData(file, true);
	}

	public void setOut(String str) {
		out = str;
	}

	public String getErr() {
		return err;
	}

	public void setErr(File file) {
		getData(file, false);
	}

	public void setErr(String str) {
		err = str;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public long getElapsed() {
		return elapsed;
	}

	public long getMemBytes() {
		return memBytes;
	}

	public void setElapsed(long elapsed) {
		this.elapsed = elapsed;
	}

	public void setMemBytes(long memBytes) {
		this.memBytes = memBytes;
	}

	public void merge(ResultHolder result) {
		exitValue = result.exitValue;
		check = result.check;
		elapsed = result.elapsed;
		memBytes = result.memBytes;

		if (out == null)
			out = result.out;
		else if (result.out != null)
			out = out + result.out;

		if (err == null)
			err = result.err;
		else if (result.err != null)
			err = err + result.err;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((check == null) ? 0 : check.hashCode());
		result = prime * result + exitValue;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResultHolder other = (ResultHolder) obj;
		if (check == null) {
			if (other.check != null)
				return false;
		} else if (!check.equals(other.check))
			return false;
		if (exitValue != other.exitValue)
			return false;
		return true;
	}

	private void getData(File file, boolean outFlag) {
		InputStream in = null;

		try {
			in = new FileInputStream(file);
			byte[] bytes = IOUtils.toByteArray(in);
			String data = new String(bytes, GradeConst.ENCODING);

			boolean checkFlag = false;
			StringBuilder checkBuilder = new StringBuilder();
			StringBuilder builder = new StringBuilder();
			String[] fields = data.split("\n");
			for (int i = 0; i < fields.length; i++) {
				String field = fields[i];

				if (field.startsWith(GradeConst.GRADE_LOG4J_PREFIX)) {
					continue;
				} else if (field.startsWith(GradeConst.GRADE_RESULT_BEGIN)) {
					checkFlag = true;
				} else if (field.startsWith(GradeConst.GRADE_RESULT_END)) {
					checkFlag = false;
				} else if (checkFlag) {
					checkBuilder.append(field);
					checkBuilder.append("\n");
				} else if (field.startsWith(GradeConst.GRADE_RESULT_ELAPSED)) {
					field = field.substring(GradeConst.GRADE_RESULT_ELAPSED
							.length());
					elapsed = Long.parseLong(field);
				} else if (field.startsWith(GradeConst.GRADE_RESULT_MEM_BYTES)) {
					field = field.substring(GradeConst.GRADE_RESULT_MEM_BYTES
							.length());
					memBytes = Long.parseLong(field);
				} else {
					builder.append(field);
					if (i < fields.length - 1 || data.endsWith("\n"))
						builder.append("\n");
				}
			}

			if (outFlag) {
				out = builder.toString();
				check = checkBuilder.toString();
			} else {
				err = builder.toString();
			}
		} catch (IOException e) {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e1) {
				}
			}
		}
	}

}

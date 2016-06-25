package com.ailk.sets.grade.intf;


/**
 * 编程题测试应答
 * @author xugq
 *
 */
public class RunTestResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;
	private int exitValue; // 退出码
	private String out; // 标准输出
	private String err; // 标准错误

	public int getExitValue() {
		return exitValue;
	}

	public void setExitValue(int exitValue) {
		this.exitValue = exitValue;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}

}

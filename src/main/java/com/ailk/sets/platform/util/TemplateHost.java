package com.ailk.sets.platform.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

public class TemplateHost {
	public static final String VM_INVITATIONHTML = "vm/mailcontent.vm";
	public static final String VM_INVITATIONHTMLMULTI = "vm/mailcontentmulti.vm";
	public static final String VM_INVITATIONDIV = "vm/mailcontentdiv.vm";
	public static final String VM_INVITATIONDIVMULTI = "vm/mailcontentdivmulti.vm";
	public static final String VM_INVITATIONDIVMULTI2 = "vm/mailcontentdivmulti2.vm";
	public static final String VM_COOPERATION = "vm/cooperation.vm";
	public static final String VM_PUBLICTEMPLATE = "vm/publicTemplate.vm";
	private VelocityEngine vEngine;

	private VelocityContext context;
	private String destDirectory;// 生成的代码保存的目录

	public TemplateHost() {
		// 创建引擎
		vEngine = new VelocityEngine();
		// 设置模板加载路径，这里设置的是class下
		vEngine.setProperty(Velocity.RESOURCE_LOADER, "class");
		vEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		vEngine.setProperty("input.encoding", "UTF-8");
		vEngine.setProperty("output.encoding", "UTF-8");
		vEngine.setProperty("default.contentType", "text/html;charset=UTF-8");
		context = new VelocityContext();

		vEngine.init();
	}

	public String makeFileString(String vmPath) {
		// 加载模板，设定模板编码
		Template t = vEngine.getTemplate(vmPath, "utf-8");
		// 设置输出
		StringWriter writer = new StringWriter();
		// 将环境数据转化输出
		t.merge(context, writer);
		return writer.toString();
	}

	/**
	 * @return the context
	 */
	public VelocityContext getContext() {
		return context;
	}

	public void BuildFile(String templateName, String dir, String destFileName) throws Exception {
		// 加载模板，设定模板编码
		Template t = vEngine.getTemplate(templateName, "utf-8");
		// 设置输出
		StringWriter writer = new StringWriter();
		// 将环境数据转化输出
		t.merge(context, writer);
		String fileName = destDirectory + File.separator + destFileName;
		File f = new File(fileName);
		f.getParentFile().mkdirs();
		FileOutputStream output = new FileOutputStream(f);
		PrintStream pst = new PrintStream(output, true, "UTF-8");
		pst.print(writer.toString());
		output.close();
	}

	public void writeFile(String fileName, byte[] uofByte) throws Exception {
		try {
			File file = new File(destDirectory + File.separator + fileName);
			file.getParentFile().mkdirs();
			FileOutputStream output = new FileOutputStream(file);
			PrintStream pst = new PrintStream(output, true, "UTF-8");
			pst.print(new String(uofByte));
			pst.flush();
			pst.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

}

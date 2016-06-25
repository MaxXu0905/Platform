package com.ailk.sets.platform;

import java.util.UUID;

import org.apache.velocity.VelocityContext;
import org.junit.Test;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.Type;

import com.ailk.sets.platform.common.MailSenderInfo;
import com.ailk.sets.platform.util.MD5;
import com.ailk.sets.platform.util.SimpleMailSender;
import com.ailk.sets.platform.util.TemplateHost;

public class EmailTest {

	@Test
	public void sendEmail() throws Exception {
		System.out.println(MD5.toMD5("123456qwerty!"));
		// 设置邮件服务器信息
		/*MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setHost("smtp.163.com");
		mailInfo.setPort("25");
		mailInfo.setValidate(true);

		// 邮箱用户名
		mailInfo.setUserName("18601322635");
		// 邮箱密码
		mailInfo.setPassword("LMY12345678");

		// 发件人邮箱
		mailInfo.setAddress("18601322635@163.com");
		// 收件人邮箱
		mailInfo.setToAddress("bixy@asiainfo-linkage.com");
		// 邮件标题
		mailInfo.setSubject("测试Java程序发送邮件");
		
		TemplateHost templateHost = new TemplateHost();
		VelocityContext context = templateHost.getContext();
		context.put("entity", new Object());
		String pageString = templateHost.makeFileString(TemplateHost.VM_INVITATIONHTML);
		mailInfo.setContent(pageString);

		// 发送html格式
		SimpleMailSender.sendHtmlMail(mailInfo);
		System.out.println("邮件发送完毕");*/
	}
	
	@Test
	public void lookUp() throws Exception {
        
	    String domain = "163.com";
	    
	    //查询邮件交换记录
        Lookup lookup = new Lookup(domain, Type.MX);
        lookup.run();
        if (lookup.getResult() != Lookup.SUCCESSFUL){
            System.out.println("ERROR: " + lookup.getErrorString());
            return;
        }
        Record[] answers = lookup.getAnswers();
        for(Record rec : answers){
            System.out.println(rec.toString());
        }

        domain = "www.101test.com";
        //查询域名对应的IP地址
        lookup = new Lookup(domain, Type.A);
        lookup.run();
        if (lookup.getResult() != Lookup.SUCCESSFUL){
            System.out.println("ERROR: " + lookup.getErrorString());
            return;
        }
        answers = lookup.getAnswers();
        for(Record rec : answers){
            System.out.println(rec.toString());
        }
    }

}

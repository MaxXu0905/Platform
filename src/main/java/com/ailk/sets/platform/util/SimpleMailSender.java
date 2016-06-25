package com.ailk.sets.platform.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Type;

import com.ailk.sets.platform.common.MailSenderInfo;
import com.sun.mail.smtp.SMTPMessage;

/**
 * 负责邮件发送的类
 * 
 * @author 毕希研
 * 
 */
public class SimpleMailSender {
	private Logger logger = LoggerFactory.getLogger(SimpleMailSender.class);
	public boolean sendTextMail(MailSenderInfo mailInfo) {
		// 判断是否需要身份认证
		MailAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MailAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			
			//设置自定义发件人昵称  
	        String nick="";  
	        try {  
	            nick = MimeUtility.encodeText("百一测评","UTF-8","B");    
	        } catch (Exception e) {  
	        }   
			// 设置邮件消息的发送者
			mailMessage.setFrom(new InternetAddress(nick+" <"+mailInfo.getAddress()+">"));
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address[] to = { new InternetAddress(mailInfo.getToAddress()) };
			mailMessage.setRecipients(Message.RecipientType.TO, to);
			// 设置邮件消息的主题
			String subject = mailInfo.getSubject();
			try {  
		      subject = MimeUtility.encodeText(subject,"UTF-8","B");    
            } catch (Exception e) {  
            }  
			mailMessage.setSubject(subject);
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// 设置邮件消息的主要内容
			String mailContent = mailInfo.getContent();
			mailMessage.setText(mailContent);
			// 发送邮件
			Transport transport = sendMailSession.getTransport();
			transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
//			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static SMTPMessage sendHtmlMail(MailSenderInfo mailInfo) throws MessagingException {
		// 判断是否需要身份认证
		MailAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		// 如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate()) {
			authenticator = new MailAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getInstance(pro, authenticator);
		// 根据session创建一个邮件消息
		SMTPMessage mailMessage = new SMTPMessage(sendMailSession);
		
		//设置自定义发件人昵称  
        String nick="";  
        try {  
            nick = MimeUtility.encodeText("百一测评","UTF-8","B");  
        } catch (Exception e) {  
        }
        
        // 设置邮件消息的发送者
        mailMessage.setFrom(new InternetAddress(nick+" <"+mailInfo.getAddress()+">"));
		// 设置邮件消息的发送者
//		mailMessage.addFrom(InternetAddress.parse(mailInfo.getAddress()));
		// 创建邮件的接收者地址，并设置到邮件消息中
		Address to = new InternetAddress(mailInfo.getToAddress());
		// Message.RecipientType.TO属性表示接收者的类型为TO
		mailMessage.setRecipient(Message.RecipientType.TO, to);
        // 设置邮件消息的主题
        String subject = mailInfo.getSubject();
        try {  
          subject = MimeUtility.encodeText(subject,"UTF-8","B");    
        } catch (Exception e) {  
        }  
        mailMessage.setSubject(subject);
		// 设置邮件消息发送的时间
		mailMessage.setSentDate(new Date());
		// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
		Multipart mainPart = new MimeMultipart();
		// 创建一个包含HTML内容的MimeBodyPart
		BodyPart html = new MimeBodyPart();
		// 设置HTML内容
		html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
		mainPart.addBodyPart(html);
		// 将MiniMultipart对象设置为邮件内容
		mailMessage.setContent(mainPart);
		// 发送邮件
		Transport.send(mailMessage);
		// mailMessage.g
		return mailMessage;
	}
	
	public boolean isValidMail(String email){
		
		boolean valid = true;
		try{
			String suffixMail = email.substring(email.indexOf("@") + 1);
			int lookNumber = 0;
			while (true) {
				if(lookNumber > 1)
					break;
				lookNumber++;
				Lookup lookup = new Lookup(suffixMail, Type.MX);
				lookup.run();
				switch (lookup.getResult()) {
				case Lookup.SUCCESSFUL:
					System.out.println("suc");
					break;
				case Lookup.TRY_AGAIN:
					System.out.println("TRY_AGAIN");
					valid = false;
					continue;
				default:
					valid = false;
					logger.error("Fail: " + lookup.getErrorString());
					break;
				}
				return valid;
			}
		}catch(Exception e){
			logger.error("error check email " + email , e);
		}
		return valid;
	}
	
	public static void main(String[] args) {
		SimpleMailSender a = new SimpleMailSender();
		System.out.println(a.isValidMail("dd@dsfdsfdsgasfab.com"));
	}
}

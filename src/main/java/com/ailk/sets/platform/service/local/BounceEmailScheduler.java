package com.ailk.sets.platform.service.local;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ailk.sets.platform.util.HibernateUtils;

public class BounceEmailScheduler implements Runnable {
	private Logger logger = LoggerFactory.getLogger(BounceEmailScheduler.class);

	private Store store;

	private static final int sleepTime = 60 * 1000;

	// public void init() {
	// try {
	// MailReceiverInfo mailReceiverInfo = new MailReceiverInfo();
	// mailReceiverInfo.setHost(configSysParamDao.getConfigParamValue(SERVER));
	// mailReceiverInfo.setAddress(configSysParamDao.getConfigParamValue(ADDRESS));
	// mailReceiverInfo.setUserName(configSysParamDao.getConfigParamValue(USERNAME));
	// mailReceiverInfo.setPassword(configSysParamDao.getConfigParamValue(PASSWORD));
	// mailReceiverInfo.setPort(configSysParamDao.getConfigParamValue(PORT));
	//
	// if ("imap".equals(PROTOCOL)) {
	// Session session = Session.getInstance(mailReceiverInfo.getImapProperties());
	// store = session.getStore(PROTOCOL);
	// } else if ("pop3".equals(PROTOCOL)) {
	// Session session = Session.getInstance(mailReceiverInfo.getPop3Properties());
	// store = session.getStore(PROTOCOL);
	// } else
	// logger.error("init BounceEmailScheduler failed, unsupport protocol " + PROTOCOL);
	// } catch (PFDaoException e) {
	// logger.error("error init bounceEmailScheduler ", e);
	// } catch (NoSuchProviderException e) {
	// logger.error("error get provider", e);
	// }
	// }

	public void run() {
		SessionFactory sessionFactory = HibernateUtils.getSessionfactory();
		Session session = sessionFactory.openSession();

		int timeLimit = 60;
		try {
			timeLimit = getTimeLimit(session);
		} catch (Exception e) {
			logger.error("get time to check bounce mail time limit failed!", e);
		}
		try {
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_WRITE);
			Message[] messages = folder.getMessages();
			for (Message msg : messages) {
				// 分析是否为退件,并且还没有别看过,如果是退件则做分析处理
				if (isNotSeened(msg) && isBounceMail(msg)) {
					String address = getRecipientFromAttach(msg);
					if (address == null)
						address = getRecipientFromContent(msg);
//					Invitation invitation = inviteDao.getInvitation(address);
//					if (invitation != null) {
//						// 如果是邀请成功的，则更新为失败
//						invitation.setInvitationState(0);
//						inviteDao.update(invitation);
//						logger.error("invitation " + invitation.getInvitationId() + " set failed");
//					}
					msg.setFlag(Flag.SEEN, true);
				}
			}
		} catch (MessagingException e) {
			logger.error("BounceEmailScheduler scheduling error , " + e);
		}
	}

	/**
	 * 判断是否是退件
	 * 
	 * @param msg
	 * @return
	 */
	private boolean isBounceMail(Message msg) {

		return true;
	}

	private boolean isNotSeened(Message msg) throws MessagingException {
		for (Flag flag : msg.getFlags().getSystemFlags())
			if (flag.equals(Flag.SEEN))
				return false;
		return true;
	}

	/**
	 * 从附件中获取收件人
	 * 
	 * @param msg
	 * @return
	 */
	private String getRecipientFromAttach(Message msg) {
		String address = null;
		return address;
	}

	/**
	 * 从邮件内容中获取收件人
	 * 
	 * @param msg
	 * @return
	 */
	private String getRecipientFromContent(Message msg) {
		String address = null;
		return address;
	}

	public int getTimeLimit(Session session) {
		try {
			String sql = "select param_value from config_sys_parameters where param_ename ='TIME_TO_CHECK_FAILED_MAIL'";
			SQLQuery query = session.createSQLQuery(sql);
			@SuppressWarnings("unchecked")
			List<Object> result = query.list();
			return Integer.parseInt(result.get(0).toString());
		} catch (Exception e) {
			logger.error("error get time limit", e);
		}
		return 60;
	}

//	public List<Invitation> getInvitations(Session session, int timeLimit) {
//		try {
//			String sql = "select param_value from config_sys_parameters where param_ename ='TIME_TO_CHECK_FAILED_MAIL'";
//			SQLQuery query = session.createSQLQuery(sql);
//			@SuppressWarnings("unchecked")
//			List<Object> result = query.list();
//			return query.list();
//		} catch (Exception e) {
//			logger.error("error get time limit", e);
//		}
//	}

	public static void main(String[] args) {
		// SessionFactory sessionFactory = HibernateUtils.getSessionfactory();
		// Session session = sessionFactory.openSession();
		// BounceEmailScheduler bs = new BounceEmailScheduler();
		// System.out.println(bs.getTimeLimit(session));
		Timestamp t = new Timestamp(System.currentTimeMillis());
		Calendar c = Calendar.getInstance();
		c.setTime(t);
		System.out.println(t);
		c.add(Calendar.MINUTE, -60);
		System.out.println(c.getTime());
	}
}

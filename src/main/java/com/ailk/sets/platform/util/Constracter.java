package com.ailk.sets.platform.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constracter {
	private static Logger logger = LoggerFactory
			.getLogger(DegreeSkillLabelUtils.class);

	/**
	 * 计算文档相似度
	 * 
	 * @param doci
	 *            准备比较的文档
	 * @param docj
	 *            样例文档
	 * @return
	 * @throws InterruptedException
	 */

	public static double calculateSimilary(Document doci, Document docj) {
		Map<String, Integer> ifreq = doci.documentFreq();// 文档词项词频
		Map<String, Integer> jfreq = docj.documentFreq();

		double ijSum = 0;
		Iterator<Entry<String, Integer>> it = ifreq.entrySet().iterator();
		// while(it.hasNext()){
		// String tem = it.next().getKey();
		// if(!jfreq.containsKey(tem))//如果文本向量2不包含这个词则加入0
		// jfreq.put(tem, 0);
		// }
		while (it.hasNext()) {
			Map.Entry<String, Integer> entry = it.next();
			if (jfreq.containsKey(entry.getKey())) {
				double iw = weight(entry.getValue());
				double jw = weight(jfreq.get(entry.getKey()));
				ijSum += (iw * jw);
			}
		}

		double iPowSum = powSum(doci);
		double jPowSum = powSum(docj);
		double similary = ijSum / (iPowSum * jPowSum);
		logger.debug("the similary is {} ", similary);

		return similary;
	}

	/**
	 * 向量的模
	 * 
	 * @param document
	 * @return
	 */
	private static double powSum(Document document) {
		Map<String, Integer> mapfreq = document.documentFreq();
		Collection<Integer> freqs = mapfreq.values();

		double sum = 0;
		for (int f : freqs) {
			double dw = weight(f);
			sum += Math.pow(dw, 2);
		}

		return Math.sqrt(sum);
	}

	/**
	 * 计算词项特征值
	 * 
	 * @param wordfreq
	 * @return
	 */
	private static double weight(float wordfreq) {// 平方根。
		return Math.sqrt(wordfreq);
	}

	public static void main(String[] args) throws InterruptedException {
		String driver = "com.mysql.jdbc.Driver";

		// URL指向要访问的数据库名scutcs
		String url = "jdbc:mysql://10.1.249.34:39306/devsetsdb1";
		String user = "devsetsusr1";
		String password = "devsetsusr1";

		ArrayList<String> a = new ArrayList<String>();
		ArrayList<String> b = new ArrayList<String>();
		try {
			// 加载驱动程序
			Class.forName(driver);
			// MySQL配置时的密码

			Connection conn = DriverManager.getConnection(url, user, password);

			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");

			// statement用来执行SQL语句
			Statement statement = conn.createStatement();

			// 要执行的SQL语句
			String sql = "select question_desc from qb_question where question_type='s_choice' limit 1000,10";// 题库里边有10道题

			// 结果集
			long t1 = System.currentTimeMillis();
			ResultSet rs = statement.executeQuery(sql);
			// String name = null;
			long t2 = System.currentTimeMillis();
			System.out.println("查询数据库用时：" + (t2 - t1));
			while (rs.next()) {
				// 选择sname这列数据
				String name = rs.getString("question_desc");
				a.add(name);

				// 首先使用ISO-8859-1字符集将name解码为字节序列并将结果存储新的字节数组中。
				// 然后使用GB2312字符集解码指定的字节数组
				// name = new String(name.getBytes("ISO-8859-1"),"GB2312")
				// 输出结果
				// System.out.println(rs.getString("sno") + "\t" + name);
			}
			sql = "select question_desc from qb_question where question_type='s_choice' limit 1010,10";
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				// 选择sname这列数据
				String name = rs.getString("question_desc");
				b.add(name);

				// 首先使用ISO-8859-1字符集将name解码为字节序列并将结果存储新的字节数组中。
				// 然后使用GB2312字符集解码指定的字节数组
				// name = new String(name.getBytes("ISO-8859-1"),"GB2312");

				// 输出结果
				// System.out.println(rs.getString("sno") + "\t" + name);
			}
			rs.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();

		} catch (SQLException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}
		// int size = a.size();
		long ll1 = System.currentTimeMillis();
		// if(size>=2)
		System.out.println(a.size() + "\t" + b.size());
		int c = 0;
		for (int j = 0; j < a.size(); j++) {// a里边有10个题

			int i;
			for (i = 0; i < b.size(); i++) {// b里面开始有10个题，a和b比对，相似拿出来，不相似，插入。对比i次
				Document document1 = new DocumentImpl(a.get(j));
				Document document2 = new DocumentImpl(b.get(i));
				// System.out.println("docA:"+document1+"\ndocB:"+document2);
				// long startTime = System.currentTimeMillis();
				double dd = Constracter.calculateSimilary(document1, document2);
				c++;
				if (dd > 2) {
					System.out.println("docA:" + document1 + "\ndocB:"
							+ document2 + "\n相似度：" + dd + "不能插入\n");
					break;
				}
				// long endTime = System.currentTimeMillis();
				// System.out.println("\t用时" + (endTime - startTime));
			}
			if (i == b.size())
				b.add(a.get(j));
		}
		long ll2 = System.currentTimeMillis();
		System.out.println("对比用时：" + (ll2 - ll1));
		System.out.println("对比了" + c + "次");
		// long startTime = System.currentTimeMillis();
		// System.out.println(Constracter.calculateSimilary(d3, d4));
		// long endTime = System.currentTimeMillis();
		// System.out.println("用时" + (endTime - startTime));
		// startTime = System.currentTimeMillis();
		// System.out.println(Constracter.calculateSimilary(d1, d2));
		// ;
		// endTime = System.currentTimeMillis();
		// System.out.println("用时" + (endTime - startTime));
		//
		// startTime = System.currentTimeMillis();
		// System.out.println(Constracter.calculateSimilary(d1, d3));
		// endTime = System.currentTimeMillis();
		// System.out.println("用时" + (endTime - startTime));
		// while(true){
		// Thread.sleep(15000);
		// }
	}

}
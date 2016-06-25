package com.ailk.sets.platform.intf.common;

public class Constants {
    
    public static final String CHARSET_UTF8 = "UTF-8"; // 字符集常量
    
    public static final int POSITIVE = 1; // 正向的常量 1
    public static final int NEGATIVE = 0; // 反向的常量 0
    
	public static final int PASSPORTLENGTH = 10;
	public static final int SESSIONTICKETLENGTH = 32;
	public static final int COMPANYID = 1;
	public static final String PAPER_TOTAL_MINUTES = "PAPER_TOTAL_MINUTES";// 试卷整体时间
//	public static final String QUESTION_TYPE_EXTRA = "extra";
	//试卷列表按如下几部分统计
	public static final String QUESTION_TYPE_OBJECT = "type_object";//客观
	public static final String QUESTION_TYPE_INTERVIEW = "type_interview";//面试
	public static final String QUESTION_TYPE_SUBJECT = "type_subject";//编程
	public static final String QUESTION_TYPE_BUSINESS = "type_business";//业务
	public static final String QUESTION_TYPE_INTELLIGE ="type_intellige";//智力
	public static final String QUESTION_TYPE_ESSAY = "type_essay";//问答题
	public static final int INTERVIEW_QBID = 800;
//	public static final String QUESTION_TYPE_INTERVIEWGRP = "iviewgrp";
	public static final int QUESTION_DERIVE_FLAG_YES = 1;// 变型题
	public static final int QUESTION_IS_SAMPLE = 1;// 试答题

	// public static final int PAPER_VERSION = 1;
	public static final int PAPER_DEPTH_FROM_QUESTION = 2;
	public static final int POSITION_LEVEL_SERIES_ID = 11;

	//
	public static final String EMAIL = "EMAIL";
	public static final String FULL_NAME = "FULL_NAME";
	public static final String ADDRESS = "RESIDENT_ADDRESS";
	public static final String COLLEGE = "GRADUATE_COLLEGE";
	public static final String PHONE = "PHONE";
	public static final String INTENTION_POSITION = "INTENTION_POSITION";
	public static final String ADDRESS_TYPE = "address";
	public static final String[] DIFFICULTY_LEVEL = { "EASY", "MEDIUM", "HARD" };
    public static final String DIFFICULTY_LEVEL_EASY = "EASY";
    public static final String DIFFICULTY_LEVEL_MEDIUM = "MEDIUM";
    public static final String DIFFICULTY_LEVEL_HARD ="HARD";
    
    
	public static final String SEARCHNAME = "\\$\\{searchName\\}";

	public static final String QB_ID = "QB_ID";
	public static final String SK_ID = "SK_ID";
	public static final String QID = "QID";

	public static final int SELF_QB_ID = 90000;
	public static final int TEST_PART_TIME = 9999;

	public static final int CATEGORY_SKILL = 1;// 技术
	public static final int CATEGORY_BUSI = 2;// 业务
	public static final int CATEGORY_INTE = 3;// 智力
	public static final int CATEGORY_INTER = 4;// 面试
	public static final int CATEGORY_PAPER = 5; // 试卷类

	
	public static final String CONFIG_QUESTION_TYPE_NAME ="QUESTION_TYPE_NAME";
	public static final String CONFIG_PAPER_PART = "PAPER_PART";
	
	
	public static final String QUESTION_TYPE_NAME_S_CHOICE = "s_choice"; // 单选
	public static final String QUESTION_TYPE_NAME_M_CHOICE = "m_choice"; // 多选
	public static final String QUESTION_TYPE_NAME_PROGRAM = "program"; // 编程
	public static final String QUESTION_TYPE_NAME_ESSAY = "essay"; // 问答题
	public static final String QUESTION_TYPE_NAME_TEXT = "extra_program"; // 文本
	public static final String QUESTION_TYPE_NAME_INTERVIEW = "video"; // 面试
	public static final String QUESTION_TYPE_NAME_GROUP  = "group";
	public static final String QUESTION_TYPE_NAME_S_CHOICE_PLUS = "s_choice_plus"; // 单选+文本
	public static final String QUESTION_TYPE_NAME_M_CHOICE_PLUS = "m_choice_plus"; // 多选+文本、
	
	//获取题库每种类型统计数  题库列表使用
	public static final String QB_QUESTIONS_SKILL_OBJECT ="object";//选择题
	public static final String QB_QUESTIONS_SKILL_SUBJECT="subject";//编程题
	public static final String QB_QUESTIONS_SKILL_ESSAY="essay";//问答题
	public static final String QB_QUESTIONS_SKILL_VIDEO="viedo";//面试
	public static final String QB_QUESTIONS_SKILL_INTE="INTE";//智力
	
	public static final String SCHOOL_LANGUAGE_JAVA = "java";
	public static final String SCHOOL_LANGUAGE_CADD = "c++";

	public static final String COMMON_BR = "<br>";
	
	public static final int QUES_DISCARD = 9;
	
	//类型 1社招，2校招
	public static final int TEST_TYPE_CLUB = 1;
	public static final int TEST_TYPE_SCHOOL =2;
	//预计答题时间
	public static final String CONFIG_SYS_OBJECT_AVG_TIME = "objectAvgTime";
	public static final String CONFIG_SYS_SUBECT_AVG_TIME = "subjectAvgTime";
	//选择试答题
	public static final long  TEST_OBJECT_QUESTION_ID = 100000000000001l;
	public static final long  TEST_SUBJECT_QUESTION_ID =100000000090001l;
	//是否预制体
	public static final int PREBUILT_SELF = 0;//自定义
	public static final int PREBUILT_SYS = 1;//预制
	//职位状态
	public static final int POSITION_STATE_NORMAL = 0;//正常
    public static final int POSITION_STATE_INVLIAD = 1;//失效
    
    //题目状态
    public static final int STATE = 1;
    
    //校招社招推送消息个数
    public static final int POS_MSG_NUM_SCHOOL = 8;
    public static final int POS_MSG_NUM_CLOB = 4;
    
    //校招positionseriesId 
    public static final int POSITION_SEREIS_ID_SCHOOL = 99;
    
    //体验公司id
    public static final int SAMPLE_COMPANY_ID = 1;
    public static final int SAMPLE_EMPLOYER_ID = -1;//体验employerId
    
    
    public static final int SAMPLE_ACCT_TYPE = 3;//体验或者第三方账号类型
    public static final int NORMAL_ACCT_TYPE = 2;//正常账号类型
    
    public static final int POSITION_TEST_SAMPLE = 1;//测评或者邀请是否为样例
    
   /** config_sys_parameters表测速App常量 **/
   public static final String SPEED_TEST_DOWNLOAD_URL = "SPEED_TEST_DOWNLOAD_URL"; //下载url
   public static final String SPEED_TEST_DOWNLOAD_HOST = "SPEED_TEST_DOWNLOAD_HOST"; //下载主机
   public static final String SPEED_TEST_DOWNLOAD_PORT = "SPEED_TEST_DOWNLOAD_PORT"; //下载端口
   public static final String SPEED_TEST_DOWNLOAD_PATH = "SPEED_TEST_DOWNLOAD_PATH"; //下载路径
   public static final String SPEED_TEST_UPLOAD_URL = "SPEED_TEST_UPLOAD_URL"; //上传url
   public static final String SPEED_TEST_FORMULA_NUM = "SPEED_TEST_FORMULA_NUM"; //公式常量值
   public static final String SPEED_TEST_NEED_TO_KNOW = "SPEED_TEST_NEED_TO_KNOW"; //公式常量值
   
   public static final String APP_ID_MSG = "APP_ID_MSG";
   public static final int  APP_STATUS_NORMAL = 1; //正常
   public static final int  APP_STATUS_NO_SIGNAL = 2;//有网络，没有信号
   public static final int  APP_STATUS_DEAD = -1; //没有心跳
   
   public static final int GROUP_FLAG_NORMAL = 0; //普通测评
   public static final int GROUP_FLAG_PARENT = 1;//组测评
   public static final int GROUP_FLAG_CHILD = 2;//子测评
   
   public static final int POSITION_RELATION_MUST = 1;//必考
   public static final int POSITION_RELATION_CHOOSE = 2;//选考
    
}

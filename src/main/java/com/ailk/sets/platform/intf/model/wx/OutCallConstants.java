package com.ailk.sets.platform.intf.model.wx;

public class OutCallConstants {
	    public static final int SUCCESS_STATUS = 1;
	    
	    public static final int OUT_STATUS_SUCCESS = 0;
	    public static final int OUT_STATUS_ERROR = 1;
	   //职酷passcode
	    public static final String MRJOB_PASSCODE = "MRJOB_PASSCODE";
	    //职酷获取tocken Url
		public static final String MRJOB_GET_EMPLOYER_URL="MRJOB_GET_EMPLOYER_URL";// = "http://ailkjobs.cnstaff.com/interface/webservices/fortest.php?method=get_token_info&passcode={0}&token={1}";
		//更新职酷报告状态
		public static final String MRJOB_UPDATE_STATUS="MRJOB_UPDATE_STATUS";// = "http://ailkjobs.cnstaff.com/interface/webservices/fortest.php?method=update_status&passcode={0}";
		//向职酷推送报告
		public static final String MRJOB_SET_REPORT="MRJOB_SET_REPORT";//= "http://ailkjobs.cnstaff.com/interface/webservices/fortest.php?method=set_report&passcode={0}";
		//查看报告
		public static final String VIEW_REPORT="VIEW_REPORT";
		//下载报告
		public static final String DOWNLOAD_REPORT="DOWNLOAD_REPORT";
		
		 //沃百业兴获取tocken Url
		public static final String WBYX_GET_EMPLOYER_URL="WBYX_GET_EMPLOYER_URL";// = "http://ailkjobs.cnstaff.com/interface/webservices/fortest.php?method=get_token_info&passcode={0}&token={1}";
	

}

package com.ailk.sets.platform.intf.model.invatition;

public class InvitationState {
	//已邀请状态
	public static final int NOT_ANSWERED = 4;//未答题
	public static final int ANSWERING = 8;//正在答题
	public static final int ANSWER_FINISHED = 12;//答题完毕
	public static final int WAIT_PROCESS = 16;//待处理
	public static final int RECOMMEND = 20;//已推荐
	public static final int WEED_OUT = 24;//已淘汰
	public static final int EXPIRED = 28;//邀请失效
	
	
	public static final int INVITATION_STATE_NORMAL = 1;
	public static final int INVITATION_STATE_FINISHED1 = 2;
	public static final int INVITATION_STATE_FINISHED2 = 3;
	
	public static final int INVITATION_STATE_REPORT1 = 34;
	public static final int INVITATION_STATE_REPORT2 = 35;

	//待处理  已推荐 已淘汰
	public static final int CANDIDATE_TEST_RESULT0 = 0;//
	public static final int CANDIDATE_TEST_RESULT1 = 1 ;
	public static final int CANDIDATE_TEST_RESULT2 = 2;
	public static final int CANDIDATE_TEST_RESULT3 = 3;//复式
}

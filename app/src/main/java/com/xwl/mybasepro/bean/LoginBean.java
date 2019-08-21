package com.xwl.mybasepro.bean;

import com.xwl.mybasepro.base.BaseBean;

public class LoginBean extends BaseBean {

	public StudenInfo data;

	public class StudenInfo {
		public long id;
		public int sex;
		public String cnName;
		public String enName;
		public String phone;
		public int isUse;
		public String birDate;
		public String portrait;
		public String creator;
		public String updater;
		public String createTime;
		public String lastLoginTime;
		public int traceId;
		public int channelId;
		public int status;
		public int totalLessonCnt;
		public int mvpCnt;
		public int awardCnt;
		public int hasLessonCnt;
		public int refundLessonCnt;
		public String deviceRemark;
		public int isPt;
		public int level;
		public int ptSmsCnt;
		public int ptSmsReceipt;
		public int vip;
		public String accessToken;
		public int age;
		public int isCompleteInfo;
		public String firstLoginTime;
		public int firstCouponStatus;
		public int achievePointCnt;
	}
}
package com.xwl.mybasepro.http;

public class Protocol {

	// 学生登录-根据短信验证码
	public static String Post_stuGetTokenByPhoneAndSms = "/ac-common/oauth/sms/stu";

	// 获取预配置信息
	public static String GET_getByAppId = "/ac-client/profile";

	// 上传用户应用列表
	public static String Post_GetUserApps = "/ac-client/user-apps/save";


}
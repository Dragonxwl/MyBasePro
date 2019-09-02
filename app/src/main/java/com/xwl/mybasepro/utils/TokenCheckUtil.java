package com.xwl.mybasepro.utils;

import com.xwl.mybasepro.base.Application;

import java.util.Arrays;
import java.util.List;

import static com.xwl.mybasepro.utils.HostUtil.GetHost;

public class TokenCheckUtil {
	public static String[] noToken;

	public static List<String> noTokenList;

	public static void setNotNeedTokenUrls() {
		// 初始化 不需要token的接口
		noToken = new String[]{
				"/ac-client/profile/8/" + Application.getAppVersionCode(Application.getContext()) +
						"?channelName=" + StringUtils.getChannelName(Application.getContext()),
				"/ac-common/oauth/sms/stu"};
		noTokenList = Arrays.asList(noToken);
	}

	public static boolean isNeedToken(String url) {
		if (noTokenList.contains(url.replace(GetHost(),""))) {
			return false;
		}
		return true;
	}
}

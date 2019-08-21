package com.xwl.mybasepro.config;

import com.xwl.mybasepro.base.Application;
import com.xwl.mybasepro.utils.SharePreferenceDataUtil;

public class ACConfig {
	private static ACConfig instance = null;

	public static ACConfig getInstance() {
		if (null == instance) {
			instance = new ACConfig();
		}
		return instance;
	}

	// 字段定义
	private static final String ACCESS_TOKEN = "accessToken";//token
	private static final String DEBUG = "Debug";//是否调试模式
	private static final String UNINIT_LOG = "uninitLog";//本地日志

	public String getAccessToken() {
		String accessToken = SharePreferenceDataUtil.getSharedStringData(Application.getContext(), ACCESS_TOKEN);
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		SharePreferenceDataUtil.setSharedStringData(Application.getContext(), ACCESS_TOKEN, accessToken);
	}

	public boolean getDebug() {
		boolean Debug = SharePreferenceDataUtil.getSharedBooleanData(Application.getContext(), DEBUG);
		return Debug;
	}

	public void setDebug(boolean Debug) {
		SharePreferenceDataUtil.setSharedBooleanData(Application.getContext(), DEBUG, Debug);
	}

	public String getUninitLog() {
		String uninitLog = SharePreferenceDataUtil.getSharedStringData(Application.getContext(), UNINIT_LOG);
		return uninitLog;
	}

	public void setUninitLog(String uninitLog) {
		SharePreferenceDataUtil.setSharedStringData(Application.getContext(), UNINIT_LOG, uninitLog);
	}
}
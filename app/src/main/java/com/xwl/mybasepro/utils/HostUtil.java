package com.xwl.mybasepro.utils;

import com.xwl.mybasepro.config.CustomerConfig;
import com.xwl.mybasepro.utils.http.okhttp.HeaderInterceptor;
import com.xwl.mybasepro.utils.http.okhttp.RetrofitClient;

public class HostUtil {
	public static String NOW_HOST_STU = CustomerConfig.HOST_STU;

	public static void SetHost(int i) {
		switch (i) {
			case 1:
				NOW_HOST_STU = CustomerConfig.HOST_STU;
				break;
			case 2:
				NOW_HOST_STU = CustomerConfig.HOST_STU_G;
				break;
			case 3:
				NOW_HOST_STU = CustomerConfig.HOST_STU_T;
				break;
			case 4:
				NOW_HOST_STU = CustomerConfig.HOST_STU_T2;
				break;
		}
		// okhttp 初始化
		RetrofitClient.getInstance().init(GetHost(), new HeaderInterceptor());
	}

	public static String GetHost() {
		return NOW_HOST_STU;
	}
}

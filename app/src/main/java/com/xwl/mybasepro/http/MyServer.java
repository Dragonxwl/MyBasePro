package com.xwl.mybasepro.http;

import com.google.gson.Gson;
import com.xwl.mybasepro.base.Application;
import com.xwl.mybasepro.base.BaseBean;
import com.xwl.mybasepro.bean.LoginBean;
import com.xwl.mybasepro.bean.ProfileResultBean;

import org.json.JSONObject;

import java.util.Map;

public class MyServer {

	/**
	 * 接口用途：获取预配置信息
	 */
	public static Object GET_getByAppId(Map<String, String> params) {
		String response = HttpUtilsHttpURLConnection.HttpGet(Application.GetHost()
				+ Protocol.GET_getByAppId + "/8/" + Application.getAppVersionCode(Application.getContext()), params);

		if (response != null) {
			try {
				Gson gson = new Gson();
				return gson.fromJson(response, ProfileResultBean.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return response;
	}

	/**
	 * 接口用途：新增日志
	 */
	public static Object Post_stuGetTokenByPhoneAndSms(JSONObject params) {
		String response = HttpUtilsHttpURLConnection.HttpPost(Application.GetHost()
				+ Protocol.Post_stuGetTokenByPhoneAndSms, params);

		if (response != null) {
			try {
				Gson gson = new Gson();
				return gson.fromJson(response, LoginBean.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return response;
	}

	/**
	 * 接口用途：新增日志
	 */
	public static Object Post_GetUserApps(JSONObject params) {
		String response = HttpUtilsHttpURLConnection.HttpPost(Application.GetHost()
				+ Protocol.Post_GetUserApps, params);

		if (response != null) {
			try {
				Gson gson = new Gson();
				return gson.fromJson(response, BaseBean.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return response;
	}

}
package com.xwl.mybasepro.utils.http;

import com.google.gson.Gson;
import com.xwl.mybasepro.base.Application;
import com.xwl.mybasepro.base.BaseBean;
import com.xwl.mybasepro.bean.LoginBean;
import com.xwl.mybasepro.bean.ProfileResultBean;

import org.json.JSONObject;

import java.util.Map;

import static com.xwl.mybasepro.utils.HostUtil.GetHost;

public class MyServer {

	/**
	 * 接口用途：获取预配置信息
	 */
	public static Object GET_getByAppId(Map<String, String> params) {
		String response = HttpUtilsHttpURLConnection.HttpGet(GetHost()
				+ Protocol.GET_getByAppId + "/8/" + Application.getAppVersionCode(Application.getContext()), params);

		try {
			Gson gson = new Gson();
			return gson.fromJson(response, ProfileResultBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 接口用途：新增日志
	 */
	public static Object Post_stuGetTokenByPhoneAndSms(JSONObject params) {
		String response = HttpUtilsHttpURLConnection.HttpPost(GetHost()
				+ Protocol.Post_stuGetTokenByPhoneAndSms, params);

		try {
			Gson gson = new Gson();
			return gson.fromJson(response, LoginBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 接口用途：上传用户安装的非系统app
	 */
	public static Object Post_GetUserApps(JSONObject params) {
		String response = HttpUtilsHttpURLConnection.HttpPost(GetHost()
				+ Protocol.Post_GetUserApps, params);

		try {
			Gson gson = new Gson();
			return gson.fromJson(response, BaseBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

}
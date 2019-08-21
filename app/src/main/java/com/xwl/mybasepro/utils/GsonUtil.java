package com.xwl.mybasepro.utils;

import com.google.gson.Gson;

/**
 * Created by shengf on 2017/12/13.
 */

public class GsonUtil {
	//将Json数据解析成相应的映射对象
	public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
		Gson gson = new Gson();
		T result = gson.fromJson(jsonData, type);
		return result;
	}

	/**
	 * 对象转换成json字符串
	 *
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		try {
			Gson gson = new Gson();
			return gson.toJson(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
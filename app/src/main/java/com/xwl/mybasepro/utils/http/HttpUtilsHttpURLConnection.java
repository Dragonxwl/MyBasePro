package com.xwl.mybasepro.utils.http;

import android.text.TextUtils;

import com.xwl.mybasepro.config.ACConfig;
import com.xwl.mybasepro.utils.TokenCheckUtil;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Xiang on 2018/5/30.
 */

public class HttpUtilsHttpURLConnection {

	/*
	 * Post 请求
	 * urlStr:网址
	 * parms：提交数据
	 * return:网页源码
	 * */
	public static String HttpPost(String urlStr, JSONObject parms) {
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setReadTimeout(5000);
			connection.setConnectTimeout(5000);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Charset", "UTF-8");
			// 设置文件类型:
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			//设置token
			if (TokenCheckUtil.isNeedToken(urlStr)) {
				connection.setRequestProperty("access-token",
						ACConfig.getInstance().getAccessToken());
			}
			// 设置接收类型否则返回415错误
			connection.setRequestProperty("accept", "application/json");

			String Json = parms.toString();

			// 往服务器里面发送数据
			if (Json != null && !TextUtils.isEmpty(Json)) {
				byte[] writebytes = Json.getBytes();
				// 设置文件长度
				connection.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
				OutputStream outputStream = connection.getOutputStream();
				outputStream.write(Json.getBytes());
				outputStream.flush();
				outputStream.close();
			}

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection
						.getInputStream()));
				String temp;
				while ((temp = reader.readLine()) != null) {
					sb.append(temp);
				}
				reader.close();
			}

			connection.disconnect();
		} catch (Exception e) {
			return e.toString();
		}

		return sb.toString();
	}

	/*
	 * Get 请求
	 * urlStr:网址
	 * parms：提交数据
	 * return:网页源码
	 * */
	public static String HttpGet(String urlStr, Map<String, String> parms) {
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(urlStr + MapToGetStr(parms));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(5000);
			connection.setConnectTimeout(5000);
			//设置token
			if (TokenCheckUtil.isNeedToken(urlStr)) {
				connection.setRequestProperty("access-token",
						ACConfig.getInstance().getAccessToken());
			}

			int code = connection.getResponseCode();
			if (code == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection
						.getInputStream()));
				String temp;
				while ((temp = reader.readLine()) != null) {
					sb.append(temp);
				}
				reader.close();
			}

			connection.disconnect();
		} catch (Exception e) {
			return e.toString();
		}

		return sb.toString();
	}

	/**
	 * 将map转换成key1=value1&key2=value2的形式
	 *
	 * @param map
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String MapToGetStr(Map<String, String> map) throws
			UnsupportedEncodingException {
		if (map != null) {
			StringBuilder sb = new StringBuilder();
			boolean isFirst = true;

			for (Map.Entry<String, String> entry : map.entrySet()) {
				if (isFirst) {
					isFirst = false;
					sb.append("?");
				} else {
					sb.append("&");
				}

				sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
				sb.append("=");
				sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
			}
			return sb.toString();
		} else {
			return "";
		}
	}

}

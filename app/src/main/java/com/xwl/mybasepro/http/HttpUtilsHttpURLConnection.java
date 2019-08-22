package com.xwl.mybasepro.http;

import android.text.TextUtils;

import com.xwl.mybasepro.config.ACConfig;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import static com.xwl.mybasepro.base.Application.noTokenList;

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
			if (!noTokenList.contains(urlStr)) {
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
	 * Put 请求
	 * urlStr:网址
	 * parms：提交数据
	 * return:网页源码
	 * */
	public static String HttpPut(String urlStr, Map<String, Object> parms) {
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("PUT");
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
			if (!noTokenList.contains(urlStr)) {
				connection.setRequestProperty("access-token",
						ACConfig.getInstance().getAccessToken());
			}
			// 设置接收类型否则返回415错误
			connection.setRequestProperty("accept", "application/json");

			String Json = MapToJsonStr(parms);

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
			if (!noTokenList.contains(urlStr)) {
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
	 * 多文件上传的方法
	 *
	 * @param actionUrl：上传的路径
	 * @param uploadFilePaths：需要上传的文件路径，数组
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String uploadFiles(String actionUrl, String[] uploadFilePaths) {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";

		DataOutputStream ds = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		try {
			// 统一资源
			URL url = new URL(actionUrl);
			// 连接类的父类，抽象类
			URLConnection urlConnection = url.openConnection();
			// http的连接类
			HttpURLConnection connection = (HttpURLConnection) urlConnection;

			// 设置是否从httpUrlConnection读入，默认情况下是true;
			connection.setDoInput(true);
			// 设置是否向httpUrlConnection输出
			connection.setDoOutput(true);
			// Post 请求不能使用缓存
			connection.setUseCaches(false);
			// 设定请求的方法，默认是GET
			connection.setRequestMethod("POST");
			// 设置字符编码连接参数
			connection.setRequestProperty("Connection", "Keep-Alive");
			// 设置字符编码
			connection.setRequestProperty("Charset", "UTF-8");
			// 设置请求内容类型
			connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

			//设置token
			if (!noTokenList.contains(actionUrl)) {
				connection.setRequestProperty("access-token",
						ACConfig.getInstance().getAccessToken());
			}

			// 设置DataOutputStream
			ds = new DataOutputStream(connection.getOutputStream());

			for (int i = 0; i < uploadFilePaths.length; i++) {
				String uploadFile = uploadFilePaths[i];
				String filename = uploadFile.substring(uploadFile.lastIndexOf("//") + 1);
				ds.writeBytes(twoHyphens + boundary + end);
				ds.writeBytes("Content-Disposition: form-data; " + "name=\"file\";filename=\"" + filename
						+ "\"" + end);
				ds.writeBytes(end);
				FileInputStream fStream = new FileInputStream(uploadFile);
				int bufferSize = 1024;
				byte[] buffer = new byte[bufferSize];
				int length = -1;
				while ((length = fStream.read(buffer)) != -1) {
					ds.write(buffer, 0, length);
				}
				ds.writeBytes(end);
				/* close streams */
				fStream.close();
			}

			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
			ds.flush();

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inputStream = connection.getInputStream();
				inputStreamReader = new InputStreamReader(inputStream);
				reader = new BufferedReader(inputStreamReader);
				tempLine = null;
				resultBuffer = new StringBuffer();
				while ((tempLine = reader.readLine()) != null) {
					resultBuffer.append(tempLine);
					resultBuffer.append("\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ds != null) {
				try {
					ds.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return resultBuffer.toString();
		}
	}


	/**
	 * 将map转换成JSONObject的形式
	 *
	 * @param map
	 * @return JSONObject
	 */
	private static String MapToJsonStr(Map<String, Object> map) {
		if (map != null) {
			JSONObject jsonObj = new JSONObject(map);
			return jsonObj.toString();
		} else {
			return "";
		}
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

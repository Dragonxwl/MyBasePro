package com.xwl.mybasepro.okhttp;

import com.xwl.mybasepro.base.Application;
import com.xwl.mybasepro.bean.ServerErrorBean;
import com.xwl.mybasepro.utils.GsonUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * An OkHttp interceptor which logs request and response information. Can be applied as an
 * {@linkplain OkHttpClient#interceptors() application interceptor} or as a {@linkplain
 * OkHttpClient#networkInterceptors() network interceptor}. <p> The format of the logs created by
 * this class should not be considered stable and may change slightly between releases. If you need
 * a stable logging format, use your own interceptor.
 */
public final class HttpErrorLogInterceptor implements Interceptor {
	private static final Charset UTF8 = Charset.forName("UTF-8");

	public interface Logger {
		void sendError(JSONObject message);

		/**
		 * A {@link Logger} defaults output appropriate for the current platform.
		 */
		void log(JSONObject message);
	}

	public HttpErrorLogInterceptor(Logger logger) {
		this.logger = logger;
	}

	private final Logger logger;

	@Override
	public Response intercept(Chain chain) throws IOException {
		JSONObject json = new JSONObject();
		Request request = chain.request();
		Headers headers = request.headers();
		Response response;
		long code = -1;
		String method;
		HttpUrl url;

		try {
			json.put("headersToStr", headers.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 遍历header
		JSONObject headerJson = new JSONObject();
		for (int i = 0, count = headers.size(); i < count; i++) {
			String name = headers.name(i);
			try {
				headerJson.put(name, headers.value(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			json.put("headers", headerJson.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 获取 请求方式 请求路径
		method = request.method();
		url = request.url();
		try {
			json.put("method", method);
			json.put("url", request.url());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 获取参数
		if (method.equals("POST") || method.equals("PUT")) {
			RequestBody body = request.body();
			if (body != null) {
				if (body instanceof FormBody) {
					JSONObject paramsJson = new JSONObject();
					for (int i = 0; i < ((FormBody) body).size(); i++) {
						try {
							paramsJson.put(((FormBody) body).name(i), ((FormBody) body).value(i));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					try {
						json.put("params", paramsJson.toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Buffer buffer = new Buffer();
					body.writeTo(buffer);
					String oldParamsJson = buffer.readUtf8();
					try {
						json.put("params", new JSONObject(oldParamsJson));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} else if (method.equals("GET")) {
			JSONObject paramsJson = new JSONObject();
			for (int i = 0; i < url.querySize(); i++) {
				try {
					paramsJson.put(url.queryParameterName(i), url.queryParameterValue(i));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			try {
				json.put("params", paramsJson.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 记录请求开始时间
		long startNs = System.nanoTime();
		try {
			response = chain.proceed(request);
		} catch (Exception e) {
			// 请求发生错误
			try {
				json.put("ErrorMessage", e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			// 发送错误日志
			logger.sendError(json);
			throw e;
		}

		// 获取请求时间
		long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
		code = response.code();
		try {
			json.put("code", response.code());
			json.put("tookMs", tookMs + "毫秒");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// 获取请求 返回body
		ResponseBody responseBody = response.body();
		BufferedSource source = responseBody.source();
		source.request(Long.MAX_VALUE); // Buffer the entire body.
		Buffer buffer = source.buffer();

		JSONObject bodyJson = null;
		try {
			bodyJson = new JSONObject(buffer.clone().readString(UTF8));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			json.put("body", bodyJson.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		int errorCode = -1;
		if (bodyJson != null) {
			try {
				errorCode = bodyJson.getInt("errorCode");
				//服务端80080001~80080099错误
				if (errorCode >= 80080001 && errorCode <= 80080099) {
					serverErrorEvent(bodyJson.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 上传条件 code 不等 200  或者 errorCode 不等于0
		if (code != 200 || errorCode != 0) {
			logger.sendError(json);
		} else {
			logger.log(json);
		}

		return response;
	}

	/**
	 * 服务端80080001~80080099错误
	 *
	 * @param bodyJsonStr
	 */
	private void serverErrorEvent(String bodyJsonStr) {
		ServerErrorBean serverErrorBean = GsonUtil.parseJsonWithGson(bodyJsonStr, ServerErrorBean.class);
		//服务端80080001~80080099错误
		if (serverErrorBean != null && serverErrorBean.data != null) {

		}
	}
}

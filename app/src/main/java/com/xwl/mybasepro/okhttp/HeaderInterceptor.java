package com.xwl.mybasepro.okhttp;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.xwl.mybasepro.base.Application;
import com.xwl.mybasepro.config.ACConfig;
import com.xwl.mybasepro.utils.LogUtil;
import com.xwl.mybasepro.utils.StringUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.xwl.mybasepro.base.Application.noTokenList;

public class HeaderInterceptor implements Interceptor {

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request originalRequest = chain.request();

		Request.Builder builder = originalRequest.newBuilder();
		builder.addHeader("Content-Type", "application/json");
		builder.addHeader("request-id", StringUtils.getUUID());
		//设置token
		if (!noTokenList.contains(originalRequest.url().toString().replace(Application.GetHost(),""))) {
			String token = ACConfig.getInstance().getAccessToken();
			if (!TextUtils.isEmpty(token)) {
				builder.addHeader("access-token", token);
				LogUtil.LogI("HTTP-access-token:", token);
			}
		}

		Request newRequest = builder.build();
		return chain.proceed(newRequest);
	}
}

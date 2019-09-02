package com.xwl.mybasepro.utils.http.okhttp;

import android.text.TextUtils;

import com.xwl.mybasepro.config.ACConfig;
import com.xwl.mybasepro.utils.LogUtil;
import com.xwl.mybasepro.utils.StringUtils;
import com.xwl.mybasepro.utils.TokenCheckUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request originalRequest = chain.request();

		Request.Builder builder = originalRequest.newBuilder();
		builder.addHeader("Content-Type", "application/json");
		builder.addHeader("request-id", StringUtils.getUUID());
		//设置token
		if (TokenCheckUtil.isNeedToken(originalRequest.url().toString())) {
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

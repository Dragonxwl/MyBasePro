package com.xwl.mybasepro.main;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xwl.mybasepro.R;
import com.xwl.mybasepro.base.Application;
import com.xwl.mybasepro.base.BaseActivity;
import com.xwl.mybasepro.config.ACConfig;
import com.xwl.mybasepro.demo.getXmlValueDemoActivity;
import com.xwl.mybasepro.demo.httpDemoActivity;
import com.xwl.mybasepro.demo.okhttpDemoActivity;
import com.xwl.mybasepro.okhttp.HeaderInterceptor;
import com.xwl.mybasepro.okhttp.RetrofitClient;
import com.xwl.mybasepro.utils.StringUtils;

import java.util.Arrays;

import static com.xwl.mybasepro.base.Application.getContext;
import static com.xwl.mybasepro.base.Application.noToken;


public class MainActivity extends BaseActivity {

	private TextView TextView_okhttp;
	private TextView TextView_http;
	private TextView TextView_get_xml;

	@Override
	protected void initUI() {
		super.initUI();
		setContentView(R.layout.main_layout);
		TextView_okhttp = findViewById(R.id.TextView_okhttp);
		TextView_http = findViewById(R.id.TextView_http);
		TextView_get_xml = findViewById(R.id.TextView_get_xml);
	}

	@Override
	protected void initData() {
		super.initData();

		// 初始化 不需要token的接口
		noToken = new String[]{
				"/ac-client/profile/8/" + Application.getAppVersionCode(getContext()) +
						"?channelName=" + StringUtils.getChannelName(getContext()),
				"/ac-common/oauth/sms/stu"};
		Application.noTokenList = Arrays.asList(noToken);

		// 设置调试模式
		ACConfig.getInstance().setDebug(true);

		// 切换服务器
		Application.SetHost(3);

		// okhttp 初始化
		RetrofitClient.getInstance().init(Application.GetHost(), new HeaderInterceptor());

		TextView_okhttp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, okhttpDemoActivity.class);
				startActivity(intent);
			}
		});

		TextView_http.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, httpDemoActivity.class);
				startActivity(intent);
			}
		});

		TextView_get_xml.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, getXmlValueDemoActivity.class);
				startActivity(intent);
			}
		});
	}
}

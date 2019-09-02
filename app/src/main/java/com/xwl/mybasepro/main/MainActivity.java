package com.xwl.mybasepro.main;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xwl.mybasepro.R;
import com.xwl.mybasepro.base.Application;
import com.xwl.mybasepro.base.BaseActivity;
import com.xwl.mybasepro.demo.getXmlValueDemoActivity;
import com.xwl.mybasepro.demo.httpDemoActivity;
import com.xwl.mybasepro.demo.okhttpDemoActivity;

import static com.xwl.mybasepro.utils.HostUtil.SetHost;


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

		// 切换服务器
		SetHost(3);

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

	@Override
	protected void onResume() {
		super.onResume();
	}
}

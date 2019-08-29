package com.xwl.mybasepro.demo;

import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.xwl.mybasepro.R;
import com.xwl.mybasepro.base.BaseActivity;
import com.xwl.mybasepro.utils.xml.ParseXmlService;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import static com.xwl.mybasepro.base.Application.handler;

public class getXmlValueDemoActivity extends BaseActivity {

	private TextView TextView_xml;
	private TextView TextView_get_xml_value;
	private TextView TextView_value;
	private TextView TextView_get_xml;
	private WebView webView;

	@Override
	protected void initUI() {
		super.initUI();
		setContentView(R.layout.xml_demo_layout);
		TextView_xml = findViewById(R.id.TextView_xml);
		TextView_get_xml_value = findViewById(R.id.TextView_get_xml_value);
		TextView_value = findViewById(R.id.TextView_value);
		TextView_get_xml = findViewById(R.id.TextView_get_xml);
		webView = findViewById(R.id.WebView);

	}

	@Override
	protected void initData() {
		super.initData();

		TextView_get_xml.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				webView.setVisibility(View.VISIBLE);
				TextView_value.setVisibility(View.GONE);
				webView.loadUrl(TextView_xml.getText().toString());
			}
		});

		TextView_get_xml_value.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				webView.setVisibility(View.GONE);
				TextView_value.setVisibility(View.VISIBLE);
				// 重新保存课件url结构体
				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						URL url = null;
						try {
							url = new URL(TextView_xml.getText().toString());
							HttpURLConnection conn = (HttpURLConnection) url
									.openConnection();
							conn.setConnectTimeout(5000);
							// 把version.xml放到网络上，然后获取文件信息
							ParseXmlService service = new ParseXmlService();
							InputStream inStream = conn.getInputStream();
							final HashMap<String, String> mHashMap = service.parseXml(inStream);
							handler.post(new Runnable() {
								@Override
								public void run() {
									TextView_value.setText(mHashMap.get("KJUrl"));
								}
							});
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				Thread thread = new Thread(runnable);
				thread.start();
			}
		});
	}
}

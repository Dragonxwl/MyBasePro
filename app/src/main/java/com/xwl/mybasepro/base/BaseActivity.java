package com.xwl.mybasepro.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.xwl.mybasepro.http.MyServer;

public class BaseActivity extends RxAppCompatActivity {

	public MyServer myServer;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Application.getInstance().addActivity(this);
		myServer = new MyServer();
		initUI();
		initData();
	}

	protected void initUI() {

	}

	protected void initData() {

	}

	@Override
	protected void onDestroy() {
		Application.getInstance().removeActivity(this);
		super.onDestroy();
	}
}

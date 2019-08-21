package com.xwl.mybasepro.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public class BaseActivity extends RxAppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Application.getInstance().addActivity(this);
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

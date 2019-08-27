package com.xwl.mybasepro.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public class BaseActivity extends RxAppCompatActivity {

	protected ImmersionBar mImmersionBar;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 状态栏导航栏设置
		mImmersionBar = ImmersionBar.with(this);
		mImmersionBar.init();
		// Application Activity 列表 添加
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
		// Application Activity 列表 删除
		Application.getInstance().removeActivity(this);
		super.onDestroy();
	}
}

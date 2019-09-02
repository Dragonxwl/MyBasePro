package com.xwl.mybasepro.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.xwl.mybasepro.function.ImmersionBarFun;
import com.xwl.mybasepro.function.TelephonyListenFun;
import com.xwl.mybasepro.function.WaitDialogFun;

public class BaseActivity extends RxAppCompatActivity {

	/***
	 *  功能块
	 *  1.状态栏、导航栏
	 *  2.来电监听
	 *  3.等待弹框
	 */
	// 状态栏、导航栏
	private ImmersionBarFun mImmersionBarFun;
	private WaitDialogFun waitDialogFun;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Application Activity 列表 添加
		Application.getInstance().addActivity(this);
		// 功能块 初始化
		initFunction();

		initUI();
		initData();
	}

	protected void initUI() {

	}

	protected void initData() {

	}

	@Override
	protected void onResume() {
		super.onResume();
		// 状态栏导航栏 默认风格
		mImmersionBarFun.mImmersionBar_Default_Activity();
	}

	@Override
	protected void onDestroy() {
		// Application Activity 列表 删除
		Application.getInstance().removeActivity(this);
		super.onDestroy();
	}

	/**
	 * 功能块 初始化
	 */
	public void initFunction() {
		// 状态栏导航栏 初始化
		mImmersionBarFun = new ImmersionBarFun(this);
		// 来电监听 回调方法Call_
		new TelephonyListenFun(this);
		// 等待弹框 初始化
		waitDialogFun = new WaitDialogFun(this);
	}

	/**
	 * 功能块 电话监听回调方法
	 */
	public void Call_RINGING() {// 响铃状态

	}

	public void Call_OFFHOOK() {// 拨号状态、接听状态、保持通话状态

	}

	public void Call_IDLE() {// 空闲状态

	}
}

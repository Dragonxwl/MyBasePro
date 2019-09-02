package com.xwl.mybasepro.function;

import android.app.Activity;
import android.widget.FrameLayout;

import com.gyf.immersionbar.ImmersionBar;

/**
 *  状态栏、导航栏
 */
public class ImmersionBarFun {

	protected ImmersionBar mImmersionBar;// 状态栏导航栏
	private Activity activity;
	private FrameLayout frameLayout;

	public ImmersionBarFun(Activity activity) {
		this.activity = activity;
		mImmersionBar_Init_Activity();
	}

	public ImmersionBarFun(FrameLayout frameLayout) {
		this.frameLayout = frameLayout;
	}

	/**
	 * 状态栏、导航栏 管理初始化
	 */
	public void mImmersionBar_Init_Activity() {
		mImmersionBar = ImmersionBar.with(activity);
		mImmersionBar.init();
	}

	/**
	 * 状态栏、导航栏 默认状态
	 */
	public void mImmersionBar_Default_Activity() {
		mImmersionBar.transparentBar().statusBarDarkFont(true).init();
	}
}

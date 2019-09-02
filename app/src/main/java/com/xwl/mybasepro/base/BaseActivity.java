package com.xwl.mybasepro.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.xwl.mybasepro.ui.Dialog.WaitDialog;

public class BaseActivity extends RxAppCompatActivity {

	protected ImmersionBar mImmersionBar;// 状态栏导航栏
	private WaitDialog mWaitDialog = null;// 等待弹框

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
	protected void onResume() {
		super.onResume();
		// 默认风格
		mImmersionBar.transparentBar().statusBarDarkFont(true).init();
	}

	@Override
	protected void onDestroy() {
		// Application Activity 列表 删除
		Application.getInstance().removeActivity(this);
		super.onDestroy();
	}



	/**
	 * 弹出等待框
	 */
	public void showWaitDialog() {
		try {
			if (mWaitDialog == null) {
				mWaitDialog = new WaitDialog(BaseActivity.this);
				mWaitDialog.setCancelable(false);
				mWaitDialog.setCanceledOnTouchOutside(false);
				mWaitDialog.getWindow().setDimAmount(0f);
				mWaitDialog.show();
			}
		} catch (Exception e) {

		}
	}

	/**
	 * 弹出等待框
	 */
	public void showWaitDialog(boolean flag) {
		try {
			if (mWaitDialog == null) {
				mWaitDialog = new WaitDialog(BaseActivity.this);
				mWaitDialog.setCancelable(flag);
				mWaitDialog.setCanceledOnTouchOutside(flag);
				mWaitDialog.getWindow().setDimAmount(0f);
				mWaitDialog.show();
			}
		} catch (Exception e) {

		}
	}

	/**
	 * 消除等待框
	 */
	public void hideWaitDialog() {
		try {
			if (mWaitDialog != null) {
				mWaitDialog.dismiss();
				mWaitDialog = null;
			}
		} catch (Exception e) {

		}
	}
}

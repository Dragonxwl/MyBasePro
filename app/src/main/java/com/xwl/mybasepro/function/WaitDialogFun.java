package com.xwl.mybasepro.function;

import android.app.Activity;

import com.xwl.mybasepro.base.BaseActivity;
import com.xwl.mybasepro.ui.Dialog.WaitDialog;


/**
 *  等待弹框
 */
public class WaitDialogFun {

	private WaitDialog mWaitDialog = null;// 等待弹框
	private BaseActivity activity;

	public WaitDialogFun(Activity activity) {
		this.activity = (BaseActivity) activity;
	}

	/**
	 * 弹出等待框
	 */
	public void showWaitDialog() {
		try {
			if (mWaitDialog == null) {
				mWaitDialog = new WaitDialog(activity);
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
				mWaitDialog = new WaitDialog(activity);
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

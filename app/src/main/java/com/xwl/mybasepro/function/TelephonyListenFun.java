package com.xwl.mybasepro.function;

import android.app.Activity;
import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.xwl.mybasepro.base.BaseActivity;

/**
 * 监听电话状态
 */
public class TelephonyListenFun {

	TelephonyManager phonyManager;
	private BaseActivity activity;

	public TelephonyListenFun(Activity activity) {
		this.activity = (BaseActivity) activity;
		listenPhoneState();
	}

	public void listenPhoneState() {
		phonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
		phonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
	}

	private PhoneStateListener phoneStateListener = new PhoneStateListener() {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			if (state == TelephonyManager.CALL_STATE_RINGING) { // 响铃状态
				activity.Call_RINGING();
			} else if (state == TelephonyManager.CALL_STATE_OFFHOOK) { // 拨号状态、接听状态、保持通话状态
				activity.Call_OFFHOOK();
			} else if (state == TelephonyManager.CALL_STATE_IDLE) { // 空闲状态
				activity.Call_IDLE();
			}
			super.onCallStateChanged(state, incomingNumber);
		}
	};

}

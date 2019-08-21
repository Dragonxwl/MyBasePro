package com.xwl.mybasepro.utils;

/**
 * Created by Xiang on 2018/6/1.
 */

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dovar.dtoast.DToast;
import com.xwl.mybasepro.R;

import java.util.List;

/**
 * @author HUPENG
 */
public class ToastUtil {

	private static Handler handler = new Handler(Looper.getMainLooper());

	/**
	 * 此处是一个封装的Toast方法，可以取消掉上一次未完成的，直接进行下一次Toast
	 *
	 * @param context context
	 * @param text    需要toast的内容
	 */
	public static void toast(Context context, String text) {
		if (context != null) {
			if (!isContextExisted(context)) {
				return;
			}
			setToast(context, text, Toast.LENGTH_SHORT);
		}
	}

	// gravity 0 下   1 中  2上
	public static void setToast(final Context context, final String str, final int Duration) {
		if ("accessToken错误".equals(str) || "用户不存在".equals(str)) {
			return;
		}
		handler.post(new Runnable() {
			@Override
			public void run() {
				try {
					if (Duration == Toast.LENGTH_SHORT) {
						View view = View.inflate(context, R.layout.view_toast_custom, null);
						((TextView) view.findViewById(R.id.tv_msg)).setText(str);
						DToast.make(context)
								.setView(view)
								.setGravity(Gravity.BOTTOM, 0, 300)
								.showLong();
					} else {
						View view = View.inflate(context, R.layout.view_toast_custom, null);
						((TextView) view.findViewById(R.id.tv_msg)).setText(str);
						DToast.make(context)
								.setView(view)
								.setGravity(Gravity.BOTTOM, 0, 300)
								.showLong();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public static boolean isServiceExisted(Context context, String className) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(Integer.MAX_VALUE);

		if (!(serviceList.size() > 0)) {
			return false;
		}

		for (int i = 0; i < serviceList.size(); i++) {
			ActivityManager.RunningServiceInfo serviceInfo = serviceList.get(i);
			ComponentName serviceName = serviceInfo.service;

			if (serviceName.getClassName().equals(className)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isContextExisted(Context context) {
		if (context != null) {
			if (context instanceof Activity) {
				if (!((Activity) context).isFinishing()) {
					return true;
				}
			} else if (context instanceof Service) {
				if (isServiceExisted(context, context.getClass().getName())) {
					return true;
				}
			} else if (context instanceof Application) {
				return true;
			}
		}
		return false;
	}
}

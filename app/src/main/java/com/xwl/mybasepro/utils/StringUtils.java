package com.xwl.mybasepro.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.leon.channel.helper.ChannelReaderUtil;

import java.util.UUID;

import static com.xwl.mybasepro.base.Application.activityList;

public class StringUtils {
	// 获取UUID
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String uniqueId = uuid.toString();

		return uniqueId;
	}

	// 获取渠道名
	public static String getChannelName(Context context) {
		String str = ChannelReaderUtil.getChannel(context.getApplicationContext());
		if (str != null) {
			return str;
		} else {
			return "guanwang";
		}
	}

	// 获取渠道号
	public static int getChannelId(Context context) {
		String str = ChannelReaderUtil.getChannel(context.getApplicationContext());
		if ("guanwang".equals(str)) {
			return 1000;
		}
		if ("yingyongbao".equals(str)) {
			return 1001;
		}
		if ("360".equals(str)) {
			return 1002;
		}
		if ("baidu".equals(str)) {
			return 1003;
		}
		if ("ali".equals(str)) {
			return 1004;
		}
		if ("oppo".equals(str)) {
			return 1005;
		}
		if ("vivo".equals(str)) {
			return 1006;
		}
		if ("huawei".equals(str)) {
			return 1007;
		}
		if ("xiaomi".equals(str)) {
			return 1008;
		}
		if ("lenovo".equals(str)) {
			return 1009;
		}
		return 1000;
	}

	public static int getAppVersionCode(Context context) {
		int versionCode = 0;
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionCode = pi.versionCode;
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionCode;
	}

	public static String getAppFirstActivity() {
		String activityName = "";
		if (activityList != null && activityList.size() > 0) {
			activityName = activityList.get(activityList.size() - 1).getLocalClassName();
		}
		return activityName;
	}

	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}
}

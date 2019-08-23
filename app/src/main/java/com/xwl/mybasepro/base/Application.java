package com.xwl.mybasepro.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.xwl.mybasepro.config.CustomerConfig;

import java.util.LinkedList;
import java.util.List;

public class Application extends android.app.Application {

	public static Handler handler = new Handler(Looper.getMainLooper());

	public static List<Activity> activityList = new LinkedList<Activity>();
	public static Application application;

	public static String[] noToken;

	public static List<String> noTokenList;

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
	}

	public Application() {
	}

	public static Application getInstance() {
		if (null == application) {
			application = new Application();
		}
		return application;
	}

	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	public static void removeActivity(Activity activity) {
		if (activityList.contains(activity)) {
			activityList.remove(activity);
		}
	}

	public static void exit() {
		for (Activity a : activityList) {
			a.finish();
		}
		ActivityManager activityMgr = (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
		activityMgr.restartPackage(application.getPackageName());
		System.exit(0);
	}

	public static Context getContext() {
		return application.getApplicationContext();
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

	public static String NOW_HOST_STU = CustomerConfig.HOST_STU;

	public static void SetHost(int i) {
		switch (i) {
			case 1:
				NOW_HOST_STU = CustomerConfig.HOST_STU;
				break;
			case 2:
				NOW_HOST_STU = CustomerConfig.HOST_STU_G;
				break;
			case 3:
				NOW_HOST_STU = CustomerConfig.HOST_STU_T;
				break;
			case 4:
				NOW_HOST_STU = CustomerConfig.HOST_STU_T2;
				break;
		}
	}

	public static String GetHost() {
		return NOW_HOST_STU;
	}
}

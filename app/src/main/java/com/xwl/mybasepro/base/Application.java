package com.xwl.mybasepro.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.xwl.mybasepro.http.MyServer;
import com.xwl.mybasepro.http.okhttp.HeaderInterceptor;
import com.xwl.mybasepro.http.okhttp.RetrofitClient;
import com.xwl.mybasepro.utils.TokenCheckUtil;

import java.util.LinkedList;
import java.util.List;

import static com.xwl.mybasepro.utils.HostUtil.GetHost;

public class Application extends android.app.Application {

	public static Handler handler = new Handler(Looper.getMainLooper());

	public static List<Activity> activityList = new LinkedList<Activity>();
	public static Application application;

	public static MyServer myServer;

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;

		// 不需要token的连接初始化
		TokenCheckUtil.setNotNeedTokenUrls();

		// okhttp 初始化
		RetrofitClient.getInstance().init(GetHost(), new HeaderInterceptor());

		// 自定义网络请求服务初始化
		myServer = new MyServer();
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
}

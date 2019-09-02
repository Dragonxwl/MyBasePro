package com.xwl.mybasepro.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.leon.channel.helper.ChannelReaderUtil;
import com.xwl.mybasepro.base.Application;

import java.io.File;
import java.util.UUID;

import static android.content.Context.ACTIVITY_SERVICE;
import static com.xwl.mybasepro.base.Application.activityList;

public class StringUtils {

	/**
	 * 获取手机信息
	 */
	public static String getAndroidInfo() {
		StringBuilder builder = new StringBuilder();
		builder.append("产品名称:" + PRODUCT);
		builder.append(", 硬件制造商:" + MANUFACTURER);
		builder.append(", 系统定制商:" + BRAND);
		builder.append(", 手机型号信息:" + MODEL);
		builder.append(", 硬件名称:" + HARDWARE);
		builder.append(", 系统版本号:" + Build.VERSION.RELEASE);
		builder.append(", 系统SDK:" + Build.VERSION.SDK_INT);
		builder.append(", 手机当前可用内存(RAM):" + getFormatSize(getAvailMemory(Application.getContext())));
		builder.append(", 手机全部存储内存:" + getFormatSize(getAllSize()));
		builder.append(", 手机当前可用存储内存:" + getFormatSize(getAvailaleSize()));
		//builder.append(", 当前使用网络:" + (getNetType() == NetWorkUtil.TYPE_MOBILE ? "手机网络" : "WIFI"));
		builder.append(", 当前应用版本号:" + getAppVersionCode(Application.getContext()));
		builder.append(", 当前渠道:" + ChannelReaderUtil.getChannel(Application.getContext()));
		return builder.toString();
	}

	/**
	 * 获取可用空间
	 *
	 * @return
	 */
	public static long getAvailaleSize() {
		try {
			File path = Environment.getExternalStorageDirectory(); //取得sdcard文件路径
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return availableBlocks * blockSize;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return -1;
		}

		//(availableBlocks * blockSize)/1024.0      KB 单位
		//(availableBlocks * blockSize)/1024.0 /1024  MB单位
	}


	/**
	 * 获取全部空间
	 *
	 * @return
	 */
	public static long getAllSize() {
		try {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getBlockCount();
			return availableBlocks * blockSize;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 获取全部空间
	 *
	 * @return
	 */
	public static String getFormatSize(long size) {

		double sizeKB = size / 1024.0;
		double sizeMB = size / 1024.0 / 1024;
		double sizeGB = size / 1024.0 / 1024 / 1024;

		if (sizeGB > 1) {
			return String.format("%.2f", sizeGB) + "GB";
		} else if (sizeMB > 1) {
			return String.format("%.2f", sizeMB) + "MB";
		} else if (sizeKB > 1) {
			return String.format("%.2f", sizeKB) + "KB";
		}
		return "1KB";
	}

	public static String getMyMemory(Context context) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
		//最大分配内存
		int memory = activityManager.getMemoryClass();
		System.out.println("memory: " + memory);
		//最大分配内存获取方法2
		float maxMemory = (float) (Runtime.getRuntime().maxMemory() * 1.0 / (1024 * 1024));
		//当前分配的总内存
		float totalMemory = (float) (Runtime.getRuntime().totalMemory() * 1.0 / (1024 * 1024));
		//剩余内存
		float freeMemory = (float) (Runtime.getRuntime().freeMemory() * 1.0 / (1024 * 1024));

		String MemoryStr = "maxMemory: " + maxMemory + " totalMemory:" + totalMemory + " freeMemory:" + freeMemory;
		return MemoryStr;
	}

	/**
	 * 获取可用手机内存(RAM)
	 *
	 * @return
	 */
	public static long getAvailMemory(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
		am.getMemoryInfo(mi);
		return mi.availMem;
	}

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

	//当一个版本属性不知道时所设定的值。
	public static final String UNKNOWN = "unknown";
	//修订版本列表码
	public static final String ID = Build.ID;
	//显示屏参数
	public static final String DISPLAY = Build.DISPLAY;
	//整个产品的名称
	public static final String PRODUCT = Build.PRODUCT;
	//设备参数
	public static final String DEVICE = Build.DEVICE;
	//主板
	public static final String BOARD = Build.BOARD;
	//cpu指令集
	public static final String CPU_ABI = Build.CPU_ABI;
	//cpu指令集2
	public static final String CPU_ABI2 = Build.CPU_ABI2;
	//硬件制造商
	public static final String MANUFACTURER = Build.MANUFACTURER;
	//系统定制商
	public static final String BRAND = Build.BRAND;
	//版本即最终用户可见的名称
	public static final String MODEL = Build.MODEL;
	//系统启动程序版本号
	public static final String BOOTLOADER = Build.BOOTLOADER;
	//硬件名称
	public static final String HARDWARE = Build.HARDWARE;
	//硬件序列号
	public static final String SERIAL = Build.SERIAL;
	//bBuild的类型
	public static final String TYPE = Build.TYPE;
	//描述bBuild的标签,如未签名，debug等等。
	public static final String TAGS = Build.TAGS;
	//唯一识别码
	public static final String FINGERPRINT = Build.FINGERPRINT;

	public static final long TIME = Build.TIME;
	public static final String USER = Build.USER;
	public static final String HOST = Build.HOST;
}

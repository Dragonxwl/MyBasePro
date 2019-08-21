package com.xwl.mybasepro.config;

import android.os.Environment;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class CustomerConfig {
	// 接口地址
	public final static String HOST_STU_T = "http://192.168.10.202:18087";
	public final static String HOST_STU_T2 = "http://192.168.10.209:18087";
	public final static String HOST_STU_G = "http://47.111.78.238:8087";
	public final static String HOST_STU = "https://api.xxxxxx.com";

	// 本地保存文件
	public static final String MEDIA_SAVE_DIR = Environment.getExternalStorageDirectory().getPath() + "/MyBaseProApp/";
}

package com.xwl.mybasepro.demo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xwl.mybasepro.R;
import com.xwl.mybasepro.base.Application;
import com.xwl.mybasepro.base.BaseActivity;
import com.xwl.mybasepro.base.BaseBean;
import com.xwl.mybasepro.bean.LoginBean;
import com.xwl.mybasepro.bean.ProfileResultBean;
import com.xwl.mybasepro.config.ACConfig;
import com.xwl.mybasepro.utils.http.okhttp.ApiService;
import com.xwl.mybasepro.utils.http.okhttp.BaseObserver;
import com.xwl.mybasepro.utils.http.okhttp.RetrofitClient;
import com.xwl.mybasepro.utils.http.okhttp.RxSchedulers;
import com.xwl.mybasepro.utils.GsonUtil;
import com.xwl.mybasepro.utils.StringUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xwl.mybasepro.utils.StringUtils.getAppVersionCode;

public class okhttpDemoActivity extends BaseActivity {

	private TextView TextView_Profile;
	private TextView TextView_Login;
	private TextView TextView_value;
	private TextView TextView_UPAppList;

	@Override
	protected void initUI() {
		super.initUI();
		setContentView(R.layout.okhttp_demo_layout);
		TextView_Profile = findViewById(R.id.TextView_Profile);
		TextView_Login = findViewById(R.id.TextView_Login);
		TextView_value = findViewById(R.id.TextView_value);
		TextView_UPAppList = findViewById(R.id.TextView_UPAppList);

		TextView_value.setMovementMethod(ScrollingMovementMethod.getInstance());
	}

	@Override
	protected void initData() {
		super.initData();
		TextView_Profile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Get_getProfileByAppId(okhttpDemoActivity.this);
			}
		});

		TextView_Login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getTokenByPhoneAndSms("13609817244", "171204");
			}
		});

		TextView_UPAppList.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getAppList();
			}
		});
	}

	// 获取用户已装app
	public void getAppList() {
		ArrayList<AppInfo> appList = new ArrayList<>();  //用来存储获取的应用信息数据
		List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
		for (int i = 0; i < packages.size(); i++) {
			PackageInfo packageInfo = packages.get(i);
			AppInfo tmpInfo = new AppInfo();
			tmpInfo.appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
			tmpInfo.packageName = packageInfo.packageName;
			if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				appList.add(tmpInfo);//如果非系统应用，则添加至appList
			} else {

			}
		}
		if (appList != null && appList.size() > 0) {
			Post_UserAppList(appList);
		}
	}

	/**
	 * 接口用途：获取预配置信息
	 * <p>
	 * appId   平台信息： 0：无平台信息 1：pc老师端 2：pc学生端 3：Ipad老师端 4：Ipad学生端
	 * 5：Iphone 老师端 6：Iphone 学生端 7：android老师端 8：android 学生端
	 * buildId 内置版本号
	 */
	public void Get_getProfileByAppId(final Context context) {
		try {
			int buildId = getAppVersionCode(context);
			Map<String, Object> params = new HashMap<>();
			params.put("channelName", StringUtils.getChannelName(this));

			RetrofitClient.getInstance()
					.obtainService(ApiService.class)
					.updateVersion(8, buildId, params)
					.compose(RxSchedulers.<ProfileResultBean>compose())
//					.compose(this.<ProfileBean>bindToLifecycle())  //不绑定生命周期
					.subscribe(new BaseObserver<ProfileResultBean>() {
						@Override
						protected void onSuccess(ProfileResultBean bean) {
							TextView_value.setText(bean.data.dbCode + "");
						}

						@Override
						protected void onFailed(int code, String msg) {
						}

						@Override
						protected void onError(int code, String msg) {
						}
					});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 方法用途：学生登录-根据短信验证码
	 */
	private void getTokenByPhoneAndSms(final String phone, final String code) {
		try {
			JSONObject json = new JSONObject();
			json.put("phone", phone);
			json.put("smsCode", code);
			json.put("channel", "MOBILE");
			json.put("channelId", StringUtils.getChannelId(this));
			if (ACConfig.getInstance().getDebug()) {
				json.put("debug", 1);
			} else {
				json.put("debug", 0);
			}

			RetrofitClient.getInstance()
					.obtainService(ApiService.class)
					.loginWithSMS(RetrofitClient.jsonToRequestBody(json))
					.compose(RxSchedulers.<LoginBean>compose())
					.compose(this.<LoginBean>bindToLifecycle())
					.subscribe(new BaseObserver<LoginBean>() {
						@Override
						protected void onSuccess(LoginBean bean) {
							LoginBean.StudenInfo data = bean.data;
							ACConfig.getInstance().setAccessToken(data.accessToken);
							TextView_value.setText(data.accessToken);
						}

						@Override
						protected void onFailed(int code, String msg) {
						}

						@Override
						protected void onError(int code, String msg) {
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void Post_UserAppList(final ArrayList<AppInfo> appList) {
		try {
			AppListRequestBean appListRequestBean = new AppListRequestBean();
			appListRequestBean.setApps(appList);
			appListRequestBean.setDevice(Build.MANUFACTURER);
			final String RequestStr = GsonUtil.toJson(appListRequestBean);

			RetrofitClient.getInstance()
					.obtainService(ApiService.class)
					.GetUserApps(RetrofitClient.stringToRequestBody(RequestStr))
					.compose(RxSchedulers.<BaseBean>compose())
					.subscribe(new BaseObserver<BaseBean>() {
						@Override
						protected void onSuccess(BaseBean bean) {
							try {
								JsonParser jsonParser = new JsonParser();
								JsonObject jsonObject = jsonParser.parse(RequestStr).getAsJsonObject();
								Gson gson = new GsonBuilder().setPrettyPrinting().create();
								TextView_value.setText(gson.toJson(jsonObject));
							} catch (Exception e) {

							}
						}

						@Override
						protected void onFailed(int code, String msg) {
						}

						@Override
						protected void onError(int code, String msg) {
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class AppInfo {
		public String appName = "";
		public String packageName = "";
	}

	public class AppListRequestBean {
		private ArrayList<AppInfo> apps;
		private String device;


		public ArrayList<AppInfo> getApps() {
			return apps;
		}

		public void setApps(ArrayList<AppInfo> apps) {
			this.apps = apps;
		}

		public String getDevice() {
			return device;
		}

		public void setDevice(String device) {
			this.device = device;
		}

	}
}

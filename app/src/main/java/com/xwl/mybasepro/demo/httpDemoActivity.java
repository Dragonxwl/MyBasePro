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
import com.xwl.mybasepro.base.BaseTask;
import com.xwl.mybasepro.bean.LoginBean;
import com.xwl.mybasepro.bean.ProfileResultBean;
import com.xwl.mybasepro.config.ACConfig;
import com.xwl.mybasepro.okhttp.ApiService;
import com.xwl.mybasepro.okhttp.BaseObserver;
import com.xwl.mybasepro.okhttp.RetrofitClient;
import com.xwl.mybasepro.okhttp.RxSchedulers;
import com.xwl.mybasepro.utils.GsonUtil;
import com.xwl.mybasepro.utils.StringUtils;
import com.xwl.mybasepro.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class httpDemoActivity extends BaseActivity {

	private TextView TextView_Profile;
	private TextView TextView_Login;
	private TextView TextView_value;
	private TextView TextView_UPAppList;

	private boolean GET_getByAppIdFlag = true;
	private boolean Post_stuGetTokenByPhoneAndSmsFlag = true;
	private boolean Post_GetUserAppsFlag = true;

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
				GET_getByAppId();
			}
		});

		TextView_Login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Post_stuGetTokenByPhoneAndSms("13609817244", "171204");
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
			Post_GetUserApps(appList);
		}
	}

	/**
	 * 接口用途：获取预配置信息
	 * <p>
	 * appId   平台信息： 0：无平台信息 1：pc老师端 2：pc学生端 3：Ipad老师端 4：Ipad学生端
	 * 5：Iphone 老师端 6：Iphone 学生端 7：android老师端 8：android 学生端
	 * buildId 内置版本号
	 */
	public void GET_getByAppId() {
		if (GET_getByAppIdFlag) {
			GET_getByAppIdFlag = false;
			BaseTask.run(new BaseTask.TaskListener() {
				@Override
				public Object onTaskRunning(int taskId, Object data) {
					Map<String, String> params = new HashMap<>();
					params.put("channelName", StringUtils.getChannelName(Application.getContext()));
					return myServer.GET_getByAppId(params);
				}

				@Override
				public void onTaskResult(int taskId, Object result) {
					GET_getByAppIdFlag = true;
					if (result instanceof ProfileResultBean) {
						ProfileResultBean mProfileResultBean = (ProfileResultBean) result;
						TextView_value.setText(mProfileResultBean.data.dbCode + "");
					}
				}

				@Override
				public void onTaskProgress(int taskId, Object... values) {

				}

				@Override
				public void onTaskPrepare(int taskId, Object data) {

				}

				@Override
				public void onTaskCancelled(int taskId) {
					GET_getByAppIdFlag = true;
				}
			});
		}
	}

	/**
	 * 方法用途：学生登录-根据短信验证码
	 */
	public void Post_stuGetTokenByPhoneAndSms(final String phone, final String code) {
		if (Post_stuGetTokenByPhoneAndSmsFlag) {
			Post_stuGetTokenByPhoneAndSmsFlag = false;
			BaseTask.run(new BaseTask.TaskListener() {
				@Override
				public Object onTaskRunning(int taskId, Object data) {
					JSONObject params = new JSONObject();
					try {
						params.put("phone", phone);
						params.put("smsCode", code);
						params.put("channel", "MOBILE");
						params.put("channelId", StringUtils.getChannelId(Application.getContext()));
						if (ACConfig.getInstance().getDebug()) {
							params.put("debug", 1);
						} else {
							params.put("debug", 0);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					return myServer.Post_stuGetTokenByPhoneAndSms(params);
				}

				@Override
				public void onTaskResult(int taskId, Object result) {
					Post_stuGetTokenByPhoneAndSmsFlag = true;
					if (result instanceof LoginBean) {
						LoginBean mLoginBean = (LoginBean) result;
						ACConfig.getInstance().setAccessToken(mLoginBean.data.accessToken);
						TextView_value.setText(mLoginBean.data.accessToken);
					}
				}

				@Override
				public void onTaskProgress(int taskId, Object... values) {

				}

				@Override
				public void onTaskPrepare(int taskId, Object data) {

				}

				@Override
				public void onTaskCancelled(int taskId) {
					Post_stuGetTokenByPhoneAndSmsFlag = true;
				}
			});
		}
	}

	public void Post_GetUserApps(final ArrayList<httpDemoActivity.AppInfo> appList) {
		if (Post_GetUserAppsFlag) {
			Post_GetUserAppsFlag = false;
			BaseTask.run(new BaseTask.TaskListener() {
				@Override
				public Object onTaskRunning(int taskId, Object data) {
					JSONObject params = new JSONObject();
					JSONArray jsonArray = new JSONArray();
					JSONObject tmpObj = null;
					try {
						for(int i = 0; i < appList.size(); i++)
						{
							tmpObj = new JSONObject();
							tmpObj.put("appName" , appList.get(i).appName);
							tmpObj.put("packageName", appList.get(i).packageName);
							jsonArray.put(tmpObj);
							tmpObj = null;
						}
						params.put("apps", jsonArray);
						params.put("device", Build.MANUFACTURER);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					return myServer.Post_GetUserApps(params);
				}

				@Override
				public void onTaskResult(int taskId, Object result) {
					Post_GetUserAppsFlag = true;
					if (result instanceof BaseBean) {
						try {
							if (((BaseBean) result).errorCode == 0) {
								AppListRequestBean appListRequestBean = new AppListRequestBean();
								appListRequestBean.setApps(appList);
								appListRequestBean.setDevice(Build.MANUFACTURER);
								JsonParser jsonParser = new JsonParser();
								JsonObject jsonObject = jsonParser.parse(GsonUtil.toJson(appListRequestBean)).getAsJsonObject();
								Gson gson = new GsonBuilder().setPrettyPrinting().create();
								TextView_value.setText(gson.toJson(jsonObject));
							}
						} catch (Exception e) {

						}
					}
				}

				@Override
				public void onTaskProgress(int taskId, Object... values) {

				}

				@Override
				public void onTaskPrepare(int taskId, Object data) {

				}

				@Override
				public void onTaskCancelled(int taskId) {
					Post_GetUserAppsFlag = true;
				}
			});
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

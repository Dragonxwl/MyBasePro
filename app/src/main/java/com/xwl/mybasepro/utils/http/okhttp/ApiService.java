package com.xwl.mybasepro.utils.http.okhttp;

import com.xwl.mybasepro.base.BaseBean;
import com.xwl.mybasepro.bean.LoginBean;
import com.xwl.mybasepro.bean.ProfileResultBean;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ApiService {

	/**
	 * 接口用途：学生登录-根据手机号密码
	 */
	@POST("/ac-common/oauth/sms/stu")
	Flowable<LoginBean> loginWithSMS(@Body RequestBody info);

	/**
	 * 接口用途：获取预配置信息
	 */
	@GET("/ac-client/profile/{appId}/{buildId}")
	Flowable<ProfileResultBean> updateVersion(@Path("appId") int appId, @Path("buildId") int buildId
			, @QueryMap Map<String, Object> params);

	/**
	 * 上传用户应用列表
	 */
	@POST("/ac-client/user-apps/save")
	Flowable<BaseBean> GetUserApps(@Body RequestBody body);
}

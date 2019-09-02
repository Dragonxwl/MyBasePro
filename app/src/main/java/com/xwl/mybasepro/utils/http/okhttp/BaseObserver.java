package com.xwl.mybasepro.utils.http.okhttp;

import com.xwl.mybasepro.base.Application;
import com.xwl.mybasepro.base.BaseBean;
import com.xwl.mybasepro.utils.LogUtil;
import com.xwl.mybasepro.utils.ToastUtil;

import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.HttpException;

public abstract class BaseObserver<T extends BaseBean> extends DisposableSubscriber<T> {

	@Override
	public void onNext(T value) {
		if (value.getErrorCode() == ErrorCode.SUCCESS) {
			onSuccess(value);
		} else {
			if (value.getErrorCode() == ErrorCode.ERROR_ACCESS_TOKEN_ERROR) {

			} else {
				onFailed(value.getErrorCode(), value.getErrorMessage());
			}
		}
	}

	@Override
	public void onError(Throwable e) {
		if (e instanceof HttpException) {
			HttpException httpException = (HttpException) e;
			onError(httpException.code(), httpException.message());
			ToastUtil.toast(Application.getContext(), "网络开小差了(" + httpException.code() + ")");
			LogUtil.e_uninit("BaseObserver", "onError:" + e.getMessage());
		} else {
			onError(-1, e.getMessage());
			ToastUtil.toast(Application.getContext(), "网络开小差了(" + -1 + ")");
			LogUtil.e_uninit("BaseObserver", "onError:" + e.getMessage());
		}
	}

	@Override
	public void onComplete() {

	}

	protected abstract void onSuccess(T t);

	protected abstract void onFailed(int code, String msg);

	protected abstract void onError(int code, String msg);
}
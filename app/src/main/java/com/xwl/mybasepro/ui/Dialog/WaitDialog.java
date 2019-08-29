package com.xwl.mybasepro.ui.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.WindowManager;
import android.widget.ImageView;

import com.xwl.mybasepro.R;
import com.xwl.mybasepro.utils.Apng.ApngDrawable;
import com.xwl.mybasepro.utils.Apng.ApngImageLoadingListener;
import com.xwl.mybasepro.utils.Apng.ApngImageUtils;
import com.xwl.mybasepro.utils.Apng.ApngLoader;
import com.xwl.mybasepro.utils.Apng.ApngPlayListener;

public class WaitDialog extends Dialog {

	private ImageView ImageView_png;

	public WaitDialog(Context context) {
		super(context);
		initUI();
	}

	//沉浸式
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
	}

	private void initUI() {
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		try {
			//隐藏状态栏
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				//定义全屏参数
				int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
				//设置当前窗体为全屏显示
				getWindow().setFlags(flag, flag);
			}
		} catch (Exception e) {

		}
		setContentView(R.layout.wait_dialog);


		ImageView_png = findViewById(R.id.ImageView_png);
		try {
			//Apng播放
			ApngLoader.init(getContext());

			// 确定apng图片
			String apngUri = ApngImageUtils.Scheme.ASSETS.wrap("waitdialog.png");
			// 显示
			ApngLoader.loadImage(apngUri, ImageView_png,
					new ApngImageLoadingListener(new ApngPlayListener() {
						@Override
						public void onAnimationStart(ApngDrawable drawable, ImageView imageView) {
						}

						@Override
						public void onAnimationEnd(ApngDrawable drawable, ImageView imageView) {
						}

						@Override
						public void onAnimationRepeat(ApngDrawable drawable, ImageView imageView) {
						}
					})
			);
		} catch (Exception e) {

		}
	}

	@Override
	public void show() {
		try {
			if (ImageView_png != null && ImageView_png.getDrawable() != null) {
				if (ImageView_png.getDrawable() instanceof ApngDrawable) {
					((ApngDrawable)ImageView_png.getDrawable()).start();
				}
			}
			super.show();
		} catch (Exception e) {
		}
	}

	@Override
	public void dismiss() {
		try {
			super.dismiss();
			if (ImageView_png != null && ImageView_png.getDrawable() != null) {
				if (ImageView_png.getDrawable() instanceof ApngDrawable) {
					((ApngDrawable)ImageView_png.getDrawable()).stop();
				}
			}
		} catch (Exception e) {
		}
	}
}

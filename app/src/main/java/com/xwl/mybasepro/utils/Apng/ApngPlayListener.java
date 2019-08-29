package com.xwl.mybasepro.utils.Apng;

import android.widget.ImageView;

/**
 * Created by xiejing on 16/3/11.
 */
public interface ApngPlayListener {
	void onAnimationStart(ApngDrawable drawable, ImageView imageView);

	void onAnimationEnd(ApngDrawable drawable, ImageView imageView);

	void onAnimationRepeat(ApngDrawable drawable, ImageView imageView);
}

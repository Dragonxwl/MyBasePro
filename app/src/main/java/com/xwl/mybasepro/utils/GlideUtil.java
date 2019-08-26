package com.xwl.mybasepro.utils;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import io.reactivex.annotations.NonNull;

/**
 * 默认的策略是DiskCacheStrategy.AUTOMATIC
 * DiskCacheStrategy有五个常量：
 * DiskCacheStrategy.ALL 使用DATA和RESOURCE缓存远程数据，仅使用RESOURCE来缓存本地数据。
 * DiskCacheStrategy.NONE 不使用磁盘缓存
 * DiskCacheStrategy.DATA 在资源解码前就将原始数据写入磁盘缓存
 * DiskCacheStrategy.RESOURCE 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
 * DiskCacheStrategy.AUTOMATIC 根据原始图片数据和资源编码策略来自动选择磁盘缓存策略。
 */

public class GlideUtil {

	public static void load(Context context, @NonNull ImageView view, String url) {
		Glide.with(context)
				.asBitmap()
				.load(url)
				.into(view);
	}

	public static void load(Context context, @NonNull ImageView view, String url, @DrawableRes int placeholder) {
		Glide.with(context)
				.asBitmap()
				.load(url)
				.placeholder(placeholder)
				.fallback(placeholder)
				.into(view);
	}

	public static void load(Context context, @NonNull ImageView view, String url, @DrawableRes int placeholder, @DrawableRes int error) {
		Glide.with(context)
				.load(url)
				.placeholder(placeholder)
				.error(error)
				.fallback(placeholder)
				.transition(DrawableTransitionOptions.withCrossFade())
				.into(view);
	}

	public static void loadPortrait(Context context, @NonNull ImageView view, String url, @DrawableRes int placeholder) {
		Glide.with(context)
				.asBitmap()
				.load(url)
				.format(DecodeFormat.PREFER_RGB_565)
				.timeout(10000)
				.placeholder(placeholder)
				.fallback(placeholder)
				.into(view);
	}

	public static void loadGif(Context context, @NonNull ImageView view, int drawable) {
		Glide.with(context)
				.asGif()
				.load(drawable)
				.into(view);
	}

	public static void loadRoundCorner(Context context, @NonNull ImageView view, String url, @DrawableRes int placeholder) {
		Glide.with(context)
				.asBitmap()
				.load(url)
				.transform(new CenterCrop(), new RoundedCorners(20))
				.placeholder(placeholder)
				.fallback(placeholder)
				.into(view);


	}

	public static void loadRoundCorner(Context context, @NonNull ImageView view, String url, @DrawableRes int placeholder, int radius) {
		Glide.with(context)
				.asBitmap()
				.load(url)
				.timeout(10000)
				.transform(new CenterCrop(), new RoundedCorners(radius))
				.placeholder(placeholder)
				.fallback(placeholder)
				.into(view);
	}

	public static void loadRoundCorner(Context context, @NonNull ImageView view, int radius, String url) {
		Glide.with(context)
				.asBitmap()
				.load(url)
				.timeout(10000)
				.transform(new CenterCrop(), new RoundedCorners(20))
				.into(view);

	}
}

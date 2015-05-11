package com.hr.aframe.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

public class Tools {

	/**
	 * 网络监测，判断往前网络是否可用
	 * */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			// 获取所有网络连接信息
			NetworkInfo activeNetInfo = connectivity.getActiveNetworkInfo();
			if (null != activeNetInfo) {
				return activeNetInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * The absolute height of the display in pixels
	 * */
	public static int getScreenHeight(Context context) {
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(localDisplayMetrics);
		return localDisplayMetrics.heightPixels;
	}

	/**
	 * The absolute width of the display in pixels.
	 * */
	public static int getScreenWidth(Context context) {
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(localDisplayMetrics);
		return localDisplayMetrics.widthPixels;
	}

	public static float getDensity(Context context) {
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		return dm.density;
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int getResourceId(Context context, String name,
			String defType, String defPackage) {
		int id = context.getResources()
				.getIdentifier(name, defType, defPackage);
		return id;
	}
}

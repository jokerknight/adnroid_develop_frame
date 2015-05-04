package com.hr.aframe.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Tools {
	
	/**
	 * 网络监测，判断往前网络是否可用
	 * */
	public static boolean isNetworkAvailable(Context mContext) {
		ConnectivityManager connectivity = (ConnectivityManager) mContext
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
	
	public static int getResourceId(Context context, String name,
			String defType, String defPackage) {
		int id = context.getResources()
				.getIdentifier(name, defType, defPackage);
		return id;
	}
}

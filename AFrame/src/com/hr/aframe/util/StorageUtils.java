package com.hr.aframe.util;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;

/**
 * @author hrxiang
 * @version 0.0.1
 * */
public class StorageUtils {

	/**
	 * 系统下载文件存储主目录
	 * */
	public static final String DIR_IMG = "img";// 图片保存位置
	public static final String DIR_IMG_AD = "ad";// 广告页图片
	public static final String DIR_IMG_SPLASH = "splash";// 引导页图片
	public static final String DIR_APK = "apk";// 更新包保存位置

	/**
	 * 获取图片存目录
	 * 
	 * @param mContext
	 * @return file
	 * */
	public static File getImageStorageDirectory(Context mContext) {
		return getStorageDirectory(mContext, DIR_IMG);
	}

	/**
	 * 获取apk更新包存储目录
	 * 
	 * @param mContext
	 * @return file
	 * */
	public static File getApkStorageDirectory(Context mContext) {
		return getStorageDirectory(mContext, DIR_APK);
	}

	/**
	 * 获取引导页图片存储目录
	 * 
	 * @param mContext
	 * @return file
	 * */
	public static File getSplashStorageDirectory(Context mContext) {
		return getStorageDirectory(mContext, DIR_IMG_SPLASH);
	}

	/**
	 * 获取广告图片存储目录
	 * 
	 * @param mContext
	 * @return file
	 * */
	public static File getAdStorageDirectory(Context mContext) {
		return getStorageDirectory(mContext, DIR_IMG_AD);
	}

	/**
	 * 获取存储路径
	 * 
	 * @param mContext
	 * @param directory
	 *            目录名
	 * @return file
	 * **/
	public static File getStorageDirectory(Context mContext, String directory) {
		File appCacheDir = null;
		// /storage/sdcard0
		if (Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			appCacheDir = mContext.getExternalCacheDir();
		}
		if (appCacheDir == null) {
			if (Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED))
				appCacheDir = getExternalCacheDir(mContext);
			else
				appCacheDir = mContext.getCacheDir();
		}

		File fileSaveDir = new File(appCacheDir, directory);
		if (!fileSaveDir.exists())
			fileSaveDir.mkdirs();
		authorization(fileSaveDir.getPath());
		return fileSaveDir;
	}

	/**
	 * 在没有sd卡的情况下，往内存写数据的时候，必须要拥有对目录的写权限 可更改文件权限为 777（可讀，可寫，可執行）
	 * **/
	public static void authorization(String path) {
		try {
			Runtime.getRuntime().exec("chmod 777 " + path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static File getExternalCacheDir(Context context) {
		File dataDir = new File(new File(
				Environment.getExternalStorageDirectory(), "Android"), "data");
		File appCacheDir = new File(
				new File(dataDir, context.getPackageName()), "cache");
		if (!appCacheDir.exists()) {
			if (!appCacheDir.mkdirs()) {
				return null;
			}
			try {
				new File(appCacheDir, ".nomedia").createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return appCacheDir;
	}
}

package com.hr.aframe.util;

import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.RemoteException;
import android.text.format.Formatter;

@SuppressLint("NewApi")
public class AppSizeUtils {
	private AppSizeInfo mAppSize;
	private Context mContext;
	private AppSizeGetCompletedLintener mLintener;

	public interface AppSizeGetCompletedLintener {
		public void onCompleted(AppSizeInfo appSizeInfo);
	}

	public class AppSizeInfo {
		/**
		 * Size of cache used by the application. (e.g.,/data/data//cache)
		 * */
		public long cacheSize;//
		/**
		 * Size of the internal data size for the application. (e.g.,
		 * /data/data/)
		 * */
		public long dataSize; // 数据大小
		/**
		 * Size of the code (e.g., APK)
		 * */
		public long codeSize; // 应用程序大小
		public long totalSize; // 总大小
		/**
		 * Size of the external cache used by the application (i.e., on the SD
		 * card). If this is a subdirectory of the data directory, this size
		 * will be subtracted out of the external data size.
		 * */
		public long externalCacheSize;
		/**
		 * Size of the secure container on external storage holding the
		 * application's code.
		 * */
		public long externalCodeSize;
		/**
		 * Size of the external data used by the application (e.g.,
		 * /Android/data/)
		 * */
		public long externalDataSize;
		/**
		 * Size of the external media size used by the application.
		 * */
		public long externalMediaSize;
		/**
		 * Size of the package's OBBs placed on external media.
		 * */
		public long externalObbSize;

		@Override
		public String toString() {
			return "AppSizeInfo [cacheSize=" + cacheSize + ", dataSize="
					+ dataSize + ", codeSize=" + codeSize + ", totalSize="
					+ totalSize + ", externalCacheSize=" + externalCacheSize
					+ ", externalCodeSize=" + externalCodeSize
					+ ", externalDataSize=" + externalDataSize
					+ ", externalMediaSize=" + externalMediaSize
					+ ", externalObbSize=" + externalObbSize + "]";
		}

	}

	// aidl文件形成的Bindler机制服务类
	public class PkgSizeObserver extends IPackageStatsObserver.Stub {

		/***
		 * 回调函数，
		 * 
		 * @param pStatus
		 *            ,返回数据封装在PackageStats对象中
		 * @param succeeded
		 *            代表回调成功
		 */
		@Override
		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
				throws RemoteException {
			// TODO Auto-generated method stub
			try {
				if (null == pStats) {
					return;
				}
				if (null == mAppSize) {
					mAppSize = new AppSizeInfo();
				}
				mAppSize.cacheSize = pStats.cacheSize; // 缓存大小
				mAppSize.dataSize = pStats.dataSize; // 数据大小
				mAppSize.codeSize = pStats.codeSize; // 应用程序大小
				mAppSize.externalCacheSize = pStats.externalCacheSize;
				mAppSize.externalCodeSize = pStats.externalCodeSize;
				mAppSize.externalDataSize = pStats.externalDataSize;
				mAppSize.externalMediaSize = pStats.externalMediaSize;
				mAppSize.externalObbSize = pStats.externalObbSize;

				mAppSize.totalSize = mAppSize.cacheSize + mAppSize.dataSize
						+ mAppSize.codeSize + mAppSize.externalCacheSize
						+ mAppSize.externalCodeSize + mAppSize.externalDataSize
						+ mAppSize.externalMediaSize + mAppSize.externalObbSize;

				if (null != mLintener) {
					mLintener.onCompleted(mAppSize);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void queryPacakgeSize(Context context) {
		String pkgName = context.getPackageName();
		if (pkgName != null) {
			// 使用放射机制得到PackageManager类的隐藏函数getPackageSizeInfo
			PackageManager pm = context.getPackageManager(); // 得到pm对象
			try {
				// 通过反射机制获得该隐藏函数
				Method getPackageSizeInfo = pm.getClass().getMethod(
						"getPackageSizeInfo", String.class,
						IPackageStatsObserver.class);
				// 调用该函数，并且给其分配参数 ，待调用流程完成后会回调PkgSizeObserver类的函数
				getPackageSizeInfo.invoke(pm, pkgName, new PkgSizeObserver());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	// 系统函数，字符串转换 long -String (kb)
	public String formateFileSize(long size) {
		return Formatter.formatFileSize(mContext, size);
	}

	public void queryAppSize(Context context,
			AppSizeGetCompletedLintener lintener) {
		this.mContext = context;
		this.mLintener = lintener;
		queryPacakgeSize(context);
	}
}

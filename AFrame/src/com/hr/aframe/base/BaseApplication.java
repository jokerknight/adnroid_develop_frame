package com.hr.aframe.base;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.multidex.MultiDex;

import com.android.volley.RequestQueue;
import com.hr.aframe.R;
import com.hr.aframe.common.Config;
import com.hr.aframe.util.CrashHandler;
import com.hr.aframe.util.CrashHandler.ISubmitServerSetting;
import com.hr.aframe.util.PropertiesUtils;
import com.hr.aframe.util.StorageUtils;
import com.hr.aframe.util.Tools;
import com.hr.aframe.util.XLog;
import com.hr.aframe.volley.RequestManager;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class BaseApplication extends Application {
	private static final String TAG = BaseApplication.class.getSimpleName();
	private SharedPreferences mSPferences;
	protected RequestQueue mRequestQueue;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		try {
			initConfig();
			initValues();
			initVolleyQueue();
			initImageLoader();
			// 注册系统崩溃监听器
			CrashHandler.getInstance().registerHandler(getApplicationContext());
			// 设置提交服务器方式
			/*CrashHandler.getInstance().submitServerSetting(
					new ISubmitServerSetting() {

						@Override
						public void onSetting(File file) {
							// TODO Auto-generated method stub
							XLog.e(TAG,
									"submit error log to server："
											+ Tools.getFileCotent(file));
						}
					});*/
			// 发送，未提交的错误日志到服务器
			/*CrashHandler.getInstance().sendPreviousReportsToServer();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initConfig() {
		mSPferences = this.getSharedPreferences(Config.LOCAL_CONFIG,
				MODE_PRIVATE);
		Properties props = PropertiesUtils.getProperties(
				getApplicationContext(), "config", "raw", getPackageName());
		Editor edit = mSPferences.edit();
		for (Object key : props.keySet()) {
			Object value = props.get(key);
			if (value instanceof String)
				edit.putString(key.toString(), value.toString());
			else if (value instanceof Boolean)
				edit.putBoolean(key.toString(), (Boolean) value);
			else if (value instanceof Integer)
				edit.putInt(key.toString(), (Integer) value);
			else if (value instanceof Long)
				edit.putLong(key.toString(), (Long) value);
			else if (value instanceof Float)
				edit.putFloat(key.toString(), (Float) value);
		}
		edit.commit();
	}

	private void initValues() throws ClassNotFoundException {
		Config.DB_NAME = mSPferences.getString("DB.NAME", "default.db");
		Config.DB_VEERSION = Integer.parseInt(mSPferences.getString(
				"DB.VERSION", "1"));
		String[] db_tables = getResources().getStringArray(R.array.db_table);
		Config.TABLES = new ArrayList<Class<?>>();
		for (String db_table : db_tables) {
			Config.TABLES.add(Class.forName(db_table));
		}
	}

	private void initVolleyQueue() {
		mRequestQueue = RequestManager
				.initRequestQueue(getApplicationContext());
	}

	private void initImageLoader() {
		File cacheDir = StorageUtils
				.getImageStorageDirectory(getApplicationContext());
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.threadPoolSize(3)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				// .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.diskCache(new UnlimitedDiscCache(cacheDir))
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.writeDebugLogs().build();
		ImageLoader.getInstance().init(config);
	}

	// 65665方法，错误
	@Override
	protected void attachBaseContext(Context base) {
		// TODO Auto-generated method stub
		super.attachBaseContext(base);
		MultiDex.install(this);
	}
}

package com.hr.aframe.volley;

import java.sql.SQLException;
import java.util.Map;

import android.content.Context;

import com.android.volley.Request.Method;
import com.google.gson.Gson;
import com.hr.aframe.base.BaseDao;
import com.hr.aframe.base.DatabaseHelper;
import com.hr.aframe.common.Config;
import com.j256.ormlite.dao.Dao;

public class DatabaseCacheHelper {
	private static final String PRIMARY_KEY = "index";
	private DatabaseHelper mDatabaseHelper;
	private DatabseCacheDao mDatabaseCacheDao;
	private static DatabaseCacheHelper mHelper;
	private final Gson mGson;

	public synchronized static DatabaseCacheHelper getHelper(Context context) {
		if (null == mHelper)
			mHelper = new DatabaseCacheHelper(context);
		return mHelper;
	}

	private DatabaseCacheHelper(Context context) {
		this.mDatabaseHelper = DatabaseHelper.getHelper(context);
		this.mDatabaseCacheDao = new DatabseCacheDao();
		this.mGson = new Gson();
	}

	protected class DatabseCacheDao extends BaseDao<DatabaseCache, String> {

		@Override
		public Dao<DatabaseCache, String> getDao() throws SQLException {
			// TODO Auto-generated method stub
			return mDatabaseHelper.getDao(DatabaseCache.class);
		}

		@Override
		public DatabaseHelper getHelper() {
			// TODO Auto-generated method stub
			return mDatabaseHelper;
		}

	}

	/**
	 * 判断是否存在缓存
	 * 
	 * */
	public boolean hasCache(String cacheIndex) {
		try {
			verify(cacheIndex);
			DatabaseCache mDatabaseCache = this.mDatabaseCacheDao.queryByField(
					PRIMARY_KEY, cacheIndex);
			return null == mDatabaseCache ? false : true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 校验数据
	 * */
	public void verify(String cacheIndex) {
		try {
			DatabaseCache mDatabaseCache = this.mDatabaseCacheDao.queryByField(
					PRIMARY_KEY, cacheIndex);
			if (null != mDatabaseCache) {
				long timestamp = mDatabaseCache.getTimestamp();
				if (generateTimestamp() - timestamp >= Config.CACHE_TIME) {
					this.mDatabaseCacheDao.delete(this.mDatabaseCacheDao.query(
							PRIMARY_KEY, cacheIndex));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public <T> T getCacheDatas(Class<T> clazz, String cacheIndex) {
		try {
			DatabaseCache mDatabaseCache = this.mDatabaseCacheDao.queryByField(
					PRIMARY_KEY, cacheIndex);
			String data = mDatabaseCache.getData();
			return mGson.fromJson(data, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public <T> void cacheDatas(T response, String cacheIndex) {
		try {
			if (null != response) {
				String data = this.mGson.toJson(response);
				DatabaseCache mDatabaseCache = new DatabaseCache();
				mDatabaseCache.setIndex(cacheIndex);
				mDatabaseCache.setTimestamp(generateTimestamp());
				mDatabaseCache.setData(data);
				this.mDatabaseCacheDao.save(mDatabaseCache);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成索引号
	 * */
	public String generateCacheIndex(int method, String url,
			Map<String, String> params, Map<String, String> headers) {
		String generateKey = "";
		switch (method) {
		case Method.GET:
			generateKey += "method=get";
			break;
		case Method.POST:
			generateKey += "method=post";
			break;
		case Method.DELETE:
			generateKey += "method=delete";
			break;
		case Method.PUT:
			generateKey += "method=put";
			break;
		}
		generateKey = generateKey + "&url=" + url + "&params={"
				+ mapformat(params) + "}&headers={" + mapformat(headers) + "}";
		return generateKey;
	}

	/**
	 * 时间戳
	 * */
	public static long generateTimestamp() {
		return System.currentTimeMillis();
	}

	public static String mapformat(Map<String, String> map) {
		StringBuffer buffer = new StringBuffer();
		if (null != map && map.size() > 0) {
			for (String key : map.keySet()) {
				buffer.append(key + "=" + map.get(key));
				buffer.append(",");
			}
			buffer.deleteCharAt(buffer.length() - 1);
		}
		return buffer.toString();
	}
}

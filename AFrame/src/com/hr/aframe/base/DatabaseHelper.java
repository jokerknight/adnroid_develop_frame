package com.hr.aframe.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.hr.aframe.common.Config;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static DatabaseHelper instance;

	private DatabaseHelper(Context context, String databaseName,
			CursorFactory factory, int databaseVersion) {
		super(context, databaseName, factory, databaseVersion);
		// TODO Auto-generated constructor stub
	}

	private DatabaseHelper(Context context) {
		// TODO Auto-generated constructor stub
		this(context, Config.DB_NAME, null, Config.DB_VEERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqliteDatabase,
			ConnectionSource connectionSource) {
		// TODO Auto-generated method stub
		try {
			for (Class<?> clazz : Config.TABLES) {
				TableUtils.createTableIfNotExists(connectionSource, clazz);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqliteDatabase,
			ConnectionSource connectionSource, int oldVer, int currentVersion) {
		// TODO Auto-generated method stub
		try {
			for (Class<?> clazz : Config.TABLES) {
				TableUtils.dropTable(connectionSource, clazz, true);
			}
			onCreate(sqliteDatabase, connectionSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

	/**
	 * 单例获取该Helper
	 * 
	 * @param context
	 * @return
	 */
	public static synchronized DatabaseHelper getHelper(Context context) {
		if (instance == null) {
			synchronized (DatabaseHelper.class) {
				if (instance == null)
					instance = new DatabaseHelper(
							context.getApplicationContext());
			}
		}
		return instance;
	}

	@Override
	public void close() {
		super.close();
	}
}

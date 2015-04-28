package com.hr.aframe.util;

import android.util.Log;

/**
 * @author hrxiang
 * @version 0.0.1
 * */
public class XLog {
	public static boolean DEBUG_ENABLED = true;// 开关，默认打开状态

	/**
	 * log level
	 * <ul>
	 * <li>VERBOSE = 0</li>
	 * <li>DEBUG = 1</li>
	 * <li>INFO = 2</li>
	 * <li>WARN = 3</li>
	 * <li>ERROR = 4</li>
	 * <li>OUT = 5</li>
	 * </ul>
	 * */
	@Deprecated
	public static int LOG_LEVEL = 2;// 日志等级，默认打印info等级

	// 等级
	public static final int VERBOSE = 0;
	public static final int DEBUG = 1;
	public static final int INFO = 2;
	public static final int WARN = 3;
	public static final int ERROR = 4;
	public static final int OUT = 5;

	/**
	 * 日志打印
	 * 
	 * @param level
	 *            <ul>
	 *            <li>VERBOSE = 0</li>
	 *            <li>DEBUG = 1</li>
	 *            <li>INFO = 2</li>
	 *            <li>WARN = 3</li>
	 *            <li>ERROR = 4</li>
	 *            <li>OUT = 5</li>
	 *            </ul>
	 * @param tag
	 * @param msg
	 * **/
	public static void print(int level, String tag, String msg) {
		if (DEBUG_ENABLED) {
			switch (level) {
			case VERBOSE:
				Log.v(tag, msg);
				break;
			case DEBUG:
				Log.d(tag, msg);
				break;
			case INFO:
				Log.i(tag, msg);
				break;
			case WARN:
				Log.w(tag, msg);
				break;
			case ERROR:
				Log.e(tag, msg);
				break;
			case OUT:
				System.out.println(tag + " <----------> " + msg);
				break;
			}
		}
	}

	public static void v(String tag, String msg) {
		if (DEBUG_ENABLED) {
			Log.v(tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (DEBUG_ENABLED) {
			Log.d(tag, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (DEBUG_ENABLED) {
			Log.i(tag, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (DEBUG_ENABLED) {
			Log.w(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (DEBUG_ENABLED) {
			Log.e(tag, msg);
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (DEBUG_ENABLED) {
			Log.e(tag, msg, tr);
		}
	}

	public static void o(String tag, String msg) {
		if (DEBUG_ENABLED) {
			System.out.println(tag + " <----------> " + msg);
		}
	}
}

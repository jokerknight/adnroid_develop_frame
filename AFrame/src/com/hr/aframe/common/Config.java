package com.hr.aframe.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Config {
	public final static String LOCAL_CONFIG = "local.config";
	public static int DB_VEERSION = 1;
	public static String DB_NAME = "test.db";
	public static List<Class> TABLES = Collections
			.synchronizedList(new ArrayList<Class>());
	/**
	 * 默认缓存有效时间3分钟
	 * */
	public static int CACHE_TIME = 3 * 60 * 1000;
}

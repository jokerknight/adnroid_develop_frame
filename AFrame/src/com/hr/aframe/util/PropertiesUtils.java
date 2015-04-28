package com.hr.aframe.util;

import java.util.Properties;

import android.content.Context;

public class PropertiesUtils {

	public static Properties getProperties(Context context, String name,
			String defType, String defPackage) {
		Properties props = new Properties();
		try {
			int id = context.getResources().getIdentifier(name, defType,
					defPackage);
			props.load(context.getResources().openRawResource(id));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return props;
	}
}

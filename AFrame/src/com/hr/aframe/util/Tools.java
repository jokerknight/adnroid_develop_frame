package com.hr.aframe.util;

import android.content.Context;

public class Tools {
	public static int getResourceId(Context context, String name,
			String defType, String defPackage) {
		int id = context.getResources()
				.getIdentifier(name, defType, defPackage);
		return id;
	}
}

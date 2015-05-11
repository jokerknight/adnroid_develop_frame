package com.hr.aframe;

import com.hr.aframe.util.Tools;
import com.hr.aframe.util.XLog;

import android.test.AndroidTestCase;

public class TestUnit extends AndroidTestCase {
	private static final String TAG = TestUnit.class.getSimpleName();
	private static final String url = "http://gc.ditu.aliyun.com/geocoding";
	public void testProperties() {
		// Config.TABLES.add(User.class);
		float density = Tools.getDensity(getContext());
		int width = (int) (220 * density);
		int height = (int) (120 * density);
		XLog.e(TAG, "density: " + density);
		XLog.e(TAG, "width: " + width);
		XLog.e(TAG, "height: " + height);
		System.out.println("density: " + density);
		System.out.println("width: " + width + "," + Tools.dip2px(getContext(), 220));
		System.out.println("height: " + height + "," + Tools.dip2px(getContext(), 120));
		try {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

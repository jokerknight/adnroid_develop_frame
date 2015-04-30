package com.hr.aframe;

import android.os.Handler;
import android.test.AndroidTestCase;

import com.android.volley.Request.Method;
import com.hr.aframe.base.BaseGsonService;
import com.hr.aframe.bean.TestBean;
import com.hr.aframe.common.Config;
import com.hr.aframe.volley.DatabaseCache;

public class TestUnit extends AndroidTestCase {
	private static final String url = "http://gc.ditu.aliyun.com/geocoding";

	public void testProperties() {
		Config.TABLES.add(DatabaseCache.class);
		try {
			BaseGsonService mBaseGsonService = new BaseGsonService(mContext,
					TestUnit.class.getSimpleName());
			mBaseGsonService.addRequestToQueue(mHandelr, Method.GET, url, null,
					null, TestBean.class, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Handler mHandelr = new Handler() {
		public void handleMessage(android.os.Message msg) {
			System.out.println("handler:" + msg.obj);
		};
	};
}

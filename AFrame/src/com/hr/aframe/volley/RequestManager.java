package com.hr.aframe.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestManager {

	private static RequestQueue mRequestQueue;

	/**
	 * In application In the initialization
	 * */
	public static RequestQueue initRequestQueue(Context context) {
		if (mRequestQueue == null) {
			synchronized (RequestManager.class) {
				if (mRequestQueue == null) {
					mRequestQueue = Volley.newRequestQueue(context
							.getApplicationContext());
				}
			}
		}
		return mRequestQueue;
	}

	public static RequestQueue getRequestQueue(Context context) {
		return initRequestQueue(context);
	}
}

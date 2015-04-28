package com.hr.aframe.base;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.hr.aframe.volley.RequestManager;

public class BaseService {
	protected RequestQueue mRequestQueue;

	public BaseService(Context context) {
		this.mRequestQueue = RequestManager.getRequestQueue(context);
	}

}

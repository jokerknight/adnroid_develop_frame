package com.hr.aframe;

import java.util.Map;

import android.os.Handler;
import android.view.ViewGroup;

import com.android.volley.Request.Method;
import com.hr.aframe.base.BaseActivity;
import com.hr.aframe.base.ViewInject;
import com.hr.aframe.bean.TestBean;

public class TestActivity extends BaseActivity {
	private static final String url = "http://gc.ditu.aliyun.com/geocoding";
	@ViewInject(R.id.test)
	private ViewGroup mTestGroup;
	private Map<String, String> mRequestParams;
	private Map<String, String> mRequestHeaders;

	@Override
	protected int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.test;
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initValues() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initDatas() {
		// TODO Auto-generated method stub
		mBaseGsonService.addRequestToQueue(mHandler, Method.GET, url,
				mRequestParams, mRequestHeaders, TestBean.class, true);
	}

	@Override
	protected void initViewEventListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void registerBroadcast() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void unregisterBroadcast() {
		// TODO Auto-generated method stub

	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			System.out.println("handler:" + msg.obj);
		};
	};
}

package com.hr.aframe;

import java.util.Map;

import android.os.Handler;
import android.view.ViewGroup;

import com.android.volley.Request.Method;
import com.hr.aframe.base.BaseActivity;
import com.hr.aframe.base.ViewInject;
import com.hr.aframe.bean.User;

public class TestActivity extends BaseActivity {
	@ViewInject(R.id.test)
	private ViewGroup mTestGroup;
	
	private Map<String, String> mRequestParams;
	private Map<String, String> mRequestHeaders;

	@Override
	protected int getLayoutResID() {
		// TODO Auto-generated method stub
		return 0;
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
		mBaseGsonService.addRequestToQueue(mHandler, Method.GET, "",
				mRequestParams, mRequestHeaders, User.class);
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

		};
	};
}

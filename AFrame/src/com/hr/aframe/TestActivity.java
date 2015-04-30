package com.hr.aframe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Handler;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request.Method;
import com.hr.aframe.adapter.TestAdapter;
import com.hr.aframe.base.BaseActivity;
import com.hr.aframe.base.ViewInject;
import com.hr.aframe.bean.TestBean;

public class TestActivity extends BaseActivity {
	private static final String url = "http://gc.ditu.aliyun.com/geocoding";
	@ViewInject(R.id.test)
	private ViewGroup mTestGroup;
	@ViewInject(R.id.list)
	private ListView mListView;

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
		List<String> list = new ArrayList<String>();
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		TestAdapter<String> adapter = new TestAdapter<String>(mContext, list);
		mListView.setAdapter(adapter);

		/***
		 * 数据请求和数据缓存测试
		 * */
		mBaseGsonService.addRequestToQueue(mHandler, Method.GET, url,
				mRequestParams, mRequestHeaders, TestBean.class, true, 5);
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

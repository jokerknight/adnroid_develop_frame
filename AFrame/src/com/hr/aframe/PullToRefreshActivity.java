package com.hr.aframe;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;

import com.hr.aframe.adapter.TestAdapter;
import com.hr.aframe.base.AbBaseFragmentActivity;
import com.hr.aframe.base.ViewInject;
import com.hr.aframe.util.XLog;
import com.hr.aframe.view.pulltorefresh.PullToRefreshLayout;
import com.hr.aframe.view.pulltorefresh.PullToRefreshLayout.OnRefreshListener;
import com.hr.aframe.view.pulltorefresh.view.PullableListView;

public class PullToRefreshActivity extends AbBaseFragmentActivity {
	private static final String TAG = PullToRefreshActivity.class.getSimpleName();
	@ViewInject(R.id.pullableview)
	private PullableListView mPullableView;
	@ViewInject(R.id.pulltorefresh)
	private PullToRefreshLayout mPullToRefreshLayout;

	@Override
	protected int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.activity_pulltorefresh;
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
		for (int i = 0; i < 80; i++) {
			list.add("item " + i);
		}
		TestAdapter<String> adapter = new TestAdapter<String>(mContext, list);
		mPullableView.setAdapter(adapter);
		mPullToRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefreshing(PullToRefreshLayout pullToRefreshLayout) {
				// TODO Auto-generated method stub
				XLog.e(TAG, "刷新.....");
				mHandler.sendMessageDelayed(mHandler.obtainMessage(0), 1000);
			}

			@Override
			public void onLoadding(PullToRefreshLayout pullToRefreshLayout) {
				// TODO Auto-generated method stub
				mHandler.sendMessageDelayed(mHandler.obtainMessage(1), 1000);
				XLog.e(TAG, "加载.....");
			}
		});
	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				mPullToRefreshLayout
						.refreshFinished(PullToRefreshLayout.SUCCEED);
				break;
			case 1:
				mPullToRefreshLayout.loadFinihsed(PullToRefreshLayout.SUCCEED);
				break;
			}

		};
	};

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

}

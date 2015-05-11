package com.hr.aframe;

import com.hr.aframe.base.AbBaseFragmentActivity;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends AbBaseFragmentActivity {

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
		MobclickAgent.setDebugMode(true);
		// SDK在统计Fragment时，需要关闭Activity自带的页面统计，
		// 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
		MobclickAgent.openActivityDurationTrack(false);
		// MobclickAgent.setAutoLocation(true);
		// MobclickAgent.setSessionContinueMillis(1000);

		MobclickAgent.updateOnlineConfig(this);
	}

	@Override
	protected void initDatas() {
		// TODO Auto-generated method stub

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

}

package com.hr.aframe.base;

import android.os.Bundle;
import android.widget.AbsListView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

public abstract class AbsListViewBaseActivity extends AbBaseFragmentActivity {

	protected static final String STATE_PAUSE_ON_SCROLL = "STATE_PAUSE_ON_SCROLL";
	protected static final String STATE_PAUSE_ON_FLING = "STATE_PAUSE_ON_FLING";

	protected AbsListView mAbsListView;

	protected boolean mPauseOnScroll = true;
	protected boolean mPauseOnFling = true;

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		mPauseOnScroll = savedInstanceState.getBoolean(STATE_PAUSE_ON_SCROLL,
				false);
		mPauseOnFling = savedInstanceState.getBoolean(STATE_PAUSE_ON_FLING,
				true);
	}

	@Override
	public void onResume() {
		super.onResume();
		applyScrollListener();
	}

	private void applyScrollListener() {
		mAbsListView.setOnScrollListener(new PauseOnScrollListener(ImageLoader
				.getInstance(), mPauseOnScroll, mPauseOnFling));
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putBoolean(STATE_PAUSE_ON_SCROLL, mPauseOnScroll);
		outState.putBoolean(STATE_PAUSE_ON_FLING, mPauseOnFling);
	}
}

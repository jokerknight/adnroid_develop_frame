package com.hr.aframe.base;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

/**
 * ViewPager cycle
 * */
@SuppressLint("HandlerLeak")
public abstract class AbBaseCyclePagerAdapter<T> extends PagerAdapter {

	protected abstract List<View> getViewList(List<T> lst);

	protected abstract void setCurrentDot(int position);

	protected List<T> mList;
	private List<View> views;
	protected ViewPager mViewPager;
	private boolean mIsCycle = false;
	private boolean mIsChanged = false;
	private int mMessageTag = 89757;
	private static final int DELAYMILLIS = 2000;
	private int mCurrentPagePosition;
	private int dotsSize;

	public AbBaseCyclePagerAdapter(ViewPager vp, List<T> lst) {
		this.mList = lst;
		this.mViewPager = vp;
		this.mViewPager.setOnPageChangeListener(mOnPageChangeListener);
		cycleJudge();
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		ViewPager v = (ViewPager) container;
		v.removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ViewPager v = (ViewPager) container;
		v.addView(views.get(position), 0);
		return views.get(position);
	}

	/**
	 * 判断是否满足循环滚动的条件
	 * */
	private void cycleJudge() {
		// TODO Auto-generated method stub
		dotsSize = null == mList ? 0 : mList.size();
		if (null != mList && mList.size() > 1) {
			T first = mList.get(0);
			T last = mList.get(mList.size() - 1);
			mList.add(0, last);
			mList.add(first);
			mIsCycle = true;
		}
		views = new ArrayList<View>();
		for (int i = 0; i < mList.size(); i++) {
			views.addAll(getViewList(mList));
		}
	}

	public void startCycle(){
		if (mIsCycle) {
			mViewPager.setCurrentItem(1, false);
			startScroll();
		}
	}
	
	/**
	 * 实现ViewPager实例默认自动滚动 + 按键事件控制滚动(手指在屏幕上时候取消自动滚动,手指离开恢复自动滚动)
	 */
	private void startScroll() {
		viewPagerAutoScrollDefault();
		viewPagerAutoScrollControl();
	}

	private Handler mCycleHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == mMessageTag) {
				mCycleHandler.removeMessages(mMessageTag);
				mViewPager
						.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
				mCycleHandler.sendEmptyMessageDelayed(mMessageTag, DELAYMILLIS);
			}
		}
	};

	private void viewPagerAutoScrollDefault() {
		mCycleHandler.sendEmptyMessageDelayed(mMessageTag, DELAYMILLIS);
	}

	private void viewPagerAutoScrollControl() {
		mViewPager.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_MOVE:
					mCycleHandler.removeMessages(mMessageTag);
					break;
				case MotionEvent.ACTION_UP:
					mCycleHandler.removeMessages(mMessageTag);
					mCycleHandler.sendEmptyMessageDelayed(mMessageTag,
							DELAYMILLIS);
				default:
					break;
				}
				return false;
			}
		});
	}

	OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			// SCROLL_STATE_IDLE(0) 停止滚动
			if (ViewPager.SCROLL_STATE_IDLE == arg0) {
				if (mIsChanged) {
					mIsChanged = false;
					mViewPager.setCurrentItem(mCurrentPagePosition, false);
				}
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		/**
		 * A B C 
		 * 0 1 2 => 
		 * C A B C A 
		 * 0 1 2 3 4
		 * */
		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			mIsChanged = true;
			if (mIsCycle && arg0 == getCount() - 1) {
				mCurrentPagePosition = 1;
			} else if (mIsCycle && arg0 == 0) {
				mCurrentPagePosition = getCount() - 2;
			} else {
				mCurrentPagePosition = arg0;
			}
			if(mIsCycle){
				arg0 = arg0 % dotsSize;
			}
			setCurrentDot(arg0);
		}
	};
}

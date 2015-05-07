package com.hr.aframe.view.pulltorefresh;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hr.aframe.util.XLog;
import com.hr.aframe.view.pulltorefresh.view.IPullable;

public class PullToRefreshLayout extends ViewGroup {
	private static final String TAG = PullToRefreshLayout.class.getSimpleName();
	/** 刷新view */
	private View mRefreshView;
	/** 加载view */
	private View mLoadmoreView;
	/** 内容view */
	private View mPullableView;
	/** 刷新距离 */
	private int mRefreshDist;
	/** 加载距离 */
	private int mLoadmoreDist;

	private long delayMillis = 1000l;
	// 初始状态
	public static final int INIT = 0;
	// 释放刷新
	public static final int RELEASE_TO_REFRESH = 1;
	// 正在刷新
	public static final int REFRESHING = 2;
	// 释放加载
	public static final int RELEASE_TO_LOAD = 3;
	// 正在加载
	public static final int LOADING = 4;
	// done
	public static final int DONE = 5;
	// 当前状态
	private int mCurrentState = INIT;
	// 刷新成功
	public static final int SUCCEED = 0;
	// 刷新失败
	public static final int FAIL = 1;

	private float mPullY;
	private float mLastY;
	private float mLastX;
	private float mRadio = 2;
	private int mEvents;
	private boolean isFirstOnLayout = true;
	private boolean isTouch;

	private OnRefreshListener mOnRefreshListener;

	public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
		this.mOnRefreshListener = onRefreshListener;
	}

	public interface OnRefreshListener {
		void onRefreshing(PullToRefreshLayout pullToRefreshLayout);

		void onLoadding(PullToRefreshLayout pullToRefreshLayout);
	}

	public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	public PullToRefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public PullToRefreshLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		if (getChildCount() != 3) {
			// 抛出异常
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			mEvents = 0;
			isTouch = true;
			mLastY = ev.getY();
			mLastX = ev.getX();
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
		case MotionEvent.ACTION_POINTER_UP:
			// 过滤多点触碰
			mEvents = -1;
			break;
		case MotionEvent.ACTION_MOVE:
			if (mEvents == 0) {
				mPullY = mPullY + (ev.getY() - mLastY) / mRadio;
				XLog.e(TAG, "PullY:" + mPullY);
				// pull down
				if (((IPullable) mPullableView).canPullDown() && mPullY > 0
						&& mCurrentState != LOADING) {
					if (mPullY > mRefreshDist) {
						if (mCurrentState != REFRESHING) {
							changeState(RELEASE_TO_REFRESH);
						}

					} else {
						if (mCurrentState != REFRESHING) {
							changeState(INIT);
						}
					}
				}
				// pull up
				else if (((IPullable) mPullableView).canPullUp() && mPullY < 0
						&& mCurrentState != REFRESHING) {
					if (Math.abs(mPullY) > mLoadmoreDist) {
						if (mCurrentState != LOADING) {
							changeState(RELEASE_TO_LOAD);
						}

					} else {
						if (mCurrentState != LOADING) {
							changeState(INIT);
						}
					}
				} else {
					mPullY = 0;
				}
			} else {
				mEvents = 0;
				mLastX = ev.getX();
			}

			if (Math.abs(mPullY) > 0) {
				// 防止下拉过程中误触发长按事件
				ev.setAction(MotionEvent.ACTION_CANCEL);
				// 防止水平滑动触发下拉刷新
				if (Math.abs(ev.getX() - mLastX) > Math.abs(mPullY)) {
					mPullY = 0;
				}
				requestLayout();
			}
			mLastY = ev.getY();
			mLastX = ev.getX();
			break;
		case MotionEvent.ACTION_UP:
			isTouch = false;
			// 防止下拉过程中误触发点击事件
			if (Math.abs(mPullY) > 0) {
				ev.setAction(MotionEvent.ACTION_CANCEL);
			}
			if (mPullY > 0) {
				if (mPullY > mRefreshDist) {
					mPullY = mRefreshDist;
					if (mCurrentState != REFRESHING) {
						changeState(REFRESHING);
						if (null != this.mOnRefreshListener) {
							this.mOnRefreshListener.onRefreshing(this);
						}
					}
				} else {
					mPullY = 0;
					if (mCurrentState != REFRESHING)
						changeState(INIT);
				}
				requestLayout();
			} else if (mPullY < 0) {
				if (Math.abs(mPullY) > mLoadmoreDist) {
					mPullY = -mLoadmoreDist;
					if (mCurrentState != LOADING) {
						changeState(LOADING);
						if (null != this.mOnRefreshListener) {
							this.mOnRefreshListener.onLoadding(this);
						}
					}
				} else {
					mPullY = 0;
					if (mCurrentState != LOADING)
						changeState(INIT);
				}
				requestLayout();
			}
			break;
		default:
			break;
		}
		// 根据下拉距离改变比例
		mRadio = (float) (4 + 2 * Math.tan(Math.PI / 2 / getMeasuredHeight()
				* Math.abs(mPullY)));
		// super.dispatchTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}

	private void changeState(int state) {
		mCurrentState = state;
		switch (state) {
		case INIT:
			// XLog.e(TAG, "INIT");
			((TextView) ((ViewGroup) mRefreshView).getChildAt(0))
					.setText("PULL_TO_REFRESH");
			((TextView) ((ViewGroup) mLoadmoreView).getChildAt(0))
					.setText("PULL_TO_REFRESH");
			break;
		case RELEASE_TO_REFRESH:
			// XLog.e(TAG, "RELEASE_TO_REFRESH");
			((TextView) ((ViewGroup) mRefreshView).getChildAt(0))
					.setText("RELEASE_TO_REFRESH");
			break;
		case REFRESHING:
			// XLog.e(TAG, "REFRESHING");
			((TextView) ((ViewGroup) mRefreshView).getChildAt(0))
					.setText("REFRESHING");
			break;
		case RELEASE_TO_LOAD:
			// XLog.e(TAG, "RELEASE_TO_LOAD");
			((TextView) ((ViewGroup) mLoadmoreView).getChildAt(0))
					.setText("RELEASE_TO_LOAD");
			break;
		case LOADING:
			// XLog.e(TAG, "LOADING");
			((TextView) ((ViewGroup) mLoadmoreView).getChildAt(0))
					.setText("LOADING");
			break;
		}
	}

	public void refreshFinished(int result) {
		switch (result) {
		case SUCCEED:
			((TextView) ((ViewGroup) mRefreshView).getChildAt(0))
					.setText("refreshing success");
			break;
		case FAIL:
			((TextView) ((ViewGroup) mRefreshView).getChildAt(0))
					.setText("refreshing fail");
			break;
		}
		mHandler.sendMessageDelayed(mHandler.obtainMessage(), delayMillis);
	}

	public void loadFinihsed(int result) {
		switch (result) {
		case SUCCEED:
			((TextView) ((ViewGroup) mLoadmoreView).getChildAt(0))
					.setText("lodding success");
			break;
		case FAIL:
			((TextView) ((ViewGroup) mLoadmoreView).getChildAt(0))
					.setText("lodding fail");
			break;
		}
		mHandler.sendMessageDelayed(mHandler.obtainMessage(), delayMillis);
	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			changeState(INIT);
			if (mPullY != 0) {
				mPullY = 0;
				requestLayout();
			}
		};
	};

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		if (isFirstOnLayout) {
			isFirstOnLayout = false;
			mRefreshView = getChildAt(0);
			mPullableView = getChildAt(1);
			mLoadmoreView = getChildAt(2);
			mRefreshDist = ((ViewGroup) mRefreshView).getChildAt(0)
					.getMeasuredHeight();
			mLoadmoreDist = ((ViewGroup) mLoadmoreView).getChildAt(0)
					.getMeasuredHeight();
			XLog.i(TAG, "header height:" + mRefreshDist);
			XLog.i(TAG, "footer height:" + mLoadmoreDist);
		}
		mRefreshView.layout(0,
				(int) (mPullY - mRefreshView.getMeasuredHeight()),
				mRefreshView.getMeasuredWidth(), (int) mPullY);

		mPullableView.layout(0, (int) mPullY, mPullableView.getMeasuredWidth(),
				(int) (mPullY + mPullableView.getMeasuredHeight()));

		mLoadmoreView
				.layout(0,
						(int) (mPullY + mPullableView.getMeasuredHeight()),
						mLoadmoreView.getMeasuredWidth(),
						(int) (mPullY + +mPullableView.getMeasuredHeight() + mLoadmoreView
								.getMeasuredHeight()));
	}

	class YScrollDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			if (distanceY != 0 && distanceX != 0) {

			}
			if (Math.abs(distanceY) >= Math.abs(distanceX)) {
				return true;
			}
			return false;
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 计算出所有的childView的宽和高
		measureChildren(widthMeasureSpec, heightMeasureSpec);
	}
}

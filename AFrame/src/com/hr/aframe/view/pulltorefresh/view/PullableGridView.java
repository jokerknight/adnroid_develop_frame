package com.hr.aframe.view.pulltorefresh.view;

import com.hr.aframe.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.GridView;

public class PullableGridView extends GridView implements IPullable {
	private boolean mCanPullDown = true;
	private boolean mCanPullUp = true;

	public PullableGridView(Context context) {
		super(context);
	}

	public PullableGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public PullableGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.IPullable);
		int mode = a.getInt(R.styleable.IPullable_PullMode, 0);
		a.recycle();
		switch (mode) {
		case 0:// both
			mCanPullDown = true;
			mCanPullUp = true;
			break;
		case 1:// top
			mCanPullDown = true;
			mCanPullUp = false;
			break;
		case -1:// bottom
			mCanPullDown = false;
			mCanPullUp = true;
			break;
		default:
			mCanPullDown = false;
			mCanPullUp = false;
			break;
		}
	}

	@Override
	public boolean canPullDown() {
		if (!mCanPullDown) {
			return false;
		}
		if (getCount() == 0) {
			// 没有item的时候也可以下拉刷新
			return true;
		} else if (getFirstVisiblePosition() == 0) {
			if (null != getChildAt(0) && getChildAt(0).getTop() >= 0)
				// 滑到顶部了
				return true;
		}
		return false;
	}

	@Override
	public boolean canPullUp() {
		if (!mCanPullUp) {
			return false;
		}
		if (getCount() == 0) {
			// 没有item的时候也可以上拉加载
			return true;
		} else if (getLastVisiblePosition() == (getCount() - 1)) {
			// 滑到底部了
			if (null != getChildAt(getLastVisiblePosition()
					- getFirstVisiblePosition())
					&& getChildAt(
							getLastVisiblePosition()
									- getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
				return true;
		}
		return false;
	}

}

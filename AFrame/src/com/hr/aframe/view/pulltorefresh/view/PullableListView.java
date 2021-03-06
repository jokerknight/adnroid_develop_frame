package com.hr.aframe.view.pulltorefresh.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ListView;

import com.hr.aframe.R;

public class PullableListView extends ListView implements IPullable {
	private boolean mCanPullDown = true;
	private boolean mCanPullUp = true;

	public PullableListView(Context context) {
		super(context);
	}

	public PullableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public PullableListView(Context context, AttributeSet attrs, int defStyle) {
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
		} else if (getFirstVisiblePosition() == 0 && null != getChildAt(0)
				&& getChildAt(0).getTop() >= 0) {
			// Top position of this view relative to its parent.
			// 滑到ListView的顶部了
			return true;
		} else
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
		} else if (getLastVisiblePosition() == (getCount() - 1)
				&& null != getChildAt(getLastVisiblePosition()
						- getFirstVisiblePosition())
				&& getChildAt(
						getLastVisiblePosition() - getFirstVisiblePosition())
						.getBottom() <= getMeasuredHeight()) {
			// 滑到底部了
			// Bottom position of this view relative to its parent.
			return true;
		}
		return false;
	}

}

package com.hr.aframe.view.pulltorefresh.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.hr.aframe.R;
import com.hr.aframe.view.pla.MultiColumnListView;

public class PullableMultiColumnListView extends MultiColumnListView implements
		IPullable {
	private boolean mCanPullDown = true;
	private boolean mCanPullUp = true;

	public PullableMultiColumnListView(Context context) {
		super(context);
	}

	public PullableMultiColumnListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public PullableMultiColumnListView(Context context, AttributeSet attrs,
			int defStyle) {
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
		} else if (getLastVisiblePosition() == (getCount() - 1)) {
			// 滑到底部了
			try {
				View penultView;
				View lastView;
				if (null == getChildAt(getLastVisiblePosition()
						- getFirstVisiblePosition())) {
					lastView = getChildAt(getLastVisiblePosition()
							- getFirstVisiblePosition() - 1);
					penultView = getChildAt(getLastVisiblePosition()
							- getFirstVisiblePosition() - 2);
				} else {
					lastView = getChildAt(getLastVisiblePosition()
							- getFirstVisiblePosition());
					penultView = getChildAt(getLastVisiblePosition()
							- getFirstVisiblePosition() - 1);
				}
				if (null != penultView && null != lastView) {
					int penultBottom = penultView.getBottom();
					int lastBottom = lastView.getBottom();
					if (penultBottom - lastBottom >= 0) {
						if (penultView.getBottom() <= getMeasuredHeight())
							return true;
					} else {
						if (lastView.getBottom() <= getMeasuredHeight())
							return true;
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return false;
	}
}

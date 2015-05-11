package com.hr.aframe.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

@SuppressLint("NewApi")
public class BottomMenu extends RelativeLayout {
	// private ViewGroup overlay;
	// private ViewGroup dialog;
	private FrameLayout mOverlay;
	private FrameLayout mContent;
	private float mDownX, mDownY;
	private boolean mCancelable;
	private long mDuration = 200l;

	public BottomMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public BottomMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public BottomMenu(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context) {

		//半透明背景
		mOverlay = new FrameLayout(context);
		mOverlay.setBackgroundColor(Color.BLACK);
		mOverlay.setAlpha(0.5f);
		mOverlay.setVisibility(View.INVISIBLE);
		addView(mOverlay, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));

		//内容容器
		mContent = new FrameLayout(context);
		mContent.setVisibility(View.INVISIBLE);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.leftMargin = 10;
		params.rightMargin = 10;
		addView(mContent, params);
		
		//半透明背景触摸事件，防dialog效果
		mOverlay.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mDownX = event.getX();
					mDownY = event.getY();
					break;
				case MotionEvent.ACTION_UP:

					if (Math.abs(event.getX() - mDownX) < 5
							&& Math.abs(event.getY() - mDownY) < 5) {
						if (mCancelable) {
							toggle();
						}
					}
					break;
				}
				return true;
			}
		});
	}

	public void setContentView(View view) {
		mContent.addView(view);
	}

	public void setCanceledOnTouchOutside(boolean cancel) {
		mCancelable = cancel;
	}

	public void setDuration(long duration) {
		mDuration = duration;
	}

	/**
	 * 显示可以隐藏都加动画
	 * */
	public void toggle() {
		if (mContent.getVisibility() == View.VISIBLE) {
			TranslateAnimation outAnimation = new TranslateAnimation(0, 0, 0,
					mContent.getMeasuredHeight());
			outAnimation.setDuration(mDuration);
			mContent.startAnimation(outAnimation);
			mContent.setVisibility(View.INVISIBLE);
			outAnimation.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					mContent.setVisibility(View.GONE);
					mOverlay.setVisibility(View.GONE);
				}
			});
		} else {
			mOverlay.setVisibility(View.VISIBLE);
			TranslateAnimation inAnimation = new TranslateAnimation(0, 0,
					mContent.getMeasuredHeight(), 0);
			inAnimation.setDuration(mDuration);
			mContent.startAnimation(inAnimation);
			mContent.setVisibility(View.VISIBLE);
		}
	}

	public boolean isShowing() {
		return mContent.getVisibility() == View.VISIBLE;
	}

	public void dissmiss() {
		toggle();
	}
}

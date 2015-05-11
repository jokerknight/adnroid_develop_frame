package com.hr.aframe.base;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hr.aframe.R;

public abstract class AbBaseDialog extends Dialog {
	/* dp */
	private static final int height = 240;
	/* dp */
	private static final int width = 440;
	private View mContentView;

	public abstract View getContentView();

	public abstract void setEventListener(View parent);

	/**
	 * use default width and height
	 * */
	public AbBaseDialog(Context context) {
		// TODO Auto-generated constructor stub
		this(context, width, height);
	}

	/**
	 * @param width
	 *            px
	 * @param height
	 *            px
	 * */
	public AbBaseDialog(Context context, int width, int height) {
		super(context, R.style.Dialog_Theme);
		// TODO Auto-generated constructor stub
		mContentView = getContentView();
		setContentView(mContentView);
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		// set width,height and gravity
		params.width = width;
		params.height = height;
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);
		setEventListener(mContentView);
		setCanceledOnTouchOutside(true);
		setCancelable(true);
	}
}

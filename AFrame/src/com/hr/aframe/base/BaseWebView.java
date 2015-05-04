package com.hr.aframe.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;

public class BaseWebView extends WebView {

	public BaseWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initDefaultWebSettings();
	}

	public BaseWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initDefaultWebSettings();
	}

	public BaseWebView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initDefaultWebSettings();
	}

	@SuppressLint("SetJavaScriptEnabled")
	public void initDefaultWebSettings() {
		getSettings().setJavaScriptEnabled(true); // 支持
		getSettings().setUseWideViewPort(false); // 将图片调整到适合webview的大小
		getSettings().setSupportZoom(true); // 支持缩放
		getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN); // 支持内容重新布局
		getSettings().supportMultipleWindows(); // 多窗口
		getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // 关闭webview中缓存
		getSettings().setAllowFileAccess(true); // 设置可以访问文件
		getSettings().setNeedInitialFocus(true); // 当webview调用requestFocus时为webview设置节点
		getSettings().setBuiltInZoomControls(true); // 设置支持缩放
		getSettings().setJavaScriptCanOpenWindowsAutomatically(true); // 支持通过JS打开新窗口
		getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
		getSettings().setLoadsImagesAutomatically(true); // 支持自动加载图片
		// getSettings().setDefaultFontSize(size);
	}
}

package com.hr.aframe.base;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BaseWebViewClient extends WebViewClient {

	/*
	 * return false表示WebView自己处理，return true表示我们自己来处理该url。
	 * 通常情况下，以http、https、file(表示本地网页)开头的url，都给WebView自己来加载， 其他类型的url都通过系统应用程序来打开
	 */
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// TODO Auto-generated method stub
		return super.shouldOverrideUrlLoading(view, url);
	}

	// 网页开始加载，在这里可以进行加载动画等等
	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		// TODO Auto-generated method stub
		super.onPageStarted(view, url, favicon);
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		// TODO Auto-generated method stub
		super.onPageFinished(view, url);
	}

	// 接收过程发生错误
	@Override
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {
		// TODO Auto-generated method stub
		super.onReceivedError(view, errorCode, description, failingUrl);
		/*
		 * 网页加载出现错误，只处理如下几种情况。默认的WebView会直接显示出错的网络地址url，有可能暴露数据。
		 * 在这种情况下，需要将网页内容置空，提示用户网络错误
		 */
		if (errorCode == WebViewClient.ERROR_CONNECT
				|| errorCode == WebViewClient.ERROR_TIMEOUT
				|| errorCode == WebViewClient.ERROR_HOST_LOOKUP) {

		}
	}

}

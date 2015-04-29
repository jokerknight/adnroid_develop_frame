package com.hr.aframe.observer.sms;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

/**
 * 获取短信验证码，自动填写到EditText
 * */
public class SmsDatabaseObserverLauncher {
	private ContentObserver mContentObserver;
	private Handler mHandler;
	private String mAddress;
	private Context mContext;
	private Uri mUri;

	public SmsDatabaseObserverLauncher(Context context, Handler handler,
			String address) {
		this.mHandler = handler;
		this.mAddress = address;
		this.mContext = context;
		this.mUri = Uri.parse(SmsContentObserver.SMS_URI_INBOX);
		mContentObserver = new SmsContentObserver(mHandler, mContext, mAddress, mUri);
	}

	public void registerSmsDatabaseObserver() {
		mContext.getContentResolver().registerContentObserver(
				mUri, true,
				mContentObserver);
	}

	public void unregisterSmsDatabaseObserver() {
		mContext.getContentResolver().unregisterContentObserver(
				mContentObserver);
	}
}

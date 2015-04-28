package com.hr.aframe.volley;

import android.os.Handler;
import android.os.Message;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.hr.aframe.util.XLog;

public class BaseErrorListener implements ErrorListener {
	private static final String TAG = BaseErrorListener.class.getSimpleName();
	private Handler mHandler;
	private int mWhat;

	public BaseErrorListener(Handler handler, int what) {
		this.mHandler = handler;
		this.mWhat = what;
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		msg.what = mWhat;
		msg.obj = error == null ? "" : error.getMessage();
		mHandler.sendMessage(msg);
		XLog.e(TAG, "error : " + msg.obj.toString());
	}
}

package com.hr.aframe.base;

import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.android.volley.Response.Listener;
import com.hr.aframe.bean.LogoutCheckResponse;
import com.hr.aframe.util.XLog;
import com.hr.aframe.volley.BaseErrorListener;
import com.hr.aframe.volley.GsonRequest;
import com.hr.aframe.volley.ResponseCode;
import com.hr.aframe.volley.URLtoUTF8;

public class BaseGsonService extends BaseService {
	private static final String TAG = BaseGsonService.class.getSimpleName();
	private Context mContext;

	public BaseGsonService(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}

	/**
	 * @param handler
	 * @param method
	 * @param url
	 * @param params
	 * @param headers
	 * @param clazz
	 * */
	public <T> void addRequestToQueue(final Handler handler, int method,
			String url, Map<String, String> params,
			Map<String, String> headers, Class<T> clazz) {
		BaseErrorListener errorListener = new BaseErrorListener(handler,
				ResponseCode.ERROR);
		Listener<T> listener = new Listener<T>() {

			@Override
			public void onResponse(T response) {
				// TODO Auto-generated method stub
				XLog.i(TAG, "response result : " + response);
				/**
				 * 登录验证
				 * */
				if (response instanceof LogoutCheckResponse) {
					LogoutCheckResponse logoutCheckResponse = (LogoutCheckResponse) response;
					if (logoutCheckResponse.isLogout()) {
						/**
						 * 登录验证失效，跳转到登录界面
						 * */
					} else {
						Message message = handler.obtainMessage();
						message.what = ResponseCode.SUCCESS;
						message.obj = response;
						handler.sendMessage(message);
					}
				} else {
					Message message = handler.obtainMessage();
					message.what = ResponseCode.SUCCESS;
					message.obj = response;
					handler.sendMessage(message);
				}
			}

		};
		/**
		 * 不能有空格
		 * */
		url = URLtoUTF8.toUtf8String(url).replaceAll(" ", "%20");
		GsonRequest<T> request = new GsonRequest<T>(method, url, clazz,
				listener, errorListener, params, headers);
		// 请用缓存
		// request.setShouldCache(true);
		request.setTag(mContext);
		// Setting Request Timeout
		// request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
		XLog.i(TAG, "request url : " + url);
		XLog.i(TAG, "request params : " + params);
		XLog.i(TAG, "request headers : " + headers);
		mRequestQueue.add(request);
	}

	public void cancel() {
		mRequestQueue.cancelAll(mContext);
	}
}

package com.hr.aframe.base;

import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.android.volley.Response.Listener;
import com.hr.aframe.bean.LogoutCheckResponse;
import com.hr.aframe.util.XLog;
import com.hr.aframe.volley.BaseErrorListener;
import com.hr.aframe.volley.DatabaseCacheHelper;
import com.hr.aframe.volley.GsonRequest;
import com.hr.aframe.volley.ResponseCode;
import com.hr.aframe.volley.URLtoUTF8;

public class BaseGsonService extends BaseService {
	private static final String TAG = BaseGsonService.class.getSimpleName();
	private Context mContext;
	private Object mTag;
	private String index;

	public BaseGsonService(Context context, Object tag) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mTag = tag;
	}

	/**
	 * @param useCache
	 *            是否使用数据库缓存
	 * @param minute
	 *            缓存时间
	 * */
	public <T, PK> void addRequestToQueue(final Handler handler, int method,
			String url, Map<String, String> params,
			Map<String, String> headers, Class<T> clazz,
			final boolean useCache, final long minute) {
		try {
			if (useCache && minute > 0) {
				// 设置缓存时间
				long millis = minute * 60 * 1000;
				DatabaseCacheHelper.getHelper(mContext).setCacheMillis(millis);

				index = DatabaseCacheHelper.getHelper(mContext)
						.generateCacheIndex(method, url, params, headers);
				boolean hasCache = DatabaseCacheHelper.getHelper(mContext)
						.hasCache(index);
				if (hasCache) {
					Message message = handler.obtainMessage();
					message.what = ResponseCode.SUCCESS;
					message.obj = DatabaseCacheHelper.getHelper(mContext)
							.getCacheDatas(clazz, index);
					handler.sendMessage(message);
					XLog.i(TAG, "<-------------------> use cache");
					return;
				}
			}

			BaseErrorListener errorListener = new BaseErrorListener(handler,
					ResponseCode.ERROR);
			Listener<T> listener = new Listener<T>() {

				@Override
				public void onResponse(T response) {
					// TODO Auto-generated method stub
					XLog.i(TAG, "response result : " + response);
					if (useCache && minute > 0) {
						DatabaseCacheHelper.getHelper(mContext).cacheDatas(
								response, index);
						XLog.i(TAG, "<-------------------> cache data");
					}
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
			// 关闭volley的缓存功能
			request.setShouldCache(false);
			request.setTag(mTag);
			// Setting Request Timeout
			// request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1,
			// 1.0f));
			XLog.i(TAG, "request url : " + url);
			XLog.i(TAG, "request params : " + params);
			XLog.i(TAG, "request headers : " + headers);
			mRequestQueue.add(request);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cancel() {
		mRequestQueue.cancelAll(mTag);
	}
}

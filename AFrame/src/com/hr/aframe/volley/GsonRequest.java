package com.hr.aframe.volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class GsonRequest<T> extends Request<T> {
	/** Default charset for JSON request. */
	protected static final String PROTOCOL_CHARSET = "utf-8";
	/**
	 * Gson parser
	 */
	private final Gson mGson;

	/**
	 * Class type for the response
	 */
	private final Class<T> mClass;

	/**
	 * Callback for response delivery
	 */
	private final Listener<T> mListener;

	private static Map<String, String> mRequestHeaders = new HashMap<String, String>();
	private static Map<String, String> mRequestParams = new HashMap<String, String>();

	/**
	 * 设置访问自己服务器时必须传递的参数，密钥等
	 */
	static {
		// mRequestHeaders.put("APP-Key", "LBS-AAA");
		// mRquestParams.put("APP-Secret", "ad12msa234das232in");
	}

	public GsonRequest(int method, String url, Class<T> clazz,
			Listener<T> listener, ErrorListener errorListener) {

		super(method, url, errorListener);
		this.mClass = clazz;
		this.mListener = listener;
		mGson = new Gson();

	}

	public GsonRequest(int method, String url, Class<T> clazz,
			Listener<T> listener, ErrorListener errorListener,
			Map<String, String> params, Map<String, String> headers) {
		this(method, url, clazz, listener, errorListener);
		if (null != params)
			mRequestParams.putAll(params);
		if (null != headers)
			mRequestHeaders.putAll(headers);
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers,
							PROTOCOL_CHARSET));
			return Response.success(mGson.fromJson(json, mClass),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		// TODO Auto-generated method stub
		return mRequestHeaders;
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		// TODO Auto-generated method stub
		return mRequestParams;
	}
}

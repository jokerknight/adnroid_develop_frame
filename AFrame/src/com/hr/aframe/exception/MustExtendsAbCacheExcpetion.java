package com.hr.aframe.exception;

import com.hr.aframe.util.XLog;

public class MustExtendsAbCacheExcpetion extends Exception {
	private static final String TAG = MustExtendsAbCacheExcpetion.class
			.getSimpleName();

	/**
	 * 
	 */
	private static final long serialVersionUID = 2676333560109257408L;

	public MustExtendsAbCacheExcpetion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MustExtendsAbCacheExcpetion(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
		// TODO Auto-generated constructor stub
		XLog.e(TAG, detailMessage, throwable);
	}

	public MustExtendsAbCacheExcpetion(String detailMessage) {
		super(detailMessage);
		// TODO Auto-generated constructor stub
		XLog.e(TAG, detailMessage);
	}

	public MustExtendsAbCacheExcpetion(Throwable throwable) {
		super(throwable);
		// TODO Auto-generated constructor stub
	}

}

package com.hr.aframe.exception;

import com.hr.aframe.util.XLog;

public class InvalidParamsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5122277869455322374L;
	public static final String TAG = InvalidParamsException.class
			.getSimpleName();

	public InvalidParamsException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvalidParamsException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
		// TODO Auto-generated constructor stub
	}

	public InvalidParamsException(String detailMessage) {
		super(detailMessage);
		// TODO Auto-generated constructor stub
		XLog.e(TAG, detailMessage);
	}

	public InvalidParamsException(Throwable throwable) {
		super(throwable);
		// TODO Auto-generated constructor stub
	}

}

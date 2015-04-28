package com.hr.aframe.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;

/**
 * 栈管理工具
 * 
 * @author xhr activity
 * ***/
public class ActivityStackManager {
	private static List<Activity> mActivityStack = Collections
			.synchronizedList(new LinkedList<Activity>());
	private static ActivityStackManager mStackManager;

	private ActivityStackManager() {
	}

	/**
	 * 单列模式获取屏幕管理器对象
	 * **/
	public static ActivityStackManager getService() {
		if (mStackManager == null) {
			mStackManager = new ActivityStackManager();
		}
		return mStackManager;
	}

	/**
	 * 从栈顶移除activity
	 * **/
	public void popActivity() {
		int size = mActivityStack.size();
		if (size > 0) {
			Activity ac = mActivityStack.remove(size - 1);
			if (null != ac) {
				ac.finish();
				ac = null;
			}
		}
	}

	/**
	 * 移除指定的activity
	 * */
	public void popActivity(Activity activity) {
		if (activity != null && mActivityStack.size() > 0) {
			mActivityStack.remove(activity);
			/*
			 * Activity.finish() Call this when your activity is done and should
			 * be closed. 在你的activity动作完成的时候，或者Activity需要关闭的时候，调用此方法。
			 * 当你调用此方法的时候，系统只是将最上面的Activity移出了栈
			 * ，并没有及时的调用onDestory（）方法，其占用的资源也没有被及时释放。因为移出了栈
			 * ，所以当你点击手机上面的“back”按键的时候，也不会再找到这个Activity。
			 */
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 得到栈顶activity
	 * */
	public Activity getStacktopActivity() {
		int size = mActivityStack.size();
		if (size > 0) {
			Activity activity = mActivityStack.get(size - 1);
			return activity;
		}
		return null;
	}

	/**
	 * 入栈
	 * */
	public void pushActivity(Activity activity) {
		mActivityStack.add(activity);
	}

	/**
	 * 将activity移植栈顶，在其上面的activity将被移除
	 * */
	public void moveToStacktop(Class cls) {
		while (true) {
			Activity activity = getStacktopActivity();
			if (activity == null) {
				break;
			}
			if (activity.getClass().equals(cls)) {
				break;
			}
			popActivity(activity);
		}
	}

	/**
	 * 清空栈
	 * */
	public void popAll() {
		while (true) {
			Activity activity = getStacktopActivity();
			if (activity == null) {
				break;
			}
			popActivity(activity);
		}
	}

	public void syncStackElement(Activity activity) {
		if (activity != null && mActivityStack.size() > 0) {
			mActivityStack.remove(activity);
			activity = null;
		}
	}
}

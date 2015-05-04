package com.hr.aframe.base;

import java.lang.reflect.Field;
import java.util.UUID;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.hr.aframe.R;
import com.hr.aframe.util.ActivityStackManager;

public abstract class AbBaseFragmentActivity extends FragmentActivity {
	protected Context mContext;
	protected BaseGsonService mBaseGsonService;

	protected abstract int getLayoutResID();

	protected abstract void initViews();

	protected abstract void initValues();

	protected abstract void initDatas();

	protected abstract void initViewEventListener();

	protected abstract void registerBroadcast();

	protected abstract void unregisterBroadcast();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		/**
		 * 添加进入动画
		 * */
		this.overridePendingTransition(R.anim.anim_activity_enter,
				R.anim.anim_activity_leave);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResID());
		mContext = this;
		mBaseGsonService = new BaseGsonService(this, generateTag());
		ActivityStackManager.getService().pushActivity(this);
		initValues();
		autoInjectAllField();
		initViews();
		initDatas();
		initViewEventListener();
	}

	/**
	 * <li>通过注解映射方式初始化组件<li/>
	 * 使用举例 ：<br/>
	 * 
	 * @ViewInject(R.id.xxx) <br/>
	 *                       private TextView mTextView;
	 */
	public void autoInjectAllField() {
		try {
			Class<?> clazz = this.getClass();
			Field[] fields = clazz.getDeclaredFields();// 获得Activity中声明的字段
			for (Field field : fields) {
				// 查看这个字段是否有我们自定义的注解类标志的
				if (field.isAnnotationPresent(ViewInject.class)) {
					ViewInject inject = field.getAnnotation(ViewInject.class);
					int id = inject.value();
					if (id > 0) {
						field.setAccessible(true);
						field.set(this, this.findViewById(id));// 给我们要找的字段设置值
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		registerBroadcast();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		unregisterBroadcast();
	}

	/**
	 * 添加退出动画
	 * */
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		this.overridePendingTransition(R.anim.anim_activity_enter,
				R.anim.anim_activity_leave);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mBaseGsonService.cancel();
		ActivityStackManager.getService().syncStackElement(this);
	}

	private String generateTag() {
		return this.getClass().getSimpleName() + UUID.randomUUID().toString();
	}
}

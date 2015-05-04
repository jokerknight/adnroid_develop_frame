package com.hr.aframe.base;

import java.lang.reflect.Field;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class AbBaseFragment extends Fragment {
	private View mFragmentView;

	protected abstract int getLayoutResID();

	protected abstract void initViews();

	protected abstract void initValues();

	protected abstract void initDatas();

	protected abstract void initViewEventListener();

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (null == mFragmentView) {
			mFragmentView = inflater
					.inflate(getLayoutResID(), container, false);
			initValues();
			autoInjectAllField();
			initViews();
			initDatas();
			initViewEventListener();
		}
		ViewGroup mViewGroup = (ViewGroup) mFragmentView.getParent();
		if (null != mViewGroup) {
			mViewGroup.removeView(mFragmentView);
		}
		return mFragmentView;
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
						field.set(this, mFragmentView.findViewById(id));// 给我们要找的字段设置值
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
}

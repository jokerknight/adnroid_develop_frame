package com.hr.aframe.base;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class AbBaseAdapter<T> extends BaseAdapter {
	protected List<T> mList;
	protected Context mContext;

	public abstract View getAbView(Context context, int position,
			View convertView, ViewGroup parent);

	public AbBaseAdapter(Context context, List<T> list) {
		this.mContext = context;
		this.mList = list;
	}

	public void addAllToFrist(List<T> list) {
		synchronized (mList) {
			mList.addAll(0, list);
			notifyDataSetChanged();
		}
	}

	public void addAll(List<T> list) {
		synchronized (mList) {
			mList.addAll(list);
			notifyDataSetChanged();
		}
	}

	public void remove(List<T> list) {
		synchronized (mList) {
			mList.removeAll(list);
			notifyDataSetChanged();
		}
	}

	public void clearn() {
		synchronized (mList) {
			mList.clear();
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return null == mList ? 0 : mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getAbView(mContext, position, convertView, parent);
	}
}

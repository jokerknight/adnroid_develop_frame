package com.hr.aframe.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hr.aframe.base.AbBaseAdapter;

public class TestAdapter<T> extends AbBaseAdapter<T> {

	public TestAdapter(Context context, List<T> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getAbView(Context context, int position, View convertView,
			ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView t = new TextView(context);
		t.setText(mList.get(position) + "");
		return t;
	}

}

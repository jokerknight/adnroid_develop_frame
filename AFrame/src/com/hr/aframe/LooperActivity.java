package com.hr.aframe;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.hr.aframe.base.BaseActivity;
import com.hr.aframe.base.BaseCyclePagerAdapter;
import com.hr.aframe.base.ViewInject;
import com.hr.aframe.bean.ImageBean;
import com.hr.aframe.util.ImageLoaderUtils;
import com.hr.aframe.util.XLog;

public class LooperActivity extends BaseActivity {
	private static final String TAG = LooperActivity.class.getSimpleName();
	@ViewInject(R.id.loppervper)
	private ViewPager mViewPager;
	private List<ImageBean> urls;

	@Override
	protected int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.activity_lopper;
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		CyclePagerAdapter mCyclePagerAdapter = new CyclePagerAdapter(mViewPager, urls);
		mViewPager.setAdapter(mCyclePagerAdapter);
		mCyclePagerAdapter.startCycle();
	}

	class CyclePagerAdapter extends BaseCyclePagerAdapter<ImageBean> {

		public CyclePagerAdapter(ViewPager vp, List<ImageBean> lst) {
			super(vp, lst);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected List<View> getViewList(List<ImageBean> lst) {
			// TODO Auto-generated method stub
			List<View> views = new ArrayList<View>();
			for (int i = 0; i < lst.size(); i++) {
				ImageView iv = new ImageView(mContext);
				iv.setTag(lst.get(i));
				ImageLoaderUtils.displayImage(lst.get(i).url, iv, null, null);
				views.add(iv);
			}
			return views;
		}

		@Override
		protected void setCurrentDot(int position) {
			// TODO Auto-generated method stub
			XLog.i(TAG, "dot position: " + position);
		}

	}

	@Override
	protected void initValues() {
		// TODO Auto-generated method stub
		urls = new ArrayList<ImageBean>();
		ImageBean ib = new ImageBean();
		ib.url = "http://img0.bdstatic.com/img/image/2043d07892fc42f2350bebb36c4b72ce1409212020.jpg";
		urls.add(ib);

		ib = new ImageBean();
		ib.url = "http://d.hiphotos.baidu.com/image/pic/item/78310a55b319ebc4b455b4168026cffc1e17160b.jpg";
		urls.add(ib);
	}

	@Override
	protected void initDatas() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initViewEventListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void registerBroadcast() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void unregisterBroadcast() {
		// TODO Auto-generated method stub

	}

}

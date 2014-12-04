package com.storeassistant.adapter;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerAdapter extends PagerAdapter {

	private ArrayList<View> mListViews; 
	private List<String> titles;
	
	public ViewPagerAdapter(ArrayList<View> mListViews) {
		super();
		this.mListViews = mListViews;
		
	}

	@Override
	public void destroyItem(View container, int postion, Object o) {
		if(mListViews.size() == 2){
			return;
		}
		// TODO Auto-generated method stub
		View view = mListViews.get(postion % mListViews.size());
		((ViewPager)container).removeView(view);
	}

	@Override
	public void finishUpdate(View arg0) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return super.getItemPosition(object);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		View view = mListViews.get(position % mListViews.size());
		if(view.getParent()!=null){
			((ViewGroup)view.getParent()).removeView(view);
		}
		((ViewPager)container).addView(view);
		return view;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
		// TODO Auto-generated method stub

	}

	

}

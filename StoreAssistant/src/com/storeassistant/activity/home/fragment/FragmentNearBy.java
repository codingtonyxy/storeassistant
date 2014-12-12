package com.storeassistant.activity.home.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.storeassistant.R;
import com.storeassistant.activity.home.MainActivity;
import com.storeassistant.component.ViewPagerComponent;
import com.storeassistant.util.MyImageLoader;

public class FragmentNearBy extends Fragment {
	ViewPagerComponent vpc = null;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_nearby, container, false);
		TextView textView = (TextView)view.findViewById(R.id.textView_fragment_nearby);
		textView.setText("FragmentNearBy");
		
		ViewPager containerViewPager = (ViewPager)view.findViewById(R.id.viewpager_nearby);
		LinearLayout dotContainer = (LinearLayout)view.findViewById(R.id.scroll_point_container_nearby);
		vpc = new ViewPagerComponent(containerViewPager, dotContainer, getViewList(), false, true,
				false, 3000, 3000, true);
		vpc.startPager();
		return view;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	public List<View> getViewList(){
		ArrayList<View> viewList = new ArrayList<View>();
		int len = MainActivity.imageList.size();
		for(int i=0;i<len;i++){
			String url = MainActivity.imageList.get(i);
			ImageView iv=new ImageView(getActivity());
			LayoutParams param = new LayoutParams(720, 160);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setLayoutParams(param);
			MyImageLoader.getImageLoader(getActivity()).displayImage(url, iv);
			viewList.add(iv);
		}
		return viewList;
	}

}

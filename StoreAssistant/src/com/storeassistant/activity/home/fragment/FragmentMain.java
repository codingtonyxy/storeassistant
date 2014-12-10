package com.storeassistant.activity.home.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.storeassistant.R;
import com.storeassistant.activity.home.MainActivity;
import com.storeassistant.appInfo.MyConstants;
import com.storeassistant.component.ViewPagerComponent;

public class FragmentMain extends Fragment {
	
	private LinearLayout scrollPointContainer;
	private ViewPager viewPager;
	private ViewPagerComponent vpc = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		viewPager=(ViewPager)view.findViewById(R.id.main_viewpager);
		scrollPointContainer = (LinearLayout)view.findViewById(R.id.scroll_point_container);
		
		vpc = new ViewPagerComponent(MainActivity.imageList, viewPager, scrollPointContainer, true, true, false, 3000, 3000);
		vpc.startPager();
		
		//获取市场名称
		TextView marketNameTv = (TextView)view.findViewById(R.id.textview_market_name_fragment_main);
		marketNameTv.setText(MyConstants.MARKET_NAME == null ? "荷花池" : MyConstants.MARKET_NAME);
		
		View tab1 = view.findViewById(R.id.tab_home_bt1);
		View tab2 = view.findViewById(R.id.tab_home_bt2);
		View tab3 = view.findViewById(R.id.tab_home_bt3);
		View tab4 = view.findViewById(R.id.tab_home_bt4);
		View tab5 = view.findViewById(R.id.tab_home_bt5);
		View tab6 = view.findViewById(R.id.tab_home_bt6);
		View tab7 = view.findViewById(R.id.tab_home_bt7);
		View tab8 = view.findViewById(R.id.tab_home_bt8);
		View tab9 = view.findViewById(R.id.tab_home_bt9);
		View tab10 = view.findViewById(R.id.tab_home_bt10);
		View tab11 = view.findViewById(R.id.tab_home_bt11);
		View tab12 = view.findViewById(R.id.tab_home_bt12);
		
		OnTouchListener onTouchListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(v.getContext(), R.anim.anim_home_rect_scale);
					v.startAnimation(hyperspaceJumpAnimation);
				}
				return false;
			}
		};
		
		tab1.setOnTouchListener(onTouchListener);
		tab2.setOnTouchListener(onTouchListener);
		tab3.setOnTouchListener(onTouchListener);
		tab4.setOnTouchListener(onTouchListener);
		tab5.setOnTouchListener(onTouchListener);
		tab6.setOnTouchListener(onTouchListener);
		tab7.setOnTouchListener(onTouchListener);
		tab8.setOnTouchListener(onTouchListener);
		tab9.setOnTouchListener(onTouchListener);
		tab10.setOnTouchListener(onTouchListener);
		tab11.setOnTouchListener(onTouchListener);
		tab12.setOnTouchListener(onTouchListener);
		
		return view;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(vpc != null){
			vpc.activeTimer();
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if(vpc != null){
			vpc.inActiveTimer();
		}
	}
	
	@Override
	public void onStop() {
		super.onStop();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	
}

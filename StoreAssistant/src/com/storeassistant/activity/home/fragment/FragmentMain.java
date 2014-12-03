package com.storeassistant.activity.home.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.storeassistant.R;
import com.storeassistant.adapter.ViewPagerAdapter;
import com.storeassistant.appInfo.MyConstants;
import com.storeassistant.util.AsyImageLoaderNoParams;
import com.storeassistant.util.CallbackImplNoParams;
import com.storeassistant.util.TimerUtil;
import com.storeassistant.util.ViewPagerComponent;

public class FragmentMain extends Fragment {
	
	private AsyImageLoaderNoParams loader=new AsyImageLoaderNoParams();
	private LinearLayout scrollPointContainer;
	private ScrollView scrollViewTools;
	private ArrayList<View> viewPagerViews = new ArrayList<View>();
	private ViewPager viewPager;
	private ImageView[] circles;
	private ViewPagerAdapter viewPagerAdapter;
	private  List<String> pagerImageUrlList = new ArrayList<String>();
	private final int msgViewPager = 1;
	private final int msgAutoPager = 2;
	private int currentPagerIndex = 0;
	private boolean isAutoPager = true;
	private Timer timer = null;
	private boolean isInited = false;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		isInited = true;
		
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		viewPager=(ViewPager)view.findViewById(R.id.main_viewpager);
		scrollPointContainer = (LinearLayout)view.findViewById(R.id.scroll_point_container);
		scrollViewTools = (ScrollView)view.findViewById(R.id.scrollView_tools);
		
		Message msg = handler.obtainMessage();
		msg.what = msgViewPager;
		msg.sendToTarget();
		
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
		if(!isInited){
			pagerImageUrlList.add(MyConstants.URL_RES+"/image/hp00.png");
			pagerImageUrlList.add(MyConstants.URL_RES+"/image/hp01.png");
			pagerImageUrlList.add(MyConstants.URL_RES+"/image/hp00.png");
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		autoPagerTask.cancel();
		if(timer != null){
			timer.cancel();
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	private Handler handler = new Handler(){
    	public void handleMessage(Message msg) {
    		switch (msg.what) {
			case msgViewPager:
				try {
						if(pagerImageUrlList.size()==0)
						{	
							Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
						}else {
							int len = pagerImageUrlList.size();
							circles=new ImageView[len];
							for(int i=0;i<len;i++){
								String url = pagerImageUrlList.get(i);
								ImageView iv=new ImageView(getActivity());
								LayoutParams param = new LayoutParams(720, 160);
								iv.setScaleType(ScaleType.FIT_XY);
								iv.setLayoutParams(param);
								CallbackImplNoParams cinp=new CallbackImplNoParams(iv);
								iv.setImageBitmap(loader.loadDrawable(url, cinp,2));
								viewPagerViews.add(iv);
								
								//СԲȦ
								RelativeLayout pointLayout=new RelativeLayout(getActivity());
								RelativeLayout.LayoutParams rl=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
								pointLayout.setLayoutParams(rl);
								ImageView circle=new ImageView(getActivity());
								RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(10,9);
								lp.setMargins(15, 0, 0, 0);
								circle.setLayoutParams(lp);
								circles[i]=circle;
								if (i == 0)
								{
									circles[i].setBackgroundResource(R.drawable.ic_focus);
								} else
								{
									circles[i].setBackgroundResource(R.drawable.ic_focus_select);
								}
								pointLayout.addView(circle);
								scrollPointContainer.addView(pointLayout);
								
							}
							viewPagerAdapter=new ViewPagerAdapter(viewPagerViews);
							viewPager.setAdapter(viewPagerAdapter);
							viewPager.setOnPageChangeListener(pageChangeListener);
							timer = TimerUtil.getTimer("autoPagerTimer");
							TimerUtil.schedule(timer, autoPagerTask, 3000, 3000);
						}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
				
			case msgAutoPager:
				viewPager.setCurrentItem(msg.arg1, true);
			default:
				break;
			}
    	};
    };
    
    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int arg0) {
			int temIn = arg0%circles.length;
			for (int i = 0; i < circles.length; i++) 
			{  
				circles[temIn].setBackgroundResource(R.drawable.ic_focus);
				if (temIn != i) {  
					circles[i].setBackgroundResource(R.drawable.ic_focus_select);  
				}  
			}
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			currentPagerIndex = arg0;
		}
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	TimerTask autoPagerTask = new TimerTask() {
		@Override
		public void run() {
			if (isAutoPager) {
				Message msg = handler.obtainMessage();
				msg.what = msgAutoPager;
				msg.arg1 = currentPagerIndex++;
				msg.sendToTarget();
			}
		}
	};
	
	public void onSaveInstanceState(Bundle bundle) {
		
	}
	
}

package com.storeassistant.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.storeassistant.R;
import com.storeassistant.util.MyImageLoader;
import com.storeassistant.util.TimerUtil;

public class ViewPagerComponent {
	
	private List<View> viewList = null;
	private boolean isShowDot = true;
	private boolean isAutoPage = true;
	private boolean isLoopEndLess = true;
	private int currentPagerIndex = 0;
	private ViewPager viewPager = null;
	private LinearLayout dotContainer;
	private Timer timer = null;
	private TimerTask autoPagerTask = null;
	private Handler handler = null;
	private final int HANDLER_MSG_PAGE = 1;
	private long pagerDelayTime = 4000;
	private long pagerTime = 4000;
	private boolean isTimerPause = false;
	private ImageView[] circles;
	private volatile boolean isTouching = false;
	private boolean isSaveInDisk = false;
	
	public ViewPagerComponent(List<String> viewList, ViewPager viewPager, LinearLayout dotContainer, 
			boolean isShowDot, boolean isAutoPage, boolean isLoopEndLess, long pagerDelayTime, long pagerTime, boolean isSaveInDisk){
		//加载图片
		this.viewList = new ArrayList<View>(viewList.size());
		int width = viewPager.getWidth();
		int height = viewPager.getHeight();
		ImageView iv = null;
		DisplayImageOptions options = null;
		
		this.viewPager = viewPager;
		this.isShowDot = isShowDot;
		this.isAutoPage = isAutoPage;
		this.isLoopEndLess = isLoopEndLess;
		this.pagerDelayTime = pagerDelayTime;
		this.pagerTime = pagerTime;
		
		this.handler = new ViewPagerHandler();
		this.timer = TimerUtil.getTimer("ViewPagerComponent timer");
		this.autoPagerTask = new AutoTimerTask();
		this.isSaveInDisk = isSaveInDisk;
		
		if(isSaveInDisk){
			options = MyImageLoader.displayImageOption_cache_in_memory_and_disk;
		}
		for (String url : viewList) {
			iv=new ImageView(viewPager.getContext());
			LayoutParams param = new LayoutParams(width, height);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setLayoutParams(param);
			MyImageLoader.getImageLoader(viewPager.getContext()).displayImage(url, iv);
			this.viewList.add(iv);
		}
		
		//dot
		if(this.isShowDot){
			this.dotContainer = dotContainer;
			initDot(this.dotContainer, viewList.size());
			this.viewPager.setOnPageChangeListener(new OnPageChangeListener() {
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
			});
		}
		
		//gesture detect
		viewPager.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					isTouching = true;
				}else if(event.getAction() == MotionEvent.ACTION_UP){
					isTouching = false;
				}
				return false;
			}
		});
	}
	
	public ViewPagerComponent(ViewPager viewPager, LinearLayout dotContainer, List<View> viewList, 
			boolean isShowDot, boolean isAutoPage, boolean isLoopEndLess, long pagerDelayTime, long pagerTime, boolean isSaveInDisk){
		
		this.viewPager = viewPager;
		this.viewList = viewList;
		this.isShowDot = isShowDot;
		this.isAutoPage = isAutoPage;
		this.isLoopEndLess = isLoopEndLess;
		this.pagerDelayTime = pagerDelayTime;
		this.pagerTime = pagerTime;
		
		this.handler = new ViewPagerHandler();
		this.timer = TimerUtil.getTimer("ViewPagerComponent timer");
		this.autoPagerTask = new AutoTimerTask();
		this.isSaveInDisk = isSaveInDisk;
		
		//dot
		if(isShowDot){
			this.dotContainer = dotContainer;
			initDot(this.dotContainer, viewList.size());
			this.viewPager.setOnPageChangeListener(new OnPageChangeListener() {
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
			});
		}
		
		//gesture detect
		viewPager.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					isTouching = true;
				}else if(event.getAction() == MotionEvent.ACTION_UP){
					isTouching = false;
				}
				return false;
			}
		});
	}
	
	
	private void initDot(LinearLayout dotContainer, int dotCount) {
		circles=new ImageView[dotCount];
		for (int i = 0; i < dotCount; i++) {
			RelativeLayout pointLayout=new RelativeLayout(dotContainer.getContext());
			RelativeLayout.LayoutParams rl=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			pointLayout.setLayoutParams(rl);
			ImageView circle=new ImageView(dotContainer.getContext());
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
			dotContainer.addView(pointLayout);
		}
	}


	public void startPager(){
		viewPager.setAdapter(new ViewPageAdapter(viewList, isLoopEndLess));
		if(timer == null || handler == null){
			return;
		}
		TimerUtil.scheduleAtFixedRate(timer, autoPagerTask, pagerDelayTime, pagerTime);
	}
	
	public void shutDownPagerTimer(){
		if(timer == null || handler == null){
			return;
		}
		timer.cancel();
		timer = null;
	}
	
	public void inActiveTimer(){
		isTimerPause = true;
	}
	
	public void activeTimer(){
		isTimerPause = false;
	}
	
	class AutoTimerTask extends TimerTask {
		@Override
		public void run() {
			if (!isTimerPause && handler != null && isAutoPage && !isTouching) {
				Message msg = handler.obtainMessage();
				msg.what = HANDLER_MSG_PAGE;
				msg.arg1 = currentPagerIndex++;
				msg.sendToTarget();
			}
		}
	}
	
	class ViewPagerHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == HANDLER_MSG_PAGE){
				viewPager.setCurrentItem(currentPagerIndex, true);
			}
		}
	}
	
	
}


class ViewPageAdapter extends PagerAdapter{

	private List<View> viewList = null;
	private boolean isLoopEndLess = true;
	
	public ViewPageAdapter(List<View> viewList,  boolean isLoopEndLess){
		this.viewList = viewList;
		this.isLoopEndLess = isLoopEndLess;
	}
	
	@Override
	public int getCount() {
		if(viewList == null){
			return 0;
		}
		int count = viewList.size();
		if(this.isLoopEndLess && count > 1){
			return 1000;
		}
		return count;
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return view == obj;
	}
	
	@Override
	public int getItemPosition(Object object) {
		return viewList.indexOf(object);
	}
	
	@Override
	public Object instantiateItem(View container, int position) {
		int index = position % viewList.size();
		View view = viewList.get(index);
		if(view.getParent()!=null){
			((ViewGroup)view.getParent()).removeView(view);
		}
		((ViewPager)container).addView(view);
		return view;
	}
	
	@Override
	/**
	 * 0123456
	 * 0101010101
	 */
	public void destroyItem(View container, int position, Object object) {
		int count = viewList.size();
		if(count <= 3){
			return;
		}
		int index = position % count;
		View view = viewList.get(index);
		if(view == null){
			return;
		}
		((ViewPager)container).removeView(view);
	}
	
	
	

}

package com.storeassistant.util;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerComponent {
	
	private List<View> viewList = null;
	private boolean isShowDot = true;//是否显示点
	private boolean isAutoPage = true;//是否自动翻页
	private boolean isLoopEndLess = true;//是否无限循环
	private int currentPagerIndex = 0;
	private ViewPager viewPager = null;
	private Timer timer = null;
	private TimerTask autoPagerTask = null;
	private Handler handler = null;
	private final int HANDLER_MSG_PAGE = 1;//自动翻页消息
	private long pagerDelayTime = 4000;
	private long pagerTime = 4000;
	
	public ViewPagerComponent(ViewPager viewPager, List<View> viewList, 
			boolean isShowDot, boolean isAutoPage, boolean isLoopEndLess, long pagerDelayTime, long pagerTime){
		
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
		
	}
	
	
	public void startPager(boolean isAutoPager){
		viewPager.setAdapter(new ViewPageAdapter(viewList, isAutoPage, isLoopEndLess));
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
	}
	
	class AutoTimerTask extends TimerTask {
		@Override
		public void run() {
			if (handler != null && isAutoPage) {
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
			if(msg.what == HANDLER_MSG_PAGE){//自动翻页
				viewPager.setCurrentItem(currentPagerIndex, true);
			}
		}
	}
	
	
}


class ViewPageAdapter extends PagerAdapter{

	private List<View> viewList = null;
	private boolean isAutoPage = true;
	private boolean isLoopEndLess = true;
	
	public ViewPageAdapter(List<View> viewList,  boolean isAutoPage, boolean isLoopEndLess){
		this.viewList = viewList;
		this.isAutoPage = isAutoPage;
		this.isLoopEndLess = isLoopEndLess;
	}
	
	@Override
	public int getCount() {
		if(viewList == null){
			return 0;
		}
		int count = viewList.size();
		if(count <= 3){//此处无限循环可能会出问题（初始化，销毁出错），所以限制循环次数
			return count;
		}
		if(this.isLoopEndLess){
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
	public void destroyItem(View container, int position, Object object) {
		int count = viewList.size();
		if(count <= 3){//小于3个图片的时候不无限循环，也不进行销毁
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

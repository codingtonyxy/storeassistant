package com.storeassistant.activity;

import java.util.Timer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.storeassistant.R;
import com.storeassistant.activity.base.BaseActivity;
import com.storeassistant.appInfo.ImageUrl;
import com.storeassistant.appInfo.MyConstants;
import com.storeassistant.task.timertask.TimerTask_toMainActivity;
import com.storeassistant.util.MyImageLoader;
import com.storeassistant.util.TimerUtil;

/**
 * 启动界面
 * @author codingtony
 */
public class StartActivity extends BaseActivity{
	
	public final int what_msg_showTimer = 1;
	public int msg_showCount = MyConstants.MSG_SHOW_COUNT_TO_INDEX;
	public Handler handler_toIndex = null;
	public Timer myTimer = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		
		ImageView startImageView = (ImageView)findViewById(R.id.img_start);
		//初始化图片加载器
		MyImageLoader.displayLatestImageCacheForNoNet(this, ImageUrl.IMAGE_START_URL, startImageView);
		
		initHander();
		
		myTimer = TimerUtil.getTimer("toIndex");
	}

	private void initHander() {
		Looper looper = this.getMainLooper();
		final TextView textViewShowTimer = (TextView)findViewById(R.id.textView_showTimerToIndex);
		handler_toIndex = new Handler(looper){
			public void dispatchMessage(Message msg){
				switch (msg.what) {
				case what_msg_showTimer:
					textViewShowTimer.setText("倒计时"+msg_showCount+"秒");
					msg_showCount --;
					break;

				default:
					break;
				}
			};
		};
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		TimerUtil.schedule(myTimer, new TimerTask_toMainActivity(this), 1000, 1000);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		myTimer.cancel();
		handler_toIndex.removeMessages(what_msg_showTimer);
	}
	
	

}

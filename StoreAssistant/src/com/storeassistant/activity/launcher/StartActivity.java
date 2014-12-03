package com.storeassistant.activity.launcher;

import java.util.Timer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.storeassistant.R;
import com.storeassistant.activity.base.BaseActivity;
import com.storeassistant.appInfo.ImageUrl;
import com.storeassistant.appInfo.MyConstants;
import com.storeassistant.listener.StartImageIistener;
import com.storeassistant.task.timertask.TimerTask_toMainActivity;
import com.storeassistant.util.TimerUtil;

/**
 * 启动界面
 * @author codingtony
 */
public class StartActivity extends BaseActivity{
	
	public final int msg_showTimer = 1;
	public int msg_showCount = MyConstants.MSG_SHOW_COUNT_TO_INDEX;
	public Handler handler_toIndex = null;
	public Timer myTimer = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		
		ImageLoader imageLoader = ImageLoader.getInstance();
		Builder builder = new ImageLoaderConfiguration.Builder(this);
		builder.diskCacheSize(10*1024*1024);
		imageLoader.init(builder.build());
		
		com.nostra13.universalimageloader.core.DisplayImageOptions.Builder builder2 = new DisplayImageOptions.Builder();
		builder2.cacheOnDisk(true);
		ImageView startImageView = (ImageView)findViewById(R.id.img_start);
		imageLoader.displayImage(ImageUrl.IMAGE_START_URL, startImageView, builder2.build(), new StartImageIistener());
		Log.i("StartActivity", "StartActivity onCrate end");
		
		myTimer = TimerUtil.getTimer("toIndex");
		TimerUtil.schedule(myTimer, new TimerTask_toMainActivity(this), 0, 1000);
		
		initHander();
	}

	private void initHander() {
		Looper looper = this.getMainLooper();
		handler_toIndex = new Handler(looper){
			public void dispatchMessage(Message msg){
				switch (msg.what) {
				case msg_showTimer:
					TextView textViewShowTimer = (TextView)findViewById(R.id.textView_showTimerToIndex);
					textViewShowTimer.setText("倒计时"+msg_showCount+"秒");
					msg_showCount --;
					break;

				default:
					break;
				}
			};
		};
		
		
	}
	
	

}

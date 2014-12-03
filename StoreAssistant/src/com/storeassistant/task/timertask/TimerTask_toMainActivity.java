package com.storeassistant.task.timertask;

import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.os.Message;

import com.storeassistant.activity.home.MainActivity;
import com.storeassistant.activity.launcher.StartActivity;
import com.storeassistant.appInfo.MyConstants;

/**
 * ����ʱ�������ҳ
 * @author Administrator
 *
 */
public class TimerTask_toMainActivity extends TimerTask{
	
	private Context context = null;
	private long cTime = 0;
    public TimerTask_toMainActivity(Context context){
    	this.context = context;
    	this.cTime = System.currentTimeMillis();
    }
	@Override
	public void run() {
		try {
			//��ʾ����ʱ����
			StartActivity sa = (StartActivity)context;
			if(sa.msg_showCount >= 0){
				Message msg = sa.handler_toIndex.obtainMessage();
				msg.what = sa.msg_showTimer;
				sa.handler_toIndex.sendMessage(msg);
				return;
			}
			
			long now = System.currentTimeMillis();
			long delayTime = now - cTime;
			//��ת������ҳ
			if(delayTime >= MyConstants.MSG_SHOW_COUNT_TO_INDEX * 1000 && context != null){
				sa.myTimer.cancel();
				Intent intent = new Intent(context, MainActivity.class);
				context.startActivity(intent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

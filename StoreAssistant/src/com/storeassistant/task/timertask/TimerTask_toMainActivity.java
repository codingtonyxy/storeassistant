package com.storeassistant.task.timertask;

import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.os.Message;

import com.storeassistant.activity.StartActivity;
import com.storeassistant.activity.home.MainActivity;

/**
 * @author Administrator
 *
 */
public class TimerTask_toMainActivity extends TimerTask{
	
	private Context context = null;
    public TimerTask_toMainActivity(Context context){
    	this.context = context;
    }
	@Override
	public void run() {
		try {
			StartActivity sa = (StartActivity)context;
			if(sa.msg_showCount >= 0){
				Message msg = sa.handler_toIndex.obtainMessage();
				msg.what = sa.what_msg_showTimer;
				sa.handler_toIndex.sendMessage(msg);
				return;
			}
			sa.myTimer.cancel();
			Intent intent = new Intent(context, MainActivity.class);
			context.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

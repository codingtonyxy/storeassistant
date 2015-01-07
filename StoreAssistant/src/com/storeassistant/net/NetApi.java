package com.storeassistant.net;

import java.util.Map;

import android.os.Handler;
import android.os.Message;

import com.storeassistant.appInfo.MyConstants;

/**
 * @author Administrator
 */
public class NetApi {
	
	public static final String BASE_SERVER_URL = "http://"+MyConstants.SERVER_IP_PORT+"/";
	
	public static String getStartImgName(){
		String imgName = "start_img";
		return imgName;
	}
	
	public static void getMarketList(final Handler handler){
		new Thread(new Runnable() {
			@Override
			public void run() {
				Map map = NetUtil.get(BASE_SERVER_URL + "getmarketlist", null);
				Message msg = handler.obtainMessage();
				msg.what = 1;
				msg.obj = map.get("ret_info");
				handler.sendMessage(msg);
			}
		}).start();
	} 
	
}

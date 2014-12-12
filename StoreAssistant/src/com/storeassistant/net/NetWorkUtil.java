package com.storeassistant.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkUtil {

	/**
	 * 检查wifi/mobile网络是否连接
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}
	
	/**
	 * 检查wifi网络是否连接
	 * @return
	 */
	public static boolean isWifiNetworkConnected(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return (networkInfo != null && networkInfo.isConnected());
	}
	
	/**
	 * 检查mobile网络是否连接
	 * @return
	 */
	public static boolean isMobileNetworkConnected(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		return (networkInfo != null && networkInfo.isConnected());
	}

}

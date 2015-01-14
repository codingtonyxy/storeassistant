package com.storeassistant.appInfo;


public class MyConstants {
	
	public static boolean IS_LOCAL_TEST = false;
	
	public static String MARKET_NAME = "荷花池市场";
	public static int TAB_TO_SHOW = 0;
	
	public static String SERVER_IP = "211.149.206.34";
	public static String SERVER_PORT = "40102";
	
	private static String init_res_base_url = "http://211.149.206.34:81/sa";
	
	
	static
	{
		if(IS_LOCAL_TEST){
			
			SERVER_IP = "192.168.2.100";
			SERVER_PORT = "40102";
			
			init_res_base_url = "http://192.168.2.100:80/sa";
		}
	}
	
	public static final AppConfig APP_CONFIG = AppConfig.getMyAppConfig(init_res_base_url);
	public static final String SERVER_IP_PORT = SERVER_IP + ":"+SERVER_PORT;
	
	//加载界面倒计时时间
	public static final int MSG_SHOW_COUNT_TO_INDEX = 2;
	
	public static int width = 0;
	public static int height = 0;
	
	
	
	
	
	
}

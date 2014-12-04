package com.storeassistant.appInfo;

import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.storeassistant.R;

public class MyConstants {
	
	//图片加载器
	private static ImageLoader imageLoader = null;
	private static DisplayImageOptions displayImageOption_default = null;
	public static final int diskCacheSize = 100 * 1024 * 1024;
	
	public static final String IP_PORT_SERVER = "192.168.2.100:5000";
//	public static final String IP_RES = "192.168.2.100:80";
	public static final String IP_RES = "211.149.206.34:81";
	public static final String URL_RES = "http://" + IP_RES + "/sa";
	
	//加载界面倒计时时间
	public static final int MSG_SHOW_COUNT_TO_INDEX = 2;
	
	public static int width = 0;
	public static int height = 0;
	
	public static DisplayImageOptions getDisplayImageOptions_default(){
		if(displayImageOption_default == null){
			DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
			builder.cacheOnDisk(true);
			builder.cacheInMemory(true);
			//默认显示图片在size较大时显示效果不好
//			builder.showImageForEmptyUri(R.drawable.pic_loading);//默认显示图片
//			builder.showImageOnFail(R.drawable.pic_loading);//默认显示图片
//			builder.showImageOnLoading(R.drawable.pic_loading);//默认显示图片
			displayImageOption_default = builder.build();
		}
		return displayImageOption_default;
	}
	
	public static ImageLoader getImageLoader_default(Context context){
		if(imageLoader == null){
			imageLoader = ImageLoader.getInstance();
			Builder builder = new ImageLoaderConfiguration.Builder(context);
			builder.diskCacheSize(MyConstants.diskCacheSize);
			builder.defaultDisplayImageOptions(getDisplayImageOptions_default());
			imageLoader.init(builder.build());
		}
		return imageLoader;
	}
	
	
}

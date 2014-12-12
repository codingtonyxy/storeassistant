package com.storeassistant.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.utils.ImageSizeUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.storeassistant.net.NetWorkUtil;

/**
 * 图片加载器
 * @author codingtony
 *
 */
public class MyImageLoader {

	private static ImageLoader imageLoader = null;
	
	public static DisplayImageOptions displayImageOption_no_cache = null;
	public static DisplayImageOptions displayImageOption_cache_in_memory = null;
	public static DisplayImageOptions displayImageOption_cache_in_disk = null;
	public static DisplayImageOptions displayImageOption_cache_in_memory_and_disk = null;
	
	static{
		
		///////////////////////////////////////////////////
		if (displayImageOption_no_cache == null) {
			DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
			builder.cacheOnDisk(false);
			builder.cacheInMemory(false);
			// 默认显示图片在size较大时显示效果不好
			// builder.showImageForEmptyUri(R.drawable.pic_loading);//默认显示图片
			// builder.showImageOnFail(R.drawable.pic_loading);//默认显示图片
			// builder.showImageOnLoading(R.drawable.pic_loading);//默认显示图片
			displayImageOption_no_cache = builder.build();
		}
		
		/////////////////////////////////////////////////////
		if (displayImageOption_cache_in_memory == null) {
			DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
			builder.cacheOnDisk(false);
			builder.cacheInMemory(true);
			// 默认显示图片在size较大时显示效果不好
			// builder.showImageForEmptyUri(R.drawable.pic_loading);//默认显示图片
			// builder.showImageOnFail(R.drawable.pic_loading);//默认显示图片
			// builder.showImageOnLoading(R.drawable.pic_loading);//默认显示图片
			displayImageOption_cache_in_memory = builder.build();
		}
		
		/////////////////////////////////////////////////////
		if (displayImageOption_cache_in_disk == null) {
			DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
			builder.cacheOnDisk(true);
			builder.cacheInMemory(false);
			// 默认显示图片在size较大时显示效果不好
			// builder.showImageForEmptyUri(R.drawable.pic_loading);//默认显示图片
			// builder.showImageOnFail(R.drawable.pic_loading);//默认显示图片
			// builder.showImageOnLoading(R.drawable.pic_loading);//默认显示图片
			displayImageOption_cache_in_disk = builder.build();
		}
		
		/////////////////////////////////////////////////////
		if (displayImageOption_cache_in_memory_and_disk == null) {
			DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
			builder.cacheOnDisk(true);
			builder.cacheInMemory(true);
			// 默认显示图片在size较大时显示效果不好
			// builder.showImageForEmptyUri(R.drawable.pic_loading);//默认显示图片
			// builder.showImageOnFail(R.drawable.pic_loading);//默认显示图片
			// builder.showImageOnLoading(R.drawable.pic_loading);//默认显示图片
			displayImageOption_cache_in_memory_and_disk = builder.build();
		}
	}
	
	/**
	 * 默认缓存在内存
	 * @param context
	 * @return
	 */
	public static ImageLoader getImageLoader(Context context){
		if(imageLoader == null){
			imageLoader = ImageLoader.getInstance();
			ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context);
			builder.defaultDisplayImageOptions(displayImageOption_cache_in_memory);
			imageLoader.init(builder.build());
		}
		return imageLoader;
	}
	
	
	
	
	
	
	/**
	 * app中需要保存部分图片，用于无网络时也可以显示
	 * 有网络时，每次都从网上下载最新的图片并保存供无网络时使用
	 * @param uri
	 * @param imageView
	 * @param listener
	 */
	public static void displayLatestImageCacheForNoNet(Context context, String uri, ImageView imageView){
		if(NetWorkUtil.isNetworkConnected(context)){
			//先删除之前的缓存
			romoveCache(context, uri, imageView);
			//再重新下载最新的图片
			getImageLoader(context).displayImage(uri, imageView, displayImageOption_cache_in_memory_and_disk);
		}else{
			getImageLoader(context).displayImage(uri, imageView, displayImageOption_cache_in_memory_and_disk);
		}
	}
	
	private static ImageSize getMaxImageSize(Context context, ImageView imageView) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

		int width = imageView.getWidth();
		if (width <= 0) {
			width = displayMetrics.widthPixels;
		}
		int height = imageView.getHeight();
		if (height <= 0) {
			height = displayMetrics.heightPixels;
		}
		return new ImageSize(width, height);
	}
	
	public static void romoveCache(Context context, String uri, ImageView imageView){
		ImageSize targetSize = ImageSizeUtils.defineTargetSizeForView(new ImageViewAware(imageView), getMaxImageSize(context, imageView));
		String memoryCacheKey = MemoryCacheUtils.generateKey(uri, targetSize);
		getImageLoader(context).getDiskCache().remove(uri);
		getImageLoader(context).getMemoryCache().remove(memoryCacheKey);
	}
	
	

}




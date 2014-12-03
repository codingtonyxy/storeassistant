package com.storeassistant.util;


import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


/** 实锟斤拷图片锟斤拷锟届步锟斤拷锟斤拷 ***/
public class AsyImageLoader {
	private Bitmap bitmap;
	private KuangUtils _ku=KuangUtils.getInstance();
	private Map<String, BitmapRef> imageCache = new HashMap<String, BitmapRef>();
	private ReferenceQueue<Bitmap> q=new ReferenceQueue<Bitmap>();//锟斤拷锟矫讹拷锟斤拷
	private String fileName;
	public Bitmap loadDrawable(final String imageUrl,
			final ImageCallback callback,final int width,final int height,final float corner) {
		if (imageCache.containsKey(imageUrl)) {
			BitmapRef sr = imageCache.get(imageUrl);
			// 锟斤拷锟斤拷锟斤拷锟酵计伙拷斜锟斤拷锟斤拷
			if (sr.get() != null) {return sr.get();}
		}
		// 没锟斤拷锟斤拷锟截的伙拷锟斤拷锟酵达拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
		final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
		//锟斤拷图片写锟诫到SD锟斤拷
		fileName=imageUrl.substring(imageUrl.lastIndexOf("/")+1,imageUrl.length());
		if(_ku.enable==false){
		try {
			if(_ku.getImageFromSD(fileName)==null){
			//锟斤拷锟斤拷锟截革拷锟斤拷锟斤拷,锟剿凤拷锟斤拷
			_ku.saveFile((Bitmap) msg.obj, fileName);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		callback.imageloader((Bitmap) msg.obj,width,height,corner);
		}};
		new Thread() {
		public void run() {
		bitmap = loadImageFromUrl(imageUrl);
		cleanCache();//锟斤拷锟矫讹拷锟斤拷锟叫碉拷bitmap锟斤拷要锟斤拷,锟斤拷锟斤拷锟斤拷锟斤拷锟秸碉拷,锟饺斤拷锟斤拷蛹锟斤拷锟斤拷锟斤拷瞥锟?
		BitmapRef ref=new BitmapRef(bitmap, q, imageUrl);
		imageCache.put(imageUrl, new BitmapRef(bitmap, q, imageUrl));
		Message msg = handler.obtainMessage(0, bitmap);
		handler.sendMessage(msg);}}.start();
		return bitmap;
	}
	// 锟斤拷锟斤拷锟斤拷锟较伙拷取图片
	public Bitmap loadImageFromUrl(String imageUrl) {
		Bitmap bitmap = null;

		try {
			URL url=new URL(imageUrl);
			InputStream is=url.openStream();
			/***锟斤拷锟絡ava.lang.OutOfMemoryError: bitmap size exceeds VM budget
			 * 锟斤拷锟斤拷,使锟矫诧拷锟斤拷值,锟斤拷锟斤拷图片太锟斤拷锟斤拷锟酵计拷锟绞癸拷锟紹itmapFactory锟斤拷锟斤拷锟斤拷锟?
			 * 锟矫达拷锟斤拷,锟揭碉拷锟斤拷锟绞的诧拷锟斤拷值**/
			BitmapFactory.Options options=new BitmapFactory.Options();
		    options.inDither=false;    /*锟斤拷锟斤拷锟斤拷图片锟斤拷锟斤拷锟斤拷锟斤拷*/
		    options.inPreferredConfig=null;  /*锟斤拷锟斤拷锟矫斤拷锟斤拷锟斤拷锟斤拷锟斤拷逊锟绞斤拷锟斤拷锟?/
		    options.inSampleSize=3;          /*图片锟斤拷锟?锟斤拷锟斤拷小锟斤拷锟斤拷*/
			bitmap = BitmapFactory.decodeStream(is,null,options);
		    is.close();
		} catch (Exception e) {}
		return bitmap;

	}
	

	// 锟截碉拷涌锟?
	protected interface ImageCallback {
		public void imageloader(Bitmap imageDrawable,int width,int height,float corner);
	}
	
	private class BitmapRef extends SoftReference<Bitmap>{
		private String key="";
		//锟斤拷默锟较的革拷锟洁构锟斤拷锟斤拷锟叫匡拷锟斤拷锟斤拷锟揭伙拷锟?
		public BitmapRef(Bitmap r, ReferenceQueue<? super Bitmap> q,String key) {
			super(r, q);
			this.key=key;
		}	
	}
	
	/**
	 * method:锟斤拷锟斤拷锟斤拷锟叫凤拷锟斤拷锟斤拷锟矫讹拷锟叫碉拷BitmapRef锟狡筹拷<br>
	 * @return
	 */
	private void cleanCache() {
		// TODO Auto-generated method stub
		BitmapRef br=null;
		//锟斤拷询锟斤拷锟矫讹拷锟斤拷,q.poll()锟斤拷锟叫匡拷锟矫碉拷锟斤拷锟矫讹拷锟斤拷,锟斤拷锟斤拷,锟斤拷锟矫讹拷锟斤拷锟斤拷为,锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟?
		while((br=(BitmapRef)q.poll())!=null){
			imageCache.remove(br.key);
		}
	}
}

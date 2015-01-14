package com.storeassistant.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.alibaba.fastjson.JSON;


/**
 * net util
 * @author Administrator
 *
 */
public class NetUtil {
	
	private static HttpURLConnection getConnection(String url){
		URL aUrl;
		HttpURLConnection urlConnection = null;
		try {
			aUrl = new URL(url);
			urlConnection = (HttpURLConnection)aUrl.openConnection();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return urlConnection;
	}
	
	public static Map get(String url, Map<String, String> param){
		System.out.println("url:"+url);
		if(url == null || url.length() <= 0){
			return new HashMap<String, Object>(0);
		}
		if(param != null && param.size() > 0){
			StringBuilder urlSb = new StringBuilder(url);
			urlSb.append("?");
			Iterator<String> iter = param.keySet().iterator();
			while(iter.hasNext()){
				String key = iter.next();
				if(key == null || key.length() <= 0){
					continue;
				}
				urlSb.append(key);
				urlSb.append("=");
				String value = param.get(key);
				if(value != null){
					try {
						value = URLEncoder.encode(value, "utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					urlSb.append(value);
				}
				urlSb.append("&");
			}
			url = urlSb.toString();
		}
		HttpURLConnection conn = getConnection(url);
		if(conn == null){
			return new HashMap<String, Object>(0);
		}
		
		ByteArrayOutputStream baos = null;
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		try {
			baos = new ByteArrayOutputStream();
			bos = new BufferedOutputStream(baos);
			bis = new BufferedInputStream(conn.getInputStream());
			byte[] buffer = new byte[512];
			int count = 0;
			while((count = bis.read(buffer)) != -1){
				bos.write(buffer, 0, count);
			}
			bos.flush();
			byte[] data = baos.toByteArray();
			String content = new String(data, "utf-8");
			System.out.println("================================");
			System.out.println("server_content:"+content);
			if(content == null || content.length() <= 0){
				return new HashMap<String, Object>(0);
			}
			return (Map)JSON.parse(content);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn.disconnect();
			try {
				if(bis != null){
					bis.close();
				}
				if(baos != null){
					baos.close();
				}
				if(bos != null){
					bos.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new HashMap<String, Object>(0);
	}
	
	
	
	public static void main(String[] args) {
		String url = "www.baidu.com";
		HashMap param = null;
		System.out.println(get(url, param));
	}

}

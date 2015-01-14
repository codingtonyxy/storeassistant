package com.storeassistant.activity.base;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;

/**
 * base activity
 * @author codingtony
 *
 */
public abstract class BaseActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	public static String getString(Map map, String key, String def){
		if(map == null || map.size()<=0){
			return def;
		}
		Object o = map.get(key);
		if(o == null){
			return def;
		}
		if(o instanceof String){
			return (String)o;
		}else if(o instanceof Number){
			return String.valueOf(o);
		}else{
			return def;
		}
	}
	
	public static int getInt(Map map, String key, int def){
		if(map == null || map.size()<=0){
			return def;
		}
		Object o = map.get(key);
		if(o == null){
			return def;
		}
		if(o instanceof String){
			return Integer.valueOf((String)o);
		}else if(o instanceof Number){
			return (Integer)o;
		}else{
			return def;
		}
	}

}

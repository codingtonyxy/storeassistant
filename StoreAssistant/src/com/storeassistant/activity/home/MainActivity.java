package com.storeassistant.activity.home;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.storeassistant.R;
import com.storeassistant.activity.CarBreakRulesActivity;
import com.storeassistant.activity.ChooseMarketActivity;
import com.storeassistant.activity.SearchResultActivity;
import com.storeassistant.activity.home.fragment.FragmentMain;
import com.storeassistant.activity.home.fragment.FragmentMall;
import com.storeassistant.activity.home.fragment.FragmentNearBy;
import com.storeassistant.activity.home.fragment.FragmentPcenter;
import com.storeassistant.appInfo.MyConstants;

public class MainActivity extends FragmentActivity implements OnClickListener{

	public static final String EXTRA_NAME_SEARCH_TEXT = "searchText";
	private FragmentManager fragmentManager = null;
	private RadioGroup radioGroup = null;
	private HashMap<Integer, Fragment> fragmentCache = new HashMap<Integer, Fragment>(4);
	public static ArrayList<String> imageList = new ArrayList<String>();
	
	static{
		imageList.add(MyConstants.URL_RES+"/image/hp00.png");
		imageList.add(MyConstants.URL_RES+"/image/hp01.png");
		imageList.add(MyConstants.URL_RES+"/image/hp02.png");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		MyConstants.width = wm.getDefaultDisplay().getWidth();
		MyConstants.height = wm.getDefaultDisplay().getHeight();
		
		fragmentManager = getSupportFragmentManager();
		radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(checkedChangeListener);
		
        addFragment(R.id.main_tab_cb_home);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
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
	
	public void addFragment(int vid){
		Fragment fragment = fragmentCache.get(vid);
		if(fragment == null){
			switch (vid) {
			case R.id.main_tab_cb_home:
				fragment = new FragmentMain();
				break;
			case R.id.main_tab_cb_nearby:
				fragment = new FragmentNearBy();
				break;
			case R.id.main_tab_cb_shop:
				fragment = new FragmentMall();
				break;
			case R.id.main_tab_cb_pcenter:
				fragment = new FragmentPcenter();
				break;

			default:
				break;
			}
			fragmentCache.put(vid, fragment);
		}
		
		FragmentTransaction fs = fragmentManager.beginTransaction();
		fs.replace(R.id.container_fragment, fragment);
		fs.commit();
	}
	
	private OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			addFragment(checkedId);
		}
	};

	@Override
	public void onClick(View v) {
		Log.i(MainActivity.class.getName(), "onClick");
	}
	
	
	public void onClickCenterTab(View v){
		if(v.getId() == R.id.tab_home_bt1){
			Intent intent = new Intent(this, CarBreakRulesActivity.class);
			startActivity(intent);
		}else if(v.getId() == R.id.tab_home_bt2){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("确认点击?").setPositiveButton("确认", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(getBaseContext(), CarBreakRulesActivity.class);
					startActivity(intent);
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			
			builder.create().show();
		}
	}
	
	public void search(View view){
		EditText searchText = (EditText)findViewById(R.id.edit_text_search_box);
		String text = searchText.getText().toString();
		Intent intent = new Intent(this, SearchResultActivity.class);
		intent.putExtra(EXTRA_NAME_SEARCH_TEXT, text);
		startActivity(intent);
	}
	
		

}

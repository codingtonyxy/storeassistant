package com.storeassistant.activity.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.ImageView.ScaleType;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.storeassistant.R;
import com.storeassistant.activity.carbreakrules.CarBreakRulesActivity;
import com.storeassistant.activity.fragment.ViewPagerFragment;
import com.storeassistant.activity.home.fragment.FragmentMain;
import com.storeassistant.activity.home.fragment.FragmentMall;
import com.storeassistant.activity.home.fragment.FragmentNearBy;
import com.storeassistant.activity.home.fragment.FragmentPcenter;
import com.storeassistant.appInfo.MyConstants;
import com.storeassistant.component.ViewPagerComponent;
import com.storeassistant.util.AsyImageLoaderNoParams;
import com.storeassistant.util.CallbackImplNoParams;
import com.storeassistant.util.KuangUtils;

public class MainActivity extends FragmentActivity implements OnClickListener{

	private FragmentManager fragmentManager = null;
	private RadioGroup radioGroup = null;
	private HashMap<Integer, Fragment> fragmentCache = new HashMap<Integer, Fragment>(4);
	public static ArrayList<String> imageList = new ArrayList<String>();
	ViewPagerComponent vpc = null;
	
	static{
		imageList.add(MyConstants.URL_RES+"/image/hp00.png");
		imageList.add(MyConstants.URL_RES+"/image/hp01.png");
		imageList.add(MyConstants.URL_RES+"/image/hp00.png");
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
        KuangUtils.initNet(this);
        

		//bottom page
		ViewPager containerViewPager = (ViewPager)findViewById(R.id.viewpager_bottom_main);
		vpc = new ViewPagerComponent(containerViewPager, getViewList(), false, true,
				false, 3000, 3000);
		
        //Ĭ�ϼ�����ҳ
        addFragment(R.id.main_tab_cb_home);
        
        
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(vpc!=null){
			vpc.startPager(true);
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
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
			System.out.println(checkedId);
			addFragment(checkedId);
		}
	};

	@Override
	public void onClick(View v) {
		Log.i(MainActivity.class.getName(), "onClick");
	}
	
	
	public void onClickCenterTab(View v){
		System.out.println("onClick:"+v);
		Toast.makeText(v.getContext(), "click:"+v.getId(), 2).show();
		if(v.getId() == R.id.tab_home_bt1){
			Intent intent = new Intent(this, CarBreakRulesActivity.class);
			startActivity(intent);
		}
	}
		
	
	
	public List<View> getViewList(){
		ArrayList<View> viewList = new ArrayList<View>();
		int len = imageList.size();
		for(int i=0;i<len;i++){
			String url = imageList.get(i);
			ImageView iv=new ImageView(this);
			LayoutParams param = new LayoutParams(720, 160);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setLayoutParams(param);
//			CallbackImplNoParams cinp=new CallbackImplNoParams(iv);
//			iv.setImageBitmap(new AsyImageLoaderNoParams().loadDrawable(url, cinp,2));
//			viewList.add(iv);
			ImageLoader imageLoader = ImageLoader.getInstance();
			imageLoader.init(new ImageLoaderConfiguration.Builder(this).build());
			imageLoader.displayImage(url, iv);
			viewList.add(iv);
		}
		return viewList;
	}




}

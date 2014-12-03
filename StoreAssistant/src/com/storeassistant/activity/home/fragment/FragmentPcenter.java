package com.storeassistant.activity.home.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.storeassistant.R;
import com.storeassistant.activity.fragment.ViewPagerFragment;
import com.storeassistant.activity.home.MainActivity;
import com.storeassistant.util.AsyImageLoaderNoParams;
import com.storeassistant.util.CallbackImplNoParams;

public class FragmentPcenter extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_pcenter, container, false);
		TextView textView = (TextView)view.findViewById(R.id.textView_fragment_pcenter);
		textView.setText("FragmentPcenter");
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.add(R.id.viewpager_container_fragment_pcenter, new ViewPagerFragment(R.layout.fragment_pageview, getViewList(), false, true, true));
		ft.commit();
		return view;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	public List<View> getViewList(){
		ArrayList<View> viewList = new ArrayList<View>();
		int len = MainActivity.imageList.size();
		for(int i=0;i<len;i++){
			String url = MainActivity.imageList.get(i);
			ImageView iv=new ImageView(getActivity());
			LayoutParams param = new LayoutParams(720, 160);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setLayoutParams(param);
			CallbackImplNoParams cinp=new CallbackImplNoParams(iv);
			iv.setImageBitmap(new AsyImageLoaderNoParams().loadDrawable(url, cinp,2));
			viewList.add(iv);
		}
		return viewList;
	}

}

package com.storeassistant.activity.home.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.storeassistant.R;
import com.storeassistant.activity.fragment.ViewPagerFragment;
import com.storeassistant.util.AsyImageLoaderNoParams;
import com.storeassistant.util.CallbackImplNoParams;

public class FragmentMall extends Fragment {
	
	private FragmentManager fragmentManager = null;
	private boolean isShowDot = false;
	private boolean isAutoPage = true;
	private boolean isLoopEndLess = true;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_mall, container, false);
		
		TextView textView = (TextView)view.findViewById(R.id.textView_fragment_mall);
		textView.setText("FragmentMall");
		
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
	


}

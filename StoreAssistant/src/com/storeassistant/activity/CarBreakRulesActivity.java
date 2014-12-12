package com.storeassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.storeassistant.R;
import com.storeassistant.activity.base.BaseActivity;
import com.storeassistant.activity.home.MainActivity;

/**
 * @author codingtony
 * car break rules query
 */
public class CarBreakRulesActivity extends BaseActivity{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_break_rules);
	}


/**
 * back to main
 * @param view
 */
	public void back(View view){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	

}

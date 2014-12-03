package com.storeassistant.activity.carbreakrules;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.storeassistant.R;
import com.storeassistant.activity.base.BaseActivity;
import com.storeassistant.activity.home.MainActivity;

/**
 * 
 * @author codingtony
 * 查询汽车违章
 */
public class CarBreakRulesActivity extends BaseActivity{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_break_rules);
	}


/**
 * 返回到主页
 * @param view
 */
	public void back(View view){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	

}

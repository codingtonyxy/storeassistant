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
 * ��ѯ����Υ��
 */
public class CarBreakRulesActivity extends BaseActivity{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_break_rules);
	}


/**
 * ���ص���ҳ
 * @param view
 */
	public void back(View view){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	

}

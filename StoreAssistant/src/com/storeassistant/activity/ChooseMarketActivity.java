package com.storeassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.storeassistant.R;
import com.storeassistant.activity.base.BaseActivity;
import com.storeassistant.activity.home.MainActivity;
import com.storeassistant.appInfo.MyConstants;

/**
 * 
 * @author codingtony
 * 选择批发市场
 */
public class ChooseMarketActivity extends BaseActivity{

	public static final String EXTRA_NAME_MARKET_NAME = "marke_name";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_market);
	}
	



	public void back(View view){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	
	public void enterMarket(View view){
		Button btn = (Button)view;
		MyConstants.MARKET_NAME = btn.getText().toString();
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	

}

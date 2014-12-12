package com.storeassistant.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.storeassistant.R;
import com.storeassistant.activity.base.BaseActivity;
import com.storeassistant.activity.home.MainActivity;

/**
 * 
 * @author codingtony
 * user info
 */
public class UserInfoActivity extends BaseActivity{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		
	}


	public void back(View view){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	

}

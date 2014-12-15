package com.storeassistant.activity.mail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.storeassistant.R;
import com.storeassistant.activity.base.BaseActivity;
import com.storeassistant.activity.home.MainActivity;

/**
 * @author codingtony
 * mail
 */
public class MailActivity extends BaseActivity{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mail);
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

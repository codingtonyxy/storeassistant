package com.storeassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.storeassistant.R;
import com.storeassistant.activity.base.BaseActivity;
import com.storeassistant.activity.home.MainActivity;

/**
 * 
 * @author codingtony
 * 搜索结果
 */
public class SearchResultActivity extends BaseActivity{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		
		String searchText = null;
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		Object extralSearchText = extras.get(MainActivity.EXTRA_NAME_SEARCH_TEXT);
		if(extralSearchText != null){
			searchText = (String)extralSearchText;
			EditText editText = (EditText)findViewById(R.id.edit_text_search_box);
			editText.setText(searchText);
		}
	}


	public void back(View view){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	

}

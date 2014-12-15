package com.storeassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

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
		MyConstants.MARKET_NAME = view.getTag().toString();
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	public void showMarketDetail(View view){
		View arrow = null;
		View lookView = findViewByTagInParent((ViewGroup)view.getParent().getParent(), "market_detail");
		if(lookView != null){
			if(lookView.getVisibility() == View.VISIBLE){
				lookView.setVisibility(View.GONE);
				arrow = findViewByIdInParent((ViewGroup)view.getParent(), R.id.market_name_arrow);
				if(arrow != null){
					ImageView arrowImageView = (ImageView)arrow;
					arrowImageView.setImageResource(R.drawable.arrow_up_bold);
				}
			}else if(lookView.getVisibility() == View.GONE){
				lookView.setVisibility(View.VISIBLE);
				arrow = findViewByIdInParent((ViewGroup)view.getParent(), R.id.market_name_arrow);
				if(arrow != null){
					ImageView arrowImageView = (ImageView)arrow;
					arrowImageView.setImageResource(R.drawable.arrow_down_bold);
				}
			}
		}
	}
	
	public View findViewByTagInParent(ViewGroup parent, String tag){
		if(parent == null){
			return null;
		}
		return parent.findViewWithTag(tag);
	}
	
	public View findViewByIdInParent(ViewGroup parent, int id){
		if(parent == null){
			return null;
		}
		return parent.findViewById(id);
	}
	

}

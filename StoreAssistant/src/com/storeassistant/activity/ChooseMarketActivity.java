package com.storeassistant.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.storeassistant.R;
import com.storeassistant.activity.base.BaseActivity;
import com.storeassistant.activity.home.MainActivity;
import com.storeassistant.adapter.MarketListAdapter;
import com.storeassistant.appInfo.MyConstants;
import com.storeassistant.net.NetApi;

/**
 * 
 * @author codingtony
 * 选择批发市场
 */
public class ChooseMarketActivity extends BaseActivity{

	public static final String EXTRA_NAME_MARKET_NAME = "marke_name";
	public Handler showMarktList;
	LinearLayout marketContainer = null;
	Context context = null;
	ListView myMarketListView = null;
	LayoutInflater inflater = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_market);
		context = this;
		inflater = LayoutInflater.from(context);
		
		marketContainer = (LinearLayout)findViewById(R.id.market_list_container);
		
		showMarktList = new Handler(){
			@Override
			public void dispatchMessage(Message msg) {
				if(msg.what == 1){
					Map retInfo = (Map) msg.obj;
					System.out.println("retInfo:"+retInfo);
					if(retInfo!= null && retInfo.size()>0){
						List<Map<String, String>> itemList = new ArrayList<Map<String, String>>();
						Set set = retInfo.entrySet();
						for (Object o : set) {
							View marketView =inflater.inflate(R.layout.market_list_container, marketContainer);
							myMarketListView = (ListView)marketView.findViewById(R.id.listview_market);
							
							ListView listViewMarket = (ListView)marketView.findViewById(R.id.listview_market);
							TextView marketName = (TextView)marketView.findViewById(R.id.market_place);
							
							Object o2 = o;
							Entry list = (Entry)o2;
							listViewMarket.setAdapter(new MarketListAdapter(context, (List)list.getValue(), myMarketListView));
							
							marketName.setText((String)list.getKey());
							
						}
					}
					
				}
			}
		};
		NetApi.getMarketList(showMarktList);
		
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
				arrow = findViewByTagInParent((ViewGroup)view.getParent(), "market_arrow");
				if(arrow != null){
					ImageView arrowImageView = (ImageView)arrow;
					arrowImageView.setImageResource(R.drawable.arrow_up_bold);
				}
			}else if(lookView.getVisibility() == View.GONE){
				lookView.setVisibility(View.VISIBLE);
				arrow = findViewByTagInParent((ViewGroup)view.getParent(), "market_arrow");
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
	
	


}

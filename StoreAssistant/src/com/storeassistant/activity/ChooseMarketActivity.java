package com.storeassistant.activity;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.storeassistant.R;
import com.storeassistant.activity.base.BaseActivity;
import com.storeassistant.activity.home.MainActivity;
import com.storeassistant.adapter.MarketListAdapter;
import com.storeassistant.appInfo.MyConstants;
import com.storeassistant.db.table.TableMarket;
import com.storeassistant.db.table.TableMarketCity;
import com.storeassistant.db.table.TableMarketType;
import com.storeassistant.net.NetApi;

/**
 * @author codingtony
 * 选择批发市场
 */
public class ChooseMarketActivity extends BaseActivity{

	LinearLayout marketContainer = null;
	Context context = null;
	ListView myMarketListView = null;
	LayoutInflater inflater = null;
	LinearLayout marketCityContainer = null;
	MarketListAdapter marketListAdapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_market);
		context = this;
		inflater = LayoutInflater.from(context);
		
		marketContainer = (LinearLayout)findViewById(R.id.market_list_container);
		marketCityContainer = (LinearLayout)findViewById(R.id.container_market_city_list);
		
		//初始化市场
		initMarketCityList();
	}
	

	private void initMarketCityList() {
		List<Map<String, Object>> cityList = TableMarketCity.queryAll();
		if(cityList.size()<=0){
			NetApi.getMarketCityList(new Handler(){
				@Override
				public void handleMessage(Message msg) {
					Object o = msg.obj;
					if(o != null){
						try{
							List<Map<String, Object>> cityList = (List<Map<String, Object>>)o;
							initMarketCity(cityList);
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
			}, true);
		}else{
			initMarketCity(cityList);
		}
	}
	
	private void initMarketCity(List<Map<String, Object>> cityList){
		boolean isShowDefaultMarketList = false;
		for (Map<String, Object> city : cityList) {
			Button BtnMarketCity = (Button)inflater.inflate(R.layout.item_market_city, null);
			
			int cityId = getInt(city, "city_id", 0);
			BtnMarketCity.setText(getString(city, "city_name", ""));
			BtnMarketCity.setTag(cityId);
			marketCityContainer.addView(BtnMarketCity);
			
			//默认显示第一个城市的市场列表
			if(!isShowDefaultMarketList && cityId > 0){
				initMarket(cityId);
				isShowDefaultMarketList = true;
			}
			
			BtnMarketCity.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int cityId = (Integer)v.getTag();
					initMarket(cityId);
				}
			});
		}
	}
	
	private void initMarket(final int cityId) {
		List<Map<String, Object>> marketList = TableMarket.getByCityId(cityId);
		if(marketList.size()<=0){
			NetApi.getMarketList(new Handler(){
				@Override
				public void handleMessage(Message msg) {
					Object o = msg.obj;
					if(o != null){
						try{
							List<Map<String, Object>> marketList = (List<Map<String, Object>>)o;
							doInitMarket(marketList);
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
			}, true, cityId);
		}else{
			doInitMarket(marketList);
		}
	}
	
	private void doInitMarket(List<Map<String, Object>> marketList){
		final View marketListContainer =inflater.inflate(R.layout.market_list_container, marketContainer);
		myMarketListView = (ListView)marketListContainer.findViewById(R.id.listview_market);
		final TextView marketPlace = (TextView)marketListContainer.findViewById(R.id.market_place);
		
		//更新市场城市名称
		int cityId2 = getInt(marketList.get(0), "city_id", 0);
		marketPlace.setText(getString(marketList.get(0), "city_name", ""));
		marketPlace.setTag(cityId2);
		
		//添加市场类型
		final LinearLayout containerMarketTypeList = (LinearLayout)marketListContainer.findViewById(R.id.container_market_type_list);
		if(containerMarketTypeList.getChildCount() > 0){
			containerMarketTypeList.removeAllViews();
		}
		List<Map<String, Object>> allType = TableMarketType.getByCityId(cityId2);
		final int cityId = (Integer)marketPlace.getTag();
		if(allType.size()<=0){
			NetApi.getMarketTypeList(new Handler(){
				@Override
				public void handleMessage(Message msg) {
					Object o = msg.obj;
					if(o != null){
						try{
							List<Map<String, Object>> allType = (List<Map<String, Object>>)o;
							doInitMarketType(allType, containerMarketTypeList, cityId);
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
			}, true);
		}else{
			doInitMarketType(allType, containerMarketTypeList, cityId);
		}
		
		
		//更新市场列表
		marketListAdapter = new MarketListAdapter(context, marketList, myMarketListView);
		myMarketListView.setAdapter(marketListAdapter);
	}
	
	private void doInitMarketType(List<Map<String, Object>> allType, LinearLayout containerMarketTypeList, final int cityId){
		for (Map<String, Object> type : allType) {     
			Button BtnMarketType = (Button)inflater.inflate(R.layout.item_market_type, null);
			BtnMarketType.setText(getString(type, "type_name", ""));
			BtnMarketType.setTag(getInt(type, "type_id", 0));
			BtnMarketType.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int typeId = (Integer)v.getTag();
					marketListAdapter.itemList = TableMarket.getByMarketCityIdAndType(cityId, typeId);
					marketListAdapter.notifyDataSetChanged();
				}
			});
			containerMarketTypeList.addView(BtnMarketType);
		}
	}


	public void back(View view){
		this.finish();
	}
	
	public void add(View view){
		Intent intent = new Intent(this, AddMarketActivity.class);
		startActivity(intent);
	}
	
	public void enterMarket(View view){
		MyConstants.MARKET_NAME = view.getTag().toString();
		MyConstants.TAB_TO_SHOW = R.id.main_tab_cb_shop;
		this.finish();
	}
	
	public View findViewByTagInParent(ViewGroup parent, String tag){
		if(parent == null){
			return null;
		}
		return parent.findViewWithTag(tag);
	}
	
	


}

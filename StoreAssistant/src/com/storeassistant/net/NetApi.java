package com.storeassistant.net;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Handler;
import android.os.Message;

import com.storeassistant.activity.base.BaseActivity;
import com.storeassistant.appInfo.MyConstants;
import com.storeassistant.db.table.TableMarket;
import com.storeassistant.db.table.TableMarketCity;
import com.storeassistant.db.table.TableMarketType;

/**
 * @author Administrator
 */
public class NetApi {
	
	public static final String BASE_SERVER_URL = "http://"+MyConstants.SERVER_IP_PORT+"/";
	public static final String method_getMarketList = "getmarketlist";
	public static final String method_getMarketCityList = "getmarketcitylist";
	public static final String method_getMarketTypeList = "getmarkettypelist";
	public static final String method_commitNewMarketInfo = "commitnewmarketinfo";
	public static final String method_getConfig = "getconfig";
	public static final String method_getImagestart = "getImageStart";
	
	/**
	 * 启动界面图片
	 * @return
	 */
	public static String getStartImgName(){
		return MyConstants.APP_CONFIG.getResBaseUrl()+"/image/image_start.png";
	}
	
	/**
	 * 市场列表
	 * @param handler
	 * @param isUpdateDb
	 */
	public static void getMarketList(final Handler handler, final boolean isUpdateDb, final int cityId){
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<Map<String, Object>> marketList = null;
				Map map = NetUtil.get(BASE_SERVER_URL + method_getMarketList, null);
				Object o = map.get("ret_info");
				if(o == null){
					return;
				}
				marketList = (List<Map<String, Object>>)o;
				if(isUpdateDb){
					if(marketList.size()>0){
						//清除之前的数据
						TableMarket.deleteAll();
						for (Map<String, Object> market : marketList) {
							TableMarket.insert(BaseActivity.getInt(market, "market_id", 0),
									BaseActivity.getString(market, "market_name", ""), 
									BaseActivity.getString(market, "market_desc", ""),
									BaseActivity.getString(market, "image_url", ""),
									BaseActivity.getString(market, "city_name", ""),
									BaseActivity.getString(market, "market_type", ""),
									BaseActivity.getInt(market, "city_id", 0),
									BaseActivity.getString(market, "market_address", ""),
									BaseActivity.getString(market, "market_open_time", "")
									);
						}
					}
					
				}
				
				if(handler!=null){
					Message msg = handler.obtainMessage();
					msg.what = 1;
					msg.obj = TableMarket.getByCityId(cityId);
					handler.sendMessage(msg);
				}
			}
		}).start();
	} 
	
	
	/**
	 * 市场城市列表
	 * @param handler
	 * @param isUpdateDb
	 */
	public static void getMarketCityList(final Handler handler, final boolean isUpdateDb){
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<Map<String, Object>> cityList = null;
				try{
					Map map = NetUtil.get(BASE_SERVER_URL + method_getMarketCityList, null);
					Object o = map.get("ret_info");
					if(o == null){
						return;
					}
					cityList = (List<Map<String, Object>>)o;
					if(isUpdateDb){
						if(cityList.size()>0){
							//清除之前的数据
							TableMarketCity.deleteAll();
							for (Map<String, Object> city : cityList) {
								TableMarketCity.insert((Integer)city.get("city_id"), (String)city.get("city_name"));
							}
						}
						
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				
				if(handler != null){
					Message msg = handler.obtainMessage();
					msg.what = 1;
					msg.obj = cityList;
					handler.sendMessage(msg);
				}
			}
		}).start();
	} 
	
	/**
	 * 市场类型列表
	 * @param handler
	 * @param isUpdateDb
	 */
	public static void getMarketTypeList(final Handler handler, final boolean isUpdateDb){
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<Map<String, Object>> typeList = null;
				try{
					Map map = NetUtil.get(BASE_SERVER_URL + method_getMarketTypeList, null);
					Object o = map.get("ret_info");
					if(o == null){
						return;
					}
					typeList = (List<Map<String, Object>>)o;
					if(isUpdateDb){
						if(typeList.size()>0){
							//清除之前的数据
							TableMarketType.deleteAll();
							for (Map<String, Object> type : typeList) {
								TableMarketType.insert(
										BaseActivity.getInt(type, TableMarketType.COLUMN_NAME_TYPE_ID, 0),
										BaseActivity.getString(type, TableMarketType.COLUMN_NAME_TYPE_NAME, ""),
										BaseActivity.getInt(type, TableMarketType.COLUMN_NAME_TYPE_CITY_ID, 0));
							}
						}
						
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				
				if(handler != null){
					Message msg = handler.obtainMessage();
					msg.what = 1;
					msg.obj = typeList;
					handler.sendMessage(msg);
				}
			}
		}).start();
	} 
	
	/**
	 * 提交新市场信息
	 * @param handler
	 * @param name
	 * @param address
	 * @param type
	 * @param openTime
	 * @param city
	 * @param imageUrl
	 */
	public static void commitNewMarketInfo(final Handler handler, final String name, final String address, final String type, 
			final String openTime, final String city, final String imageUrl){
		new Thread(new Runnable() {
			@Override
			public void run() {
				Map<String, String> retMap = null;
				try{
					Map<String, String> param = new HashMap<String, String>();
					param.put("name", name);
					param.put("address", address);
					param.put("type", type);
					param.put("open_time", openTime);
					param.put("city", name);
					param.put("image_url", name);
					
					Map map = NetUtil.get(BASE_SERVER_URL + method_commitNewMarketInfo, param);
					
					retMap = (Map<String, String>)map.get("ret_info");
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					if(handler != null){
						Message msg = handler.obtainMessage();
						msg.what = 1;
						msg.obj = retMap;
						handler.sendMessage(msg);
					}
				}
			}
		}).start();
	} 
	
	/**
	 * 初始化app配置信息
	 */
	public static void initConfig(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				Map<String, Object> configMap = null;
				try{
					Map map = NetUtil.get(BASE_SERVER_URL + method_getConfig, null);
					Object o = map.get("ret_info");
					if(o == null){
						return;
					}
					configMap = (Map<String, Object>)o;
					if(configMap.size()>0){
						//资源基础路径
						o = configMap.get("res_base_url");
						if(o != null){
							String resBaseUrl = (String)o;
							if(resBaseUrl!=null && resBaseUrl.length()>0){
								MyConstants.APP_CONFIG.setResBaseUrl(resBaseUrl);
							}
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}).start();
	} 
	
}

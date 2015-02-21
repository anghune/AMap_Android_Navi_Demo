package com.amap.navi.demo.activity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.maps.MapView;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.view.RouteOverLay;
import com.amap.navi.demo.MainApplication;
import com.amap.navi.demo.R;
import com.amap.navi.demo.util.Utils;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 路径规划结果展示界面
 */
public class NaviRouteActivity extends Activity implements OnClickListener,
		OnMapLoadedListener {

	// View
	private Button mStartNaviButton;// 实时导航按钮
	private MapView mMapView;// 地图控件
	private AutoCompleteTextView mThemeText;// 选择导航界面的风格
	private ImageView mThemeImage;// 选择按钮
	private ImageView mRouteBackView;// 返回按钮
	private TextView mRouteDistanceView;// 距离显示控件
	private TextView mRouteTimeView;// 时间显示控件
	private TextView mRouteCostView;// 花费显示控件
	// 地图导航资源
	private AMap mAmap;
	private AMapNavi mAmapNavi;
	private RouteOverLay mRouteOverLay;
	// 主题数组
	private String mTheme[];

	private boolean mIsMapLoaded = false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route);
		initResources();
		initView(savedInstanceState);
		MainApplication.getInstance().addActivity(this);
	}

	// -----------------------初始化----------------------------------

	/**
	 * 初始化资源
	 */
	private void initResources() {
		Resources res = getResources();
		mTheme = new String[] { res.getString(R.string.theme_blue),
				res.getString(R.string.theme_pink),
				res.getString(R.string.theme_white) };
	}

	/**
	 * 初始化控件
	 */
	private void initView(Bundle savedInstanceState) {
		mStartNaviButton = (Button) findViewById(R.id.routestartnavi);

		mRouteBackView = (ImageView) findViewById(R.id.route_back_view);

		mThemeText = (AutoCompleteTextView) findViewById(R.id.navi_theme_text);
		mThemeText.setInputType(InputType.TYPE_NULL);
		ArrayAdapter<String> themeAdapter = new ArrayAdapter<String>(this,
				R.layout.strategy_inputs, mTheme);
		mThemeText.setAdapter(themeAdapter);

		mThemeText.setDropDownBackgroundResource(R.drawable.whitedownborder);

		mThemeImage = (ImageView) findViewById(R.id.navi_theme_image);
		mRouteDistanceView = (TextView) findViewById(R.id.navi_route_distance);
		mRouteTimeView = (TextView) findViewById(R.id.navi_route_time);
		mRouteCostView = (TextView) findViewById(R.id.navi_route_cost);
		mMapView = (MapView) findViewById(R.id.routemap);
		mMapView.onCreate(savedInstanceState);
		mAmap = mMapView.getMap();
		mAmap.setOnMapLoadedListener(this);
		mThemeImage.setOnClickListener(this);
		mThemeText.setOnClickListener(this);
		mStartNaviButton.setOnClickListener(this);
		mRouteBackView.setOnClickListener(this);
		mRouteOverLay = new RouteOverLay(mAmap, null);
	}

	/**
	 * 初始化路线描述信息和加载线路
	 */
	private void initNavi() {
		
		mAmapNavi = AMapNavi.getInstance(this);
		AMapNaviPath naviPath = mAmapNavi.getNaviPath();
		if (naviPath == null) {
			return;
		}
		// 获取路径规划线路，显示到地图上
		mRouteOverLay.setRouteInfo(naviPath);
		mRouteOverLay.addToMap();
		if (mIsMapLoaded) {
			mRouteOverLay.zoomToSpan();
		}

		double length = ((int) (naviPath.getAllLength() / (double) 1000 * 10))
				/ (double) 10;
		// 不足分钟 按分钟计
		int time = (naviPath.getAllTime() + 59) / 60;
		int cost = naviPath.getTollCost();
		mRouteDistanceView.setText(String.valueOf(length));
		mRouteTimeView.setText(String.valueOf(time));
		mRouteCostView.setText(String.valueOf(cost));
	}

	/**
	 * 获取导航界面主题样式
	 * 
	 * @param themeColor
	 * @return
	 */
	private int getThemeStyle(String themeColor) {
		int theme = AMapNaviViewOptions.BLUE_COLOR_TOPIC;
		if (mTheme[0].equals(themeColor)) {
			theme = AMapNaviViewOptions.BLUE_COLOR_TOPIC;
		} else if (mTheme[1].equals(themeColor)) {
			theme = AMapNaviViewOptions.PINK_COLOR_TOPIC;
		} else if (mTheme[2].equals(themeColor)) {
			theme = AMapNaviViewOptions.WHITE_COLOR_TOPIC;
		}
		return theme;
	}

	// ------------------------------事件处理-----------------------------
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 实时导航操作
		case R.id.routestartnavi:
			Bundle bundle = new Bundle();
			bundle.putInt(Utils.THEME, getThemeStyle(mThemeText.getText()
					.toString()));
			Intent routeIntent = new Intent(NaviRouteActivity.this,
					NaviCustomActivity.class);
			routeIntent.putExtras(bundle);
			startActivity(routeIntent);
			break;
		// 返回操作
		case R.id.route_back_view:
			Intent startIntent = new Intent(NaviRouteActivity.this,
					NaviStartActivity.class);
			startIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(startIntent);
			MainApplication.getInstance().deleteActivity(this);
			finish();
			break;
		// 主题选择
		case R.id.navi_theme_image:
		case R.id.navi_theme_text:
			mThemeText.showDropDown();
			break;
		}

	}

	/**
	 * 
	 * 返回键监听
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(NaviRouteActivity.this,
					NaviStartActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
			MainApplication.getInstance().deleteActivity(this);
			finish();

		}
		return super.onKeyDown(keyCode, event);
	}

	// ------------------------------生命周期必须重写方法---------------------------
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}

	@Override
	public void onResume() {
		super.onResume();
		mMapView.onResume();
		initNavi();
	}

	@Override
	public void onPause() {
		super.onPause();
		mMapView.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
	}

	@Override
	public void onMapLoaded() {
		mIsMapLoaded = true;
		if (mRouteOverLay != null) {
			mRouteOverLay.zoomToSpan();

		}
	}

}

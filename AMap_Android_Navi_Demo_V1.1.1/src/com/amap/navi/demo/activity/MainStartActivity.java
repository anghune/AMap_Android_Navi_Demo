package com.amap.navi.demo.activity;
 

import com.amap.api.navi.AMapNavi;
import com.amap.navi.demo.MainApplication;
import com.amap.navi.demo.R;
import com.amap.navi.demo.TTSController;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
/**
 * 
 * 首页面
 * */
public class MainStartActivity extends Activity implements OnClickListener{
 
	private TextView messageView;

	private TextView recordsView;
	private TextView naviView;
	
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainstart);
		initView();
	}
	private void initView(){
		setTitle("安全行车导航 ");
		messageView=(TextView) findViewById(R.id.main_message);
		recordsView=(TextView) findViewById(R.id.main_records);
		naviView=(TextView) findViewById(R.id.main_navi);
		
		messageView.setOnClickListener(this);		
		recordsView.setOnClickListener(this);
		naviView.setOnClickListener(this);
		
		TTSController ttsManager = TTSController.getInstance(this);// 初始化语音模块
		ttsManager.init();
		AMapNavi.getInstance(this).setAMapNaviListener(ttsManager);// 设置语音模块播报
	}
	
	
	
	/**
	 * 返回键处理事件
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			MainApplication.getInstance().exit(); 
			finish();
			System.exit(0);// 退出程序
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		//路径规划
		case R.id.main_message:
			Intent MessageIntent=new Intent(MainStartActivity.this,MessageSendActivity.class);
			MessageIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(MessageIntent);
			break;
			//实时导航
		case R.id.main_records:
			Intent RecordsIntent=new Intent(MainStartActivity.this,RecordsActivity.class);
			RecordsIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(RecordsIntent);
			break;
			//综合展示
		case R.id.main_navi:
			Intent naviStartIntent=new Intent(MainStartActivity.this,NaviStartActivity.class);
			naviStartIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(naviStartIntent);
			break;	
		}
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy(); 
		// 这是最后退出页，所以销毁导航和播报资源
		AMapNavi.getInstance(this).destroy();// 销毁导航
		TTSController.getInstance(this).stopSpeaking();
		TTSController.getInstance(this).destroy();

	}
	
}

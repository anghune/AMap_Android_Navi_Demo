package com.amap.navi.demo.activity;

import com.amap.navi.demo.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class SmsMessage extends Activity{
	
	@SuppressWarnings("deprecation")
	private SmsManager smsManager;
	private SharedPreferences sharedPreferences, sharePreferencestel;
	
	private int messageCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smsmessage);
		MainStartActivity main=new MainStartActivity();
		/**关闭监测加速度service*/
		stopService(main.intent);
		
		System.out.println("5");
		
		sharedPreferences=getSharedPreferences("share",MODE_PRIVATE);
		sharePreferencestel=getSharedPreferences("save",MODE_PRIVATE);
		
		messageCount=sharedPreferences.getInt("count",1);
		
		smsManager=SmsManager.getDefault();
		//Intent intent=this.getIntent();		
		//int m=intent.getIntExtra("com.example.cunchu.m", 0);
		
		for(int i=0;i<messageCount;i++){
			try{
			smsManager.sendTextMessage(sharePreferencestel.getString("telephonetext1", null), null,"(此短信为手机应急小助手软件自动发送)",null,null);
			smsManager.sendTextMessage(sharePreferencestel.getString("telephonetext2", null), null,"(此短信为手机应急小助手软件自动发送)",null,null);
			smsManager.sendTextMessage(sharePreferencestel.getString("telephonetext3", null), null,"(此短信为手机应急小助手软件自动发送)",null,null);
			Toast.makeText(SmsMessage.this,"消息发送中...",Toast.LENGTH_SHORT).show();
			}catch(Exception e){
				Toast.makeText(SmsMessage.this, "已发送给你已设置的联系人",Toast.LENGTH_SHORT).show();
			}
		}
	}

	
	@Override
	public void onBackPressed() {
		/*TheLimitTimeDialog thelimit=new TheLimitTimeDialog();
		stopService(new  TheLimitTimeDialog().intent1);
		stopService(thelimit.locationIntent);*/
		finish();
	}
}
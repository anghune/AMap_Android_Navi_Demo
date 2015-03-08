package com.amap.navi.demo.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.navi.demo.R;



public class Limit extends Activity{
	
	/**定位相关变量*/
	public Thread thread;
	public Timer timer=new Timer();
	int i=0;
	public WakeLock wakeLock = null;
	public PowerManager powerManager;

	public TextView thelimittime;
	private Button cancle;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
    	// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setFinishOnTouchOutside(false); 
		setTitle("系统提示:");
		powerManager = (PowerManager) this.getSystemService(this.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"my");
		wakeLock.acquire();
		setContentView(R.layout.limit);
		
		MainStartActivity main=new MainStartActivity();
		/**关闭监测加速度service*/
		stopService(main.intent);
		
thelimittime=(TextView)findViewById(R.id.limittimetextView);
		
		cancle=(Button)findViewById(R.id.ok);
		cancle.setOnClickListener(new myOn());
		
		thread=new Thread(update);
		thread.start();
		try {
			thread.sleep(1000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}
	
	class myOn implements View.OnClickListener{

		@Override
		public void onClick(View V) {
			
			thread.interrupted();
			timer.cancel();
			timer=null;
			Limit.this.finish();
			MainStartActivity main=new MainStartActivity();
			startService(main.intent);
		}
	}
	Runnable update=new Runnable() {
		@Override
		public void run() {
			
			timer.schedule(task,1000,1000);
		}

	};
	
	TimerTask task=new TimerTask() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			runOnUiThread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					i++;
					if(i<=30)
						thelimittime.setText("离自动发送求救短信还有"+(30-i)+"秒");
					else{
						if(i>30){
						timer.cancel();
						timer=null;
						thread.interrupt();
						Intent intent=new Intent();
						intent.setClass(Limit.this,SmsMessage.class);
						startActivity(intent);
						Limit.this.finish();
						/**再次开启监听服务*/
						startService((new MainStartActivity()).intent);
						}
					}
				}
				
			});
		}
	};
	/**屏蔽后退键*/
	@Override
	public void onBackPressed() {
		
		}
	}


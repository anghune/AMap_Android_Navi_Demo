package com.amap.navi.demo.activity;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class Acclerometerservice extends Service{
	SharedPreferences perPreferences;

	private SensorManager manager;
	private Sensor sensor;
	public float x, y, z;
	public WakeLock wakeLock = null;
	public PowerManager powerManager;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO 自动生成的方法存根
		return null;
	}
	
	@Override
	@Deprecated
	public void onStart(Intent intent,int startId){
		powerManager = (PowerManager) this.getSystemService(this.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"my");
		wakeLock.acquire();
		perPreferences = getSharedPreferences("share", MODE_PRIVATE);
		manager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		manager.registerListener(lisener, sensor, manager.SENSOR_DELAY_NORMAL);
		
		System.out.println("2");
		
	}
	private SensorEventListener lisener = new SensorEventListener(){
	
		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO 自动生成的方法存根
			x = event.values[0];
			y = event.values[1];
			z = event.values[2];
			//System.out.println("3");
			if (x * x + y * y + z * z >250) {
				MainStartActivity main = new MainStartActivity();
				stopService(main.intent);
				Intent intent1 = new Intent();
				intent1.setClass(Acclerometerservice.this,Limit.class);
				intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//启动一个activity
				startActivity(intent1);
				System.out.println("4");
				
				/*Intent intent=new Intent();
				intent.setClass(Acclerometerservice.this,SmsMessage.class);
				intent.putExtra("com.example.cunchu.m",1);
				startActivity(intent);*/
		}
		}

		@Override
		public void onAccuracyChanged(Sensor arg0,int arg1){
			// TODO 自动生成的方法存根
			
		}
		
	};
	@Override
	public void onDestroy() {
		super.onDestroy();
		manager.unregisterListener(lisener);
		wakeLock.release();
	}

}

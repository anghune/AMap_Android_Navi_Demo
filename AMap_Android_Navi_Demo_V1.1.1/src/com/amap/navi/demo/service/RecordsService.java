package com.amap.navi.demo.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.amap.navi.demo.activity.RecordsActivity;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

public class RecordsService extends android.app.Service {

	private LocationManager locationManager;
	boolean flag;

	int CMD_START_SERVICE = 1;
	int CMD_STOP_SERVICE = 0;
	CommandReceiver commandReceiver;

	String ts = "";
	String te = "";
	String tl = "";
	long allsecond = 0;
	long lastsecond = 0;
	double distance = 0.0;

	private int iCount = 0;
	private String form = "yyyy-MM-dd HH:mm:ss";
	static final int NOTIFICATION_ID = 0x123;
	private NotificationManager manager;
	double spd_0;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		// locationManager =
		// (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		super.onCreate();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		commandReceiver = new CommandReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("Gps_demo_1");
		registerReceiver(commandReceiver, filter);
		manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		return START_STICKY;
	}

	LocationListener locationListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			UpdateView(locationManager.getLastKnownLocation(provider));

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			UpdateView(null);
		}

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			UpdateView(location);

		}
	};

	private void UpdateView(Location location) {
		// TODO Auto-generated method stub
		if (location != null) {
			iCount++;
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			double spd = location.getSpeed();
			double spd1 = spd * 3.6f;
			if (spd1 > 3) {
				sendNotifi();
			}

			if (iCount != 1) {
				distance = GetDistance(spd);
			}

			Intent dataintent = new Intent();
			dataintent.setAction("Gps_demo_1_");
			dataintent.putExtra("lng", lng);
			dataintent.putExtra("lat", lat);
			dataintent.putExtra("spd", spd);
			dataintent.putExtra("distance", distance);

			SimpleDateFormat sdf = new SimpleDateFormat(form);
			te = sdf.format(new Date());
  
			try {
				allsecond = (sdf.parse(te).getTime() - sdf.parse(ts).getTime());
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			dataintent.putExtra("Time", allsecond);
			dataintent.putExtra("Times", iCount);
			System.out.println("-----UpdateView(Location location)------");
			System.out.println("Service2--->lng" + lng);
			System.out.println("Service2--->lat" + lat);
			System.out.println("Service2--->spd" + spd);
			System.out.println("Service2---->distance" + distance);
			System.out.println("Service2---->Time" + allsecond);
			System.out.println("Service2---->Times" + iCount);
			sendBroadcast(dataintent);
			System.out.println("----sendBroadcast(dataintent)-----");

		}
	}

	private double GetDistance(double spd1) {

		distance = spd1 * 2 + distance;
		return distance;
	}

	private void sendNotifi() {
		Intent intent = new Intent(RecordsService.this, RecordsActivity.class);
		PendingIntent pi = PendingIntent.getActivity(RecordsService.this, 0,
				intent, 0);

		Notification notification = new Notification.Builder(this)
				.setAutoCancel(true)
				.setTicker("敬告，您已经超速！")
				.setSmallIcon(R.drawable.btn_default)
				.setContentText("请注意")
				.setContentText("敬告，您已经超速！！！")
				.setDefaults(
						Notification.DEFAULT_SOUND
								| Notification.FLAG_SHOW_LIGHTS)
				.setContentIntent(pi).build();
		manager.notify(NOTIFICATION_ID, notification);

	}

	private class CommandReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int cmd = intent.getIntExtra("cmd", -1);
			System.out.println("Service-cmd----->" + cmd);

			if (cmd == CMD_START_SERVICE) {

				locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

				SimpleDateFormat sdf = new SimpleDateFormat(form);// 格式化时间
				ts = sdf.format(new Date());// 按以上格式 将当前时间转换成字符串

				Location location = locationManager
						.getLastKnownLocation(LocationManager.GPS_PROVIDER);

				locationManager.requestLocationUpdates(
						LocationManager.GPS_PROVIDER, 2000, (float) 1.5,
						locationListener);
				if (location != null) {
					double lng = location.getLongitude();
					double lat = location.getLatitude();
					double spd = location.getSpeed();
					Intent dataintent = new Intent();
					dataintent.setAction("GPS_demo_1_");
					dataintent.putExtra("lng", lng);
					dataintent.putExtra("lat", lat);
					dataintent.putExtra("spd", spd);
					dataintent.putExtra("distance", distance);

					te = sdf.format(new Date());

					try {
						allsecond = (sdf.parse(te).getTime() - sdf.parse(ts)
								.getTime()) / 1000;
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					dataintent.putExtra("Time", allsecond);
					dataintent.putExtra("Times", iCount);

					System.out.println("-----getLastKnownLocation------");
					System.out.println("Service1--->lng" + lng);
					System.out.println("Service1--->lat" + lat);
					System.out.println("Service1--->spd" + spd);
					System.out.println("Service1---->distance" + distance);
					System.out.println("Service1---->Time" + allsecond);
					System.out.println("Service1---->Times" + iCount);
					sendBroadcast(dataintent);
					System.out.println("----sendBroadcast(dataintent)-----");

				} else {
					// 这里可以使用network_provider
					location = locationManager
							.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					System.out.println("---NETWORK_PROVIDER---");
				}
			}
			if (cmd == CMD_STOP_SERVICE) {
				iCount = 0;
				distance = 0;
				locationManager.removeUpdates(locationListener);
				System.out.println("--cmd == CMD_STOP_SERVICE--");
			}

		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		this.unregisterReceiver(commandReceiver);
		System.out.println("---Service_onDestroy--");
		super.onDestroy();
	}

}
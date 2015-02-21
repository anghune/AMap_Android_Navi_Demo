package com.amap.navi.demo.activity;

import com.amap.navi.demo.R;
import com.amap.navi.demo.service.RecordsService;

import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RecordsActivity extends Activity {
	private TextView LongitudeView;
	private TextView LatitudeView;
	private TextView SpeedView;
	private TextView TimeView;
	private TextView DistanceView;
	private Button button;
	private TextView TimesView;
	private Boolean blnGo = false;
    private Boolean BlnGps = false;
	private int iCount = 0;
	DataReceiver dataReceiver;
	// 圆
	com.amap.navi.demo.activity.GaugeChart01View chart = null;

	private int CMD_START_SERVICE = 1;
	private int CMD_STOP_SERVICE = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_records);

		LongitudeView = (TextView) findViewById(R.id.Longitude);
		LatitudeView = (TextView) findViewById(R.id.Latitude);
		SpeedView = (TextView) findViewById(R.id.Speed);
		TimeView = (TextView) findViewById(R.id.Time);
		DistanceView = (TextView) findViewById(R.id.Distance);
		TimesView = (TextView) findViewById(R.id.times);
		chart = (com.amap.navi.demo.activity.GaugeChart01View) findViewById(R.id.chart_view);
		Intent myintent = new Intent(RecordsActivity.this, RecordsService.class);
		RecordsActivity.this.startService(myintent);

		gofun();

	}

	private void gofun() {
		button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new ButtonListener());

	}

	class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			LongitudeView.setText("0");
			LatitudeView.setText("0");
			SpeedView.setText("0");
			DistanceView.setText("0");
			// distance = 0.0;
			TimeView.setText("0");
			TimesView.setText("0");
			chart.setAngle(0);
			chart.chartRender();
			chart.invalidate();
			if (!blnGo) {
				button.setText("Stop");
				blnGo = true;
				Intent intent2 = new Intent();
				intent2.setAction("Gps_demo_1");
				intent2.putExtra("cmd", CMD_START_SERVICE);
				sendBroadcast(intent2);
				System.out.println("-----Activity_CMD_START_SERVICE------");
			} else {
				button.setText("Start");
				blnGo = false;

				System.out.println("Activity--->blnGo = false;");
				Intent intent = new Intent();
				intent.setAction("Gps_demo_1");
				intent.putExtra("cmd", CMD_STOP_SERVICE);
				sendBroadcast(intent);
				System.out.println("--------Activity_CMD_STOP_SERVICE----");

			}

		}

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		dataReceiver = new DataReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("Gps_demo_1_");
		registerReceiver(dataReceiver, intentFilter);
		super.onStart();
	}

	private class DataReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			double lat = intent.getDoubleExtra("lat", 0.1);
			LatitudeView.setText(String.valueOf(lat));
			double lng = intent.getDoubleExtra("lng", 0.2);
			LongitudeView.setText(String.valueOf(lng));
			double spd = intent.getDoubleExtra("spd", 0.3);
			SpeedView.setText(String.valueOf(spd * 3.6f));
			chart.setAngle(spd * 3.6f * 4);
			chart.chartRender();
			chart.invalidate();
			double distance = intent.getDoubleExtra("distance", 0.4);
			DistanceView.setText(String.valueOf(distance));
			long time = intent.getLongExtra("Time", 0);
			TimeView.setText(ChangeTime(time));
			int iCount = intent.getIntExtra("Times", 0);
			TimesView.setText(String.valueOf(iCount));
			System.out.println("-----Activity-onRecevice------");
			System.out.println("Activity--->lat（纬度）" + lat);
			System.out.println("Activity--->lng（经度）" + lng);
			System.out.println("Activity--->spd（速度）" + spd);
			System.out.println("Activity--->distance（路程）" + distance);
			System.out.println("Activity--->Time(（时间）" + time);
			System.out.println("Activity--->Times（次数）" + iCount);
		}

	}

	/**
	 * 
	 * @param millisUntilFinished
	 * @return
	 */
	public String ChangeTime(long millisUntilFinished) {
		// TODO Auto-generated method stub
		String time;
		long myhour = (millisUntilFinished / 1000) / 3600;
		long myminute = ((millisUntilFinished / 1000) - myhour * 3600) / 60;
		long mysecond = millisUntilFinished / 1000 - myhour * 3600 - myminute
				* 60;
		time = myhour + ":" + myminute + ":" + mysecond;
		return time;

	}
	public boolean IsGpsOn() {
		// 获得手机是不是设置了GPS开启状态true：gps开启，false：GPS未开启
		LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		boolean GPS_status = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (GPS_status) {
			BlnGps = true;
		} else {
			BlnGps = false;
		}
		return BlnGps;
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (IsGpsOn() == false) {
			new AlertDialog.Builder(this)
					.setTitle("信息")
					.setMessage("GPS必须开启才能测速")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									Intent callGPSSettingIntent = new Intent(
											android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
									startActivity(callGPSSettingIntent);
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							}).show();

		}
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		unregisterReceiver(dataReceiver);
		finish();
		super.onStop();
	}

}

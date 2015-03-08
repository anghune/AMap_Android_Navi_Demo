package com.amap.navi.demo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.navi.demo.R;

public class MessageSendActivity extends Activity {
	private List<View> viewList;
	public static String strphonenumber1, strphonenumber2, strphonenumber3;
	public SharedPreferences preferences;
	public String telephonetext1, telephonetext2, telephonetext3, persontext1,
			persontext2, persontext3;
	public AutoCompleteTextView auto1;
	public AutoCompleteTextView auto2;
	public AutoCompleteTextView auto3;
	public TextView textView1, textView2, textView3;
	private String[] in = { Contacts._ID, Contacts.DISPLAY_NAME, Phone.NUMBER,
			Phone.CONTACT_ID };
	private Button save;
	private ArrayList<String> nameinformation = new ArrayList<String>();
	public ArrayAdapter<String> adapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		setTitle("短信发送");

		name();
		adapter = new ArrayAdapter<String>(MessageSendActivity.this,
				android.R.layout.simple_dropdown_item_1line, nameinformation);

		textView1 = (TextView) findViewById(R.id.teltphoneone);
		textView2 = (TextView) findViewById(R.id.teltphonetwo);
		textView3 = (TextView) findViewById(R.id.teltphonethree);
		auto1 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView11);
		auto2 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView12);
		auto3 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView13);
		save = (Button) findViewById(R.id.button);

		preferences = getSharedPreferences("save", MODE_PRIVATE);
		persontext1 = preferences.getString("persontext1", null);
		persontext2 = preferences.getString("persontext2", null);
		persontext3 = preferences.getString("persontext3", null);
		telephonetext1 = preferences.getString("telephonetext1", null);
		telephonetext2 = preferences.getString("telephonetext2", null);
		telephonetext3 = preferences.getString("telephonetext3", null);

		if (persontext1 != null)
			auto1.setText(persontext1);
		if (persontext2 != null)
			auto2.setText(persontext2);
		if (persontext3 != null)
			auto3.setText(persontext3);
		if (telephonetext1 != null)
			textView1.setText(telephonetext1);
		if (telephonetext2 != null)
			textView2.setText(telephonetext2);
		if (telephonetext3 != null)
			textView3.setText(telephonetext3);

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString("persontext1", auto1.getText().toString());
				editor.putString("persontext2", auto2.getText().toString());
				editor.putString("persontext3", auto3.getText().toString());
				/** 如果为空则在TextView里也显示为空 */
				if ((auto1.getText().toString()).equals("")) {
					editor.putString("telephonetext1", null);
					textView1.setText(null);
				} else {

					editor.putString("telephonetext1", textView1.getText()
							.toString());
				}

				if ((auto2.getText().toString()).equals("")) {
					editor.putString("telephonetext2", null);
					textView2.setText(null);
				} else {
					editor.putString("telephonetext2", textView2.getText()
							.toString());
				}

				if ((auto3.getText().toString()).equals("")) {
					editor.putString("telephonetext3", null);
					textView3.setText(null);
				} else {
					editor.putString("telephonetext3", textView3.getText()
							.toString());
				}
				editor.commit();
				Toast.makeText(MessageSendActivity.this, "保存成功!",
						Toast.LENGTH_SHORT).show();
			}
		});
		/** 绑定适配器 */
		auto1.setAdapter(adapter);
		auto2.setAdapter(adapter);
		auto3.setAdapter(adapter);

		/** 添加事件监听器 */
		auto1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				/** 找到相应联系人的电话并把它显示在TextView上 */
				ContentResolver resolver = getContentResolver();
				Cursor cursor = resolver.query(Contacts.CONTENT_URI, null,
						null, null, null);
				while (cursor.moveToNext()) {
					int iddex = cursor.getColumnIndex(in[0]);
					int id = cursor.getInt(iddex);
					int nameid = cursor.getColumnIndex(in[1]);
					String name = cursor.getString(nameid);
					if (name.equals(auto1.getText().toString())) {
						Cursor PhoneNumber = resolver.query(Phone.CONTENT_URI,
								null, in[3] + "=" + id, null, null);
						while (PhoneNumber.moveToNext()) {
							int phonenumber = PhoneNumber.getColumnIndex(in[2]);
							String phonenumber1 = PhoneNumber.getString(
									phonenumber).replace("-", "");
							textView1.setText(phonenumber1);
							strphonenumber1 = phonenumber1;
							;
						}
						break;
					}
				}
			}
		});

		auto2.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				/** 找到相应联系人的电话并把它显示在TextView上 */
				ContentResolver resolver = getContentResolver();
				Cursor cursor = resolver.query(Contacts.CONTENT_URI, null,
						null, null, null);
				while (cursor.moveToNext()) {
					int iddex = cursor.getColumnIndex(in[0]);
					int id = cursor.getInt(iddex);
					int nameid = cursor.getColumnIndex(in[1]);
					String name = cursor.getString(nameid);
					if (name.equals(auto2.getText().toString())) {
						Cursor PhoneNumber = resolver.query(Phone.CONTENT_URI,
								null, in[3] + "=" + id, null, null);
						while (PhoneNumber.moveToNext()) {
							int phonenumber = PhoneNumber.getColumnIndex(in[2]);
							String phonenumber1 = PhoneNumber.getString(
									phonenumber).replace("-", "");
							textView2.setText(phonenumber1);
							strphonenumber2 = phonenumber1;
						}
						break;
					}
				}
			}
		});
		auto3.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				/** 找到相应联系人的电话并把它显示在TextView上 */
				ContentResolver resolver = getContentResolver();
				Cursor cursor = resolver.query(Contacts.CONTENT_URI, null,
						null, null, null);
				while (cursor.moveToNext()) {
					int iddex = cursor.getColumnIndex(in[0]);
					int id = cursor.getInt(iddex);
					int nameid = cursor.getColumnIndex(in[1]);
					String name = cursor.getString(nameid);
					if (name.equals(auto3.getText().toString())) {
						Cursor PhoneNumber = resolver.query(Phone.CONTENT_URI,
								null, in[3] + "=" + id, null, null);
						while (PhoneNumber.moveToNext()) {
							int phonenumber = PhoneNumber.getColumnIndex(in[2]);
							String phonenumber1 = PhoneNumber.getString(
									phonenumber).replace("-", "");
							textView3.setText(phonenumber1);
							strphonenumber3 = phonenumber1;
						}
						break;
					}
				}
			}
		});
	}

	private void name() {

		ContentResolver resolver = this.getContentResolver();
		Cursor cursor = resolver.query(Contacts.CONTENT_URI, null, null, null,
				null);
		while (cursor.moveToNext()) {
			int nameid = cursor.getColumnIndex(in[1]);
			String name = cursor.getString(nameid);
			nameinformation.add(name);
		}
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) {
	 * getMenuInflater().inflate(R.menu.main, menu); return true; }
	 */
}

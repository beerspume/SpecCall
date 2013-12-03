package com.example.speccall;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Button btn_call = null;
	private Button btn_addContactor=null;
	private Button btn_deviceInfo=null;
	private Button btn_jni=null;
	private Button btn_vrl=null;
	private TextView et_phone_number = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		et_phone_number = (TextView) findViewById(R.id.et_phoneNumber);
		btn_call = (Button) findViewById(R.id.btn_call);
		btn_call.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {// Click Event of Call Button
				Intent intent = new Intent(
						"android.intent.action.CALL",
						Uri.parse("tel:" + et_phone_number.getText().toString()));
				startActivity(intent);
			}
		});
		btn_addContactor=(Button)findViewById(R.id.btn_add_contractor);
		btn_addContactor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {//Click Event of Add-Contactor Button
				Intent intent=new Intent(MainActivity.this,AddContactorActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
		
		btn_deviceInfo=(Button)findViewById(R.id.btn_main_device_info);
		btn_deviceInfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(MainActivity.this,DeviceInfoActivity.class);
				MainActivity.this.startActivity(intent);
				
			}
		});
		
		btn_jni=(Button)findViewById(R.id.btn_main_jni);
		btn_jni.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(MainActivity.this,LuaActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
		
		btn_vrl=(Button)findViewById(R.id.btn_main_voice_record_list);
		btn_vrl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(MainActivity.this,VoiceRecordListActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

package com.example.speccall;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class DeviceInfoActivity extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TelephonyManager tm=(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		
		List<String> data=new ArrayList<String>();
		data.add(this.getString(R.string.text_di_tv_phone_number) + ":\n"
				+ tm.getLine1Number());
		data.add(this.getString(R.string.text_di_tv_device_id) + ":\n"
				+ tm.getDeviceId());
		data.add(this.getString(R.string.text_di_tv_device_sofrware_version) + ":\n"
				+ tm.getDeviceSoftwareVersion());
		data.add(this.getString(R.string.text_di_tv_phone_type) + ":\n"
				+ tm.getPhoneType());
		data.add(this.getString(R.string.text_di_tv_subscriber_id) + ":\n"
				+ tm.getSubscriberId());
		
		data.add("");
		data.add(this.getString(R.string.text_di_tv_sim_sn) + ":\n"
				+ tm.getSimSerialNumber());
		data.add(this.getString(R.string.text_di_tv_sim_operator) + ":\n"
				+ tm.getSimOperator());
		data.add(this.getString(R.string.text_di_tv_sim_operator_name) + ":\n"
				+ tm.getSimOperatorName());
		data.add(this.getString(R.string.text_di_tv_sim_country_info) + ":\n"
				+ tm.getSimCountryIso());
		data.add(this.getString(R.string.text_di_tv_sim_state) + ":\n"
				+ tm.getSimState());

		data.add("");
		data.add(this.getString(R.string.text_di_tv_network_operator) + ":\n"
				+ tm.getNetworkOperator());
		data.add(this.getString(R.string.text_di_tv_network_operator_name) + ":\n"
				+ tm.getNetworkOperatorName());
		data.add(this.getString(R.string.text_di_tv_network_country_info) + ":\n"
				+ tm.getNetworkCountryIso());
		data.add(this.getString(R.string.text_di_tv_network_type) + ":\n"
				+ tm.getNetworkType());
		

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
		this.setListAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.device_info, menu);
		return true;
	}

}

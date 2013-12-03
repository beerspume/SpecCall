package com.example.speccall;

import com.example.speccall.util.Util;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContactorActivity extends Activity {
	private EditText et_phoneNumber=null;
	private EditText et_displayName=null;
	private Button btn_add=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contactor);
		et_phoneNumber=(EditText)this.findViewById(R.id.et_ac_phone_number);
		et_displayName=(EditText)this.findViewById(R.id.et_ac_display_name);
		btn_add=(Button)this.findViewById(R.id.btn_ac_add);
		btn_add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String phoneNumber=et_phoneNumber.getText().toString();
				String displayName=et_displayName.getText().toString();
				if(Util.isEmptyString(phoneNumber) || Util.isEmptyString(displayName)){
					Toast.makeText(AddContactorActivity.this, R.string.text_alert_empty, Toast.LENGTH_LONG).show();
				}else{
					insertContact(AddContactorActivity.this,displayName,phoneNumber);
				}
			}
		});
	}
	
	private Uri insertContact(Context context, String name, String phone) {
	       return null;
	}
}

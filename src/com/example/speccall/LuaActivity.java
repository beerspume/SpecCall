package com.example.speccall;

import com.example.speccall.util.Lua;
import com.example.speccall.util.Native;
import com.example.speccall.util.Util;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class LuaActivity extends Activity {
	private TextView tv1=null;
	private TextView tv2=null;
	private TextView tv3=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lua);
		
		tv1=(TextView)this.findViewById(R.id.textView1);
		tv1.setText(Native.getString());

		tv2=(TextView)this.findViewById(R.id.textView2);
		tv2.setText(Lua.runTest(this));
		
		tv3=(TextView)this.findViewById(R.id.textView3);
		tv3.setText(Util.getTempLuaScriptFolder().getAbsolutePath());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lua, menu);
		return true;
	}

}

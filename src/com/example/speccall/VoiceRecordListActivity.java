package com.example.speccall;

import java.io.File;

import com.example.speccall.util.Util;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class VoiceRecordListActivity extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
		this.setListAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.voice_record_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==R.id.menu_vrl_clean_all){
			File folder=Util.getVoiceRecordFolder();
			File[] files=folder.listFiles();
			for(int i=0;i<files.length;i++){
				files[i].delete();
			} 
		}
		this.refreshList();
		return true;
	}
	
	@Override
	protected void onResume() {
		this.refreshList();
		super.onResume();
	}

	private void refreshList(){
		ArrayAdapter<String> adapter=(ArrayAdapter<String>)this.getListAdapter();
		adapter.clear();
		File folder=Util.getVoiceRecordFolder();
		File[] files=folder.listFiles();
		if(files.length==0){
			adapter.add(this.getString(R.string.text_vrl_empty_record));
		}else{
			for(int i=0;i<files.length;i++){
				adapter.add(files[i].getName());
			}
		}
		adapter.notifyDataSetChanged();
	
	}

}

package com.example.speccall;

import java.io.File;
import java.util.Locale;

import com.example.speccall.util.Util;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioEncoder;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaRecorder.OutputFormat;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

public class PhoneService extends Service{
	MediaRecorder recorder=null;
	
	View view = null;
	TextView tv_direction=null;
	TextView tv_phoneNumber=null;
	TextView tv_displayName=null;
	private LayoutParams layoutParams;
	private boolean viewAdded = false;
	private WindowManager windowManager;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}


	@Override
	public void onCreate() {
		Log.i("PhoneService", "onCreate");
		windowManager = (WindowManager) this.getSystemService(WINDOW_SERVICE);
		view= LayoutInflater.from(this).inflate(R.layout.phone_service, null);
		layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,  
                LayoutParams.WRAP_CONTENT, LayoutParams.TYPE_SYSTEM_ERROR,  
                LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.RGBA_8888);
		layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
		layoutParams.height=500;
		
		tv_direction=(TextView)view.findViewById(R.id.tv_direction);
		tv_phoneNumber=(TextView)view.findViewById(R.id.tv_ac_phone_number);
		tv_displayName=(TextView)view.findViewById(R.id.tv_display_name);
		super.onCreate();
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String action=intent.getStringExtra("action");
		if("open".equals(action)){
			String phoneNumber=intent.getStringExtra("phoneNumber");
			String direction=intent.getStringExtra("direction");
			fillView(phoneNumber,direction);
			Log.i("PhoneService", "onStartCommand:"+phoneNumber);
			this.refresh();
		}else if("close".equals(action)){
			this.close();
		}else if("startRecord".equals(action)){
			String phoneNumber=intent.getStringExtra("phoneNumber");
			this.startRecord(phoneNumber);
		}else if("stopRecord".equals(action)){
			this.stopRecord();
		}
		return super.onStartCommand(intent, flags, startId);
	}
	
    private void refresh() {  
        if (viewAdded) {  
            windowManager.updateViewLayout(view, layoutParams);  
        } else {  
            windowManager.addView(view, layoutParams);  
            viewAdded = true;  
        }  
    }  
    private void close() {  
    	
        if (viewAdded) {  
        	tv_direction.setText("");
        	tv_phoneNumber.setText("");
            windowManager.removeView(view);  
            viewAdded = false;  
        }  
    }
    
    private void fillView(String phoneNumber,String direction){
    	this.clearView();
    	if("incoming".equals(direction)){
    		tv_direction.setText(R.string.text_ps_incoming);
    	}else if("outgoing".equals(direction)){
    		tv_direction.setText(R.string.text_ps_outgoing);
    	}
    	tv_phoneNumber.setText(phoneNumber);
    	Contactor c=this.findContactor(phoneNumber);
    	if(c!=null){
    		tv_displayName.setText(c.getDisplayName());
    	}
    	
    }
    private void clearView(){
		tv_direction.setText("");
    	tv_phoneNumber.setText("");
		tv_displayName.setText("");
    }
    
    private Contactor findContactor(String phoneNumber){
    	Log.i("FindContactor", "Phone Number:"+phoneNumber);
    	Contactor ret=null;
    	if(phoneNumber!=null){
	    	ContentResolver cr=this.getContentResolver();
	    	String[] columns = new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
	    	Cursor phoneCursor =cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, columns, null, null, null);
	    	if(phoneCursor!=null){
	    		while(phoneCursor.moveToNext()){
	    			String pn=phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
    				String displayName=phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
    		    	Log.i("FindContactor", "Found(phone number:"+pn+"; display name:"+displayName+"). Check match is:"+comparePhoneNumber(phoneNumber,pn));
	    			if(comparePhoneNumber(phoneNumber,pn)){
	    				ret=new Contactor();
	    				phoneNumber=ret.getPhoneNumber();
	    				ret.setDisplayName(displayName);
	    				ret.setPhoneNumber(phoneNumber);
	    				break;
	    			}else{
	    				continue;
	    			}
	    		}
	    	}
    	}
    	return ret;
    	
    }
    private class Contactor{
    	private String phoneNumber;
    	private String displayName;
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getDisplayName() {
			return displayName;
		}
		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}
    	
    }
    
    private boolean comparePhoneNumber(String p1,String p2){
    	boolean ret=false;
    	p1=Util.simplePhoneNumber(p1);
    	p2=Util.simplePhoneNumber(p2);
    	ret=p1.equals(p2);
    	return ret;
    }
    private void startRecord(String phoneNumber){
    	try{
    		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("yyyyMMddHHmmss",Locale.getDefault());
    		File voiceRecordFolder=Util.getVoiceRecordFolder();
    		Log.i("PhoneService","CallRecords Path:"+voiceRecordFolder.getAbsolutePath());
    		File voiceRecordFile = new File(voiceRecordFolder, phoneNumber + "_" + sdf.format(new java.util.Date(System.currentTimeMillis())) + ".3gp");
    		recorder = new MediaRecorder();
			recorder.setAudioSource(AudioSource.VOICE_CALL);
			recorder.setOutputFormat(OutputFormat.DEFAULT);
			recorder.setAudioEncoder(AudioEncoder.DEFAULT);
			recorder.setOutputFile(voiceRecordFile.getAbsolutePath());
			recorder.prepare();
			recorder.start();
			Log.i("PhoneService","startRecord");
    	}catch(Exception e){
			Log.i("PhoneService","startRecord Error");
    		stopRecord();
    	}
    }
    private void stopRecord(){
    	if(recorder!=null){
			try{
				recorder.stop();
			}catch(Exception e1){}
			recorder=null;
    	}
		Log.i("PhoneService","stopRecord");
    }
}

package com.example.speccall;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneStateReciver extends BroadcastReceiver {
	private static String lastPhoneNumber=null;
	private static String type=null;
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		Log.i("PhoneStateReciver","action:"+action);
		if (action.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
			lastPhoneNumber = intent
					.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
			type="outgoing";
		}else if(action.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)){
			String state=intent.getStringExtra(TelephonyManager.EXTRA_STATE);
			if(state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
				this.close(context);
				this.stopRecord(context);
			}else if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
				lastPhoneNumber = intent
						.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
				type="incoming";
				this.show(context, lastPhoneNumber,type);
			}else if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
				if("outgoing".equals(type)){
					this.show(context, lastPhoneNumber,type);
				}
				this.startRecord(context,lastPhoneNumber);
			}
		}else if(action.equals(Intent.ACTION_BOOT_COMPLETED)){
			Log.i("PhoneStateReciver", "BOOT_COMPLETED");
		}
	}
	
	private void show(Context context,String phoneNumber,String direction){
		Intent intent=new Intent(context,PhoneService.class);
		intent.putExtra("action", "open");
		intent.putExtra("phoneNumber", phoneNumber);
		intent.putExtra("direction", direction);
		context.startService(intent);
	}
	
	private void close(Context context){
		Intent intent=new Intent(context,PhoneService.class);
		intent.putExtra("action", "close");
		context.startService(intent);
	}
	
	private void startRecord(Context context,String phoneNumber){
		Intent intent=new Intent(context,PhoneService.class);
		intent.putExtra("action", "startRecord");
		intent.putExtra("phoneNumber", phoneNumber);
		context.startService(intent);
	}
	private void stopRecord(Context context){
		Intent intent=new Intent(context,PhoneService.class);
		intent.putExtra("action", "stopRecord");
		context.startService(intent);
	}

}

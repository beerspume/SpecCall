package com.example.speccall.lua;

import org.keplerproject.luajava.LuaObject;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

public class LuaContext {
	private static LuaState L=null;
	private static LuaContext instance=null;
	
	private TelephonyManager _tm=null;
	
	public static void init(Context context){
		if(instance==null){
			instance=new LuaContext();
		}
		
		instance._tm=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		
		L=LuaStateFactory.newLuaState();
		L.openLibs();
		try{
			L.pushObjectValue(instance);
			L.setGlobal("context");
		}catch(Exception e){
			Log.d("LuaContext", "Error cause:"+e.getMessage());
		}
		
	}
	
	public static LuaState getInstatnce(){
		return L;
	}
	
	public TelephonyManager getTelephonyManager(){
		return _tm;
	}
	
	public void runCallback(LuaObject o){
		if(o.isFunction()){
			try{
				o.call(new Object[]{"This message is form callback. Sub result is "});
			}catch(Exception e){
				Log.d("LuaContext",e.getMessage());
			}
		}
	}
	public void Log_i(String tag,String msg){
		Log.i(tag, msg);
	}
	public void Log_d(String tag,String msg){
		Log.d(tag, msg);
	}
	
}

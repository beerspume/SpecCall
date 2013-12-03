package com.example.speccall.util;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class Util {
	public static String cutSpace(String str){
		StringBuffer ret=new StringBuffer();
		if(str!=null){
			str=str.trim();
			for(int i=0;i<str.length();i++){
				if(str.charAt(i)!=' '){
					ret.append(str.charAt(i));
				}
			}
		}
		return ret.toString();
	}
	
	public static String simplePhoneNumber(String phoneNumber){
		StringBuffer ret=new StringBuffer();
		if(phoneNumber!=null){
			phoneNumber=cutSpace(phoneNumber);
			if(phoneNumber.length()==12 || (phoneNumber.length()==11 && !phoneNumber.startsWith("1"))){
				phoneNumber=phoneNumber.substring(phoneNumber.length()-8,phoneNumber.length());
			}
			if(phoneNumber.startsWith("+86")){
				phoneNumber=phoneNumber.substring(3,phoneNumber.length());
			}
		}
		ret.append(phoneNumber);
		return ret.toString();
	}
	
	public static boolean isEmptyString(String str){
		return (str==null || str.trim().equals(""));
	}
	
	public static File getDataFolder(){
		File ret=new File(Environment.getExternalStorageDirectory(),"SpecCall_Data");
		if(!ret.exists()){
			ret.mkdirs();
		}
		return ret;
		
		
	}
	public static File getVoiceRecordFolder(){
		File ret=new File(getDataFolder(),"CallRecords");
		if(!ret.exists()){
			ret.mkdirs();
		}
		return ret;
	}
	
	public static File getTempLuaScriptFolder(){
		File ret=new File(getDataFolder(),"TempLua");
		if(!ret.exists()){
			ret.mkdirs();
		}
		return ret;
	}
	
	public static String getLuaScript(Context context,String filename){
		java.io.InputStream is=null;
		java.io.ByteArrayOutputStream ret= new java.io.ByteArrayOutputStream(); 
		try{
			is=context.getAssets().open(filename);
			byte[] buff=new byte[1024];
			int readlen;
			while(-1!=(readlen=is.read(buff))){
				ret.write(buff, 0, readlen);
			}
		}catch(Exception e){
			Log.d("getLuaScript","Error when read file "+filename+", cause ("+e.getMessage()+")");
		}finally{
			try{
				is.close();
			}catch(Exception e){}
		}
		return ret.toString();
	}
}

package com.example.speccall.lua;

import org.keplerproject.luajava.LuaObject;
import org.keplerproject.luajava.LuaState;

import com.example.speccall.util.Util;

import android.content.Context;
import android.util.Log;

public class Lua {
	private static void initContext(Context context){
		if(LuaContext.getInstatnce()==null){
			LuaContext.init(context);
			LuaContext.getInstatnce().LdoString(Util.getLuaScript(context, "lua/test.lua"));
			LuaContext.getInstatnce().LdoString(Util.getLuaScript(context, "lua/test2.lua"));
		}
	}
	
	public static String runTest(Context context){
		initContext(context);
		
		LuaState L=LuaContext.getInstatnce();
		String ret="";
		
		L.getGlobal("sub");
		L.pushNumber(149);
		L.pushNumber(10);
		L.call(2, 1);
		L.setGlobal("ret");
		LuaObject lua_o=L.getLuaObject("ret");
		
//		L.getGlobal("runCallback");
//		L.call(0, 0);
//		
//		L.getGlobal("testSimInfo");
//		L.call(0, 0);

//		L.getGlobal("setText");
//		L.getGlobal("temp");
//		L.call(1, 0);
		
		ret=L.getLuaObject("text").getString();

		ret+=lua_o.getString();
		
		return ret;
	}
	
	public static String runTest2(Context context){
		initContext(context);

		LuaState L=LuaContext.getInstatnce();
		String ret="";
		

		L.getGlobal("getPhoneNumber");
		L.call(0, 1);
		L.setGlobal("ret");
		LuaObject lua_o=L.getLuaObject("ret");
		
		ret=lua_o.getString();
		
		return ret;
	}
	
	public static String getTag(Context context){
		initContext(context);
		
		LuaState L=LuaContext.getInstatnce();
		
		int n=0;
		L.getGlobal("setText");
		n++;
		L.getGlobal("text");
		n++;
		L.pushString("1111111s");
		n++;

		showStack(L);
		L.pop(n);
		showStack(L);
		
		return ""+L.isNoneOrNil(-1);
		
	}
	public static void showStack(LuaState L){
		Log.i("Lua_showStack", "Stack lenth is "+L.getTop());
		for(int i=1;i<=L.getTop();i++){
			LuaObject lua_o=L.getLuaObject(i);
			Log.i("Lua_showStack", i+":"+lua_o.toString());
		}
	}
}

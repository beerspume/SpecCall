package com.example.speccall.lua;

import org.keplerproject.luajava.LuaObject;
import org.keplerproject.luajava.LuaState;

import com.example.speccall.util.Util;

import android.content.Context;

public class Lua {
	private static void initContext(Context context){
		if(LuaContext.getInstatnce()==null){
			LuaContext.init(context);
		}
	}
	
	public static String runTest(Context context){
		initContext(context);
		
		LuaState L=LuaContext.getInstatnce();
		String ret="";
		L.LdoString(Util.getLuaScript(context, "lua/test.lua"));
		
		L.getGlobal("sub");
		L.pushNumber(149);
		L.pushNumber(10);
		L.call(2, 1);
		L.setGlobal("ret");
		LuaObject lua_o=L.getLuaObject("ret");
		
		L.getGlobal("runCallback");
		L.call(0, 0);
		
		L.getGlobal("testSimInfo");
		L.call(0, 0);
		
		ret=L.getLuaObject("text").getString();

		ret+=lua_o.getString();
		
		return ret;
	}
	
	public static String runTest2(Context context){
		initContext(context);

		LuaState L=LuaContext.getInstatnce();
		String ret="";
		L.LdoString(Util.getLuaScript(context, "lua/test2.lua"));
		

		L.getGlobal("getPhoneNumber");
		L.call(0, 1);
		L.setGlobal("ret");
		LuaObject lua_o=L.getLuaObject("ret");
		
		ret=lua_o.getString();
		
		return ret;
	}
}

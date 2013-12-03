package com.example.speccall.util;

import org.keplerproject.luajava.LuaObject;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import android.content.Context;

public class Lua {
	public static LuaState L=null;
	static{
		L=LuaStateFactory.newLuaState();
		L.openLibs();
	}
	public static String runTest(Context context){
		L.LdoString(Util.getLuaScript(context, "lua/test.lua"));
		String ret=L.getLuaObject("text").getString();
		L.getGlobal("sub");
//		L.getField(LuaState.LUA_GLOBALSINDEX, "sub");
		L.pushNumber(149);
		L.pushNumber(10);
		L.call(2, 1);
		L.setGlobal("ret");
//		L.setField(LuaState.LUA_GLOBALSINDEX, "ret");
		LuaObject lua_o=L.getLuaObject("ret");
		ret+=lua_o.getString();
		return ret;
	}
}

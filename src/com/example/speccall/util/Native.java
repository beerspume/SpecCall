package com.example.speccall.util;

public class Native {
	static{
		System.loadLibrary("SpecCallNative");
	}
	public static native String getString();
}

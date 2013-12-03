#include<jni.h>

JNIEXPORT jstring JNICALL Java_com_example_speccall_util_Native_getString
  (JNIEnv * env, jclass thiz){
	return (*env)->NewStringUTF(env, "Hi, I am JNI code.Hia hia");
}

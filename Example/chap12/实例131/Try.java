#include <jni.h>
#include "HelloWorld.h"
#include <stdio.h>
#include <windows.h>
JNIEXPORT void JNICALL
Java_HelloWorld_displayHelloWorld(JNIEnv *env, jobject obj){
    printf("Hello world!\n");// 在这里实现java类中定义的方法
    MessageBeep(0);// 调用到Windows本地，会发一声响
    return;
}

#include <jni.h>
#include "HelloWorld.h"
#include <stdio.h>
#include <windows.h>
JNIEXPORT void JNICALL
Java_HelloWorld_displayHelloWorld(JNIEnv *env, jobject obj){
    printf("Hello world!\n");// ������ʵ��java���ж���ķ���
    MessageBeep(0);// ���õ�Windows���أ��ᷢһ����
    return;
}

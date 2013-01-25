#include "WinReg.h"
#include "WinRegNameEnumeration.h"
#include <string.h>
#include <stdlib.h>
#include <windows.h>
JNIEXPORT jobject JNICALL Java_WinReg_getValue(JNIEnv* env, jobject this_obj, jstring name){  
	const char* cname; 
	jstring path; 
	const char* cpath; 
	HKEY hkey; 
	DWORD type; 
	DWORD size; 
	jclass this_class; 
	jfieldID id_root; 
	jfieldID id_path; 
	HKEY root; 
	jobject ret; 
	char* cret; 
	// ��ȡ��
	this_class = (*env)->GetObjectClass(env, this_obj); 
	// ��ȡ��IDs 
	id_root = (*env)->GetFieldID(env, this_class, "root", "I");
	id_path = (*env)->GetFieldID(env, this_class, "path", "Ljava/lang/String;"); 
	// ����� 
	root = (HKEY)(*env)->GetIntField(env, this_obj, id_root); 
	path = (jstring)(*env)->GetObjectField(env, this_obj, id_path); 
	cpath = (*env)->GetStringUTFChars(env, path, NULL); 
	// ��ע����
	if (RegOpenKeyEx(root, cpath, 0, KEY_READ, &hkey)!= ERROR_SUCCESS) {  
		(*env)->ThrowNew(env, (*env)->FindClass(env, "WinRegException"),"Open key failed");
		(*env)->ReleaseStringUTFChars(env, path, cpath); 
		return NULL; 
	}
	(*env)->ReleaseStringUTFChars(env, path, cpath); 
	cname = (*env)->GetStringUTFChars(env, name, NULL); 
	// ���ֵ�����ͺʹ�С
	if (RegQueryValueEx(hkey, cname, NULL, &type, NULL, &size) != ERROR_SUCCESS) {  
		(*env)->ThrowNew(env,(*env)->FindClass(env, "WinRegException"), "Query value key failed");
		RegCloseKey(hkey); 
		(*env)->ReleaseStringUTFChars(env, name, cname); 
		return NULL; 
	}
	// �����ڴ�ռ����洢�ջ�õ�ֵ 
	cret = (char*)malloc(size); 
	// ��ȡ��Щֵ
	if (RegQueryValueEx(hkey, cname, NULL, &type, cret, &size) != ERROR_SUCCESS){ 
		(*env)->ThrowNew(env,(*env)->FindClass(env, "WinRegException"), "Query value key 
failed");
		free(cret); 
		RegCloseKey(hkey); 
		(*env)->ReleaseStringUTFChars(env, name, cname); 
		return NULL; 
	}
	// ���ݲ�ͬ������,��ֵ�ֱ���� string, integer �� byte array */
	if (type == REG_SZ) {  
		ret = (*env)->NewStringUTF(env, cret); 
	}
	else if (type == REG_DWORD) { 
		jclass class_Integer = (*env)->FindClass(env, "java/lang/Integer");
		// ��ù��췽��ID
		jmethodID id_Integer = (*env)->GetMethodID(env, class_Integer, "<init>", "(I)V");
		int value = *(int*)cret; 
		// ���ù��췽��
		ret = (*env)->NewObject(env, class_Integer, id_Integer, value); 
	}
	else if (type == REG_BINARY) {  
		ret = (*env)->NewByteArray(env, size); 
		(*env)->SetByteArrayRegion(env, (jarray)ret, 0, size, cret); 
	}
	else {  
		(*env)->ThrowNew(env, (*env)->FindClass(env, "WinRegException"), "Unsupported value 
type");
		ret = NULL; 
	}
	free(cret); 
	RegCloseKey(hkey); 
	(*env)->ReleaseStringUTFChars(env, name, cname); 
	return ret; 
}
JNIEXPORT void JNICALL Java_WinReg_setValue(JNIEnv* env, jobject this_obj, jstring name, jobject 
value){  
	const char* cname; 
	jstring path; 
	const char* cpath; 
	HKEY hkey; 
	DWORD type; 
	DWORD size; 
	jclass this_class; 
	jclass class_value; 
	jclass class_Integer; 
	jfieldID id_root; 
	jfieldID id_path; 
	HKEY root; 
	const char* cvalue; 
	int ivalue; 
	// ��ȡ��
	this_class = (*env)->GetObjectClass(env, this_obj); 
	// ��ȡ��IDs 
	id_root = (*env)->GetFieldID(env, this_class, "root", "I");
	id_path = (*env)->GetFieldID(env, this_class, "path", "Ljava/lang/String;"); 
	// ��ȡ��
	root = (HKEY)(*env)->GetIntField(env, this_obj, id_root); 
	path = (jstring)(*env)->GetObjectField(env, this_obj, id_path); 
	cpath = (*env)->GetStringUTFChars(env, path, NULL); 
	// ��ע����
	if (RegOpenKeyEx(root, cpath, 0, KEY_WRITE, &hkey) != ERROR_SUCCESS) { 
		(*env)->ThrowNew(env,(*env)->FindClass(env, "WinRegException"), "Open key failed");
		(*env)->ReleaseStringUTFChars(env, path, cpath); 
		return; 
	}
	(*env)->ReleaseStringUTFChars(env, path, cpath); 
	cname = (*env)->GetStringUTFChars(env, name, NULL); 
	class_value = (*env)->GetObjectClass(env, value); 
	class_Integer = (*env)->FindClass(env, "java/lang/Integer");
	// �жϲ�ȷ������
	if ((*env)->IsAssignableFrom(env, class_value,(*env)->FindClass(env, "java/lang/String"))) {  
		// ���ָ���ַ���ָ��
		cvalue = (*env)->GetStringUTFChars(env, (jstring)value, NULL); 
		type = REG_SZ; 
		size = (*env)->GetStringLength(env, (jstring)value) + 1; 
	}
	else if ((*env)->IsAssignableFrom(env, class_value, class_Integer)) {  
		// ʹ��intValue�����ֵ
		jmethodID id_intValue = (*env)->GetMethodID(env,class_Integer, "intValue", "()I");
		ivalue = (*env)->CallIntMethod(env, value, id_intValue); 
		type = REG_DWORD; 
		cvalue = (char*)&ivalue; 
		size = 4; 
	}
	else if ((*env)->IsAssignableFrom(env, class_value, (*env)->FindClass(env, "[B"))) {  
		// ���ָ��bytes��ָ��
		type = REG_BINARY; 
		cvalue = (char*)(*env)->GetByteArrayElements(env, (jarray)value, NULL); 
		size = (*env)->GetArrayLength(env, (jarray)value); 
	}
	else {  
		// ����������������������͵����ͣ����޷����� 
		(*env)->ThrowNew(env,(*env)->FindClass(env, "WinRegException"), "Unsupported value 
type");
		RegCloseKey(hkey); 
		(*env)->ReleaseStringUTFChars(env, name, cname); 
		return; 
	}
	// ����ֵ
	if (RegSetValueEx(hkey, cname, 0, type, cvalue, size)!= ERROR_SUCCESS) 
	{
(*env)->ThrowNew(env,(*env)->FindClass(env, "WinRegException"), "Query value key 
failed");
	}
	RegCloseKey(hkey); 
	(*env)->ReleaseStringUTFChars(env, name, cname); 
	// ������ֵ�� string ����byte array,���ͷ�ָ��
	if (type == REG_SZ) {  
		(*env)->ReleaseStringUTFChars(env, (jstring)value, cvalue); 
	}
	else if (type == REG_BINARY) { 
		(*env)->ReleaseByteArrayElements(env, (jarray)value, cvalue, 0); 
	}
}
// ��ʼ�����ֵ�ö��
static int startNameEnumeration(JNIEnv* env, jobject this_obj, jclass this_class){  
	jfieldID id_index; 
	jfieldID id_count; 
	jfieldID id_root; 
	jfieldID id_path; 
	jfieldID id_hkey; 
	jfieldID id_maxsize; 
	HKEY root; 
	jstring path; 
	const char* cpath; 
	HKEY hkey; 
	int maxsize = 0; 
	int count = 0; 
	// ��ȡ��IDs 
	id_root = (*env)->GetFieldID(env, this_class, "root", "I");
	id_path = (*env)->GetFieldID(env, this_class, "path", "Ljava/lang/String;"); 
	id_hkey = (*env)->GetFieldID(env, this_class, "hkey", "I");
	id_maxsize = (*env)->GetFieldID(env, this_class, "maxsize", "I");
	id_index = (*env)->GetFieldID(env, this_class, "index", "I");
	id_count = (*env)->GetFieldID(env, this_class, "count", "I");
	// ��ȡ�� 
	root = (HKEY)(*env)->GetIntField(env, this_obj, id_root); 
	path = (jstring)(*env)->GetObjectField(env, this_obj, id_path); 
	cpath = (*env)->GetStringUTFChars(env, path, NULL); 
	// ��ע����
	if (RegOpenKeyEx(root, cpath, 0, KEY_READ, &hkey)!= ERROR_SUCCESS) { 
		(*env)->ThrowNew(env,(*env)->FindClass(env, "WinRegException"), "Open key failed");
		(*env)->ReleaseStringUTFChars(env, path, cpath); 
		return -1; 
	}
	(*env)->ReleaseStringUTFChars(env, path, cpath); 
	// ��ѯ��������������ֵĳ���
	if (RegQueryInfoKey(hkey, NULL, NULL, NULL, NULL,NULL, NULL, &count, &maxsize, NULL, NULL, NULL)!= ERROR_SUCCESS) {  
		(*env)->ThrowNew(env, 
			(*env)->FindClass(env, "Win32RegKeyException"),
			"Query info key failed");
		return -1; 
	}
	// �������ֵ
	(*env)->SetIntField(env, this_obj, id_hkey, (DWORD)hkey); 
	(*env)->SetIntField(env, this_obj, id_maxsize, maxsize + 1); 
	(*env)->SetIntField(env, this_obj, id_index, 0); 
	(*env)->SetIntField(env, this_obj, id_count, count); 
	return count; 
}
JNIEXPORT jboolean JNICALL
Java_Win32RegKeyNameEnumeration_hasMoreElements(JNIEnv* env, jobject this_obj){ 
	jclass this_class; 
	jfieldID id_index; 
	jfieldID id_count; 
	int index; 
	int count; 
	// ��ȡ��
	this_class = (*env)->GetObjectClass(env, this_obj); 
	// ��ȡ��IDs
	id_index = (*env)->GetFieldID(env, this_class, "index", "I");
	id_count = (*env)->GetFieldID(env, this_class, "count", "I");
	index = (*env)->GetIntField(env, this_obj, id_index); 
	if (index == -1) // ��һ��
	{  
		count = startNameEnumeration(env, this_obj, this_class); 
		index = 0; 
	}
	else
		count = (*env)->GetIntField(env, this_obj, id_count); 
	return index < count; 
}
JNIEXPORT jobject JNICALL 
Java_WinRegNameEnumeration_nextElement(JNIEnv* env, jobject this_obj){  
	jclass this_class; 
	jfieldID id_index; 
	jfieldID id_hkey; 
	jfieldID id_count; 
	jfieldID id_maxsize; 
	HKEY hkey; 
	int index; 
	int count; 
	int maxsize; 
	char* cret; 
	jstring ret; 
	// ��ȡ��
	this_class = (*env)->GetObjectClass(env, this_obj); 
	// ��ȡ��IDs 
	id_index = (*env)->GetFieldID(env, this_class, "index", "I");
	id_count = (*env)->GetFieldID(env, this_class, "count", "I");
	id_hkey = (*env)->GetFieldID(env, this_class, "hkey", "I");
	id_maxsize = (*env)->GetFieldID(env, this_class, "maxsize", "I");
	index = (*env)->GetIntField(env, this_obj, id_index); 
	if (index == -1) /* first time */
	{ 
		count = startNameEnumeration(env, this_obj, this_class); 
		index = 0; 
	}
	else
		count = (*env)->GetIntField(env, this_obj, id_count); 
	if (index >= count) // �Ѿ������һ��
	{ 
		(*env)->ThrowNew(env, 
			(*env)->FindClass(env, 
			"java/util/NoSuchElementException"),
			"past end of enumeration");
		return NULL; 
	}
	maxsize = (*env)->GetIntField(env, this_obj, id_maxsize); 
	hkey = (HKEY)(*env)->GetIntField(env, this_obj, id_hkey); 
	cret = (char*)malloc(maxsize); 
	// Ѱ����һ������ 
	if (RegEnumValue(hkey, index, cret, &maxsize, NULL, NULL,NULL, NULL) != 
ERROR_SUCCESS) { 
		(*env)->ThrowNew(env,(*env)->FindClass(env, "WinRegException"), "Enum value failed");
		free(cret); 
		RegCloseKey(hkey); 
		(*env)->SetIntField(env, this_obj, id_index, count); 
		return NULL; 
	}
	ret = (*env)->NewStringUTF(env, cret); 
	free(cret); 
	// ����index
	index++;
	(*env)->SetIntField(env, this_obj, id_index, index); 
	if (index == count) /* at end */
	{  
		RegCloseKey(hkey); 
	}
	return ret; 
}

// ��γ���Ӧ���.c�ļ�
JNIEXPORT void JNICALL 
Java_OrderServerImpl_shutDown(JNIEnv *env, jobject this) 
// ������ʵ��java���ж���ķ���
{ 
	jclass cls; 
	jfieldID fid; 
	DataSet *ds; 
	cls = (*env)->GetObjectClass(env, this); 
	fid = (*env)->GetFieldID(env, cls, "dataSet", "J"); 
	ds = (DataSet *) (*env)->GetObjectField(env, this, fid); 
	/*ͨ��DataSetָ��ʹ�� API */ 
	DSshutDown(ds); 
}

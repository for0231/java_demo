// 这段程序应存成.c文件
JNIEXPORT void JNICALL 
Java_OrderServerImpl_shutDown(JNIEnv *env, jobject this) 
// 在这里实现java类中定义的方法
{ 
	jclass cls; 
	jfieldID fid; 
	DataSet *ds; 
	cls = (*env)->GetObjectClass(env, this); 
	fid = (*env)->GetFieldID(env, cls, "dataSet", "J"); 
	ds = (DataSet *) (*env)->GetObjectField(env, this, fid); 
	/*通过DataSet指针使用 API */ 
	DSshutDown(ds); 
}

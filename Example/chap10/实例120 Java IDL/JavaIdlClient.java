//����Ҫʹ�õİ�
��	import JavaIdlApp.*; // ��Ӧ�õ�stub��
��	import org.omg.CosNaming.*; // Ҫʹ��CORBA�����ַ���
��	import org.omg.CORBA.*; // ʹ��CORBA����
import JavaIdlApp.*;
��	public class JavaIdlClient {//�����ͻ�Ӧ����
����public static void main(String args[]){
���� try {//����ORB����
���� 	ORB orb = ORB.init(args, null); // argsΪ�ͻ���������ʱ�������в���
���� 	org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
		//����
���� 	NamingContext ncRef = NamingContextHelper.narrow(objRef); // ���ͱ任
���� 	NameComponent nc = new NameComponent("JavaIdl", "");// ע�������
���� 	NameComponent path[]= {nc};
		//ʹ��ORB�����ַ���Ѱ��JavaIdl����
���� 	JavaIdl JavaIdlRef = JavaIdlHelper.narrow(ncRef.resolve(path));
���� 	//����SimpleJavaIdl����,�ѷ���˷��ص�������ʾ����Ļ��
String JavaIdl = JavaIdlRef.SimpleJavaIdl();
���� 	System.out.println(JavaIdl);
���� } catch(Exception e) {
���� 	System.out.println("ERROR : " �� e);
��  e.printStackTrace(System.out);
���� } 
����} // main()
����} // JavaIdlClient

//����Ҫʹ�õİ���
����import JavaIdlApp.*; // ��Ӧ�õ�stub��
����import org.omg.CosNaming.*; // Ҫʹ��CORBA�����ַ���
����import org.omg.CORBA.*; // ʹ��CORBA����
����import org.omg.CosNaming.NamingContextPackage.* // ���ַ�������⴦��;
����
����public class JavaIdlServer
����{
���� public static void main(String args[]){
���� try{//����ORB����
���� ORB orb = ORB.init(args, null);
���� JavaIdlServant JavaIdlRef = new JavaIdlServant();
���� orb.connect(JavaIdlRef);
	 //ʹ��ORB�����ַ���Ѱ��JavaIdl����
���� org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
���� NamingContext ncRef = NamingContextHelper.narrow(objRef); // ���ͱ任
���� NameComponent nc = new NameComponent("JavaIdl", "");// ע�������
���� NameComponent path[]= {nc};
���� ncRef.rebind(path, JavaIdlRef);
���� java.lang.Object sync = new java.lang.Object();
���� synchronized(sync){ sync.wait(); }
���� } catch(Exception e) {
���� System.out.println("ERROR: " �� e);
���� e.printStackTrace(System.out);
���� } 
���� } // main()
����} // JavaIdlServer
����class JavaIdlServant extends _JavaIdlImplBase{
���� public String SimpleJavaIdl() { return "\n\nJava IDL��������˼�!\n\n; }
����}

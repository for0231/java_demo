//引入要使用的包：
　　import JavaIdlApp.*; // 本应用的stub类
　　import org.omg.CosNaming.*; // 要使用CORBA的名字服务
　　import org.omg.CORBA.*; // 使用CORBA服务
　　import org.omg.CosNaming.NamingContextPackage.* // 名字服务的例外处理;
　　
　　public class JavaIdlServer
　　{
　　 public static void main(String args[]){
　　 try{//建立ORB对象
　　 ORB orb = ORB.init(args, null);
　　 JavaIdlServant JavaIdlRef = new JavaIdlServant();
　　 orb.connect(JavaIdlRef);
	 //使用ORB的名字服务寻找JavaIdl对象
　　 org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
　　 NamingContext ncRef = NamingContextHelper.narrow(objRef); // 类型变换
　　 NameComponent nc = new NameComponent("JavaIdl", "");// 注册服务类
　　 NameComponent path[]= {nc};
　　 ncRef.rebind(path, JavaIdlRef);
　　 java.lang.Object sync = new java.lang.Object();
　　 synchronized(sync){ sync.wait(); }
　　 } catch(Exception e) {
　　 System.out.println("ERROR: " ＋ e);
　　 e.printStackTrace(System.out);
　　 } 
　　 } // main()
　　} // JavaIdlServer
　　class JavaIdlServant extends _JavaIdlImplBase{
　　 public String SimpleJavaIdl() { return "\n\nJava IDL，可以如此简单!\n\n; }
　　}

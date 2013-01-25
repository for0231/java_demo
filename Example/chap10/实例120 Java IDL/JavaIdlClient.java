//引入要使用的包
　	import JavaIdlApp.*; // 本应用的stub类
　	import org.omg.CosNaming.*; // 要使用CORBA的名字服务
　	import org.omg.CORBA.*; // 使用CORBA服务
import JavaIdlApp.*;
　	public class JavaIdlClient {//声明客户应用类
　　public static void main(String args[]){
　　 try {//建立ORB对象
　　 	ORB orb = ORB.init(args, null); // args为客户程序启动时的命令行参数
　　 	org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
		//启动
　　 	NamingContext ncRef = NamingContextHelper.narrow(objRef); // 类型变换
　　 	NameComponent nc = new NameComponent("JavaIdl", "");// 注册服务类
　　 	NameComponent path[]= {nc};
		//使用ORB的名字服务寻找JavaIdl对象
　　 	JavaIdl JavaIdlRef = JavaIdlHelper.narrow(ncRef.resolve(path));
　　 	//调用SimpleJavaIdl操作,把服务端返回的内容显示在屏幕上
String JavaIdl = JavaIdlRef.SimpleJavaIdl();
　　 	System.out.println(JavaIdl);
　　 } catch(Exception e) {
　　 	System.out.println("ERROR : " ＋ e);
　  e.printStackTrace(System.out);
　　 } 
　　} // main()
　　} // JavaIdlClient

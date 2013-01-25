package jtajts;

import javax.naming.*;
import java.util.Properties;
import javax.rmi.PortableRemoteObject;
import javax.transaction.UserTransaction;
import javax.transaction.*;

public class JtaClient {
  private JtaHome jtaHome = null;

  ////创建一个EJB客户端
  public JtaClient() {
    try {
      //得到名字上下文
      Context ctx = getInitialContext();
      //查询jndi名
      Object ref = ctx.lookup("Jta");
      //通过强制转型得到Home接口
      jtaHome = (JtaHome) PortableRemoteObject.narrow(ref, JtaHome.class);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private Context getInitialContext() throws Exception {
    String url = "t3://cgb-4wn01xj69v6:7001";
    String user = null;
    String password = null;
    Properties properties = null;
    try {
      properties = new Properties();
      properties.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
      properties.put(Context.PROVIDER_URL, url);
      if (user != null) {
        properties.put(Context.SECURITY_PRINCIPAL, user);
        properties.put(Context.SECURITY_CREDENTIALS, password == null ? "" : password);
      }

      return new InitialContext(properties);
    }
    catch(Exception e) {
      System.out.println("Unable to connect to WebLogic server at " + url);
      System.out.println("Please make sure that the server is running.");
      throw e;
    }
  }
 
  //工具函数
  
  public JtaHome getHome() {
    return jtaHome;
  }
  //Main函数

  public static void main(String[] args) {
    JtaClient client = new JtaClient();
	 //用client的getHome()函数调用Home接口函数得到远程接口的引用 
    // 用远程接口的引用访问EJB
    System.out.print("This is a transaction example based on JTA!\n");
    try {

      Jta Jta=client.getHome().create();
      System.out.print(Jta.myTransation());

      System.out.print("The example is over!");
    }
    catch(Exception e) {
          e.printStackTrace();
    }
  }
}
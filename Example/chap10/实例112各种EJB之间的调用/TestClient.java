package interejbs;

import javax.naming.*;
import java.util.Properties;
import javax.rmi.PortableRemoteObject;

public class TestClient {
  private firstEJBHome firstEJBHomeObject = null;

  //创建一个EJB客户端
  public TestClient() {
    try {
      //得到名字上下文
      Context ctx = getInitialContext();
      //查询jndi名
      Object ref = ctx.lookup("firstEJB");
      //通过强制转型得到Home接口
      firstEJBHomeObject = (firstEJBHome) PortableRemoteObject.narrow(ref, firstEJBHome.class);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
	//这个函数返回一个经过初始化的上下文
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

  //----------------------------------------------------------------------------
  //工具函数
  //----------------------------------------------------------------------------

  public firstEJBHome getHome() {
    return firstEJBHomeObject;
  }
  //Main函数

  public static void main(String[] args) {
    TestClient client = new TestClient();
	 //用client的getHome()函数调用Home接口函数得到远程接口的引用 
    // 用远程接口的引用访问EJB
    try {
      firstEJB firstEJB=client.getHome().create();
      System.out.print(firstEJB.MyPrint());
    }
    catch(Exception e) {
            e.printStackTrace();
    }    
  }
}
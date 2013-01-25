//一个简单例子的客户端测试程序
package simpleejb;

import javax.naming.*;
import java.util.Properties;
import javax.rmi.PortableRemoteObject;

public class TimeFunctionsTestClient1 {
  static final private String ERROR_NULL_REMOTE = "Remote interface reference is null.  It must be created by calling one of the Home interface methods first.";
  static final private int MAX_OUTPUT_LINE_LENGTH = 100;
  private boolean logging = true;
  private TimeFunctionsHome timeFunctionsHome = null;
  private TimeFunctions timeFunctions = null;

  //创建一个访问EJB的客户端应用程序
  public TimeFunctionsTestClient1() {
    long startTime = 0;
    if (logging) {
      log("Initializing bean access.");
      startTime = System.currentTimeMillis();
    }

    try {
      //得到名字上下文
      Context ctx = getInitialContext();
      //查询jndi名
      Object ref = ctx.lookup("TimeFunctions");
      //通过强制转型得到Home接口
      timeFunctionsHome = (TimeFunctionsHome) PortableRemoteObject.narrow(ref, TimeFunctionsHome.class);
      if (logging) {
        long endTime = System.currentTimeMillis();
        log("Succeeded initializing bean access.");
        log("Execution time: " + (endTime - startTime) + " ms.");
      }
    }
    catch(Exception e) {
      if (logging) {
        log("Failed initializing bean access.");
      }
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
      log("Unable to connect to WebLogic server at " + url);
      log("Please make sure that the server is running.");
      throw e;
    }
  }

  //----------------------------------------------------------------------------
  // 这个函数用于使用本地接口产生一个远程接口的应用 
  //----------------------------------------------------------------------------
  public TimeFunctions create() {
    long startTime = 0;
    if (logging) {
      log("Calling create()");
      startTime = System.currentTimeMillis();
    }
    try {
      timeFunctions = timeFunctionsHome.create();
      if (logging) {
        long endTime = System.currentTimeMillis();
        log("Succeeded: create()");
        log("Execution time: " + (endTime - startTime) + " ms.");
      }
    }
    catch(Exception e) {
      if (logging) {
        log("Failed: create()");
      }
      e.printStackTrace();
    }

    if (logging) {
      log("Return value from create(): " + timeFunctions + ".");
    }
    return timeFunctions;
  }

   //通过Bean使用远程接口方法访问数据
  
  public long getTime() {
    long returnValue = 0;
    if (timeFunctions == null) {
      System.out.println("Error in getTime(): " + ERROR_NULL_REMOTE);
      return returnValue;
    }
    long startTime = 0;
    if (logging) {
      log("Calling getTime()");
      startTime = System.currentTimeMillis();
    }

    try {
      returnValue = timeFunctions.getTime();
      if (logging) {
        long endTime = System.currentTimeMillis();
        log("Succeeded: getTime()");
        log("Execution time: " + (endTime - startTime) + " ms.");
      }
    }
    catch(Exception e) {
      if (logging) {
        log("Failed: getTime()");
      }
      e.printStackTrace();
    }

    if (logging) {
      log("Return value from getTime(): " + returnValue + ".");
    }
    return returnValue;
  }

  public void testRemoteCallsWithDefaultArguments() {
    if (timeFunctions == null) {
      System.out.println("Error in testRemoteCallsWithDefaultArguments(): " + ERROR_NULL_REMOTE);
      return ;
    }
    getTime();
  }

  // 工具函数
  private void log(String message) {
    if (message == null) {
      System.out.println("-- null");
      return ;
    }
    if (message.length() > MAX_OUTPUT_LINE_LENGTH) {
      System.out.println("-- " + message.substring(0, MAX_OUTPUT_LINE_LENGTH) + " ...");
    }
    else {
      System.out.println("-- " + message);
    }
  }
  //main()函数

  public static void main(String[] args) {
    TimeFunctionsTestClient1 client = new TimeFunctionsTestClient1();
    client.create();
    client.getTime();
    
  }
}
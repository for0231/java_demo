package interejbs;

import javax.ejb.*;
import javax.naming.*;
import java.util.Properties;
import javax.rmi.PortableRemoteObject;

public class firstEJBBean implements SessionBean {
  SessionContext sessionContext;
  private secondEJBHome secondEJBHomeObject = null;
  public void ejbCreate() throws CreateException {
    try {
      //得到名字上下文
      Context ctx = getInitialContext();
      //查询jndi名
      Object ref = ctx.lookup("secondEJB");
      //通过强制转型得到Home接口
      secondEJBHomeObject = (secondEJBHome) PortableRemoteObject.narrow(ref, secondEJBHome.class);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public void ejbRemove() {
  }
  public void ejbActivate() {
  }
  public void ejbPassivate() {
  }
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
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
           throw e;
    }
  }
	 //这个函数将调用secondEJB
  public String MyPrint() {
    String ret="This is a example for interEJBS:\n";
    ret=ret+"This is firstEJB,now calling secondEJB\n";
    try {
      secondEJB secondEJB=secondEJBHomeObject.create();
      ret=secondEJB.forFirst(ret)+"example is over!\n";
    }
    catch(Exception e) {
        e.printStackTrace();
    }
    return ret;
  }
   //这个函数将被secondEJB调用
  public String forSecond(String fromSecond) {
    fromSecond=fromSecond+"This is firstEJB called by secondEJB\n";
    return fromSecond;
  }
}
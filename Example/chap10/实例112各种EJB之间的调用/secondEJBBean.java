package interejbs;

import javax.ejb.*;
import javax.naming.*;
import java.util.Properties;
import javax.rmi.PortableRemoteObject;

public class secondEJBBean implements SessionBean {
  SessionContext sessionContext;
  private firstEJBHome firstEJBHomeObject = null;
  public void ejbCreate() throws CreateException {
    try {
      //�õ�����������
      Context ctx = getInitialContext();
      //��ѯjndi��
      Object ref = ctx.lookup("firstEJB");
      //ͨ��ǿ��ת�͵õ�Home�ӿ�
      firstEJBHomeObject = (firstEJBHome) PortableRemoteObject.narrow(ref, firstEJBHome.class);
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
    //�����������һ��������ʼ����������
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
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  //�����������FirstEJB���ã�ͬʱ��������secondEJB
  public String forFirst(String fromFirst) {
    try {
      firstEJB firstEJB=firstEJBHomeObject.create();
      fromFirst=fromFirst+"This is secondEJB called by firstEJB\n";
      fromFirst=firstEJB.forSecond(fromFirst);
    }
    catch(Exception e) {
            e.printStackTrace();
    }    
    return fromFirst;
  }
}
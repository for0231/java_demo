package interejbs;

import javax.naming.*;
import java.util.Properties;
import javax.rmi.PortableRemoteObject;

public class TestClient {
  private firstEJBHome firstEJBHomeObject = null;

  //����һ��EJB�ͻ���
  public TestClient() {
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
      System.out.println("Unable to connect to WebLogic server at " + url);
      System.out.println("Please make sure that the server is running.");
      throw e;
    }
  }

  //----------------------------------------------------------------------------
  //���ߺ���
  //----------------------------------------------------------------------------

  public firstEJBHome getHome() {
    return firstEJBHomeObject;
  }
  //Main����

  public static void main(String[] args) {
    TestClient client = new TestClient();
	 //��client��getHome()��������Home�ӿں����õ�Զ�̽ӿڵ����� 
    // ��Զ�̽ӿڵ����÷���EJB
    try {
      firstEJB firstEJB=client.getHome().create();
      System.out.print(firstEJB.MyPrint());
    }
    catch(Exception e) {
            e.printStackTrace();
    }    
  }
}
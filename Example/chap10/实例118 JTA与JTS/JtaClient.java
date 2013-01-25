package jtajts;

import javax.naming.*;
import java.util.Properties;
import javax.rmi.PortableRemoteObject;
import javax.transaction.UserTransaction;
import javax.transaction.*;

public class JtaClient {
  private JtaHome jtaHome = null;

  ////����һ��EJB�ͻ���
  public JtaClient() {
    try {
      //�õ�����������
      Context ctx = getInitialContext();
      //��ѯjndi��
      Object ref = ctx.lookup("Jta");
      //ͨ��ǿ��ת�͵õ�Home�ӿ�
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
 
  //���ߺ���
  
  public JtaHome getHome() {
    return jtaHome;
  }
  //Main����

  public static void main(String[] args) {
    JtaClient client = new JtaClient();
	 //��client��getHome()��������Home�ӿں����õ�Զ�̽ӿڵ����� 
    // ��Զ�̽ӿڵ����÷���EJB
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
package csejb;

import javax.naming.*;
import java.util.Properties;
import javax.rmi.PortableRemoteObject;

public class CsClient {
  private theServerHome theServerHomeObject = null;

  //����һ������EJB�Ŀͻ���Ӧ�ó���
  public CsClient() {
    try {
      //�õ�����������
      Context ctx = getInitialContext();
      //��ѯjndi��
      Object ref = ctx.lookup("theServer");
      //ͨ��ǿ��ת�͵õ�Home�ӿ�
      theServerHomeObject = (theServerHome) PortableRemoteObject.narrow(ref, theServerHome.class);
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
  // ���ߺ���
  //----------------------------------------------------------------------------

  public theServerHome getHome() {
    return theServerHomeObject;
  }
  //Main����

  public static void main(String[] args) {
    String retStr="This is C/S EJB example.\n";
     CsClient client = new CsClient();
	  //��client��getHome()��������Home�ӿں����õ�Զ�̽ӿڵ����� 
    // ��Զ�̽ӿڵ����÷���EJB
    try {
      retStr=retStr+"Now the Client application is ready to access the Server.\n";
      theServer theServer=client.getHome().create();
      retStr=retStr+theServer.CServer();
      retStr=retStr+"This is client speaking.\nThe example is over!\n";
      System.out.print(retStr);
    }
    catch(Exception e) {
        e.printStackTrace();
    }

  }
}
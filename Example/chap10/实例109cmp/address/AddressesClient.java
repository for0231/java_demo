package address;

import javax.naming.*;
import java.util.Properties;
import javax.rmi.PortableRemoteObject;
import java.util.Collection;
import java.util.Iterator;
import java.util.Enumeration;

public class AddressesClient {
  static final private int MAX_OUTPUT_LINE_LENGTH = 50;
  private AddressesHome addressesHome = null;

  //����һ��EJB���Կͻ���
  public AddressesClient() {
    try {
      //�õ�����������
      Context ctx = getInitialContext();
      //��ѯjndi��
      Object ref = ctx.lookup("Addresses");
      //ͨ��ǿ��ת�͵õ�Home�ӿ�
      addressesHome = (AddressesHome) PortableRemoteObject.narrow(ref, AddressesHome.class);
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

  // ���ߺ���
  public AddressesHome getHome() {
    return addressesHome;
  }

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

  public void executeFindAll() {
    try {
      executeCollectionFinder(addressesHome.findAll());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void executeCollectionFinder(Collection all) {
    try {
      Iterator iterator = all.iterator();
      while (iterator.hasNext()) {
        Object object = iterator.next();
        Addresses addresses = (Addresses) PortableRemoteObject.narrow(object, Addresses.class);
        log(addresses.toString());
      }
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void executeEnumerationFinder(Enumeration all) {
    try {
      while (all.hasMoreElements()) {
        Object object = all.nextElement();
        Addresses addresses = (Addresses) PortableRemoteObject.narrow(object, Addresses.class);
        log(addresses.toString());
      }
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  //Main����

  public static void main(String[] args) {
    AddressesClient client = new AddressesClient();
	 //��client��getHome()��������Home�ӿں����õ�Զ�̽ӿڵ����� 
    // ��Զ�̽ӿڵ����÷���EJB
    try {
      Addresses Addressesobj=client.getHome().create("Jame","Janny");
      Addressesobj.setCity("New York");
      Addressesobj.setState("New York");
      Addressesobj.setZip("310000");
      Addressesobj.setAddress("Wall Street");
      System.out.print("This is CMP EntityBean example!\n");
	  System.out.print("FirstName:"+Addressesobj.getFirstName()+"\n");
      System.out.print("LastName:"+Addressesobj.getLastName()+"\n");
      System.out.print("Address:"+Addressesobj.getAddress()+"\n");
      System.out.print("State:"+Addressesobj.getState()+"\n");
	  System.out.print("The example is over!\n");
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
}
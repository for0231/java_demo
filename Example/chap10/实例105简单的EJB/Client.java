import javax.naming.*;
import javax.rmi.PortableRemoteObject;
import java.util.Properties;
import com.javapro.ejb.StringProcessor;
import com.javapro.ejb.StringProcessorHome;
//ͨ��Client��������Bean����
public class Client 
{
  public static void main(String[] args)
 {
//ΪJNDI��ʼ��ȡ��ϵͳ����
    Properties properties = new Properties();
    try {
       //ȡ�ó�ʼ������jndiContext����������JNDI������ʼ��
InitialContext jndiContext = new InitialContext(properties);
       //ȡ��Home���������
         Object ref  = jndiContext.lookup("HelloWorldHome");
         HelloWorldHome home = (HelloWorldHome)
PortableRemoteObject.narrow (ref, HelloWorldHome.class);
      //��EJB���ɿ�Home����EJB����
         HelloWorld hello= home.create();
      //����EJB���󷽷�printHelloWorld����,EJB����ѵ���ί�ɸ�Bean
         System.out.println (hello.printHelloWorld());
}
    catch(Exception e) {
      System.out.println(e.toString());
    }
  }
}

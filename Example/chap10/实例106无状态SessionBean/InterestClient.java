package Interest;
import java.util.Properties;
import javax.rmi.PortableRemoteObject; 
import javax.naming.*;
class InterestClient
{
   public static void main(String[] args)
{ 
       try
       {   //��ȡϵͳ���ԣ���ʼ��JNDI
 Properties props = new Properties();
// ȡ��Home���������
          InitialContext jndiContext = new InitialContext(props);
          Object ref  = jndiContext.lookup("interest/Interest");                      InterestHome home = (InterestHome) 
          PortableRemoteObject.narrow (ref, InterestHome.class);
         // ����EJB����
          Interest interest = home.create();
	// ������Ϣ
        System.out.println("��Ϣ�ǣ�");
         System.out.println(interest.calculateCompoundInterest (1000, 0.10, 2));
       }
       catch(Exception e)
       {
          System.out.println(e.toString());
       }
     }

package myEjb.Hello;
import java.rmi.RemoteException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
// ��״̬�Ự(������һ��ʵ���о��彲��)Bean
public class StringProcessorBean implements SessionBean 
{
 // ������EJB����ʵ�ֵķ���
  public void ejbCreate() 
{
  System.out.println("ejbCreate()");
  }
  public void ejbRemove() 
{
    System.out.println("ejbRemove()");
  }
  public void ejbActivate() 
{
  System.out.println("ejbActivate()");
  }
  public void ejbPassivate()
{
  System.out.println("ejbPassivate()");
  }
  public void setSessionContext(SessionContext sc)
{
}
//���񷽷� printHelloWorld()
  public String printHelloWorld()
  {
return "Hello,World!"
}
}

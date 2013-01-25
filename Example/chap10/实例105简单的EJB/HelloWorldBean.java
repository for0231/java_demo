package myEjb.Hello;
import java.rmi.RemoteException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
// 无状态会话(将在下一个实例中具体讲解)Bean
public class StringProcessorBean implements SessionBean 
{
 // 下面是EJB必须实现的方法
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
//商务方法 printHelloWorld()
  public String printHelloWorld()
  {
return "Hello,World!"
}
}

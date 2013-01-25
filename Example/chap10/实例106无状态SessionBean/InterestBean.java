package Interest;
import javax.ejb.*;
//会话Bean必须实现SessionBean
public class CountBean implements SessionBean
 {
public double calInterest(double principle, double rate, double periods)
{
   System.out.println ("Someone called `calculateCompoundInterest!'");
    // 返回一个利息
return principle * Math.pow(1+rate, periods) - principle;
}
// EJB 必须实现的方法　　　　
  public void ejbCreate() throws CreateException{
   System.out.println("ejbCreate()");
  }
  public void ejbRemove() {
System.out.println("ejbRemove()");  
}
  public void ejbActivate() {
    System.out.println("ejbActivate()");
  }
  public void ejbPassivate() {
   System.out.println("ejbPassivate()");
  }
  public void setSessionContext(SessionContext sessionContext) {
  }
}

package Count;
import javax.ejb.*;
//会话Bean必须实现SessionBean
public class CountBean implements SessionBean {
 // 这个计数值就是会话状态的　　　　　　　　　　　　　　　　　　　　　　　　
int val;
  SessionContext sessionContext;
// 累加器，以对话状态存储起来
  public int  count(){
 System.out.println("count()");
  return val++;
}
// EJB 必须实现的方法　　　　
  public void ejbCreate(int value) throws CreateException{
   this.val = value;
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
    this.sessionContext = sessionContext;
  }
}

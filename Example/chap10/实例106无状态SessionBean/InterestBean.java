package Interest;
import javax.ejb.*;
//�ỰBean����ʵ��SessionBean
public class CountBean implements SessionBean
 {
public double calInterest(double principle, double rate, double periods)
{
   System.out.println ("Someone called `calculateCompoundInterest!'");
    // ����һ����Ϣ
return principle * Math.pow(1+rate, periods) - principle;
}
// EJB ����ʵ�ֵķ�����������
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

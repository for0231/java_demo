package Count;
import javax.ejb.*;
//�ỰBean����ʵ��SessionBean
public class CountBean implements SessionBean {
 // �������ֵ���ǻỰ״̬�ġ�����������������������������������������������
int val;
  SessionContext sessionContext;
// �ۼ������ԶԻ�״̬�洢����
  public int  count(){
 System.out.println("count()");
  return val++;
}
// EJB ����ʵ�ֵķ�����������
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

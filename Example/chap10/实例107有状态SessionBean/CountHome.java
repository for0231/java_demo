package Count;
import java.rmi.RemoteException;
import javax.ejb.*;
// Home������EJB������������ɿ�
public interface CountHome extends EJBHome 
{
  // �÷�������EJB����,value�������ڼ������ĳ�ʼ��
 // ����״̬�ỰBean�в��ܴ�����
Trader create(int value) throws CreateException, RemoteException;
}

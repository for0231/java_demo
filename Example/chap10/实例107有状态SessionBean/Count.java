package Count;
import javax.ejb.*;
import java.rmi.RemoteException
// ����CountBean�ӿ�
public interface Count extends EJBObject 
{
  public int count()throws RemoteException;
}
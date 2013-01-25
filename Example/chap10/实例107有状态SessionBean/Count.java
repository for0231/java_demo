package Count;
import javax.ejb.*;
import java.rmi.RemoteException
// 定义CountBean接口
public interface Count extends EJBObject 
{
  public int count()throws RemoteException;
}
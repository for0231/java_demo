package Count;
import java.rmi.RemoteException;
import javax.ejb.*;
// Home对象是EJB对象的制作生成库
public interface CountHome extends EJBHome 
{
  // 该方法生成EJB对象,value参数用于计数器的初始化
 // 在无状态会话Bean中不能带参数
Trader create(int value) throws CreateException, RemoteException;
}

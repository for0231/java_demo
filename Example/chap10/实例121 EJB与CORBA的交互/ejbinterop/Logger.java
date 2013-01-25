//Code Example 1: Logger.java
package ejbinterop;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
//接受简单的字符串日志信息并在服务器上显示出来
public interface Logger extends EJBObject
{//将服务器提供的信息和当前的时间记入日志
    void logString(String message) throws RemoteException;
}

//Code Example 1: Logger.java
package ejbinterop;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
//���ܼ򵥵��ַ�����־��Ϣ���ڷ���������ʾ����
public interface Logger extends EJBObject
{//���������ṩ����Ϣ�͵�ǰ��ʱ�������־
    void logString(String message) throws RemoteException;
}

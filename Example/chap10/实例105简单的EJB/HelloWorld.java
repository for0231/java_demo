package myEjb.Hello;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
// ����ӿ��ǿͻ�����EJB�����໥���õ��м�;��
public interface HelloWorld extends EJBObject 
{
public String printHelloWorld() throws RemoteException;
}

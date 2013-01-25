package myEjb.Hello;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
// 这个接口是客户端与EJB对象相互作用的中间途径
public interface HelloWorld extends EJBObject 
{
public String printHelloWorld() throws RemoteException;
}

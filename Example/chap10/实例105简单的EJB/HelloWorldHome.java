package myEjb.Hello;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;
/**
*这是HelloWorldBean的Home接口，它是EJB对象的生成库
*/
public interface HelloWorldHome extends EJBHome 
{
   HelloWorld create() throws RemoteException, CreateException;
}

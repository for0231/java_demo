package myEjb.Hello;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;
/**
*����HelloWorldBean��Home�ӿڣ�����EJB��������ɿ�
*/
public interface HelloWorldHome extends EJBHome 
{
   HelloWorld create() throws RemoteException, CreateException;
}

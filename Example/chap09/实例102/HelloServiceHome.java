package samples;
public interface HelloServiceHome extends javax.ejb.EJBHome
{
	samples.HelloService create()
		throws javax.ejb.CreateException, java.rmi.RemoteException;
}

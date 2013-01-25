package samples;
public interface HelloService extends javax.ejb.EJBObject
{
	java.lang.String hello(java.lang.String phrase)
		throws java.rmi.RemoteException;
}

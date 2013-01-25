package samples;
import java.rmi.RemoteException;
import java.security.Identity;
import java.util.Properties;
import javax.ejb.*;
public class HelloServiceBean implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
	public void ejbActivate() throws java.rmi.RemoteException
	{
	}
	public void ejbCreate()
		throws javax.ejb.CreateException, java.rmi.RemoteException
	{
	}
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	public void ejbRemove() throws java.rmi.RemoteException
	{
	}
	public javax.ejb.SessionContext getSessionContext()
	{
		return mySessionCtx;
	}
	public String hello(String phrase)
	{
		return "HELLO!! You just said :" + phrase;
	}
	public void setSessionContext(javax.ejb.SessionContext ctx)
		throws java.rmi.RemoteException
	{
		mySessionCtx = ctx;
	}
}

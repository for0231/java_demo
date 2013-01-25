package ejbinterop;

import java.rmi.RemoteException;
import javax.ejb.EJBHome;
import javax.ejb.CreateException;

public interface LoggerHome extends EJBHome
{
    Logger create() throws RemoteException, CreateException;
}
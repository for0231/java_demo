package Interest;
 import java.rmi.RemoteException;
 import javax.ejb.CreateException;
 import javax.ejb.EJBHome;
public interface InterestHome extends EJBHome
 {
   Interest create()throws RemoteException, CreateException;
 }

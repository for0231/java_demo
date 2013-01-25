package enroll.ejb;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface EnrollSessionHome extends EJBHome{

  EnrollSession create(String student_id) 
    throws RemoteException, CreateException;
}

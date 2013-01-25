//实现Enroll的本地接口
package enroll.ejb;

import java.util.*;
import java.rmi.RemoteException;
import javax.ejb.*;

public interface EnrollHome extends EJBHome{

  public Enroll create(
    String student_id, ArrayList courseItems)
    throws RemoteException, CreateException;
    
  public Enroll findByPrimaryKey(String student_id)
    throws ObjectNotFoundException,
      FinderException, RemoteException;
        
  public Collection findAll() 
    throws FinderException, RemoteException;          
}

//实现Course的本地接口功能
package enroll.ejb;

import java.util.Collection;
import java.rmi.RemoteException;
import javax.ejb.*;

public interface CourseHome extends EJBHome{
   
  public Course create(
    String course_id, String name) 
    throws DuplicateKeyException, 
      CreateException, RemoteException;
             
  public Course findByPrimaryKey(String course_id)
    throws ObjectNotFoundException, 
      FinderException, RemoteException;
    
  public Collection findAll() 
    throws FinderException, RemoteException;
}

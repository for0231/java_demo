//ʵ��Student�ı���Home�ӿ�
package enroll.ejb;

import java.util.Collection;
import java.rmi.RemoteException;
import javax.ejb.*;

public interface StudentHome extends EJBHome{
   
  public Student create(
    String student_id, String name) 
    throws DuplicateKeyException, 
      CreateException, RemoteException;
             
  public Student findByPrimaryKey(String student_id)
    throws ObjectNotFoundException, 
      FinderException, RemoteException;
    
  public Collection findAll() 
    throws FinderException, RemoteException;
}	

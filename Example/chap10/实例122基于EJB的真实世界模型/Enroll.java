//实现Enroll的远程接口
package enroll.ejb;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.*;

public interface Enroll extends EJBObject {
 
  public ArrayList getCourseItems()
    throws RemoteException;
 
  public String getStudent_id()
    throws RemoteException;
    
  public void replaceCourseItems(ArrayList courseItems)
    throws RemoteException;    
}

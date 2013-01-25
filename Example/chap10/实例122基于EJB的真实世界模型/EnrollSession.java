//实现EnrollSession的远程接口
package enroll.ejb;

import javax.ejb.*;
import java.util.*;
import java.rmi.RemoteException;

public interface EnrollSession extends EJBObject{
 
  public String getStudentName()
    throws RemoteException;

  public void enroll(ArrayList courseItems)
    throws RemoteException;

  public void unenroll()
    throws RemoteException;

  public void deleteStudent()
    throws FinderException, RemoteException;

  public void deleteCourse(String course_id)
    throws RemoteException;
}

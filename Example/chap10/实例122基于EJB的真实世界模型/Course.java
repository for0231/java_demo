//实现Course的远程接口功能
package enroll.ejb;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

public interface Course extends EJBObject{
  
  public void setName(String name) 
    throws RemoteException; 

  public String getName() 
    throws RemoteException; 
}

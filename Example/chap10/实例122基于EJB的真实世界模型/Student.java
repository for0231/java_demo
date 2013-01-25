//实现Student的远程接口
package enroll.ejb;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

public interface Student extends EJBObject{
  
  public void setName(String name) 
    throws RemoteException; 

  public String getName() 
    throws RemoteException; 
}

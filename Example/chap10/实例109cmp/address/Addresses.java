package address;

import java.rmi.*;
import javax.ejb.*;
//Addresses EJB的远程接口
public interface Addresses extends EJBObject {
  public String getFirstName() throws RemoteException;
  public String getLastName() throws RemoteException;
  public String getAddress() throws RemoteException;
  public void setAddress(String address) throws RemoteException;
  public String getCity() throws RemoteException;
  public void setCity(String city) throws RemoteException;
  public String getState() throws RemoteException;
  public void setState(String state) throws RemoteException;
  public String getZip() throws RemoteException;
  public void setZip(String zip) throws RemoteException;
}
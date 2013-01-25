package address;

import java.rmi.*;
import javax.ejb.*;
import java.util.Collection;
//Addresses EJBµÄHome½Ó¿Ú
public interface AddressesHome extends EJBHome {
  public Addresses create(String firstName, String lastName, String address, String city, String state, String zip) throws RemoteException, CreateException;
  public Addresses create(String firstName, String lastName) throws RemoteException, CreateException;
  public Addresses findByPrimaryKey(AddressesPK primaryKey) throws RemoteException, FinderException;
  public Collection findAll() throws RemoteException, FinderException;
}
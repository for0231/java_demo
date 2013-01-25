package interejbs;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;

public interface firstEJBHome extends javax.ejb.EJBHome {
  public firstEJB create() throws CreateException, RemoteException;
}
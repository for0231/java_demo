package interejbs;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;

public interface secondEJBHome extends javax.ejb.EJBHome {
  public secondEJB create() throws CreateException, RemoteException;
}
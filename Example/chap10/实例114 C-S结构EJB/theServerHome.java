package csejb;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;

public interface theServerHome extends javax.ejb.EJBHome {
  public theServer create() throws CreateException, RemoteException;
}
package interejbs;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;

public interface secondEJB extends javax.ejb.EJBObject {
  public String forFirst(String fromFirst) throws RemoteException;
}
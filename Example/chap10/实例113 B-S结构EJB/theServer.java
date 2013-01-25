package bsejb;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;

public interface theServer extends javax.ejb.EJBObject {
  public String BServer() throws RemoteException;
}
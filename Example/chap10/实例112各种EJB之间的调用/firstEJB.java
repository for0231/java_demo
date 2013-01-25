package interejbs;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;

public interface firstEJB extends javax.ejb.EJBObject {
  public String MyPrint() throws RemoteException;
  public String forSecond(String fromSecond) throws RemoteException;
}
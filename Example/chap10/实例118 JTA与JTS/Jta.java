package jtajts;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;

public interface Jta extends javax.ejb.EJBObject {
  public String myTransation() throws RemoteException;
}
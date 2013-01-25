package jtajts;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;
public interface JtaHome extends javax.ejb.EJBHome {
  public Jta create() throws CreateException, RemoteException;
}
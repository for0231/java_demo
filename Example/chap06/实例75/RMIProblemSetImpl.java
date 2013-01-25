import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class RMIProblemSetImpl
    extends java.rmi.server.UnicastRemoteObject
    implements RMIProblemSet {

  protected double value;
  protected double solution;
  
  public RMIProblemSetImpl() throws RemoteException {
    value = 0.0;
    solution = 0.0;
  }
  
  public double getValue() throws RemoteException {
    return value;
  }
  
  public double getSolution() throws RemoteException {
    return solution;
  }
  
  public void setValue(double v) throws RemoteException {
    value = v;
  }
  
  public void setSolution(double s) throws RemoteException {
    solution = s;
  }
}
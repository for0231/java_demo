import java.rmi.*;

public interface RMIProblemSet extends Remote {
  public double getValue() throws RemoteException;
  public double getSolution() throws RemoteException;
  public void setValue(double v) throws RemoteException;
  public void setSolution(double s) throws RemoteException;
}
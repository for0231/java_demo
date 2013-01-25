import java.rmi.*;
import java.io.OutputStream;

public interface RMISolver extends java.rmi.Remote
{
  public boolean solve() throws RemoteException;
  public boolean solve(RMIProblemSet s,
                       int numIters) throws RemoteException;

  public RMIProblemSet getProblem() throws RemoteException;
  public boolean setProblem(RMIProblemSet s) throws RemoteException;
  public int getIterations() throws RemoteException;
  public boolean setIterations(int numIter) throws RemoteException;
}


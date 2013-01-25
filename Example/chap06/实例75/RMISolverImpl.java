import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;

public class RMISolverImpl
    extends UnicastRemoteObject
    implements RMISolver {

  // Protected implementation variables
  protected int numIterations = 1; // not used for this Solver...
  protected RMIProblemSet currProblem = null;

  // Constructors
  public RMISolverImpl() throws RemoteException { super(); }
  public RMISolverImpl(int numIter) throws RemoteException {
    super();
    numIterations = numIter;
  }

  // Public methods
  public boolean solve() throws RemoteException {
    System.out.println("Solving current problem...");
    return solve(currProblem, numIterations);
  }

  public boolean solve(RMIProblemSet s, int numIters) 
      throws RemoteException {
    boolean success = true;

    if (s == null) {
      System.out.println("No problem to solve.");
      return false;
    }

    System.out.println("Problem value = " + s.getValue());

    // Solve problem here...
    try {
      s.setSolution(Math.sqrt(s.getValue()));
    }
    catch (ArithmeticException e) {
      System.out.println("Badly-formed problem.");
      success = false;
    }

    System.out.println("Problem solution = " + s.getSolution());

    return success;
  }

  public RMIProblemSet getProblem() throws RemoteException {
    return currProblem;
  }
  
  public boolean setProblem(RMIProblemSet s) throws RemoteException {
    currProblem = s;
    return true;
  }

  public int getIterations() throws RemoteException {
    return numIterations;
  }
  
  public boolean setIterations(int numIter) throws RemoteException {
    numIterations = numIter;
    return true;
  }

  public static void main(String argv[]) {
    try {
      // Register an instance of RMISolverImpl with the
      // RMI Naming service
      String name = "TheSolver";
      System.out.println("Registering RMISolverImpl as \"" + name + "\"");
      RMISolverImpl solver = new RMISolverImpl();
      Naming.rebind(name, solver);
      System.out.println("Remote Solver ready...");
    }
    catch (Exception e) {
      System.out.println("Caught exception while registering: " + e);
    }
  }
}
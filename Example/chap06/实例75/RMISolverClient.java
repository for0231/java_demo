import java.rmi.*;
import java.rmi.server.*;

public class RMISolverClient {
  public static void main(String argv[]) {
    // Install a security manager that can handle remote stubs
    System.setSecurityManager(new RMISecurityManager());

    // Get a remote reference to the RMISolver class
    String name = "rmi://objhost.myorg.com/TheSolver";
    System.out.println("Looking up " + name + "...");
    RMISolver solver = null;
    try {
      solver = (RMISolver)Naming.lookup(name);
    }
    catch (Exception e) {
      System.out.println("Caught an exception looking up Solver.");
      System.exit(1);
    }

    // Make a problem set for the solver
    RMIProblemSetImpl s = null;
    
    try {
      s = new RMIProblemSetImpl();
      s.setValue(Double.valueOf(argv[0]).doubleValue());
    }
    catch (Exception e) {
      System.out.println("Caught exception initializing problem.");
      e.printStackTrace();
    }

    // Ask solver to solve
    try {
      if (solver.solve(s, 1)) {
        System.out.println("Solver returned solution: " +
                           s.getSolution());
      }
      else {
        System.out.println(
          "Solver was unable to solve problem with value = " +
          s.getValue());
      }
    }
    catch (RemoteException e) {
      System.out.println("Caught remote exception.");
      System.exit(1);
    }
  }
}
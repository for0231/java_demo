package simpleejb;

import javax.naming.*;
import java.util.Properties;
import javax.rmi.PortableRemoteObject;

public class TimeFunctionsTestClient1 {
  static final private String ERROR_NULL_REMOTE = "Remote interface reference is null.  It must be created by calling one of the Home interface methods first.";
  static final private int MAX_OUTPUT_LINE_LENGTH = 100;
  private boolean logging = true;
  private TimeFunctionsHome timeFunctionsHome = null;
  private TimeFunctions timeFunctions = null;

  //Construct the EJB test client
  public TimeFunctionsTestClient1() {
    long startTime = 0;
    if (logging) {
      log("Initializing bean access.");
      startTime = System.currentTimeMillis();
    }

    try {
      //get naming context
      Context ctx = getInitialContext();

      //look up jndi name
      Object ref = ctx.lookup("TimeFunctions");

      //cast to Home interface
      timeFunctionsHome = (TimeFunctionsHome) PortableRemoteObject.narrow(ref, TimeFunctionsHome.class);
      if (logging) {
        long endTime = System.currentTimeMillis();
        log("Succeeded initializing bean access.");
        log("Execution time: " + (endTime - startTime) + " ms.");
      }
    }
    catch(Exception e) {
      if (logging) {
        log("Failed initializing bean access.");
      }
      e.printStackTrace();
    }
  }

  private Context getInitialContext() throws Exception {
    String url = "t3://cgb-4wn01xj69v6:7001";
    String user = null;
    String password = null;
    Properties properties = null;
    try {
      properties = new Properties();
      properties.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
      properties.put(Context.PROVIDER_URL, url);
      if (user != null) {
        properties.put(Context.SECURITY_PRINCIPAL, user);
        properties.put(Context.SECURITY_CREDENTIALS, password == null ? "" : password);
      }

      return new InitialContext(properties);
    }
    catch(Exception e) {
      log("Unable to connect to WebLogic server at " + url);
      log("Please make sure that the server is running.");
      throw e;
    }
  }

  //----------------------------------------------------------------------------
  // Methods that use Home interface methods to generate a Remote interface reference
  //----------------------------------------------------------------------------

  public TimeFunctions create() {
    long startTime = 0;
    if (logging) {
      log("Calling create()");
      startTime = System.currentTimeMillis();
    }
    try {
      timeFunctions = timeFunctionsHome.create();
      if (logging) {
        long endTime = System.currentTimeMillis();
        log("Succeeded: create()");
        log("Execution time: " + (endTime - startTime) + " ms.");
      }
    }
    catch(Exception e) {
      if (logging) {
        log("Failed: create()");
      }
      e.printStackTrace();
    }

    if (logging) {
      log("Return value from create(): " + timeFunctions + ".");
    }
    return timeFunctions;
  }

  //----------------------------------------------------------------------------
  // Methods that use Remote interface methods to access data through the bean
  //----------------------------------------------------------------------------

  public long getTime() {
    long returnValue = 0;
    if (timeFunctions == null) {
      System.out.println("Error in getTime(): " + ERROR_NULL_REMOTE);
      return returnValue;
    }
    long startTime = 0;
    if (logging) {
      log("Calling getTime()");
      startTime = System.currentTimeMillis();
    }

    try {
      returnValue = timeFunctions.getTime();
      if (logging) {
        long endTime = System.currentTimeMillis();
        log("Succeeded: getTime()");
        log("Execution time: " + (endTime - startTime) + " ms.");
      }
    }
    catch(Exception e) {
      if (logging) {
        log("Failed: getTime()");
      }
      e.printStackTrace();
    }

    if (logging) {
      log("Return value from getTime(): " + returnValue + ".");
    }
    return returnValue;
  }

  public void testRemoteCallsWithDefaultArguments() {
    if (timeFunctions == null) {
      System.out.println("Error in testRemoteCallsWithDefaultArguments(): " + ERROR_NULL_REMOTE);
      return ;
    }
    getTime();
  }

  //----------------------------------------------------------------------------
  // Utility Methods
  //----------------------------------------------------------------------------

  private void log(String message) {
    if (message == null) {
      System.out.println("-- null");
      return ;
    }
    if (message.length() > MAX_OUTPUT_LINE_LENGTH) {
      System.out.println("-- " + message.substring(0, MAX_OUTPUT_LINE_LENGTH) + " ...");
    }
    else {
      System.out.println("-- " + message);
    }
  }
  //Main method

  public static void main(String[] args) {
    TimeFunctionsTestClient1 client = new TimeFunctionsTestClient1();
    client.create();
    client.getTime();
    // Use the client object to call one of the Home interface wrappers
    // above, to create a Remote interface reference to the bean.
    // If the return value is of the Remote interface type, you can use it
    // to access the remote interface methods.  You can also just use the
    // client object to call the Remote interface wrappers.
  }
}
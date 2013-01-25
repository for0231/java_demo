/*
 * Client application
 */
import javax.rmi.PortableRemoteObject;
import javax.naming.Context;
import javax.naming.InitialContext;

public class Client {
    public static void main(String[] args) {
	try {
	    // get the naming context
            Context ic = new InitialContext();

	    // get a reference to the remote object
            Object objRef = ic.lookup("Server");

	    // narrow it into our RMIInterface
	    RMIInterface ri = 
		(RMIInterface)PortableRemoteObject.narrow(objRef, RMIInterface.class);
	
	    // call the hello method
	    System.out.println("Recieved from server: "+ri.hello()+"\n");  

	    // now lets try RMI serialization
	    SerClass se = new SerClass(5, "Client string! ");
	    // pass the class to be altered on the server
	    // of course behind the scenes this class is being serialized over IIOP
	    se = ri.alterClass(se);
	    // now let's see the result
	    System.out.println("Serialization results :\n"+
			"Integer was 5 now is "+se.getX()+"\n"+
			"String was \"Client String! \" now is \""+se.getString()+"\"");

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
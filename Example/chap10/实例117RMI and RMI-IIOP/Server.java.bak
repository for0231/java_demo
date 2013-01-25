/*
 * Simple server
 */
import java.util.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.rmi.PortableRemoteObject;
import javax.naming.Context;
import javax.naming.InitialContext;

public class Server extends PortableRemoteObject implements RMIInterface {
    // must explicitly create default constructor to throw RemoteException
    public Server() throws RemoteException {
    }

    // implementation of RMIInterface methods
    public String hello() throws RemoteException {
	return "Hello there!";
    }

    public SerClass alterClass(SerClass classObject) throws RemoteException {
	// change the values of SerClass and return it.
	classObject.setX( 
	   classObject.getX() + 5 ); // add 5 to X
	classObject.setString( 
	   classObject.getString() + " : I've altered you" ); // alter the string
	return classObject;
    }	

    public static void main(String[] args) {
	try {
 	    Server s = new Server();

	    // create the naming context
            Context initialNamingContext = new InitialContext();
            initialNamingContext.rebind("Server", s );

	    System.out.println("Hello Server waiting...");
	    // it's that easy - we're registered and listening for incoming requests

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
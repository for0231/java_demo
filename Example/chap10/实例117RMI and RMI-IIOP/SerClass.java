/**
 *  This class is intended to be serialized over RMI-IIOP.
 */
public class SerClass implements java.io.Serializable {
	// members
	private int x;
	private String myString;

	// constructor
	public SerClass(int x, String myString) throws java.rmi.RemoteException {
		this.x=x;
		this.myString=myString;
	} 
	
	// some accessor methods
	public int getX() {  return x;}
	public void setX(int x) { this.x=x; }
	public String getString() {  return myString;  }
	public void setString(String str) { myString=str; }
}
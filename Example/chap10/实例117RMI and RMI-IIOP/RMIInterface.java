/*
 * Remote interface
 */
public interface RMIInterface extends java.rmi.Remote {
    public String hello() throws java.rmi.RemoteException;
    public SerClass alterClass(SerClass classObject) throws java.rmi.RemoteException;
}
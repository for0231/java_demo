package Interest
 import javax.ejb.EJBObject;
 import java.rmi.RemoteException;
 public interface Interest extends EJBObject 
 {
  // 该函数返回利息 
public double calInterest(double principle, 
     double rate, double periods) throws RemoteException;
 }

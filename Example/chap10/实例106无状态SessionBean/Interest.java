package Interest
 import javax.ejb.EJBObject;
 import java.rmi.RemoteException;
 public interface Interest extends EJBObject 
 {
  // �ú���������Ϣ 
public double calInterest(double principle, 
     double rate, double periods) throws RemoteException;
 }

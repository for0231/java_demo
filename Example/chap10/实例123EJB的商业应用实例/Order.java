 package order.ejb;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.*;
public interface Order extends EJBObject {
 // ��ö�����Ŀ 
  public ArrayList getOrderItems()
    throws RemoteException;
  // ��ö������  
  public String getOrder_id()
    throws RemoteException;
  // ��ö������˺�
  public String getUser_id()
    throws RemoteException;
  // ��ö����ܼ۸�
  public int getTotalPrice()
    throws RemoteException;
  // ���¶������ܼ۸�
  public void setTotalPrice(int totalPrice)
    throws RemoteException;    
  // ���¶�����Ŀ  
  public void setOrderItems(ArrayList orderItems)
    throws RemoteException;    
}

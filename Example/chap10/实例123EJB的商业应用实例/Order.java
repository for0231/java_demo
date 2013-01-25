 package order.ejb;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.*;
public interface Order extends EJBObject {
 // 获得订单项目 
  public ArrayList getOrderItems()
    throws RemoteException;
  // 获得订单编号  
  public String getOrder_id()
    throws RemoteException;
  // 获得订单者账号
  public String getUser_id()
    throws RemoteException;
  // 获得订单总价格
  public int getTotalPrice()
    throws RemoteException;
  // 更新订单的总价格
  public void setTotalPrice(int totalPrice)
    throws RemoteException;    
  // 更新订单项目  
  public void setOrderItems(ArrayList orderItems)
    throws RemoteException;    
}

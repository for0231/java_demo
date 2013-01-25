package order.ejb;
import order.common.*;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
public interface Product extends EJBObject{
 // 设置产品的名称和价格
  public void setProductData(String name, int price) throws RemoteException;
 // 取得产品项目
  public ProductItem getProductData() throws RemoteException; 
   // 设置产品价格
  public void setPrice(int price) throws RemoteException; 
   // 取得产品价格
  public int getPrice() throws RemoteException; 
  // 设置产品名称
  public void setName(String name) throws RemoteException; 
 // 取得产品名称
  public String getName() throws RemoteException; 
}

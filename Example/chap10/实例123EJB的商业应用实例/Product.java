package order.ejb;
import order.common.*;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
public interface Product extends EJBObject{
 // ���ò�Ʒ�����ƺͼ۸�
  public void setProductData(String name, int price) throws RemoteException;
 // ȡ�ò�Ʒ��Ŀ
  public ProductItem getProductData() throws RemoteException; 
   // ���ò�Ʒ�۸�
  public void setPrice(int price) throws RemoteException; 
   // ȡ�ò�Ʒ�۸�
  public int getPrice() throws RemoteException; 
  // ���ò�Ʒ����
  public void setName(String name) throws RemoteException; 
 // ȡ�ò�Ʒ����
  public String getName() throws RemoteException; 
}

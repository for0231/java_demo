package order.ejb;
import java.util.*;
import java.rmi.RemoteException;
import javax.ejb.*;
public interface OrderHome extends EJBHome{
 // ���ݶ������˺ţ���Ǯ�Ͷ�����Ŀ����һ��ʵ��Order Bean  
public Order create(String user_id, int totalPrice, ArrayList orderItems)
      throws RemoteException, CreateException;
// ���ݶ�����Ų���һ��ʵ��Order Bean 
  public Order findByPrimaryKey(String orderId) 
             throws FinderException, RemoteException;
 // ��������ʵ��Order Bean
 public Collection findAll()  throws FinderException, RemoteException;
 //  ���ݶ������˺Ų���һ�������ߵ�����ʵ��Order Bean
  public Collection findByUserId(String userId)
           throws FinderException, RemoteException;
}

package order.ejb;
import java.util.*;
import java.rmi.RemoteException;
import javax.ejb.*;
public interface OrderHome extends EJBHome{
 // 根据订购者账号，价钱和订购项目建立一个实体Order Bean  
public Order create(String user_id, int totalPrice, ArrayList orderItems)
      throws RemoteException, CreateException;
// 根据订购编号查找一个实体Order Bean 
  public Order findByPrimaryKey(String orderId) 
             throws FinderException, RemoteException;
 // 查找所有实体Order Bean
 public Collection findAll()  throws FinderException, RemoteException;
 //  根据订购的账号查找一个订购者的所有实体Order Bean
  public Collection findByUserId(String userId)
           throws FinderException, RemoteException;
}

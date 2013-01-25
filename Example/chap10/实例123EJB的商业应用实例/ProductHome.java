package order.ejb;
import java.util.Collection;
import java.rmi.RemoteException;
import javax.ejb.*;
public interface ProductHome extends EJBHome{
// 根据产品id建立一个 Product Bean
public Product create(String product_id) throws DuplicateKeyException, 
    CreateException, RemoteException;
 // 根据id,名称，价格建立Bean         
  public Product create(String product_id, String name, int price)
 throws DuplicateKeyException,  CreateException, RemoteException;
 // 通过id好查找Product Bean
  public Product findByPrimaryKey(String product_id)
    throws ObjectNotFoundException, FinderException, RemoteException;
  //取得所有产品的Product Bean
  public Collection findAll() throws FinderException, RemoteException;
}

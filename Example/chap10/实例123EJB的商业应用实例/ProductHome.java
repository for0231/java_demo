package order.ejb;
import java.util.Collection;
import java.rmi.RemoteException;
import javax.ejb.*;
public interface ProductHome extends EJBHome{
// ���ݲ�Ʒid����һ�� Product Bean
public Product create(String product_id) throws DuplicateKeyException, 
    CreateException, RemoteException;
 // ����id,���ƣ��۸���Bean         
  public Product create(String product_id, String name, int price)
 throws DuplicateKeyException,  CreateException, RemoteException;
 // ͨ��id�ò���Product Bean
  public Product findByPrimaryKey(String product_id)
    throws ObjectNotFoundException, FinderException, RemoteException;
  //ȡ�����в�Ʒ��Product Bean
  public Collection findAll() throws FinderException, RemoteException;
}

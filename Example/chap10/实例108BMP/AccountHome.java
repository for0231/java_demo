package Account;
import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.util.Collection;
import java.rmi.RemoteException;
public interface AccountHome extends EJBHome {
// 我们定义了单独的create()方法在这个本地接口中
// 它对应AccountBean中的ejbCreate()方法
   Account create(String accountID, String ownerName) throws CreateException, RemoteException;
// 通过主键(accountID)查找一个账户
   public Account findByPrimaryKey(AccountPK key) throws FinderException, RemoteException;
// 用它的所有者的名字查找账户
   public Collection findByOwnerName(String name) throws FinderException, RemoteException;
// 返回所有账户
   public double getTotalBankValue() throws AccountException, RemoteException;
}

package Account;
import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.util.Collection;
import java.rmi.RemoteException;
public interface AccountHome extends EJBHome {
// ���Ƕ����˵�����create()������������ؽӿ���
// ����ӦAccountBean�е�ejbCreate()����
   Account create(String accountID, String ownerName) throws CreateException, RemoteException;
// ͨ������(accountID)����һ���˻�
   public Account findByPrimaryKey(AccountPK key) throws FinderException, RemoteException;
// �����������ߵ����ֲ����˻�
   public Collection findByOwnerName(String name) throws FinderException, RemoteException;
// ���������˻�
   public double getTotalBankValue() throws AccountException, RemoteException;
}

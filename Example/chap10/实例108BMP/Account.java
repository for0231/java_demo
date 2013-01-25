package Account;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
public interface Account extends EJBObject 
{
   // 存amt到帐户中去， 
public void deposit(double amt) throws AccountException, RemoteException;
     // 从账户中取出amt，如果amt>账户余额抛出异常
   public void withdraw(double amt) throws AccountException, RemoteException;
    // 在实体Bean上的获得器/设置器方法
    public double getBalance() throws RemoteException;
    public String getOwnerName() throws RemoteException;
    public void setOwnerName(String name) throws RemoteException;
    public String getAccountID() throws RemoteException;
    public void setAccountID(String id) throws RemoteException;
}

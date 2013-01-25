package Account;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
public interface Account extends EJBObject 
{
   // ��amt���ʻ���ȥ�� 
public void deposit(double amt) throws AccountException, RemoteException;
     // ���˻���ȡ��amt�����amt>�˻�����׳��쳣
   public void withdraw(double amt) throws AccountException, RemoteException;
    // ��ʵ��Bean�ϵĻ����/����������
    public double getBalance() throws RemoteException;
    public String getOwnerName() throws RemoteException;
    public void setOwnerName(String name) throws RemoteException;
    public String getAccountID() throws RemoteException;
    public void setAccountID(String id) throws RemoteException;
}

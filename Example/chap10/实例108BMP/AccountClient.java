package Account;
import javax.naming.InitialContext;
import javax.naming.Context;
import java.util.Hashtable;
import java.util.Iterator;
public class AccountClient {
    public static void main(String[] args) throws Exception {
        Account account = null;
        try { // ��ȡһ�����ض��������
            Properties props = System.getProperties();
            Context ctx = new InitialContext(props);
            Object obj = ctx.lookup("Account");
            AccountHome home = (AccountHome)obj;
            System.err.println("Total of all accounts in bank initially = " + 
			       home.getTotalBankValue());
           // ����EJB����
            home.create("123-456-7890", "John Smith");
           // ����һ���˻�
            Iterator i = home.findByOwnerName("John Smith").iterator();
            if (i.hasNext()) {
		      account = (Account)i.next();
		     javax.rmi.PortableRemoteObject.narrow(i.next(),Account.class);
            }
            else {
                throw new Exception("Could not find account");
            }
            // ����balance()����������ӡ���
            System.out.println("Initial Balance = " + account.getBalance());
           // ���100
            account.deposit(100);
            System.out.println("After depositing 100, account balance = " +
			       account.getBalance());
            System.out.println("Total of all accounts in bank now = " + 
			       home.getTotalBankValue());
           // ��ȡEJB���������
            AccountPK pk = (AccountPK) account.getPrimaryKey();
           // �ͷ����ǵ��ϵ�EJB��������ã�ͨ��ID�����Ų���
            account = null;
            account = home.findByPrimaryKey(pk);
           // ��ӡ���е����
            System.out.println("Found account with ID " + pk + 
			       " Balance = " + account.getBalance());
          //ȡ��150
            System.out.println("Now trying to withdraw $150, which is more "+
			       "than is currently available.  This should " +
			       "generate an exception..");
            account.withdraw(150);
        }
        catch (Exception e) {
            System.out.println("Caught exception!\n" + e);
            // e.printStackTrace();
        }
        finally {// ���õ�ɾ��ʵ��
            try {
                System.out.println("Destroying account..");
                if (account != null) {
                    account.remove();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

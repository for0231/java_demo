package Account;
import javax.naming.InitialContext;
import javax.naming.Context;
import java.util.Hashtable;
import java.util.Iterator;
public class AccountClient {
    public static void main(String[] args) throws Exception {
        Account account = null;
        try { // 获取一个本地对象的引用
            Properties props = System.getProperties();
            Context ctx = new InitialContext(props);
            Object obj = ctx.lookup("Account");
            AccountHome home = (AccountHome)obj;
            System.err.println("Total of all accounts in bank initially = " + 
			       home.getTotalBankValue());
           // 生成EJB对象
            home.create("123-456-7890", "John Smith");
           // 查找一个账户
            Iterator i = home.findByOwnerName("John Smith").iterator();
            if (i.hasNext()) {
		      account = (Account)i.next();
		     javax.rmi.PortableRemoteObject.narrow(i.next(),Account.class);
            }
            else {
                throw new Exception("Could not find account");
            }
            // 调用balance()方法，并打印输出
            System.out.println("Initial Balance = " + account.getBalance());
           // 存款100
            account.deposit(100);
            System.out.println("After depositing 100, account balance = " +
			       account.getBalance());
            System.out.println("Total of all accounts in bank now = " + 
			       home.getTotalBankValue());
           // 提取EJB对象的主键
            AccountPK pk = (AccountPK) account.getPrimaryKey();
           // 释放我们的老的EJB对象的引用，通过ID主键号查找
            account = null;
            account = home.findByPrimaryKey(pk);
           // 打印现有的余额
            System.out.println("Found account with ID " + pk + 
			       " Balance = " + account.getBalance());
          //取款150
            System.out.println("Now trying to withdraw $150, which is more "+
			       "than is currently available.  This should " +
			       "generate an exception..");
            account.withdraw(150);
        }
        catch (Exception e) {
            System.out.println("Caught exception!\n" + e);
            // e.printStackTrace();
        }
        finally {// 永久的删除实体
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

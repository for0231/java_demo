package Account;
import java.sql.*;
import javax.ejb.*;
import java.util.Collection;
import java.util.Vector;
// �������־û�ʵ��Bean��ʾһ�������˻�
public class AccountBean implements EntityBean 
{
    protected EntityContext ctx;
// Bean��״̬��
    private String accountID;    // �����е�����
    private String ownerName;
    private double balance;
public AccountBean() 
    { }
// �����߼�����
   //���
    public void deposit(double amt) throws AccountException {
        balance += amt;
    }
  // ȡ����amt>���׳��쳣
    public void withdraw(double amt) throws AccountException {
        if (amt > balance) {
            throw new AccountException(����");
        }
        balance -= amt;
    }
    // ʵ��Bean�ϵĻ����/����������
    public double getBalance() {
        System.out.println("getBalance()");
        return balance;
    }
    public void setOwnerName(String name) {
        System.out.println("setOwnerName()" + accountID);
        ownerName = name;
    }
    public String getOwnerName() {
        System.out.println("getOwnerName() on" + accountID);
        return ownerName;
    }
    public String getAccountID() {
        System.out.println("getAccountID() on" + accountID);
        return accountID;
    }
    public void setAccountID(String id) {
        System.out.println("setAccountID() on " + accountID);
        this.accountID = id;
    }
  // ���������˻������
    public double ejbHomeGetTotalBankValue() throws AccountException {
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
// �����ݿ�����
conn = getConnection();
 			// ִ�в�ѯ�������õ������˻�����ܺ�
            pstmt = conn.prepareStatement(
		    "select sum(balance) as total from accounts");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        }
        catch (Exception e) {
          e.printStackTrace();
          throw new AccountException(e);
        }
        finally {// �ͷ����ݿ���Դ
            try { if (pstmt != null) pstmt.close(); }
            catch (Exception e) {}
            try { if (conn != null) conn.close(); }
            catch (Exception e) {}
        }
        throw new AccountException("Error!");
    }
// EJB����ʵ�ֵķ��� 
    public void ejbActivate() {
        System.out.println("ejbActivate()");
}
// �����ݿ����Ƴ�ʵ��Bean
    public void ejbRemove() throws RemoveException {
        System.out.println("ejbRemove()");
//  һ��ʵ��Bean���Ա�ʾ��ͬ������ʵ��
//  ����Ӧ��ͨ���������ж�ɾ���ĸ�����ʵ��
        AccountPK pk = (AccountPK) ctx.getPrimaryKey();
        String id = pk.accountID;
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
          // ����µ����ݿ�����
            conn = getConnection();
           // �����ݿ���ɾ���˺�
            pstmt = conn.prepareStatement("delete from accounts where id = ?");
            pstmt.setString(1, id);
          // �������ݿ�ʧ�ܣ��׳�һ��ϵͳ������
            if (pstmt.executeUpdate() == 0) {
                throw new RemoveException("Account " + pk +
		      " failed to be removed from the database");
            }
        }
        catch (Exception ex) {
            throw new EJBException(ex.toString());
        }
        finally { // �ͷ����ݿ�����
            try { if (pstmt != null) pstmt.close(); }
            catch (Exception e) {}
            try { if (conn != null) conn.close(); }
            catch (Exception e) {}
        }
    }
   // �ۻ���Ϊ�˹����ͷŵ���Դ
    public void ejPassivate() {
        System.out.println("ejbPassivate ()on " + accountID);
}
// �����ڴ��е�ʵ��Bean���󣬷�ӳ���ݿ��еĵ�ǰֵ
    public void ejbLoad() {
        System.out.println("ejbLoad() on " + accountID);
      // �õ�ʵ����������������֪���Ǹ�ʵ��������
        AccountPK pk = (AccountPK) ctx.getPrimaryKey();
        String id = pk.accountID;
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
           // ����µ����ݿ�����
            conn = getConnection();
           // ͨ��ID��ѯ���õ����ݿ��е��˺�
            pstmt = conn.prepareStatement(
		    "select ownerName, balance from accounts where id = ?");
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            ownerName = rs.getString("ownerName");
            balance = rs.getDouble("balance");
        }
        catch (Exception ex) {
            throw new EJBException("Account " + pk + 
				   " failed to load from database", ex);
        }
        finally { // �ͷ����ݿ�����
            try { if (pstmt != null) pstmt.close(); }
            catch (Exception e) {}
            try { if (conn != null) conn.close(); }
            catch (Exception e) {}
        }
    }
    // �������ݿ⣬��ӳ��ǰ�ڴ��е�ֵ
    public void ejbStore() {
        System.out.println("ejbStore() on " + accountID);
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
           // ����µ����ݿ�����
            conn = getConnection();
           // �����ݿ�洢�˻�
            pstmt = conn.prepareStatement(
	       "update accounts set ownerName = ?, balance = ? where id = ?");
            pstmt.setString(1, ownerName);
            pstmt.setDouble(2, balance);
            pstmt.setString(3, accountID);
            pstmt.executeUpdate();
}
        catch (Exception ex) {
            throw new EJBException("Account " + accountID +
				   " failed to save to database", ex);
        }
        finally { // �ͷ����ݿ�����
            try { if (pstmt != null) pstmt.close(); }
            catch (Exception e) {}
            try { if (conn != null) conn.close(); }
            catch (Exception e) {}
        }
    }
// ��һ��Beanʵ����һ���ض��Ļ��������������
    public void setEntityContext(EntityContext ctx) {
        System.out.println("setEntityContext called");
        this.ctx = ctx;
        }
// ��һ��Bean�����Ļ�����������
    public void unsetEntityContext() {
        System.out.println("unsetEntityContext called on " + accountID);
        this.ctx = null;
        }
  // ��ejbCreate()���ú�Bean���Դ����Ļ�������ȡEJB����
// ������һ��this��������
    public void ejbPostCreate(String accountID, String ownerName) {
    }
   // �˷�����ӦHome�е�create()����
// ���ͻ��˵��ñ��ض���(Home)��create()����ʱ��
// ���ض������ejbCreate()����
    public AccountPK ejbCreate(String accountID, String ownerName) 
	throws CreateException {
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            System.out.println("ejbCreate() called on " + accountID);
            this.accountID = accountID;
            this.ownerName = ownerName;
            this.balance = 0;
        // ������ݿ������
            conn = getConnection();
        // �����˺�
            pstmt = conn.prepareStatement(
	     "insert into accounts (id, ownerName, balance) values (?, ?, ?)");
            pstmt.setString(1, accountID);
            pstmt.setString(2, ownerName);
            pstmt.setDouble(3, balance);
            pstmt.executeUpdate();
          // ����һ��������������
            return new AccountPK(accountID);
        }
        catch (Exception e) {
            throw new CreateException(e.toString());
        }
        finally { //�ͷ����ݿ������
            try { if (pstmt != null) pstmt.close(); }
            catch (Exception e) {}
            try { if (conn != null) conn.close(); }
            catch (Exception e) {}
        }
    }
  // ͨ�������ҵ�һ���˺�
    public AccountPK ejbFindByPrimaryKey(AccountPK key) 
	throws FinderException {
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            System.out.println("ejbFindByPrimaryKey(" + key + ") called");
            // ���ݿ�����
            conn = getConnection();
           // �����ݿ��в������ʵ��
            pstmt = conn.prepareStatement(
                    "select id from accounts where id = ?");
            pstmt.setString(1, key.toString());
            ResultSet rs = pstmt.executeQuery();
            rs.next();
        // ��������
            return key;
        }
        catch (Exception e) {
            throw new FinderException(e.toString());
        }
        finally {// �ͷ����ݿ�
            try { if (pstmt != null) pstmt.close(); }
            catch (Exception e) {}
            try { if (conn != null) conn.close(); }
            catch (Exception e) {}
        }
    }
// ͨ�����ֲ��������˺�
    public Collection ejbFindByOwnerName(String name) throws FinderException {
        PreparedStatement pstmt = null;
        Connection conn = null;
        Vector v = new Vector();
        try {
            System.out.println("ejbFindByOwnerName(" + name + ") called");
             // ������ݿ�����
            conn = getConnection();
            // �����ݿ������������
            pstmt = conn.prepareStatement(
                    "select id from accounts where ownerName = ?");
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
           // ���ҵ��������ŵ�Vector��
            while (rs.next()) {
                String id = rs.getString("id");
                v.addElement(new AccountPK(id));
            }
		// ����һ���ҵ����������о�
            return v;
        }
        catch (Exception e) {
            throw new FinderException(e.toString());
        }
        finally {// �ͷ����ݿ�
            try { if (pstmt != null) pstmt.close(); }
            catch (Exception e) {}
            try { if (conn != null) conn.close(); }
            catch (Exception e) {}
        }
    }
    // �����ӳ��еõ�JDBC������
     //����JDBC����
    public Connection getConnection() throws Exception {
        try {
              DriverManager.registerDriver(
                 new oracle.jdbc.driver.OracleDriver() );
              Connection conn = DriverManager.getConnection(
                 "jdbc:oracle:thin:@155.99.198.130:1521:CSDB",
                 "anyone", "NewKid");
              return conn;
        }
        catch (Exception e) {
            System.err.println("Could not locate datasource!  Reason:");
            e.printStackTrace();
            throw e;
        }
    }
}
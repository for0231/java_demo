package Account;
import java.sql.*;
import javax.ejb.*;
import java.util.Collection;
import java.util.Vector;
// 这个管理持久化实体Bean表示一个银行账户
public class AccountBean implements EntityBean 
{
    protected EntityContext ctx;
// Bean的状态域
    private String accountID;    // 本例中的主键
    private String ownerName;
    private double balance;
public AccountBean() 
    { }
// 商务逻辑方法
   //存款
    public void deposit(double amt) throws AccountException {
        balance += amt;
    }
  // 取款，如果amt>余额，抛出异常
    public void withdraw(double amt) throws AccountException {
        if (amt > balance) {
            throw new AccountException(余额不够");
        }
        balance -= amt;
    }
    // 实体Bean上的获得器/设置器方法
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
  // 返回所有账户的余额
    public double ejbHomeGetTotalBankValue() throws AccountException {
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
// 和数据库连接
conn = getConnection();
 			// 执行查询操作，得到所有账户余额总和
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
        finally {// 释放数据库资源
            try { if (pstmt != null) pstmt.close(); }
            catch (Exception e) {}
            try { if (conn != null) conn.close(); }
            catch (Exception e) {}
        }
        throw new AccountException("Error!");
    }
// EJB必须实现的方法 
    public void ejbActivate() {
        System.out.println("ejbActivate()");
}
// 从数据库中移除实体Bean
    public void ejbRemove() throws RemoveException {
        System.out.println("ejbRemove()");
//  一个实体Bean可以表示不同的数据实例
//  我们应该通过主键来判断删除哪个数据实例
        AccountPK pk = (AccountPK) ctx.getPrimaryKey();
        String id = pk.accountID;
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
          // 获得新的数据库连接
            conn = getConnection();
           // 从数据库中删除账号
            pstmt = conn.prepareStatement("delete from accounts where id = ?");
            pstmt.setString(1, id);
          // 操作数据库失败，抛出一个系统级错误
            if (pstmt.executeUpdate() == 0) {
                throw new RemoveException("Account " + pk +
		      " failed to be removed from the database");
            }
        }
        catch (Exception ex) {
            throw new EJBException(ex.toString());
        }
        finally { // 释放数据库连接
            try { if (pstmt != null) pstmt.close(); }
            catch (Exception e) {}
            try { if (conn != null) conn.close(); }
            catch (Exception e) {}
        }
    }
   // 钝化，为了挂起释放的资源
    public void ejPassivate() {
        System.out.println("ejbPassivate ()on " + accountID);
}
// 更新内存中的实体Bean对象，反映数据库中的当前值
    public void ejbLoad() {
        System.out.println("ejbLoad() on " + accountID);
      // 得到实体主键，这样我们知道那个实例被加载
        AccountPK pk = (AccountPK) ctx.getPrimaryKey();
        String id = pk.accountID;
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
           // 获得新的数据库连接
            conn = getConnection();
           // 通过ID查询，得到数据库中的账号
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
        finally { // 释放数据库连接
            try { if (pstmt != null) pstmt.close(); }
            catch (Exception e) {}
            try { if (conn != null) conn.close(); }
            catch (Exception e) {}
        }
    }
    // 更新数据库，反映当前内存中的值
    public void ejbStore() {
        System.out.println("ejbStore() on " + accountID);
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
           // 获得新的数据库连接
            conn = getConnection();
           // 往数据库存储账户
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
        finally { // 释放数据库连接
            try { if (pstmt != null) pstmt.close(); }
            catch (Exception e) {}
            try { if (conn != null) conn.close(); }
            catch (Exception e) {}
        }
    }
// 把一个Bean实例和一个特定的环境对象关联起来
    public void setEntityContext(EntityContext ctx) {
        System.out.println("setEntityContext called");
        this.ctx = ctx;
        }
// 把一个Bean和它的环境变量分离
    public void unsetEntityContext() {
        System.out.println("unsetEntityContext called on " + accountID);
        this.ctx = null;
        }
  // 在ejbCreate()调用后，Bean可以从它的环境中提取EJB对象
// 并当作一个this参数传递
    public void ejbPostCreate(String accountID, String ownerName) {
    }
   // 此方法对应Home中的create()方法
// 当客户端调用本地对象(Home)的create()方法时，
// 本地对象调用ejbCreate()方法
    public AccountPK ejbCreate(String accountID, String ownerName) 
	throws CreateException {
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            System.out.println("ejbCreate() called on " + accountID);
            this.accountID = accountID;
            this.ownerName = ownerName;
            this.balance = 0;
        // 获得数据库的连接
            conn = getConnection();
        // 插入账号
            pstmt = conn.prepareStatement(
	     "insert into accounts (id, ownerName, balance) values (?, ?, ?)");
            pstmt.setString(1, accountID);
            pstmt.setString(2, ownerName);
            pstmt.setDouble(3, balance);
            pstmt.executeUpdate();
          // 产生一个主键，并返回
            return new AccountPK(accountID);
        }
        catch (Exception e) {
            throw new CreateException(e.toString());
        }
        finally { //释放数据库的连接
            try { if (pstmt != null) pstmt.close(); }
            catch (Exception e) {}
            try { if (conn != null) conn.close(); }
            catch (Exception e) {}
        }
    }
  // 通过主键找到一个账号
    public AccountPK ejbFindByPrimaryKey(AccountPK key) 
	throws FinderException {
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            System.out.println("ejbFindByPrimaryKey(" + key + ") called");
            // 数据库连接
            conn = getConnection();
           // 在数据库中查找这个实体
            pstmt = conn.prepareStatement(
                    "select id from accounts where id = ?");
            pstmt.setString(1, key.toString());
            ResultSet rs = pstmt.executeQuery();
            rs.next();
        // 返回主键
            return key;
        }
        catch (Exception e) {
            throw new FinderException(e.toString());
        }
        finally {// 释放数据库
            try { if (pstmt != null) pstmt.close(); }
            catch (Exception e) {}
            try { if (conn != null) conn.close(); }
            catch (Exception e) {}
        }
    }
// 通过名字查找所有账号
    public Collection ejbFindByOwnerName(String name) throws FinderException {
        PreparedStatement pstmt = null;
        Connection conn = null;
        Vector v = new Vector();
        try {
            System.out.println("ejbFindByOwnerName(" + name + ") called");
             // 获得数据库连接
            conn = getConnection();
            // 在数据库中找这个主键
            pstmt = conn.prepareStatement(
                    "select id from accounts where ownerName = ?");
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
           // 把找到的主键放到Vector中
            while (rs.next()) {
                String id = rs.getString("id");
                v.addElement(new AccountPK(id));
            }
		// 返回一个找到的主键的列举
            return v;
        }
        catch (Exception e) {
            throw new FinderException(e.toString());
        }
        finally {// 释放数据库
            try { if (pstmt != null) pstmt.close(); }
            catch (Exception e) {}
            try { if (conn != null) conn.close(); }
            catch (Exception e) {}
        }
    }
    // 从连接池中得到JDBC的连接
     //返回JDBC连接
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
// Chapter 05, sample 60
// JDBCDriver.java
// Connect database with JDBC 2.0 Driver
package javadatabase;
import java.sql.*;
public class JDBCDriver
{
	private String dbURL;			// 数据库标识名
	private String user;				// 数据库用户
	private String password;		// 数据库用户密码
	public static void main(String args[])
	{
		try
		{
			JDBCDriver diver=new JDBCDriver();
			driver.setURL("还没确定用哪个数据库");
			driver.setUser("sa");
			driver.setPassword("sa");
			Connection con=driver.getConnection();	// 得到数据库连接
			System.out.println(con.getCatalog());		// 打印当前数据库目录名称
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	public Connection getConnection()
	{
		try
		{
			Class.forName("还没确定用什么数据库");		// 装载数据库驱动
			return DriverManager.getConnection(dbURL,user,password);
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return null;
	}
	public void setURL(String dbURL)
	{
		this.dbURL=dbURL;		// 设置数据库标识
	}
	public void setUser(String user)
	{
		this.user=user;			// 设置当前用户
	}
	public void setPassword(String password)
	{
		this.password=password;	// 设置用户密码
	}
}

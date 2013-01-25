// Chapter 05, sample 60
// JDBCDriver.java
// Connect database with JDBC 2.0 Driver
package javadatabase;
import java.sql.*;
public class JDBCDriver
{
	private String dbURL;			// ���ݿ��ʶ��
	private String user;				// ���ݿ��û�
	private String password;		// ���ݿ��û�����
	public static void main(String args[])
	{
		try
		{
			JDBCDriver diver=new JDBCDriver();
			driver.setURL("��ûȷ�����ĸ����ݿ�");
			driver.setUser("sa");
			driver.setPassword("sa");
			Connection con=driver.getConnection();	// �õ����ݿ�����
			System.out.println(con.getCatalog());		// ��ӡ��ǰ���ݿ�Ŀ¼����
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
			Class.forName("��ûȷ����ʲô���ݿ�");		// װ�����ݿ�����
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
		this.dbURL=dbURL;		// �������ݿ��ʶ
	}
	public void setUser(String user)
	{
		this.user=user;			// ���õ�ǰ�û�
	}
	public void setPassword(String password)
	{
		this.password=password;	// �����û�����
	}
}

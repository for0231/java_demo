// Chapter 05, sample 59
// JDBCODBCBridge.java
// Connect database with JDBC-ODBC bridge
package javadatabase;
import java.sql.*;
public class JDBCODBCBridge
{
	private String dbURL;			//���ݿ��ʶ��
	private String user;				//���ݿ��û�
	private String password;		//���ݿ��û�����
	public static void main(String args[])
	{
		try
		{
			JDBCODBCBridge bridge=new JDBCODBCBridge();
			bridge.setURL("jdbc:odbc:sample");
			bridge.setUser("");
			bridge.setPassword("");
			Connection con=bridge.getConnection();	//�õ����ݿ�����
			System.out.println(con.getCatalog());		//��ӡ��ǰ���ݿ�Ŀ¼����
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
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");	//װ�����ݿ�����
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
		this.dbURL=dbURL;		//�������ݿ��ʶ
	}
	public void setUser(String user)
	{
		this.user=user;			//���õ�ǰ�û�
	}
	public void setPassword(String password)
	{
		this.password=password;	//�����û�����
	}
}

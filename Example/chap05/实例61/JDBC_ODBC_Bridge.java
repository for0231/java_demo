// Chapter 05, sample 61
// JDBC_ODBC_Bridge.java
// Connect database with JDBC-ODBC bridge
package javadatabase;
import java.sql.*;
public class DatabaseBasicOperation
{
	private Statement sta;			//用于数据库操作的Statement
	public static void main(String args[])
	{
		try
		{
			JDBCODBCBridge bridge=new JDBCODBCBridge();
			DatabaseBasicOperation operator=new DatabaseBasicOperation();
			ResultSet rs;				//结果集
			String sqlCommand;		//用于操作的SQL命令
			bridge.setURL("jdbc:odbc:sample");
			bridge.setUser("");
			bridge.setPassword("");
			Connection con=bridge.getConnection();
			operator.setStatement(con);		//设置Satement
			//查询操作
			sqlCommand="SELECT * FROM STUDENTS";
			System.out.println("\n#########查询操作#########");
			System.out.println("输入SQL命令"+sqlCommand+"\n");
			rs=operator.executeQuery(sqlCommand);
			rs.next();				
			for(int i=0;i<rs.getRow();i++)
			{
				System.out.println(rs.getString("NAME"));
				
				rs.next();
			}
			//修改操作
			sqlCommand=
				"UPDATE STUDENTS SET AGE=22 WHERE NAME=\'巧玲\'";
			System.out.println("\n#########修改操作#########");
			System.out.println("输入SQL命令"+sqlCommand+"\n");
			operator.executeUpdate(sqlCommand);
			sqlCommand="SELECT * FROM STUDENTS WHERE NAME=\'巧玲\'";
			rs=operator.executeQuery(sqlCommand);
			rs.next();
			for(int i=0;i<rs.getRow();i++)
			{
				System.out.println(rs.getString("NAME")+
					"年龄修改为"+rs.getInt("AGE"));
				rs.next();
			}
			//添加操作
			sqlCommand="INSERT INTO STUDENTS(ID,NAME,AGE)"+
				"VALUES(\'5\',\'小猫\',1)";
			System.out.println("\n#########添加操作#########");
			System.out.println("输入SQL命令"+sqlCommand+"\n");
			operator.executeInsert(sqlCommand);
			sqlCommand="SELECT * FROM STUDENTS WHERE ID=\'5\'";
			rs=operator.executeQuery(sqlCommand);
			rs.next();
			for(int i=0;i<rs.getRow();i++)
			{
				System.out.println("ID为"+rs.getString("ID")+
					"处已添加"+rs.getString("NAME"));
				rs.next();
			}
			//删除操作
			sqlCommand="DELETE FROM STUDENTS WHERE ID=\'5\'";
			System.out.println("\n#########删除操作#########");
			System.out.println("输入SQL命令"+sqlCommand+"\n");
			operator.executeDelete(sqlCommand);
			sqlCommand="SELECT * FROM STUDENTS WHERE ID=\'5\'";
			rs=operator.executeQuery(sqlCommand);
			rs.next();
			if(!rs.next())
			{
				System.out.println("不存在ID为5的数据，此数据已被删除");
			}
			//关闭所有连接,释放资源
			rs.close();
			operator.closeStatement();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	public void setStatement(Connection con)
	{
		try
		{
			this.sta=con.createStatement();		//Statement中有关数据库查询的函数
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	public ResultSet executeQuery(String sqlCommand)
	{
		try
		{
			//Statement中有关修改数据库的函数
			return sta.executeQuery(sqlCommand); 
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		return null;
	}
	public void executeUpdate(String sqlCommand)
	{
		try
		{
			sta.executeUpdate(sqlCommand);
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	public void executeInsert(String sqlCommand)
	{
		try
		{
			sta.executeUpdate(sqlCommand);
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	} 
	public void executeDelete(String sqlCommand)
	{
		try
		{
			sta.executeUpdate(sqlCommand);
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}  
	public void closeStatement()
	{
		try
		{
			sta.close();
			
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
}

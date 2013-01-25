// Chapter 05, sample 61
// JDBC_ODBC_Bridge.java
// Connect database with JDBC-ODBC bridge
package javadatabase;
import java.sql.*;
public class DatabaseBasicOperation
{
	private Statement sta;			//�������ݿ������Statement
	public static void main(String args[])
	{
		try
		{
			JDBCODBCBridge bridge=new JDBCODBCBridge();
			DatabaseBasicOperation operator=new DatabaseBasicOperation();
			ResultSet rs;				//�����
			String sqlCommand;		//���ڲ�����SQL����
			bridge.setURL("jdbc:odbc:sample");
			bridge.setUser("");
			bridge.setPassword("");
			Connection con=bridge.getConnection();
			operator.setStatement(con);		//����Satement
			//��ѯ����
			sqlCommand="SELECT * FROM STUDENTS";
			System.out.println("\n#########��ѯ����#########");
			System.out.println("����SQL����"+sqlCommand+"\n");
			rs=operator.executeQuery(sqlCommand);
			rs.next();				
			for(int i=0;i<rs.getRow();i++)
			{
				System.out.println(rs.getString("NAME"));
				
				rs.next();
			}
			//�޸Ĳ���
			sqlCommand=
				"UPDATE STUDENTS SET AGE=22 WHERE NAME=\'����\'";
			System.out.println("\n#########�޸Ĳ���#########");
			System.out.println("����SQL����"+sqlCommand+"\n");
			operator.executeUpdate(sqlCommand);
			sqlCommand="SELECT * FROM STUDENTS WHERE NAME=\'����\'";
			rs=operator.executeQuery(sqlCommand);
			rs.next();
			for(int i=0;i<rs.getRow();i++)
			{
				System.out.println(rs.getString("NAME")+
					"�����޸�Ϊ"+rs.getInt("AGE"));
				rs.next();
			}
			//��Ӳ���
			sqlCommand="INSERT INTO STUDENTS(ID,NAME,AGE)"+
				"VALUES(\'5\',\'Сè\',1)";
			System.out.println("\n#########��Ӳ���#########");
			System.out.println("����SQL����"+sqlCommand+"\n");
			operator.executeInsert(sqlCommand);
			sqlCommand="SELECT * FROM STUDENTS WHERE ID=\'5\'";
			rs=operator.executeQuery(sqlCommand);
			rs.next();
			for(int i=0;i<rs.getRow();i++)
			{
				System.out.println("IDΪ"+rs.getString("ID")+
					"�������"+rs.getString("NAME"));
				rs.next();
			}
			//ɾ������
			sqlCommand="DELETE FROM STUDENTS WHERE ID=\'5\'";
			System.out.println("\n#########ɾ������#########");
			System.out.println("����SQL����"+sqlCommand+"\n");
			operator.executeDelete(sqlCommand);
			sqlCommand="SELECT * FROM STUDENTS WHERE ID=\'5\'";
			rs=operator.executeQuery(sqlCommand);
			rs.next();
			if(!rs.next())
			{
				System.out.println("������IDΪ5�����ݣ��������ѱ�ɾ��");
			}
			//�ر���������,�ͷ���Դ
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
			this.sta=con.createStatement();		//Statement���й����ݿ��ѯ�ĺ���
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
			//Statement���й��޸����ݿ�ĺ���
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

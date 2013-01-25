import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
// �������ʹ��JDBC�õ����ݿ��е����ݣ�����ʾ��ҳ����
public class SimpleDB
{	
	public void doGet(HttpServletRequest req,HttpServletResponse res)
	    throws ServletException,IOException
	    {
	    	res.setContentType("text/html");
	    	PrintWriter out = new PrintWriter(res.getOutputStream());
	    	out.println("<html>");
	      out.println("<head>");
	      out.println("<title>SimpleDB</title>");
	      out.println("</head>");
	      // Ϊ�������ݿ���Ӷ������Ϣ����user,password
	      //java.util.Properties props = new java.util.Properties();
	      // System.out.println("ok");
	      query("sun.jdbc.odbc.JdbcOdbcDriver",//jdbc-odbc����
	            "jdbc:odbc:userfile",// ���ݿ�λ��
	             "SELECT name,sex,work from userfile",out);
	      //System.out.println("ok");
	     out.println("</html>");
	     out.close();
	    }
	    private boolean  query(String driverName,String conURL,
	                 String query,PrintWriter out)
	    {
	    	boolean rc = true;
	    	// jdbc���ݿ����Ӷ���
	    	Connection con = null;
	    	// ��ѯ����
	    	Statement stmt = null;
	    	// ������϶���
	    	ResultSet rs = null;
	    	try{
	    		// ʵ����jdbc�������Ա���������������ע��
	    		Class.forName(driverName).newInstance();
	    		// �����ݿ⽨������
	    		con = DriverManager.getConnection(conURL);
	    		stmt = con.createStatement();
	    		// ��ѯ���ݿ�
	    		rs = stmt.executeQuery(query);
	    		// ����ѯ�ṹ�����HTMLҳ��
	    		outTable(rs,out);
	    	}
	    	catch(Exception e){
	    	 e.printStackTrace(out);
	    	 rc = false;
	    	}
	    	finally{
	    		try{
	    	  // �ͷ�ռ�õ���Դ
	    	  rs.close();
	    	  stmt.close();
	    	  con.close();
	    	 }
	    	 catch(Exception e){
	    	 }
	    	}
	     return rc;
	    }
	    private void outTable(ResultSet rs,PrintWriter out)
	      throws Exception
	    {
	    	// ҳ�����
	    	out.println("<center><table border='2'>");
	    	//��ResultSetMetaData�õ��б���
	    	ResultSetMetaData rsmd = rs.getMetaData();
	    	int colCount = rsmd.getColumnCount();
	    	out.println("<tr>");
	    	for(int i=0; i<colCount; i++)
	    	{
	    		//���±��1��ʼ�������Ǵ�0��ʼ
	    		out.println("<th>"+rsmd.getColumnLabel(i+1)+"</th>");
	    	}
	    	out.println("</tr>");
	    	//���ResultSet�е�����
	    	while(rs.next()){
	    	 out.println("<tr>");
	    	 for(int i=0; i<colCount; i++)
	    	 {
	    	 	 out.println("<td>"+rs.getString(i+1)+"</td>");
	    	 }
	    	 out.println("</tr>");
	    	}
	    	out.println("</table></center>");
	    }
}
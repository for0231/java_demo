import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
// 这个例子使用JDBC得到数据库中的数据，并显示在页面上
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
	      // 为连接数据库添加额外的信息，如user,password
	      //java.util.Properties props = new java.util.Properties();
	      // System.out.println("ok");
	      query("sun.jdbc.odbc.JdbcOdbcDriver",//jdbc-odbc驱动
	            "jdbc:odbc:userfile",// 数据库位置
	             "SELECT name,sex,work from userfile",out);
	      //System.out.println("ok");
	     out.println("</html>");
	     out.close();
	    }
	    private boolean  query(String driverName,String conURL,
	                 String query,PrintWriter out)
	    {
	    	boolean rc = true;
	    	// jdbc数据库连接对象
	    	Connection con = null;
	    	// 查询对象
	    	Statement stmt = null;
	    	// 结果集合对象
	    	ResultSet rs = null;
	    	try{
	    		// 实例化jdbc驱动，以便在驱动管理器中注册
	    		Class.forName(driverName).newInstance();
	    		// 和数据库建立连接
	    		con = DriverManager.getConnection(conURL);
	    		stmt = con.createStatement();
	    		// 查询数据库
	    		rs = stmt.executeQuery(query);
	    		// 将查询结构输出到HTML页面
	    		outTable(rs,out);
	    	}
	    	catch(Exception e){
	    	 e.printStackTrace(out);
	    	 rc = false;
	    	}
	    	finally{
	    		try{
	    	  // 释放占用的资源
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
	    	// 页面输出
	    	out.println("<center><table border='2'>");
	    	//从ResultSetMetaData得到列标题
	    	ResultSetMetaData rsmd = rs.getMetaData();
	    	int colCount = rsmd.getColumnCount();
	    	out.println("<tr>");
	    	for(int i=0; i<colCount; i++)
	    	{
	    		//列下标从1开始，而不是从0开始
	    		out.println("<th>"+rsmd.getColumnLabel(i+1)+"</th>");
	    	}
	    	out.println("</tr>");
	    	//输出ResultSet中的内容
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
import java.sql.*;
public class  ConDB{
	// jdbc-odbc驱动
String sDBDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
 // 指定要访问的数据库
String sConnStr = "jdbc:odbc:course";
Connection conn = null;
ResultSet rs = null;
public ConDB() {
try {
	// 注册jdbc驱动程序
Class.forName(sDBDriver); 
}
catch(java.lang.ClassNotFoundException e) {
System.err.println("course(): " + e.getMessage());
}
}

public ResultSet executeQuery(String sql) {
rs = null;
try {
	// 连接数据库
conn = DriverManager.getConnection(sConnStr); 
 // 执行查询操作
Statement stmt = conn.createStatement();
rs = stmt.executeQuery(sql);
} 
catch(SQLException ex) { 
System.err.println("executeQuery: " + ex.getMessage());
}
return rs;
}
}

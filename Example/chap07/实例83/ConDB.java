import java.sql.*;
public class  ConDB{
	// jdbc-odbc����
String sDBDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
 // ָ��Ҫ���ʵ����ݿ�
String sConnStr = "jdbc:odbc:course";
Connection conn = null;
ResultSet rs = null;
public ConDB() {
try {
	// ע��jdbc��������
Class.forName(sDBDriver); 
}
catch(java.lang.ClassNotFoundException e) {
System.err.println("course(): " + e.getMessage());
}
}

public ResultSet executeQuery(String sql) {
rs = null;
try {
	// �������ݿ�
conn = DriverManager.getConnection(sConnStr); 
 // ִ�в�ѯ����
Statement stmt = conn.createStatement();
rs = stmt.executeQuery(sql);
} 
catch(SQLException ex) { 
System.err.println("executeQuery: " + ex.getMessage());
}
return rs;
}
}

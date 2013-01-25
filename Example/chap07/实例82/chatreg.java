package test;
import java.sql.*;

public class  chatreg{
String sDBDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
String sConnStr = "jdbc:odbc:chatreg";
Connection conn = null;
ResultSet rs = null;

public chatreg() {
try {
Class.forName(sDBDriver); 
}
catch(java.lang.ClassNotFoundException e) {
System.err.println("chatreg(): " + e.getMessage());
}
}

public ResultSet executeQuery(String sql) {
rs = null;
try {
conn = DriverManager.getConnection(sConnStr); 
Statement stmt = conn.createStatement();
rs = stmt.executeQuery(sql);
} 
catch(SQLException ex) { 
System.err.println("aq.executeQuery: " + ex.getMessage());
}
return rs;
}
}

import java.sql.*;
import java.util.*;
public class usetrans {
	public static void main(String arg[]){
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@host:1521:SID","username","userpwd");
			conn.setAutoCommit(false);// 禁止自动提交，设置回滚点
			stmt = conn.createStatement();
			stmt.executeUpdate("alter table …"); // 数据库更新操作1,可添加具体内容
			stmt.executeUpdate("insert into table …"); // 数据库更新操作2 ，可添加具体内容
			conn.commit(); // 事务提交
		}catch(Exception ex) {
			ex.printStackTrace();
			try {
				conn.rollback(); // 操作不成功则回滚
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}

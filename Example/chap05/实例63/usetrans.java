import java.sql.*;
import java.util.*;
public class usetrans {
	public static void main(String arg[]){
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@host:1521:SID","username","userpwd");
			conn.setAutoCommit(false);// ��ֹ�Զ��ύ�����ûع���
			stmt = conn.createStatement();
			stmt.executeUpdate("alter table ��"); // ���ݿ���²���1,����Ӿ�������
			stmt.executeUpdate("insert into table ��"); // ���ݿ���²���2 ������Ӿ�������
			conn.commit(); // �����ύ
		}catch(Exception ex) {
			ex.printStackTrace();
			try {
				conn.rollback(); // �������ɹ���ع�
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}

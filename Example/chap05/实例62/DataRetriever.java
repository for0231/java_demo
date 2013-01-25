import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import sun.jdbc.odbc.JdbcOdbcDriver;
public class DataRetriever{
	//���� JDBC ����
	static String jdbcDriver="sun.jdbc.odbc.JdbcOdbcDriver";
	static String dbName="Contacts";
	static String urlRoot="jdbc:odbc:";
	private ActionListener exceptionListener=null;
	public DataRetriever(){
		registerDriver();    //ע������
	}
	public void setDatabaseName(String dbName){   //����DB��
		this.dbName=dbName;
	}
	public void registerDriver{  //��������
		try{
			Class.forName(jdbcDriver);
			DriverManager.registerDriver(new JdbcOdbcDriver());
		}catch(ClassNotFoundException e){
			reportException(e.getMessage());
		}
		catch(SQLException e){
			reportException(e.getMessage());
		}
	}
	public String[][] executeQuery(String SQLQuery){    //ִ��SQL��ѯ
		Vector dataset=new Vector();
		String url=urlRoot+dbName;
		try{
			//�������ݿ�
			Connection con=DriverManager.getConnection(url);
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(SQLQuery);    //�Ѳ�ѯ�����������
			ResultSetMetaData md=rs.getMetaData();
			int nColumns=md.getColumnCount();
			//�򵥵���ResultSet��ѭ��
			while(rs.next()){
				String[] rowData=new String[nColumns];
				for(int i=0;i<nColumns;i++){
					rowData[i]=rs.getObject(i+1).toString();
				}
				dataset.addElement(rowData);     //����dataset
			}
			con.close();    //�ر�����
		}
		catch(SQLException e){
			reportException(e.getMessage());
		}
		String[][] records=new String[dataset.size()][];    //����ֵ����records��ά����
		for(int i=0;i<records.length;i++){
			records[i]=(String[])dataset.elementAt(i);
		}
		return records;
	}
	public void setExceptionListenner(ActionListerner exceptionListener){
		this.exceptionListerner=exceptionListerner;
	}
	private void reportException(String exception){ //�����쳣
		if(exceptionListener!=null){
			ActionEvent evt=new ActionEvent(this,0,exception);
			exceptionListener.actionPerformed(evt);
		}
		else{
			System.err.println(exception);
		}
	}
	public static void main(String args[]){
		DataRetriever retriever=new DataRetriever();
		retriever.setDatabaseName("Contacts");  //����DB��
		//ִ��SQL��䲢�ѷ���ֵ����records��ά����
		String[][] records=retriever.executeQuery("SELECT * FROM Contact_Info");
		for(int i=0;i<records.length;i++){     //��records������ȡ������ӡ���
			String[] record=records[i];
			for(int j=0;j<record.length;j++){
				if(j>0) System.out.print("\t");
				System.out.print(record[j]);
			}
			System.out.println();
		}
	}
}

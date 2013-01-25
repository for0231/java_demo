import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import sun.jdbc.odbc.JdbcOdbcDriver;
public class DataRetriever{
	//加载 JDBC 驱动
	static String jdbcDriver="sun.jdbc.odbc.JdbcOdbcDriver";
	static String dbName="Contacts";
	static String urlRoot="jdbc:odbc:";
	private ActionListener exceptionListener=null;
	public DataRetriever(){
		registerDriver();    //注册驱动
	}
	public void setDatabaseName(String dbName){   //设置DB名
		this.dbName=dbName;
	}
	public void registerDriver{  //加载驱动
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
	public String[][] executeQuery(String SQLQuery){    //执行SQL查询
		Vector dataset=new Vector();
		String url=urlRoot+dbName;
		try{
			//连接数据库
			Connection con=DriverManager.getConnection(url);
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(SQLQuery);    //把查询结果放入结果集
			ResultSetMetaData md=rs.getMetaData();
			int nColumns=md.getColumnCount();
			//简单地在ResultSet中循环
			while(rs.next()){
				String[] rowData=new String[nColumns];
				for(int i=0;i<nColumns;i++){
					rowData[i]=rs.getObject(i+1).toString();
				}
				dataset.addElement(rowData);     //加入dataset
			}
			con.close();    //关闭连接
		}
		catch(SQLException e){
			reportException(e.getMessage());
		}
		String[][] records=new String[dataset.size()][];    //返回值放入records二维数组
		for(int i=0;i<records.length;i++){
			records[i]=(String[])dataset.elementAt(i);
		}
		return records;
	}
	public void setExceptionListenner(ActionListerner exceptionListener){
		this.exceptionListerner=exceptionListerner;
	}
	private void reportException(String exception){ //处理异常
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
		retriever.setDatabaseName("Contacts");  //设置DB名
		//执行SQL语句并把返回值放入records二维数组
		String[][] records=retriever.executeQuery("SELECT * FROM Contact_Info");
		for(int i=0;i<records.length;i++){     //从records中逐条取出并打印结果
			String[] record=records[i];
			for(int j=0;j<record.length;j++){
				if(j>0) System.out.print("\t");
				System.out.print(record[j]);
			}
			System.out.println();
		}
	}
}

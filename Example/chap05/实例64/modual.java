try{
	//����һ��������������дӱ� 'my_table' ��ѯ���Ľ������
	Statement stmt=Connection.createStatement();
	String sql��"SELECT * FROM my_table"�� 
		ResultSet rs=stmt.executeQuery(sql); 
}
catch(SQLException e){}

try{
	//建立一个结果集包含所有从表 'my_table' 查询出的结果数据
	Statement stmt=Connection.createStatement();
	String sql＝"SELECT * FROM my_table"； 
		ResultSet rs=stmt.executeQuery(sql); 
}
catch(SQLException e){}

try{
	String ul,namestr,typestr,cstr,strn,indexstr,prostr; 
	short data; 
	int index,pron; 
	String[] type={"table"};
	ul="jdbc:odbc:useDSN";
	// 加载驱动
	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	Connection con=DriverManager.getConnection(ul,"sa","sa");
	DatabaseMetaData dbmd=con.getMetaData();
	// 建立一个结果集
	ResultSet rs=dbmd.getTables(null,null,null,type); 
	// 输出信息
	while(rs.next()){
		namestr=rs.getString("TABLE_NAME");
		typestr=rs.getString("TABLE_TYPE");
		// 在这里读者可以自行增加与用户交互的内容				
	}			
	rs=dbmd.getColumns(null,null,"student","%");
	// 产生一个对表列的结果集
	while(rs.next()){
		namestr=rs.getString("TABLE_NAME");
		cstr=rs.getString("COLUMN_NAME");
		typestr=rs.getString("TYPE_NAME");
		data=rs.getShort("DATA_TYPE");
		// 在这里读者可以自行增加与用户交互的内容
	}			
	// 获得索引信息
	rs=dbmd.getIndexInfo(null,null,"student",false,false); 
	while(rs.next()){
		namestr=rs.getString("INDEX_NAME");
		index=rs.getInt("TYPE");
		if(index==0)
			indexstr="没有索引";
		if(index==1)
			indexstr="聚集索引";
		if(index==2)
			indexstr="哈希表索引";
		if(index==3) 
			indexstr="其它索引";
		//在这里读者可以自行增加与用户交互的内容
	}			
	rs=dbmd.getProcedures(null,null,"%"); 
	while(rs.next()){
		namestr=rs.getString("PROCEDURE_NAME");
		pron=rs.getInt("PROCEDURE_TYPE");
		if(pron==0)
			prostr="返回结果未知";
		if(pron==1)
			prostr="没有返回结果";
		if(pron==2)
			prostr="有返回结果";
		// 在这里读者可以自行增加与用户交互的内容
	}
	rs=dbmd.getProcedureColumns(null,null,"%","%"); 
	while(rs.next()){
		namestr=rs.getString("PROCEDURE_NAME");
		prostr=rs.getString("COLUMN_NAME");
		// 在这里读者可以自行增加与用户交互的内容
	}		
	// 关闭连接
	con.close();
}catch(Exception e){ 
	System.out.println(e.getMessage());
	e.printStackTrace();
}

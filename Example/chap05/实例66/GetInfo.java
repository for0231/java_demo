try{
	String ul,namestr,typestr,cstr,strn,indexstr,prostr; 
	short data; 
	int index,pron; 
	String[] type={"table"};
	ul="jdbc:odbc:useDSN";
	// ��������
	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	Connection con=DriverManager.getConnection(ul,"sa","sa");
	DatabaseMetaData dbmd=con.getMetaData();
	// ����һ�������
	ResultSet rs=dbmd.getTables(null,null,null,type); 
	// �����Ϣ
	while(rs.next()){
		namestr=rs.getString("TABLE_NAME");
		typestr=rs.getString("TABLE_TYPE");
		// ��������߿��������������û�����������				
	}			
	rs=dbmd.getColumns(null,null,"student","%");
	// ����һ���Ա��еĽ����
	while(rs.next()){
		namestr=rs.getString("TABLE_NAME");
		cstr=rs.getString("COLUMN_NAME");
		typestr=rs.getString("TYPE_NAME");
		data=rs.getShort("DATA_TYPE");
		// ��������߿��������������û�����������
	}			
	// ���������Ϣ
	rs=dbmd.getIndexInfo(null,null,"student",false,false); 
	while(rs.next()){
		namestr=rs.getString("INDEX_NAME");
		index=rs.getInt("TYPE");
		if(index==0)
			indexstr="û������";
		if(index==1)
			indexstr="�ۼ�����";
		if(index==2)
			indexstr="��ϣ������";
		if(index==3) 
			indexstr="��������";
		//��������߿��������������û�����������
	}			
	rs=dbmd.getProcedures(null,null,"%"); 
	while(rs.next()){
		namestr=rs.getString("PROCEDURE_NAME");
		pron=rs.getInt("PROCEDURE_TYPE");
		if(pron==0)
			prostr="���ؽ��δ֪";
		if(pron==1)
			prostr="û�з��ؽ��";
		if(pron==2)
			prostr="�з��ؽ��";
		// ��������߿��������������û�����������
	}
	rs=dbmd.getProcedureColumns(null,null,"%","%"); 
	while(rs.next()){
		namestr=rs.getString("PROCEDURE_NAME");
		prostr=rs.getString("COLUMN_NAME");
		// ��������߿��������������û�����������
	}		
	// �ر�����
	con.close();
}catch(Exception e){ 
	System.out.println(e.getMessage());
	e.printStackTrace();
}

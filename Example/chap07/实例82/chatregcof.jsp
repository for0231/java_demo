<html>
<head>
<title>��¼���</title>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=gb2312" %> 

</head>

<body bgcolor="#ccccff">

<%@ page language="java"  %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page  import="java.sql.*" %>


<%
   String regName=(String)request.getParameter("username"); 
      regName=regName.trim();
   
   String regPassword=(String)request.getParameter("password");
      regPassword=regPassword.trim();

   String regEmail=(String)request.getParameter("useremail");
      regEmail=regEmail.trim();

   String regHomepage=(String)request.getParameter("homepage");
      regHomepage=regHomepage.trim();
 
%>
 <jsp:useBean id="reg" scope="page" class="test.chatreg" />
<%
  
  String sql="select * from chatreg where username='" + regName + "'";
  ResultSet rs = reg.executeQuery(sql); 
  if(rs.next()) { rs.close();
  
  out.println("<center><h2 >�� �� �� �� �� �� �� �� �� �� ��</h2> </center><br> <br>");
  out.println( "<center><a href=\"chatreg.jsp\"  > �� �� ע �� </a></center><br> "); 
  out.println( "<center><a href=\"netchat.jsp\"  > �� �� ע �� �� </a></center> ");
} 
else
{ 
 String strSQL="insert into chatreg(username , password , email ,homepage ) values('" + regName + "',  '" + regPassword +"'  ,  '" + regEmail + "'  , '" + regHomepage + "') ";
 reg.executeQuery(strSQL);
 out.println("<center><h2 color=red>�� ϲ �� ע �� �� �� ��</h2> </center><br> <br>");
 out.println( "<center><a href=\"netchat.jsp\"  > ���� </a></center> "); 
} 
%>

 
</body>
</html>
<html>
<head>
<title>��¼���</title>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=gb2312" %> 
</head>
<body>
<%@ page language="java"  %>
<%@ page import="java.util.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page  import="java.sql.*" %>
<%@ page  import="ConDB" %>
<jsp:useBean id="check" scope="page" class="ConDB" />
<%
 boolean flag = false;
String name=(String)request.getParameter("name");
name=name.trim();
String stdid=(String)request.getParameter("id");
stdid = stdid.trim();
%>
<%
if(name.length()!=0&&stdid.length()!=0){
   String sql = "select * from student where  stdname='"+name+" and 'stdid= '"+stdid+"' ";
ResultSet RS = check.executeQuery(sql);
 if(RS.next()) 
             { 
             flag = true;
	     RS.close();
             }
          else
             { 
             out.println("<center>û�����ѧ����¼!<br>");
             out.println("<a href='./login.htm'>����</a></center>");
	      RS.close();
             }
             }
     else{
          out.println("<center>��û��д������ѧ��!<br>");
           out.println("<a href='./login.htm'>����</a></center>");
     }
 if(flag){
  application.setAttribute("stdname",""+name+"");
   application.setAttribute("stdid",""+stdid+"");
  %>
   <jsp:forward page="select.jsp"/>
<%}%>
</body>
</html>
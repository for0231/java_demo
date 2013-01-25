<html>
<head>
<title>登录检查</title>
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
             out.println("<center>没有这个学生记录!<br>");
             out.println("<a href='./login.htm'>返回</a></center>");
	      RS.close();
             }
             }
     else{
          out.println("<center>你没有写姓名或学号!<br>");
           out.println("<a href='./login.htm'>返回</a></center>");
     }
 if(flag){
  application.setAttribute("stdname",""+name+"");
   application.setAttribute("stdid",""+stdid+"");
  %>
   <jsp:forward page="select.jsp"/>
<%}%>
</body>
</html>
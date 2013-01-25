<html>
<head>
<title>更新数据库</title>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=gb2312" %>
</head>
<body bgcolor="#CCFFCC">
<%@ page language="java"  %>
<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page  import="java.sql.*" %>
<%@ page  import="ConDB" %>
<jsp:useBean id="check" scope="page" class="ConDB" /> 
<%
String id = (String)application.getAttribute("stdid");
int[] course= new int[]{-1,-1};
int sum=0 ; // 记录选了几门课
String[] checked = new String[5];
checked[0] = (String)request.getParameter("check0");
checked[1] = (String)request.getParameter("check1");
checked[2] = (String)request.getParameter("check2");
checked[3] = (String)request.getParameter("check3");
checked[4] = (String)request.getParameter("check4");
for(int i=0; i<5; i++)
{
 if(checked[i].equals("on")){
  course[sum++] = i+1;
  }
}
if(sum>2){ 
  out.println("<center>选课不能超过两门!");
  out.println("<a href='./select.jsp'>返回</a></center>");
 }
 if(sum==1){
  String sql = "insert into sc(stdid,course1,course2) values('"+id+"','"+course[0]+"','NULL')";
  check.executeQuery(sql); 
  }
 if(sum==2){
  String sql = "insert into sc(stdid,course1,course2) values('"+id+"','"+course[0]+"','"+course[1]+"')";
 check.executeQuery(sql);
 } 
out.println("<center>选课成功!</center>");
%>

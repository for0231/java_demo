<%@ page contentType="text/html;charSet=gb2312"%>
<html>
<head>
<title>���ﳵ</title>
</head>
<body>
<%@ page language="java" import="DummyCart"%>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<jsp:useBean id="cart" scope="session" class="DummyCart"/>
<%
  cart.processRequest(request);
%>
<font size="5" color="#cc0000">
<br>
����Ĺ��ﳵ����������Ʒ:
<ol>
<%
  String[] items = cart.getItems();
  for(int i=0; i<items.length; i++){
%>
<li><%=items[i]%>
<%}
%>
</ol>
</font>
<hr>
<%@ include file="carts.htm"%>
<body>
</html>
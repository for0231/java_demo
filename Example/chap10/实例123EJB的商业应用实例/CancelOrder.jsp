<%@ page language="java" errorPage="orderErr.jsp"
    import="order.javabean.*" contentType="text/html; charset=Big5" %> 
<jsp:useBean id="OrderList" scope="session" class="order.javabean.OrderForm"/>
<%
    OrderList.cancelOrder();    
%>
<html>
<head>
<title>order</title>
<meta http-equiv="Content-Type" content="text/html; charset=big5">
</head>
<body bgcolor="#FFFFFF">
<form method="post" action="">
  <p align="center">
    <font size="6">
      <b>ȡ��������</b>
    </font></p>
  <p align="center">&nbsp; </p>
  <p align="center">������� : 
    <%=OrderList.getOrder_id()%> �Ѿ�ȡ��</p>
    <% OrderList.setOrder_id(""); %>
  <p align="center"><a href="index.html">����ҳ</a></p>
</form>
</body>
</html>

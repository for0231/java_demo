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
      <b>取消订购单</b>
    </font></p>
  <p align="center">&nbsp; </p>
  <p align="center">订单编号 : 
    <%=OrderList.getOrder_id()%> 已经取消</p>
    <% OrderList.setOrder_id(""); %>
  <p align="center"><a href="index.html">回首页</a></p>
</form>
</body>
</html>

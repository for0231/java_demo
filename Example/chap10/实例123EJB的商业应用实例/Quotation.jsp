<%@ page language="java"  errorPage="orderErr.jsp"
    import=
     "java.sql.*,
      java.util.*,
      order.javabean.*,
      order.common.*"
    contentType="text/html; charset=Big5" %>
<jsp:useBean id="OrderList" 
  scope="session" class="order.javabean.OrderForm"/>
<jsp:setProperty name="OrderList" 
  property="user_id" param="user_id"/>  
<jsp:setProperty name="OrderList" property="itemNum" param="itemNum"/>
<jsp:setProperty name="OrderList" property="product_id" param="product_id"/>
<jsp:setProperty name="OrderList" property="name" param="name"/>
<jsp:setProperty name="OrderList" property="price" param="price"/>  
<jsp:setProperty name="OrderList"  property="quantity" param="quantity"/>   
<%
    OrderList.setOrderList();
    OrderList.Order();
%>
<html>
<head>
<title>order</title>
<meta http-equiv="Content-Type"  content="text/html; charset=big5">
</head>
<body bgcolor="#FFFFFF">
<form method="post" action="OrderForm.jsp">
  <p align="center">
    <font size="6">
      <b>订购单</b>
    </font></p>
  <p align="center">&nbsp;</p>
  <p align="center">
    订购者帐号 : <%=OrderList.getUser_id()%> 
    订单编号 : <%=OrderList.getOrder_id()%></p>
  <table align="center">
    <tr bgcolor="#CCCCCC"> 
      <td nowrap>产品名称</td>
      <td nowrap>价 格</td>
      <td nowrap>订购数量</td>
      <td nowrap>金额</td>
    </tr>
<% 
  Collection c=(Collection)OrderList.getOrderList();
  Iterator oItr=c.iterator();
  while(oItr.hasNext()){
    OrderItem oItem=(OrderItem)oItr.next(); 
%> 
    <tr bgcolor="#99FFFF"> 
      <td nowrap><%=oItem.getName()%> </td>
      <td nowrap><%=oItem.getPrice()%> </td>
      <td nowrap> <%=oItem.getQuantity()%> </td>
      <td nowrap> <%=oItem.getTotalprice()%> </td>
    </tr>
<%    
  }
%> 
  </table>
  <p align="center"> 
    总价 : <%=OrderList.getTotalprice()%></p>
  <p align="center"> 
    <input type="hidden" name="user_id" 'value='<%=OrderList.getUser_id()%>'>
    <input type="hidden" name="order_id" value='<%=OrderList.getOrder_id()%>'>   
    <input type="submit" name="Submit" value="  继续订购  ">
    <input type="button" name="Cancel" value="  取消订购  "
      onclick="this.form.action ='CancelOrder.jsp';this.form.submit();">
    <input type="submit" name="Submit3" value="  更改订购  "
      onclick="this.form.action ='UpdateForm.jsp';this.form.submit();">
    <a href="index.html">回首页</a> </p>
</form>
</body>
</html>

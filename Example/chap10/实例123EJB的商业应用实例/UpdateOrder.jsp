<%@ page language="java" 
    errorPage="orderErr.jsp"
    import=
     "java.sql.*,
      java.util.*,
      order.javabean.*,
      order.common.*"
    contentType="text/html; charset=Big5" %> 
<jsp:useBean id="OrderList" scope="session" class="order.javabean.OrderForm">
</jsp:useBean>
<jsp:setProperty name="OrderList" property="itemNum" param="itemNum"/>
<jsp:setProperty name="OrderList" property="product_id" param="product_id"/>
<jsp:setProperty name="OrderList" property="name" param="name"/>
<jsp:setProperty name="OrderList" property="price" param="price"/>  
<jsp:setProperty name="OrderList" property="quantity" param="quantity"/>    
<%
    OrderList.setOrderList();
    OrderList.updateOrder();
%>
<html>
<head>
<title>order</title>
<meta http-equiv="Content-Type" content="text/html; charset=big5">
</head>
<body bgcolor="#FFFFFF">
<form method="post" action="OrderForm.jsp">
  <p align="center">
    <font size="6">
      <b>隆劃等</b>
    </font></p>
  <p align="center">&nbsp;</p>
  <p align="center">
    隆劃氪梛瘍 : <%=OrderList.getUser_id()%> 
    隆等晤瘍 : <%=OrderList.getOrder_id()%></p>
  <table align="center">
    <tr bgcolor="#CCCCCC"> 
      <td nowrap>莉こ靡備</td>
      <td nowrap>歎 跡</td>
      <td nowrap>隆劃杅講</td>
      <td nowrap>踢塗</td>
    </tr>
<% 
  Collection c=(Collection)OrderList.getOrderList();
  Iterator oItr=c.iterator();
  while(oItr.hasNext()){
    OrderItem oItem=(OrderItem)oItr.next(); 
%> 
    <tr bgcolor="#99FFFF"> 
      <td nowrap>
        <%=oItem.getName()%> </td>
      <td nowrap>
        <%=oItem.getPrice()%> </td>
      <td nowrap>
        <%=oItem.getQuantity()%> </td>
      <td nowrap>
        <%=oItem.getTotalprice()%> </td>
    </tr>
<%    
  }
%> 
  </table>
  <p align="center"> 軞歎 : <%=OrderList.getTotalprice()%></p>
  <p align="center">
    <input type="submit" name="Submit" value="  樟哿隆劃  ">
    <input type="button" name="Cancel" value="  �＋�隆劃  "
      onclick="this.form.action ='CancelOrder.jsp';this.form.submit();">
    <input type="button" name="Update" value="  載蜊隆劃  "
      onclick="this.form.action ='UpdateForm.jsp';this.form.submit();">
    <a href="index.html">隙忑珜</a> </p>
</form>
</body>
</html>

<%@ page  language="java"  errorPage="orderErr.jsp"
    import=
     "java.sql.*,
      java.util.*,
      order.javabean.*,
      order.common.*"
    contentType="text/html; charset=Big5" %>
<jsp:useBean id="Orders" scope="page" class="order.javabean.QueryOrder"/>
<jsp:setProperty name="Orders"  property="user_id" param="user_id"/>
<html>
<head>
<title>order</title>
<meta http-equiv="Content-Type" content="text/html; charset=big5">
</head>
<body bgcolor="#FFFFFF">
<form method="post" action="OrderForm.jsp">
  <p align="center">
    <font size="6">
      <b>������</b>
    </font></p>
  <p>&nbsp;</p>
  <p align="center">< �������ʺ� : <%=Orders.getUser_id()%> ></p>        
<%
  Hashtable oHt=Orders.getOrders();
  Enumeration orderKey=oHt.keys();
  while(orderKey.hasMoreElements()){
    String order_id=(String)orderKey.nextElement();
    // order_id�Ķ�����Ŀ
    Collection c=(Collection)oHt.get(order_id);
%>
  <p align="center"> ������� : <%=order_id%></p>
  <table align="center">
    <tr bgcolor="#CCCCCC"> 
      <td nowrap>��Ʒ����</td>
      <td nowrap>�� ��</td>
      <td nowrap>��������</td>
      <td nowrap>���</td>
    </tr>
<% 
    Iterator oItr=c.iterator();
    while(oItr.hasNext()){
      OrderItem oItem=(OrderItem)oItr.next();
      String product_id=oItem.getProduct_id();
      int price=Orders.getProductPrice(product_id);
      int quantity=oItem.getQuantity();
%> 
    <tr bgcolor="#99FFFF"> 
      <td nowrap>
        <%=Orders.getProductName(product_id)%></td>
      <td nowrap><%=price%></td>
      <td nowrap><%=quantity%></td>
      <td nowrap><%=price*quantity%></td>
    </tr>
<%    
    }
%> 
  </table>
<%    
  }
%>   
  <p align="center"> 
    <input type="hidden" name="user_id" ' value='<%=Orders.getUser_id()%>'>  
    <input type="submit" name="Submit" value="  ��������  ">
    <a href="index.html">����ҳ</a></p>
</form>
</body>
</html>

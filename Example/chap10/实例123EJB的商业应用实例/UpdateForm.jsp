<%@ page  language="java" errorPage="orderErr.jsp"
    import=
     "java.sql.*,
      java.util.*,
      order.javabean.*,
      order.common.*"
    contentType="text/html; charset=Big5" %>   
<jsp:useBean id="OrderList" scope="session" class="order.javabean.OrderForm"/>
<jsp:useBean id="ProductTbl" scope="session" class="order.javabean.OrderForm"/>
<html>
<head>
<title>order</title>
<meta http-equiv="Content-Type" 
 content="text/html; charset=big5">
</head>
<body bgcolor="#FFFFFF">
<form method="post" action="UpdateOrder.jsp">
  <p align="center"> 
    <font size="6"><b>�������</b></font></p>
  <p align="center">&nbsp;</p>
  <p align="center">
    �������ʺ� : <%=OrderList.getUser_id()%> 
    ������� : <%=OrderList.getOrder_id()%></p>
  <table align="center">
    <tr bgcolor="#CCCCCC"> 
      <td nowrap>��Ʒ����</td>
      <td nowrap>�� ��</td>
      <td nowrap>��������</td>
      <td nowrap>���</td>
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
  <p align="center"> 
    �ܼ� : <%=OrderList.getTotalprice()%></p>
  <p align="center">
    <b><font size="4">&lt; �¶��� &gt;</font></b></p>
  <table align="center">
    <tr bgcolor="#CCCCCC"> 
      <td nowrap>����</td>
      <td nowrap>��Ʒ����</td>
      <td nowrap>�� ��</td>
      <td nowrap>��������</td>
    </tr>
<%  
  Vector pVector=ProductTbl.getProductList();
  Enumeration pEnu=pVector.elements();
  int num = 0;
  while(pEnu.hasMoreElements()){
    ProductItem p=(ProductItem)pEnu.nextElement(); 
%> 
    <tr bgcolor="#99FFFF"> 
      <td nowrap> 
        <div align="center"> 
          <input type="checkbox" name="itemNum" 
            value='<%=num++%>'>
          <input type="hidden" name="product_id" 
            value='<%=p.getProduct_id()%>'>
          <input type="hidden" name="name" 
            value='<%=p.getName()%>'>
          <input type="hidden" name="price" 
            value='<%=p.getPrice()%>'>
        </div>
      </td>
      <td nowrap> <%=p.getName()%> </td>
      <td nowrap> <%=p.getPrice()%> </td>
      <td nowrap> 
        <input type="text" name="quantity" maxlength="4">
      </td>
    </tr>
<%    
  }
%> 
  </table>
  <p align="center">  
    <input type="submit" name="Submit" value="ȷ��">
    <input type="reset" name="Reset" value="����">
    <a href="index.html">����ҳ</a> 
</form>
</body>
</html>

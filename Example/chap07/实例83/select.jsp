<html>
<head>
<title>ѡ�ε�</title>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=gb2312" %> 
</head>
<body bgcolor="#CCFFCC">
<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%
//String coutse[2]={"-1","-1"};
String name=(String)application.getAttribute("stdname");
out.println("<font color='red' size='1'> ���!"+name);
out.println("</font>");
%>
<center><h1>ѡ�ε�</h1></center><br>
<center><font size="1" color="red">˵��:���ֻ��ѧ2��,������Ч </font></center>
<form name="form" method="post" action="update.jsp">
  <table width="90%" border="1"   align="center">
    <tr> 
      <th width="32%"><strong>�γ�����</strong></th>
      <th width="30%"><strong>�ڿν�ʦ</strong></th>
      <th width="%"><strong>�Ͽ�ʱ��</strong></th>
    </tr>
    <tr>
      <td> <div align="left">
          <input type="checkbox" name="check0">
          �ʵ�ά�� </div></td>
      <td><div align="center">�ŷ� </div></td>
      <td><div align="center">����һ </div></td>
    </tr>
    <tr>
      <td><input type="checkbox" name="check1" >
        ���﹤�� </td>
      <td><div align="center">����</div></td>
      <td><div align="center">���ڶ�</div></td>
    </tr>
    <tr>
      <td><input type="checkbox" name="check2">
        ���� </td>
      <td><div align="center">���</div></td>
      <td><div align="center">������</div></td>
    </tr>
    <tr>
      <td><input type="checkbox" name="check3">
        ��������</td>
      <td><div align="center">����</div></td>
      <td><div align="center">������</div></td>
    </tr>
    <tr>
      <td><input type="checkbox" name="check4">
        �й���ʷ</td>
      <td><div align="center">����</div></td>
      <td><div align="center">������</div></td>
    </tr>
  </table>
  <p align="center"> 
    <input type="submit" name="Submit" value="�ύ">&nbsp;&nbsp;
    <input type="reset" name="Reset" value="����">
  </p>
</form>
</body>
</html>
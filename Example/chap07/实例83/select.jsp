<html>
<head>
<title>选课单</title>
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
out.println("<font color='red' size='1'> 你好!"+name);
out.println("</font>");
%>
<center><h1>选课单</h1></center><br>
<center><font size="1" color="red">说明:最多只能学2个,否则无效 </font></center>
<form name="form" method="post" action="update.jsp">
  <table width="90%" border="1"   align="center">
    <tr> 
      <th width="32%"><strong>课程名字</strong></th>
      <th width="30%"><strong>授课教师</strong></th>
      <th width="%"><strong>上课时间</strong></th>
    </tr>
    <tr>
      <td> <div align="left">
          <input type="checkbox" name="check0">
          彩电维修 </div></td>
      <td><div align="center">张方 </div></td>
      <td><div align="center">星期一 </div></td>
    </tr>
    <tr>
      <td><input type="checkbox" name="check1" >
        生物工程 </td>
      <td><div align="center">宋桃</div></td>
      <td><div align="center">星期二</div></td>
    </tr>
    <tr>
      <td><input type="checkbox" name="check2">
        物理 </td>
      <td><div align="center">李洁</div></td>
      <td><div align="center">星期三</div></td>
    </tr>
    <tr>
      <td><input type="checkbox" name="check3">
        电子商务</td>
      <td><div align="center">陈天</div></td>
      <td><div align="center">星期四</div></td>
    </tr>
    <tr>
      <td><input type="checkbox" name="check4">
        中国历史</td>
      <td><div align="center">赵云</div></td>
      <td><div align="center">星期五</div></td>
    </tr>
  </table>
  <p align="center"> 
    <input type="submit" name="Submit" value="提交">&nbsp;&nbsp;
    <input type="reset" name="Reset" value="重置">
  </p>
</form>
</body>
</html>
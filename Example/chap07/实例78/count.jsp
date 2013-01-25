<%@ page contentType="text/html;charSet=gb2312"%>
<HTML> 
<HEAD> 
<TITLE> 
counter 
</TITLE> 
</HEAD> 
<BODY>
<center> 
<H1> 计数器实例 
</H1></center> 
<%@ page language="java" import="counter" %>
<!-初始化counter这个Bean，实例为bean0> 
<jsp:useBean id="bean0" scope="application" class="counter"/> 
<% 
out.println("<center>你是第<font color='red'>"+bean0.getCount()+"</font>个访问该页面</center><BR>"); 
%> 
</BODY> 
</HTML>
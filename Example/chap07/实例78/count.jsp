<%@ page contentType="text/html;charSet=gb2312"%>
<HTML> 
<HEAD> 
<TITLE> 
counter 
</TITLE> 
</HEAD> 
<BODY>
<center> 
<H1> ������ʵ�� 
</H1></center> 
<%@ page language="java" import="counter" %>
<!-��ʼ��counter���Bean��ʵ��Ϊbean0> 
<jsp:useBean id="bean0" scope="application" class="counter"/> 
<% 
out.println("<center>���ǵ�<font color='red'>"+bean0.getCount()+"</font>�����ʸ�ҳ��</center><BR>"); 
%> 
</BODY> 
</HTML>
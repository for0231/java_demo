<%@ page contentType="text/html;charSet=gb2312"%>
<HTML> 
<HEAD> 
<TITLE> 
</TITLE> 
</HEAD> 
<BODY>
<%@ page language="java" import="SimpleDB" %>
<jsp:useBean id="TheBean" scope="page" class="SimpleDB"/> 
<% 
TheBean.doGet(request,response);
%> 
</BODY> 
</HTML>
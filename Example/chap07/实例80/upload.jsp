<%@ page contentType="text/html;charSet=gb2312"%>
<HTML> 
<HEAD> 
<TITLE> 
</TITLE> 
</HEAD> 
<BODY>
<%@ page language="java" import="FileUpload" %>
<jsp:useBean id="TheBean" scope="page" class="FileUpload"/> 
<% 
TheBean.doUpload(request);
out.println("<center>�ļ��ϴ��ɹ���"); 
out.println("�ϴ��ļ�:" + TheBean.getFilename()); 
out.println("<BR>�ļ���������:" + TheBean.getContentType()); 
out.println("<BR>�ϴ���:" + TheBean.getFieldValue("name"));  
out.println("<BR>�ϴ�˵��:" + TheBean.getFieldValue("comment"));
out.println("<center>"); 
%> 
</BODY> 
</HTML>
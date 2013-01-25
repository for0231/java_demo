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
out.println("<center>文件上传成功！"); 
out.println("上传文件:" + TheBean.getFilename()); 
out.println("<BR>文件内容类型:" + TheBean.getContentType()); 
out.println("<BR>上传者:" + TheBean.getFieldValue("name"));  
out.println("<BR>上传说明:" + TheBean.getFieldValue("comment"));
out.println("<center>"); 
%> 
</BODY> 
</HTML>
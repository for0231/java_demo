<%@ page import = "java.io.*,java.util.*" %> 
<%@ page contentType="text/html;charSet=gb2312"%>
<html> 
<head> 
<title>���Բ�</title> 
</head> 
<body> 
<center>
  <h1> лл�������� </h1>
</center>
<br><a href="guestbook.htm">����</a> 
<% 
String yourName = request.getParameter( "name");
String yourComment = request.getParameter("content"); 
File file = new File ("D:/mydata.txt"); 
FileWriter fw = new FileWriter(file,true); 
PrintWriter pw = new PrintWriter(fw); 
pw.println ("����:"+yourName +"����:" +yourComment); 
pw.close(); 
fw.close(); 
%> 
</body> 

</html> 


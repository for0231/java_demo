<HTML>
<HEAD>
<%@ page session="true"  %>
<%@ page contentType="text/html;charset=gb2312" %>
 <TITLE>
logout
</TITLE>
</HEAD>
<BODY bgcolor="#CCFFCC">
<%@ page language="java"  %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<br>
<br>
<br>
<br>

    <% 
     String logout=(String)request.getParameter("logout");
       
      if(logout==null)
       logout=new String("no");
       else if(logout.equals("yes"))
     {
      
        synchronized (application)
           {
            Vector UserName=null;  
            UserName= (Vector)application.getAttribute("UserName");
            UserName.remove(session.getValue("name"));
	    }
        synchronized (application)
	{
            Vector outMessage=null;	  
	     outMessage= (Vector)application.getAttribute("Message");
            if(outMessage==null) 
                   outMessage= new Vector(30,10);
             String outstr=new String("<font color=blue>[����]>>�����ǣ�������һ���ˣ�������ڣ���</font><br><font color=orage><b>[����]���ѣ��ټ��� </b></font>");
	     String str4=new String("no");
	     String str5=new String("EveryOne");
	            str5=new String(str5.getBytes("iso-8859-1"),"GBK"); 
             outMessage.addElement(str4); 
             outMessage.addElement(session.getValue("name"));
             outMessage.addElement(str5);
             outMessage.addElement(outstr);
             application.setAttribute("Message", outMessage);                
             %>
	     <script language="JavaScript">
	      window.location="netchat.jsp"
              </script>  
                  <%
	  }
           
       }  %>

</BODY>
</HTML>
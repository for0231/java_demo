<html>
<head>
<title>登录检查</title>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=gb2312" %> 
</head>
<body>

<%@ page language="java"  %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page  import="java.sql.*" %>

<jsp:useBean id="reg" scope="page" class="chatreg" />
<%
   String regName=(String)request.getParameter("regName"); 
   regName=regName.trim();
   String regPassword=(String)request.getParameter("regPassword");
   regPassword=regPassword.trim();
   String Name=(String)request.getParameter("username");
   Name=Name.trim();
   String regname=null;
%>
<%
 if((regName.length()!=0))
 {  
     String sql="select * from chatreg where username='"+ regName +"' and password='"+ regPassword +"' ";
     ResultSet RS = reg.executeQuery(sql);
          if(RS.next()) 
             { 
             regname=regName+"@";
	     RS.close();
             }
          else
             { 
	       RS.close();
             session.putValue("confirm_message", "<center><font color=red><b>错误信息：</b></font><br><font color=blue><b>[用户名或密码错误！]</b></font></center>");
               %>
             <jsp:forward page="in.jsp"/>
               <%  
	     }
         
 }
else
{

   if((Name.length()<3)||(Name.length()>8))
    { 
    session.putValue("confirm_message","<center><font color=red><b>错误信息：</b></font><br><font color=blue><b>[你的名字长度必须在3--8之间，谢谢！]</b></font></center>");
  %>
    <jsp:forward page="in.jsp"/>
    <% }
 %>
   <%
      Character c=new Character(' ');
      for(int i=0;i<Name.length();i++)
       {   
           if( Name.charAt(i) == c.charValue() )
	  {
	  session.putValue("confirm_message","<center><font color=red><b>错误信息：</b></font><br><font color=blue><b>[名字中不能有空格，谢谢！]</b></font></center>");
	    %>
          <jsp:forward page="in.jsp"/>
          <%}
      }
}
    %>
  <%  
     
    synchronized (application)
           {
            Vector UserName=null;  
            UserName= (Vector)application.getAttribute("UserName");
            if(UserName==null)
                      {
		      UserName= new Vector(30,10);   		     
		      }
            else if(regname!=null )
	    {
	      UserName.addElement(regname);
	      regname=new String(regname.getBytes("iso-8859-1"),"GBK");  
              session.putValue("Name", regname); 
	      }

            else if( UserName.contains(Name))            
                {
                 session.putValue("confirm_message","<center><font color=red><b>错误信息：</b></font><br><font color=blue><b>[你的名字已经被别人用了，请换名，谢谢！]</b></font></center>");
                    %>
                   <jsp:forward page="in.jsp"/>
                    <% 
                }  
            else 		     
           {
	    UserName.addElement(Name);
            Name=new String(Name.getBytes("iso-8859-1"),"GBK");  
            session.putValue("Name", Name); 
	    }
           application.setAttribute("UserName",UserName);
	           
           %>
              <script language="JavaScript">
	      window.location="netchat1.jsp"
              </script>   
            <% 
    }%>
 
</body>
</html>
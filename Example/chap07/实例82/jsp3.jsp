<HTML>
<HEAD>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=gb2312" %> 
<META http-equiv="refresh" content="8">
<script language="JavaScript">
 function PerformSubmit(user) 
 {
        parent.inputFrame.Jsp2Form.talkwith.value=user;
	return true;
  }
</script>
<TITLE>
Listuser
</TITLE>
<link rel="stylesheet" href="a.css">
</HEAD>
<BODY bgcolor="#CCCCFF" >
<%@ page import="java.util.*" %> <%@ page import="java.io.*" %> <%@ page import="javax.servlet.*" %> 
<%@ page import="javax.servlet.http.*" %> 
<FORM method="post" name="userlist_form" >
<font color=blue>在线人员列表:</font><br><br>
<font color=#669900><a href="jsp3.jsp" onClick=PerformSubmit("大家")>[所有人]</a></font><br>
 <% 
   synchronized(application){
        Vector ListUser=null;
        ListUser =(Vector)application.getAttribute("UserName");
        if(ListUser !=null)
            for(int i=0;i<ListUser.size();i++)
             {
              String User= (String)ListUser.get(i);
	       User=new String(User.getBytes("iso-8859-1"),"GBK");
	      %>
             <a href="jsp3.jsp" onClick=PerformSubmit("<%= User%>") ><font color=#ff6633>
               <% 
                 out.println("["+User+"]");
                
                  %>
             </font></a>
        <% }
          else
          out.println(new String("Welcome to here!!"));
         } %>

   
</FORM>
</BODY>
</HTML>

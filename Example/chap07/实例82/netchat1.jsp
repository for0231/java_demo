<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<%@ page session="true" %>
<%@ page contentType="text/html;charset=gb2312" %> 
</head>

<frameset rows="400*,*" frameborder="NO" border="0" framespacing="0" cols="*" > 
  <frameset cols="94,689*" frameborder="NO" border="0" framespacing="0" rows="*" > 
   <frame name="userlistFrame"   src="jsp3.jsp">
    <frameset cols="*,0" frameborder="NO" border="0" framespacing="0"> 
      <frameset rows="80,*" frameborder="NO" border="0" framespacing="0"> 
        <frame name="topFrame" scrolling="NO" noresize src="top.jsp" >
       <frame name="mainFrame"  src="jsp4.jsp">
      </frameset>
       <frame name="hiddenFrame"  src="jsp5.jsp">
    </frameset>
  </frameset>
 <frame name="inputFrame"  src="jsp2.jsp?username=<%= session.getValue("Name") %>">
</frameset>
<noframes><body bgcolor="#FFFFFF" onUnload="logout.jsp">

</body></noframes>
</html>

<HTML>
<HEAD>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=gb2312" %> 
<script language="JavaScript">
function NameGotFocus() {
        document.LoginForm.username.focus();
	}
</script>
<TITLE>欢迎进入聊天室</TITLE>
<link rel="stylesheet" href="a.css">
</HEAD>
<BODY onload="NameGotFocus()" bgcolor="#CEFFCE">
<% String getMessage=(String)session.getValue("confirm_message");
     if (getMessage==null)
             getMessage="";%> 
<table border="0" width="80%" align="center" bgcolor="#CCFFCC">
  <tr> 
    <td width="1%"> 
      <div align="center"></div>
    </td>
    <td width="52%" bgcolor="#CCFFCC"><img src="images/bt.gif" width="314" height="62"> 
    </td>
    <td width="47%">
      <p>&nbsp;</p>
      <p>注意事项：</p>
      </td>
  </tr>
  <tr>
    <td width="1%">&nbsp;</td>
    <td width="52%" bgcolor="#CCFFCC"> 
      <form method="post" name="LoginForm" action="confirm.jsp">
        <%= getMessage%> 
        <div align="center"> 
          <p><br>
            <font size="4"><b>用　　　户</b></font><br>
            用户名 
            <input type="text" name="regName" size="13">
            <br>
            密　码 
            <input type="password" name="regPassword" size="13">
            <br>
            <br>
            <b>游　　　客</b><br>
            昵　称 
            <input type="text" name="username" size="13">
            <br>
            <br>
            <input type="submit" name="Submit" value="进入" >
            <input type="RESET" value="取消" name="RESET">
          </p>
          </div>
      </form>
    </td>
    <td width="47%"> 
      <ul>
        <li>遵守中华人民共和国各项法律法规的要求<br>
          <br>
        </li>
        <li>严禁谈论有损国家利益、安全等方面的内容 <br>
          <br>
        </li>
        <li>保持良好的休闲氛围，恶意捣乱者将被踢出 <br>
          <br>
        </li>
        <li>言谈举止要文明，不讲脏话、粗话 <br>
          <br>
        </li>
        <li>不得对他人进行人身攻击、侮辱、诽谤 <br>
          <br>
        </li>
        <li>不得谈论有关淫秽、色情等方面的内容 </li>
      </ul>
    </td>
  </tr>
</table>
</BODY>
</HTML>

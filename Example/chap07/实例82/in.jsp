<HTML>
<HEAD>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=gb2312" %> 
<script language="JavaScript">
function NameGotFocus() {
        document.LoginForm.username.focus();
	}
</script>
<TITLE>��ӭ����������</TITLE>
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
      <p>ע�����</p>
      </td>
  </tr>
  <tr>
    <td width="1%">&nbsp;</td>
    <td width="52%" bgcolor="#CCFFCC"> 
      <form method="post" name="LoginForm" action="confirm.jsp">
        <%= getMessage%> 
        <div align="center"> 
          <p><br>
            <font size="4"><b>�á�������</b></font><br>
            �û��� 
            <input type="text" name="regName" size="13">
            <br>
            �ܡ��� 
            <input type="password" name="regPassword" size="13">
            <br>
            <br>
            <b>�Ρ�������</b><br>
            �ǡ��� 
            <input type="text" name="username" size="13">
            <br>
            <br>
            <input type="submit" name="Submit" value="����" >
            <input type="RESET" value="ȡ��" name="RESET">
          </p>
          </div>
      </form>
    </td>
    <td width="47%"> 
      <ul>
        <li>�����л����񹲺͹�����ɷ����Ҫ��<br>
          <br>
        </li>
        <li>�Ͻ�̸������������桢��ȫ�ȷ�������� <br>
          <br>
        </li>
        <li>�������õ����з�Χ�����⵷���߽����߳� <br>
          <br>
        </li>
        <li>��̸��ֹҪ�����������໰���ֻ� <br>
          <br>
        </li>
        <li>���ö����˽��������������衢�̰� <br>
          <br>
        </li>
        <li>����̸���й����ࡢɫ��ȷ�������� </li>
      </ul>
    </td>
  </tr>
</table>
</BODY>
</HTML>

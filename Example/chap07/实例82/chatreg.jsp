<HTML>
<HEAD>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=gb2312" %> 
<script language="javascript">
function validate_form() {
  validity = true;
  if (!check_empty(document.regForm.username.value))
        {
	validity = false; alert('�Բ��� �� ���ֳ��ȱ�����(3--8) ��'); 
	}
  else  if((document.regForm.password.value.length==0)&&( document.regForm.cofpassword.value.length==0))
       {
        validity = false; alert('�Բ��� ������������ ��'); 
        }
  else  if(!passwordcof(document.regForm.password.value  ,document.regForm.cofpassword.value))
       {
        validity = false; alert('�Բ��� �� ������������벻һ�� ��'); 
        }
  else if (!check_email(document.regForm.useremail.value))
        {
	validity = false; alert('�Բ��� ����������ȷ����Email��ַ ��');
	}
  
  return validity; 
}

function passwordcof(text1 ,text2)
{
  return(text1==text2);
}
function check_empty(text) {
  return ( (text.length>1) &&(text.length<9) ); 
}

function check_email(address) {
  if ((address == "")
    || (address.indexOf ('@') == -1)
    || (address.indexOf ('.') == -1))
      return false;
  return true;
}
</script>
<TITLE>
register
</TITLE>
</HEAD>
<body bgcolor="ffffcc" >
<form   name="regForm" method="post"  action="chatregcof.jsp"  onSubmit="return validate_form()">
  <p>&nbsp;</p>
  <p>&nbsp;</p>
  <table width="66%" border="0" cellpadding="5" align="center" >
    <tr>
      <td width="23%" height="26">&nbsp;</td>
      <td width="77%" height="26"> ���<font color="#FF0033">**</font>Ϊ������</td>
    </tr>
    <tr> 
      <td width="23%" height="26"><font color="#FF3300">**</font>�����ʺ�:</td>
      <td width="77%" height="26"> 
        <input type="text" name="username">
      [����ֻ����2-8֮��] </td>
    </tr>
    <tr> 
      <td width="23%" height="37"><font color="#FF0000">**</font>����:</td>
      <td width="77%" height="37"> 
        <input type="password" name="password">
      </td>
    </tr>
    <tr> 
      <td width="23%" height="8"><font color="#FF0033">**</font>ȷ������:</td>
      <td width="77%" height="8"> 
        <input type="password" name="cofpassword">
      </td>
    </tr>
    <tr> 
      <td width="23%"><font color="#FF0000">**</font>email:</td>
      <td width="77%"> 
        <input type="text" name="useremail">[��ʽ��xuetaomei@263.net]
      </td>
    </tr>
    <tr> 
      <td width="23%" height="38">��ҳ��ַ:</td>
      <td width="77%" height="38"> 
        <input type="text" name="homepage" value="http://">
      </td>
    </tr>
    <tr> 
      <td width="23%" height="38">&nbsp; </td>
      <td width="77%" height="38"> 
        <input type="submit" name="Submit" value="�����">
        <input type="reset" name="Submit2" value="����ˣ�">
      </td>
    </tr>
  </table>
  
</form>

</body>
</html>
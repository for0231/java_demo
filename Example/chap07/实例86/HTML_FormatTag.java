import java.io.IOException;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
public class HTML_FormatTag extends BodyTagSupport
{
// �ڱ�ǩĩ�������������
public int doEndTag() throws JspTagException
{ 
try
{ //�õ���ǩ�е��ı� 
BodyContent l_tagbody = getBodyContent();
String ls_output = "";
// �����ǩ�����ı����ʹ����� 
if(l_tagbody != null)
{ 
HTML_Format l_format = new HTML_Format();
// ����ǩ�������ת��Ϊһ���ַ���
String ls_html_text = l_tagbody.getString();
ls_output = l_format.HTML_Encode(ls_html_text);
}
// �����д�ص���������
pageContext.getOut().write(ls_output.trim());
}
catch (IOException e)
{ 
 throw new JspTagException("Tag Error:" + e.toString());
}
// ��JSP������������ҳ������� 
return EVAL_PAGE;
}
}

import java.io.IOException;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
public class HTML_FormatTag extends BodyTagSupport
{
// 在标签末将会调用这个函�
public int doEndTag() throws JspTagException
{ 
try
{ //得到标签中的文本 
BodyContent l_tagbody = getBodyContent();
String ls_output = "";
// 如果标签体有文本，就处理它 
if(l_tagbody != null)
{ 
HTML_Format l_format = new HTML_Format();
// 将标签体的内容转换为一个字符串
String ls_html_text = l_tagbody.getString();
ls_output = l_format.HTML_Encode(ls_html_text);
}
// 将结果写回到数据流中
pageContext.getOut().write(ls_output.trim());
}
catch (IOException e)
{ 
 throw new JspTagException("Tag Error:" + e.toString());
}
// 让JSP继续处理以下页面的内容 
return EVAL_PAGE;
}
}

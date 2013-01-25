import java.io.IOException;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
public class HTML_FormatTag extends BodyTagSupport
{
// ÔÚ±êÇ©Ä©½«»áµ÷ÓÃÕâ¸öº¯Ê
public int doEndTag() throws JspTagException
{ 
try
{ //µÃµ½±êÇ©ÖĞµÄÎÄ±¾ 
BodyContent l_tagbody = getBodyContent();
String ls_output = "";
// Èç¹û±êÇ©ÌåÓĞÎÄ±¾£¬¾Í´¦ÀíËü 
if(l_tagbody != null)
{ 
HTML_Format l_format = new HTML_Format();
// ½«±êÇ©ÌåµÄÄÚÈİ×ª»»ÎªÒ»¸ö×Ö·û´®
String ls_html_text = l_tagbody.getString();
ls_output = l_format.HTML_Encode(ls_html_text);
}
// ½«½á¹ûĞ´»Øµ½Êı¾İÁ÷ÖĞ
pageContext.getOut().write(ls_output.trim());
}
catch (IOException e)
{ 
 throw new JspTagException("Tag Error:" + e.toString());
}
// ÈÃJSP¼ÌĞø´¦ÀíÒÔÏÂÒ³ÃæµÄÄÚÈİ 
return EVAL_PAGE;
}
}

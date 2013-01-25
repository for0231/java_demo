public class HTML_Format extends Object implements java.io.Serializable {
// 创建新的HTML_Format 
public HTML_Format(){}
// 将一个字符串中所有的所有 ＜ 和 ＞ 字符用响应的HTML编码代替 
public String HTML_Encode(String as_data)
{
int li_len = as_data.length();
//string buffer的长度要比原来的字符串长
StringBuffer lsb_encode = new StringBuffer(li_len + (li_len/10));
// 循环替换全部的＜ 和 ＞ 字符 
for(int li_count = 0 ; li_count < li_len ; li_count++)
{ String ls_next = String.valueOf(as_data.charAt(li_count));
if (ls_next.equals("<")) ls_next = "<";
if (ls_next.equals(">")) ls_next = ">";
// 将编码加到string buffer 中
lsb_encode.append(ls_next);
}
return(lsb_encode.toString() );
}
}

public class HTML_Format extends Object implements java.io.Serializable {
// �����µ�HTML_Format 
public HTML_Format(){}
// ��һ���ַ��������е����� �� �� �� �ַ�����Ӧ��HTML������� 
public String HTML_Encode(String as_data)
{
int li_len = as_data.length();
//string buffer�ĳ���Ҫ��ԭ�����ַ�����
StringBuffer lsb_encode = new StringBuffer(li_len + (li_len/10));
// ѭ���滻ȫ���ģ� �� �� �ַ� 
for(int li_count = 0 ; li_count < li_len ; li_count++)
{ String ls_next = String.valueOf(as_data.charAt(li_count));
if (ls_next.equals("<")) ls_next = "<";
if (ls_next.equals(">")) ls_next = ">";
// ������ӵ�string buffer ��
lsb_encode.append(ls_next);
}
return(lsb_encode.toString() );
}
}

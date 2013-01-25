public class TestStringBuffer
{
  public static void main(String[] args)
  {
    StringBuffer str = new StringBuffer("The substring begins at the specified beginIndex.");
    StringBuffer str1 = new StringBuffer("string");
    String str2 = new String();
    int size = str.length();
    int flag = str.indexOf("substring");
    str2 = str.substring(flag, flag+19);
    StringBuffer strOut = new StringBuffer("字符串");
    strOut.append(str);
    strOut.append("总长度为: ");
    strOut.append(size);
    int f = strOut.indexOf("总");
    strOut.insert(f, '\n');
    System.out.println(strOut.toString());
    if (str1.toString().equals(str2)) 
      System.out.println("截取的字符串为: " + str1.toString());
    else
      System.out.println("截取的字符串为: " + str2);
  }
}

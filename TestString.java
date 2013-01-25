public class TestString
{
  public static void main(String[] args)
  {
    String str = new String("The substring begins at the specified beginIndex.");
    String str1 = new String("string");
    String str2 = new String();
    int size =str.length();
    int flag = str.indexOf("substring");
    str2 = str.substring(flag, flag+19);
    System.out.println("字符串" + str + "\n总长度为: " + size);
    if (str1.equals(str2)) 
      System.out.println("截取的字符串为: " + str1);
    else 
      System.out.println("截取的字符串为: " + str2);
  }
}

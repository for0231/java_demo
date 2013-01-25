import java.util.*;
public class UseToken
{
  public static void main(String[] args)
  {
    String str = "数学::英语::语文::化学";
    StringTokenizer st = new StringTokenizer(str, "::");
    System.out.println(str + "\n课程数为: " + st.countTokens());
    while (st.hasMoreTokens()) 
    {
      System.out.println(st.nextToken("::"));
    }
    str = "Hello this is a test";
    st = new StringTokenizer(str);
    System.out.println(str + "\n单词数为: " + st.countTokens());
    while (st.hasMoreTokens()) 
    {
      System.out.println(st.nextToken());
    }
  }
}

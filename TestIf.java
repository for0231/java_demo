public class TestIf
{
  // 声明全局变量x
  static int x;
  public static void main(String args[])
  {
    x = 12;
    if (x>10)
    {
      System.out.println("x = "+x+"结果正确");
    }
    else 
    {
      System.out.println("x = 10" + "结果不正确");
    }
    change();
    System.out.println("修改x的值之后");
    if (x > 10) 
    {
      System.out.println("x = " + x + "结果正确");
    }
    else
    {
      System.out.println("x = 10" + "结果不正确");
    }
  }
  public static void change()
  {
    x = 5;
  }
}

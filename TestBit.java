public class TestBit
{
  public static  void main(String args[])
  {
    int a = 36;
    int b = 2;
    // 定义结果变量
    int r1,r2;
    // 计算结果
    r1 = a >> b;
    r2 = a << b;

    // 输出结果
    System.out.println("a =" +a+" b= "+b);
    System.out.println("a>>b= "+r1);
    System.out.println("a<<b= "+r2);
  }
}

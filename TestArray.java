public class TestArray
{
  public static void main(String args[])
  {
    // 声明数组
    int a[];
    char b[];

    // 创建数组
    a = new int[3];
    b = new char[2];
    // 数组初始化
    for (int i=0; i<3; i++)
    {
      a[i] = i * 3;
    }
    b[0] = 'a';
    b[1] = 'b';

    // 快速初始化数组
    int c[] = {0,1*3, 2*3};

    // 输出结果 
    System.out.println("数组a\n");
    for (int i = 0; i < 2; i++)
    {
      System.out.print(b[i] + " ");
    }
    System.out.print("\n数组c\n");
    for (int i=0; i<3; i++)
    {
      System.out.print(c[i] + " ");
    }
    System.out.println("\n");
  }
}

public class SetVariable
{
  // 全局变量
  static double pi=3.141592654;
  static short s1;
  static int   i1;
  static long  l1;
  static char  ch1;
  static float f1;
  static double d1;
  static boolean b1;
  public static void main(String args[])
  {
    // 局部变量
    short s2 = 35;
    int   i2 = -32;
    long  l2 = 34555L;
    char  ch2 = 'A';
    float f2 = 897.89F;
    double d2 = 34.345;
    boolean b2 = false;
    // 输出变量
    System.out.println("数学常量pi ="+pi);
    // 输出局部变量
    System.out.println("*********局部变量*********");
    System.out.println("短整型变量s2 ="+s2);
    System.out.println("整型变量i2 ="+i2);
    System.out.println("长整型变量l2 ="+l2);
    System.out.println("字符变量ch2 ="+ch2);
    System.out.println("浮点数类型f2 ="+f2);
    System.out.println("双精度型变量d2 ="+d2);
    System.out.println("布尔型变量b2 ="+b2);

    // 调用方法修改全局变量的值
    change();
    // 输出全局变量的值
    System.out.println("*********全局变量************");
    System.out.println("短整型变量s1 ="+s1);
    System.out.println("整型变量i1 ="+i1);
    System.out.println("长整型变量l1 ="+l1);
    System.out.println("字符型变量ch1 ="+ch1);
    System.out.println("浮点数类型f1 ="+f1);
    System.out.println("双精度型变量d1 ="+d1);
    System.out.println("布尔型变量b1 ="+b1);
  }
  public static void change()
  {
    s1 = 125;
    i1 = 88;
    l1 = 9876544321L;
    ch1= 'B';
    f1 = 3.2590F;
    d1 = -1.04E-5;
    b1 = true;
  }
}

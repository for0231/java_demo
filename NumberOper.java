public class NumberOper
{
  public static void main(String args[])
  {
    // 变量初始化
    int a = 30;
    int b = 20;
    // 定义结果变量
    int r1,r2,r3,r4,r5,r6,r7,r8,r9;
    // 计算结果
    r1 = a + b;
    r2 = a - b;
    r3 = a * b;
    r4 = a / b;
    r5 = a % b;
    r6 = a ++;
    r7 = b --;
    r8 = ++ a;
    r9 = --b;
    // 输出结果
    System.out.println("a = " +a+" b =" +b); // a,b的值
    System.out.println("a+b = " +r1);
    System.out.println("a-b = " + r2);
    System.out.println("a*b = " + r3);
    System.out.println("a/b = " + r4);
    System.out.println("a%b = " + r5);
    System.out.println("a++ = " + r6);
    System.out.println("b-- = " + r7);
    System.out.println("++a = " + r8);
    System.out.println("--b = " + r9);
  }
}

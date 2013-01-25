public class SetApple
{
  public static void main(String[] args)
  {
    apple a = new apple();
    a.appleweight = 0.5;
    System.out.println("苹果的重量为1两");
    System.out.println(a.bite());
    a.appleweight = 5;
    System.out.println("苹果的重量为5两");
    System.out.println(a.bite());
  }
}
class apple
{
  long applecolor;
  double appleweight;
  boolean eatup;
  // 类方法
  public boolean bite()
  {
    if (appleweight < 1)
    {
      System.out.println("苹果已经吃完了!");
      eatup = true;
    }
    else 
    {
      System.out.println("苹果吃不下了!");
      eatup = false;
    }

    return eatup;
  }
}

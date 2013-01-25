public class UseInterface
{
  public static void main(String[] args)
  {
    Computer p = new Computer();
    System.out.print(p.ADDRESS + p.MAKER);
    System.out.println("计算机的价格: " + p.getPrice() + "万元");
  }
}

class Computer implements Product
{
  public int getPrice()
  {
    return 1;
  }
}

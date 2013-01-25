import java.util.Date;
public class UseDate
{
  public static void main(String[] args)
  {
    Date dOld = new Date();
    long lOld = dOld.getTime();
    System.out.println("循环前系统时间为: " + dOld.toString());
    int sum = 0;
    for ( int i=0; i< 100; i++)
    {
      sum += i;
    }
    Date dNew = new Date();
    long lNew = dNew.getTime();
    System.out.println("循环后系统时间为: " +dNew.toString());
    System.out.println("循环花费的毫秒数为:　" + (lNew - lOld));
  }
}

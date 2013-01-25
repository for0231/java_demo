import java.util.Vector;
public class UseVector
{
  public static void main(String[] args)
  {
    Vector vScore = new Vector();
    vScore.add("86");
    vScore.add("98");
    vScore.add(1, "99");

    for(int i=0; i < vScore.size(); i++)
    {
      System.out.println(vScore.get(i) + " ");
    }
    vScore.set(1, "77");
    vScore.remove(0);
    System.out.println("\n修改并删除之后");
    for(int i=0; i<vScore.size(); i++)
    {
      System.out.print(vScore.get(i)+" ");
    }
    System.out.println("\n转换成字符串之后的输出\n" + vScore.toString());
  }
}

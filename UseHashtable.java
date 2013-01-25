import java.util.Hashtable;
public class UseHashtable
{
  public static void main(String[] args)
  {
    Hashtable hScore = new Hashtable();
    hScore.put("张一", "86");
    hScore.put("李二", "98");
    hScore.put("海飞", "99");
    System.out.println("转换成字符串之后的输出: " + hScore.toString());
    hScore.put("李二", "77");
    hScore.remove("张一");
    System.out.println("修改并删除之后");
    System.out.println("转换成字符串之后的输出: " + hScore.toString());
  }
}

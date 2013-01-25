import java.util.*;
public class UseEnumeration
{
  public static void main(String[] args)
  {
    Vector vScore = new Vector();
    vScore.add("86");
    vScore.add("98");
    vScore.add(1, "99");
    System.out.println("Vector: " + vScore.toString());
    for (Enumeration e = vScore.elements(); e.hasMoreElements(); ) 
    {
      System.out.println(e.nextElement());
    }
    Hashtable hScore = new Hashtable();
    hScore.put("张一", "86");
    hScore.put("李二", "98");
    hScore.put("海飞", "99");
    System.out.println("Hashtable: " + hScore.toString());
    for ( Enumeration e = hScore.keys(); e.HasMoreElements(); ) 
    {
      String str = (String) e.nextElement();
      System.out.println(str + ": ");
      System.out.println(hScore.get(str));
    }
  }
}

import java.net.*;
public class FindHost
{
  public static void main(String[] args)
  {
    try
    {
      InetAddress h = InetAddress.getLocalHost();
      System.out.println("toString(): " + h.toString());
      System.out.println("getHostName(): " + h.getHostName());
      System.out.println("getHostAddress(): " + h.getHostAddress());
      h = InetAddress.getByName("engine");
      System.out.println(h.getHostName() + ":" + h.getHostAddress());
    }
    catch(UnknownHostException e)
    {
      System.out.println(e.getMessage());
    }
  }
}

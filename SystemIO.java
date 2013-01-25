import java.io.*;
public class SystemIO
{
  public static void main(String[] args)
  {
    int bytes = 0;
    byte buf[] = new byte[255];
    System.out.println("\n请输入任意文本: ");
    try
    {
      // 接收输入字符串
      bytes = System.in.read(buf, 0, 255);

      System.out.println("这是你输入的文本行: ");
      String inStr = new String(buf, 0, bytes);
      // 输出字符串
      System.out.println(inStr);
    }
    catch (IOException e)
    {
      System.out.println(e.getMessage());
    }
  }
}

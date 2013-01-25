import java.io.*;
public class UseFileInputStream
{
  public static void main(String[] args)
  {
    byte buf[] = new byte[2056];
    try
    {
      // 构造文件输入流
      FileInputStream fileIn = new FileInputStream("UseFileInputStream.java");
      // 存入缓冲buf
      int bytes = fileIn.read(buf, 0, 2056);
      String inStr = new String(buf, 0, bytes);
      // 输出文件内容
      System.out.println(inStr);
    }
    catch(IOException e)
    {
      System.out.println(e.getMessage());
    }
  }
}

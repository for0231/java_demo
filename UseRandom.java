import java.io.*;
public class UseRandom
{
  public static void main(String[] args)
  {
    try
    {
      // 构建随机访问文件对象
      RandomAccessFile f = new RandomAccessFile("Hello.tx", "rw");
      // 得到文件指针和长度　
      long flag = 0;
      long len=f.length();
      // 字符处理后输出
      while (flag < len)
      {
        String s = f.readLine();
        System.out.println(parseChinese(s));
        flag = f.getFilePointer();
      }
      // 末尾写入字符
      f.writeChar('O');
      f.writeChar('K');
    }
    catch (IOException e)
    {
      System.out.println(e.getMessage());
    }
  }

  // 解决中文转换问题
  public static String parseChinese(String inStr)
  {
    String s = null;
    byte temp[];
    if (inStr == null)
    {
      return new String("");
    }
    try
    {
      temp = inStr.getBytes("iso-8859-1");
      s = new String(temp);
    }
    catch (UnsupportedEncodingException e)
    {
      System.out.println(e.toString());
    }
    return s;
  }
}

import java.io.*;
public class UseFile
{
  public static void main(String[] args)
  {
    try
    {
      File f = new File("temp.txt");
      System.out.println("创建临时文件");
      FileOutputStream fout = new FileOutputStream(f);
      PrintStream p = new PrintStream(fout);
      p.println("将这句话放入临时文件");
      System.out.println("写临时文件");
      f.deleteOnExit();
      System.out.println("删除临时文件");
    }
    catch (IOException e)
    {
      System.out.println(e.getMessage());
    }
  }
}

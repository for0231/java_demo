import java.io.*;
import java.util.*;
public class UseStream extends Object
{
  public static void main(String[] args)
  {
    // 构建Vector对象
    Vector v = new Vector();
    v.add(0,"语文");
    v.add(1,"数学");
    v.add(2,"物理");
    try
    {
      // 文件处理对象
      File f = new File("temp.txt");
      FileOutputStream fOut = new FileOutputStream(f);
      ObjectOutputStream objOut = new ObjectOutputStream(fOut);
      // 写入日期对象
      objOut.writeObject(new Date());
      // 写入Vector对象
      objOut.writeObject(v);
      objOut.close();
      // 构建readObj的实例
      readObj rObj = new readObj();

      // 调用方法输出
      rObj.readO();
    }
    catch (IOException e)
    {
      System.out.println(e.getMessage());
    }
  }
}
class readObj extends Object
{
  public void readO()
  {
    try
    {
      File f = new File("temp.txt");
      FileInputStream fIn = new FileInputStream(f);
      ObjectInputStream objIn = new ObjectInputStream(fIn);
      // 读取对象输出
      Object ob1 = objIn.readObject();
      System.out.println(ob1);
      Object ob2 = objIn.readObject();
      System.out.println(ob2);
    }
    catch(IOException e)
    {
      System.out.println(e.getMessage());
    }
    catch (ClassNotFoundException e)
    {
      System.out.println(e.getMessage());
    }
  }
}

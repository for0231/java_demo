import java.io.*;
import java.net.*;
class SSClient
{
  public static void main (String [] args){
    String host = "localhost";
    // 从命令行参数获得localhost名
    if (args.length == 1)
      host = args [0];
    BufferedReader br = null;
    PrintWriter pw = null;
    Socket s = null;
    try{
          // 创建一个可以连接到server的套接字
          // 设定端口10000
          s = new Socket (host, 10000);
          // 创建一个输入流reader。输入流reader把从套接字中读出的字节转换成字符，
          // 这个转换是基于平台默认字符集之上的
          InputStreamReader isr;
          isr = new InputStreamReader (s.getInputStream ());
          // 创建一个 buffered reader，它提供了读取整行文本的方便方法
          br = new BufferedReader (isr);
          // 创建一个 print writer 用来把发送给套接字的字符转换成字节
          pw = new PrintWriter (s.getOutputStream (), true);
          // 发送DATE 命令到server.
          pw.println ("DATE");
          // 获取并打印当前的日期和时间
          System.out.println (br.readLine ());
          // 发送PAUSE 命令到server.
          pw.println ("PAUSE");
          // 发送DOW命令到server.
          pw.println ("DOW");
          // 获取并打印当前是星期几.
          System.out.println (br.readLine ());
          //发送DOW命令到server.
          pw.println ("DOM");
          // 获取并打印当前月份
          System.out.println (br.readLine ());
          // 发送DOY命令到server.
          pw.println ("DOY");
          // 获取并打印当前年份
          System.out.println (br.readLine ());
        }catch (IOException e){
          System.out.println (e.toString ());
        }
        finally{
          try{
            if (br != null)
              br.close ();
            if (pw != null)
              pw.close ();
            if (s != null)
              s.close ();
          }catch (IOException e){
          }
        }
      }
}
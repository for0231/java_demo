import java.io.*;
import java.net.*;

public class ReadClient
{
  public static void main(String args[])
  {
    try
    {
      // 初始化Socket对象
      Socket clientSocket = new Socket("www.xjtu.edu.cn", 80);
      System.out.println("Client1: " + clientSocket);
      // 初始化对象流
      DataOutputStream outbound = new DataOutputStream(
        clientSocket.getOutputStream()
      );
      DataInputStream inbound = new DataInputStream(clientSocket.getInputStream());
      InputStreamReader inS = new InputStreamReader(inbound);
      File f = new File("xjtu.html");
      FileOutputStream fOut = new FileOutputStream(f);
      PrintStream p = new PrintStream(fOut);
      outbound.writeBytes("GET/HTTP/1.0\r\n\r\n");
      // 写入文件
      int c;
      while ((c = inS.read()) != -1)
      {
        p.print((char)c);
      } 
      // 关闭流
      inS.close();
      outbound.close();
      inbound.close();
      clientSocket.close();
    }
    catch(UnknownHostException uhe)
    {
      System.out.println("UnknownHostException: " + uhe);
    }
    catch(IOException ioe)
    {
      System.err.println("IOException: " + ioe);
    }
  }
}

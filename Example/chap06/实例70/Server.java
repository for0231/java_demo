import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
public class Server
{
     public static void main(String[] args)
       {
          ServerService MyServer=new ServerService(6544,10);
       }
}
 class ServiceThread extends Frame implements Runnable
{//当CLIENT有请求时 SERVER端创建一个FRAME用于与之交互数据
      ServerService FatherListener;
      Socket ConnectedClient;
      Thread ConnectThread;
      Panel ListenerPanel;
      TextArea ServerMeg;
      public ServiceThread(ServerService sv,Socket s)//构造函数
      {
           FatherListener=sv;
           ConnectedClient=s;
           ConnectThread=new Thread(this);
           setTitle("Server(服务器端)");
           setLayout(new BorderLayout());
           ServerMeg=new TextArea(13,50);
           add("Center",ServerMeg);
           setResizable(false);
           pack();
           show();
           InetAddress ClientAddress=ConnectedClient.getInetAddress();
                    //获得请求服务的CLIENT端计算机的IP地址
           ServerMeg.appendText("Server connect"+" to: \n\n"+ClientAddress.toString()+".\n");
       }
       
      public void run()
       {
         try{
  DataInputStream in=new DataInputStream(//获得从CILENT读入的数据流
          new BufferedInputStream(ConnectedClient.getInputStream()));
  PrintStream out=new PrintStream(//获得向CILENT输出的数据流
          new BufferedOutputStream(ConnectedClient.getOutputStream()));
      
        out.println("  Hello!Welcome connect to me(Server)!\r");
        out.flush();//向CLIENT端输出信息
        String s=in.readLine();//从CLIENT端读入信息
        while(!s.equals("Bye"))
         {
            ServerMeg.appendText("client端输入的信息为：    \n"+s);
             s=in.readLine();//读入CLIENT端写入的下一行信息
          }
           ConnectedClient.close();
        }
       catch(Exception e){}
       FatherListener.addMeg("Client"+"closed."+"\n");
        dispose();
     }//run()
 }

//*********************************************************************************************//
class ServerService extends Frame //服务器端的监听窗口
 {
     ServerSocket m_sListener;    //监听器
     TextArea ListenerMeg;        //显示信息的监听器窗口
     public ServerService(int Port,int Count)
     {
        try{
             m_sListener=new ServerSocket(6544,10);//建立监听服务
             setTitle("Server Listener(监听器窗口)");  //建立监听服务的窗口并显示
             this.addWindowListener(new WinAdpt());
             setLayout(new BorderLayout());
             ListenerMeg=new TextArea("      [监听服务已启动啦]\n\n\n",10,50);
             add("Center",ListenerMeg);
             setResizable(false);
             pack();
             show();
            while(true)
           {
       Socket Connected=m_sListener.accept();//接受来自Client端的请求
          InetAddress ClientAddress=Connected.getInetAddress();

ListenerMeg.appendText("Client "+" connected "+" from:\n\n"+ClientAddress.toString()+" .\n");
//获得客户机的IP地址 建立新线程新窗口与这个CLIENT进行通信
        ServiceThread MyST=new ServiceThread(this,Connected);
         MyST.ConnectThread.start();
            }
         } 
          
      catch(IOException e){}//异常处理方法

       }
             
         public void addMeg(String s)
          {
             ListenerMeg.appendText(s);
          }
   }//ServerService Class
       
         class WinAdpt extends WindowAdapter
      {
          public void windowClosing(WindowEvent e)
           {
              ((Frame)e.getWindow()).dispose();
                  System.exit(0);
            }
       }
          
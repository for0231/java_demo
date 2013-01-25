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
{//��CLIENT������ʱ SERVER�˴���һ��FRAME������֮��������
      ServerService FatherListener;
      Socket ConnectedClient;
      Thread ConnectThread;
      Panel ListenerPanel;
      TextArea ServerMeg;
      public ServiceThread(ServerService sv,Socket s)//���캯��
      {
           FatherListener=sv;
           ConnectedClient=s;
           ConnectThread=new Thread(this);
           setTitle("Server(��������)");
           setLayout(new BorderLayout());
           ServerMeg=new TextArea(13,50);
           add("Center",ServerMeg);
           setResizable(false);
           pack();
           show();
           InetAddress ClientAddress=ConnectedClient.getInetAddress();
                    //�����������CLIENT�˼������IP��ַ
           ServerMeg.appendText("Server connect"+" to: \n\n"+ClientAddress.toString()+".\n");
       }
       
      public void run()
       {
         try{
  DataInputStream in=new DataInputStream(//��ô�CILENT�����������
          new BufferedInputStream(ConnectedClient.getInputStream()));
  PrintStream out=new PrintStream(//�����CILENT�����������
          new BufferedOutputStream(ConnectedClient.getOutputStream()));
      
        out.println("  Hello!Welcome connect to me(Server)!\r");
        out.flush();//��CLIENT�������Ϣ
        String s=in.readLine();//��CLIENT�˶�����Ϣ
        while(!s.equals("Bye"))
         {
            ServerMeg.appendText("client���������ϢΪ��    \n"+s);
             s=in.readLine();//����CLIENT��д�����һ����Ϣ
          }
           ConnectedClient.close();
        }
       catch(Exception e){}
       FatherListener.addMeg("Client"+"closed."+"\n");
        dispose();
     }//run()
 }

//*********************************************************************************************//
class ServerService extends Frame //�������˵ļ�������
 {
     ServerSocket m_sListener;    //������
     TextArea ListenerMeg;        //��ʾ��Ϣ�ļ���������
     public ServerService(int Port,int Count)
     {
        try{
             m_sListener=new ServerSocket(6544,10);//������������
             setTitle("Server Listener(����������)");  //������������Ĵ��ڲ���ʾ
             this.addWindowListener(new WinAdpt());
             setLayout(new BorderLayout());
             ListenerMeg=new TextArea("      [����������������]\n\n\n",10,50);
             add("Center",ListenerMeg);
             setResizable(false);
             pack();
             show();
            while(true)
           {
       Socket Connected=m_sListener.accept();//��������Client�˵�����
          InetAddress ClientAddress=Connected.getInetAddress();

ListenerMeg.appendText("Client "+" connected "+" from:\n\n"+ClientAddress.toString()+" .\n");
//��ÿͻ�����IP��ַ �������߳��´��������CLIENT����ͨ��
        ServiceThread MyST=new ServiceThread(this,Connected);
         MyST.ConnectThread.start();
            }
         } 
          
      catch(IOException e){}//�쳣������

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
          
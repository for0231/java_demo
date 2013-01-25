
import java.net.*;
import java.io.*;
import java.util.*;

/**
 *  ��Server����Ϸ����˷������ࡣ
 **/
public class Server implements Runnable {
  private int port = 6564;     // �˿ں�
  private Hashtable idcon = new Hashtable();  // �洢�������û�������
  private int id = 0;
  static final String CRLF = "\r\n";
  
  /** ÿ�������û�����ʱ�����øú��� */
  synchronized void addConnection(Socket s) {
    ClientConnection con = new ClientConnection(this, s, id);
    
    id++;
  }
  
  /** ���ñ���������Ӧ�ͻ���set()����ɢ�б�idcon�е��������ӣ���ֹ
   *  �ظ����ܿͻ������֣�����setBusy()��ʾĳ�����ӿ��Կ�ʼ��Ϸ�ˡ�
   *  �����з�æ���ӣ�����add��Ϣ֪ͨ��Щ��Ϸ����������ӡ�
   **/
  synchronized void set(String the_id, ClientConnection con) {
    idcon.remove(the_id) ; 
    con.setBusy(false);
    Enumeration e = idcon.keys();
    while (e.hasMoreElements()) {
      String id = (String)e.nextElement();
      ClientConnection other = (ClientConnection) idcon.get(id);
      if (!other.isBusy())
        con.write("add " + other + CRLF);
    }
    idcon.put(the_id, con);
    broadcast(the_id, "add " + con);
  }
  
  /** ��Ӧto��Ϣ����body�ַ���������ֱ��д��dest��־������ */
  synchronized void sendto(String dest, String body) {
    ClientConnection con = (ClientConnection)idcon.get(dest);
    if (con != null) {
      con.write(body + CRLF);
    }
  }
  
  /** ��body�У������������еĳ�����exclude�б�ʶ�ĵ������ӷ���һ����Ϣ */
  synchronized void broadcast(String exclude, String body) {
    Enumeration e = idcon.keys();
    while (e.hasMoreElements()) {
      String id = (String)e.nextElement();
      if (!exclude.equals(id)) {
        ClientConnection con = (ClientConnection) idcon.get(id);
        con.write(body + CRLF);
      }
    }
  }
  
  /** ������֪ͨ�������ӵĿͻ���������������the_id ���ڿͻ��Ѿ���ʼ��Ϸ
   *  �����Լ��ӿ�ѡ����ս�߱���ɾ��ʱʹ�� 
   **/
  synchronized void delete(String the_id) {
     broadcast(the_id, "delete " + the_id);
  }
  
  /** ��һ���ͻ�����quit��Ϣ����ȷ�˳���Ϸ�����û����˳������ʱ
   *  ���ø÷��� 
   **/
  synchronized void kill(ClientConnection c) {
    if (idcon.remove(c.getId()) == c) {
      delete(c.getId());
    }
  }
  
  /** �������Ƿ���������ѭ�������ڶ˿�6564����һ�����׽��֣�Ȼ�����
   *  ����ѭ���ȴ��ͻ����׽�������
   **/
  public void run() {
    try {
      ServerSocket acceptSocket = new ServerSocket(port);
      System.out.println("Server listening on port " + port);
      while (true) {
        Socket s = acceptSocket.accept();
        addConnection(s);
      }
    } catch (IOException e) {
      System.out.println("accept loop IOException: " + e);
    }
  }
  
  /** main()��������һ��Serverʵ����Ȼ������һ���µ��߳������� */
  public static void main(String args[]) {
    new Thread(new Server()).start();
    try {
      Thread.currentThread().join();
    } catch (InterruptedException e) { }
  }
}
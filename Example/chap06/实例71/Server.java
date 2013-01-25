
import java.net.*;
import java.io.*;
import java.util.*;

/**
 *  类Server是游戏服务端方的主类。
 **/
public class Server implements Runnable {
  private int port = 6564;     // 端口号
  private Hashtable idcon = new Hashtable();  // 存储与所有用户的连接
  private int id = 0;
  static final String CRLF = "\r\n";
  
  /** 每当有新用户加入时，调用该函数 */
  synchronized void addConnection(Socket s) {
    ClientConnection con = new ClientConnection(this, s, id);
    
    id++;
  }
  
  /** 调用本函数来响应客户，set()跟踪散列表idcon中的所有连接，防止
   *  重复接受客户的名字，调用setBusy()表示某个连接可以开始游戏了。
   *  对所有非忙连接，发出add消息通知这些游戏者这个新连接。
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
  
  /** 响应to消息，将body字符串的内容直接写入dest标志的连接 */
  synchronized void sendto(String dest, String body) {
    ClientConnection con = (ClientConnection)idcon.get(dest);
    if (con != null) {
      con.write(body + CRLF);
    }
  }
  
  /** 在body中，本方法向所有的除了在exclude中标识的单个连接发送一个消息 */
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
  
  /** 本方法通知所有连接的客户忘掉曾经听过的the_id 。在客户已经开始游戏
   *  并将自己从可选的挑战者表中删除时使用 
   **/
  synchronized void delete(String the_id) {
     broadcast(the_id, "delete " + the_id);
  }
  
  /** 当一个客户发送quit消息，明确退出游戏，或当用户简单退出浏览器时
   *  调用该方法 
   **/
  synchronized void kill(ClientConnection c) {
    if (idcon.remove(c.getId()) == c) {
      delete(c.getId());
    }
  }
  
  /** 本方法是服务器的主循环，它在端口6564建立一个新套接字，然后进入
   *  无限循环等待客户的套接字连接
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
  
  /** main()方法创建一个Server实例，然后启动一个新的线程来运行 */
  public static void main(String args[]) {
    new Thread(new Server()).start();
    try {
      Thread.currentThread().join();
    } catch (InterruptedException e) { }
  }
}

import java.net.*;
import java.io.*;
import java.util.*;

/** 
 *  ClientConnection 是ServerConnection的镜像。它为每个客户创建一个
 *  连接。它的工作是管理与客户之间的I/O操作
 **/
class ClientConnection implements Runnable {
  private Socket sock;
  private BufferedReader in;
  private OutputStream out;
  private String host;
  private Server server;
  private static final String CRLF = "\r\n";
  private String name = null;    // for humans
  private String id;
  private boolean busy = false;
  
  /** 构造函数保存服务器的引用变量，套接字和唯一的ID。最后创建并
   *  启动一个新线程处理连接*/
  public ClientConnection(Server srv, Socket s, int i) {
    try {
      server = srv;
      sock = s;
      in = new BufferedReader(new InputStreamReader(s.getInputStream()));
      out = s.getOutputStream();
      host = s.getInetAddress().getHostName();
      id = "" + i;
      
      write("id " + id + CRLF);

      new Thread(this).start();
    } catch (IOException e) {
      System.out.println("failed ClientConnection " + e);
    }
  }
  
  /** 重载toString()函数，来清楚的表示一个连接记录 */
  public String toString() {
    return id + " " + host + " " + name;
  }
  
  // 下面的getHost(), getId(), isBusy(), setBusy()将host，id和
  // busy包装在公用方法中允许只读访问

  public String getHost() {
    return host;
  }
 
  public String getId() {
    return id;
  }
 
  public boolean isBusy() {
    return busy;
  }
 
  public void setBusy(boolean b) {
    busy = b;
  }
  
  /** 如果客户显式退出或在读套接字时捕获到一个异常则调用本函数*/
  public void close() {
    server.kill(this);
    try {
      sock.close();   // closes in and out too.
    } catch (IOException e) { }
  }
  
  /** 将字符串写入流，使用getBytes()方法来将它转换成一个字节数组 */
  public void write(String s) {
    byte buf[];
    buf = s.getBytes();
    try {
      out.write(buf, 0, buf.length);
    } catch (IOException e) {
      close();
    }
  }
  
  private String readline() {
    try {
      return in.readLine();
    } catch (IOException e) {
      return null;
    }
  }
  
  // 这部分和ServerConnection类中的对应部分类似，解析另一方的消息。

  static private final int NAME = 1;
  static private final int QUIT = 2;
  static private final int TO = 3;
  static private final int DELETE = 4;
 
  static private Hashtable keys = new Hashtable();
  static private String keystrings[] = {
    "", "name", "quit", "to", "delete"
  };
  static {
    for (int i = 0; i < keystrings.length; i++)
      keys.put(keystrings[i], new Integer(i));
  }
 
  private int lookup(String s) {
    Integer i = (Integer) keys.get(s);
    return i == null ? -1 : i.intValue();
  }
  
  /** 本方法循环管理所有与客户通信的消息 */
  public void run() {
    String s;
    StringTokenizer st;

    while ((s = readline()) != null) {
      st = new StringTokenizer(s);
      String keyword = st.nextToken();
      switch (lookup(keyword)) {
      default:
        System.out.println("bogus keyword: " + keyword + "\r");
        break;
      case NAME:
        name = st.nextToken() +
          (st.hasMoreTokens() ? " " + st.nextToken(CRLF) : "");
        System.out.println("[" + new Date() + "] " + this + "\r");
        server.set(id, this);
        break;
      case QUIT:
        close();
        return;
      case TO:
        String dest = st.nextToken();
        String body = st.nextToken(CRLF);
        server.sendto(dest, body);
        break;
      case DELETE:
        busy = true;
        server.delete(id);
        break;
      }
    }
    close();
  }
}

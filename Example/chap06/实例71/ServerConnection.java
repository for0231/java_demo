
import java.io.*;
import java.net.*;
import java.util.*;

/** 
 *  该类封装了与服务器和对手之间的通讯
 **/
class ServerConnection implements Runnable {
  private static final int port = 6564;   // 端口号
  private static final String CRLF = "\r\n";
  private BufferedReader in;  // 与服务器间的I/O流
  private PrintWriter out;
  private String id, toid = null;  // 本地ID和对手ID
  private Scrabblet scrabblet;
  
  /** 构造函数利用一个网络站点名，打开一个套接字连接到对应主机上的端口
   *  如果成功，用InputStreamReader和BufferedReader包装输入，用PrintWriter
   *  包装输出。如果失败，向调用者引发一个异常
   **/
  public ServerConnection(Scrabblet sc, String site) throws IOException {
    scrabblet = sc;
    Socket server = new Socket(site, port);
    in = new BufferedReader(new InputStreamReader(server.getInputStream()));
    out = new PrintWriter(server.getOutputStream(), true);
  }
  
  private String readline() {
    try {
      return in.readLine();
    } catch (IOException e) {
      return null;
    }
  }
  
  /** 该方法通知服务器本地游戏者的名字 */
  void setName(String s) {
    out.println("name " + s);
  }
 
  /** 该方法将自己从服务器的列表上删除 */
  void delete() {
    out.println("delete " + id);
  }
  
  /** 绑定对手的id */
  void setTo(String to) {
    toid = to;
  }

  void send(String s) {
    if (toid != null)
      out.println("to " + toid + " " + s);
  }
  
  // 下面的方法都是从客户端向服务器发送一行消息，然后服务器将这些消息
  // 发送给对手。

  /** challenge消息初始启动游戏 */
  void challenge(String destid) {
    setTo(destid);
    send("challenge " + id);
  }
 
  /** accept消息用来响应挑战 */
  void accept(String destid, int seed) {
    setTo(destid);
    send("accept " + id + " " + seed);
  }
 
  void chat(String s) {
    send("chat " + id + " " + s);
  }
 
  /** 每次移动一个字母，发送一个move消息 */
  void move(String letter, int x, int y) {
    send("move " + letter + " " + x + " " + y);
  }
 
  /** 每次回合结束时发送turn消息 */
  void turn(String words, int score) {
    send("turn " + score + " " + words);
  }
 
  /** 客户退出或离开程序时发送quit消息 */
  void quit() {
    send("quit " + id);  // tell other player
    out.println("quit"); // unhook
  }

  // 启动线程管理客户方面的网络
 
  private Thread t;
 
  void start() {
    t = new Thread(this);
    t.start();
  }

  // 这里显示的静态变量和静态块被用来初始化keys Hashtable， 这里使用这个
  // 散列表在keystrings中的字符串和数组位置之间映射  
  private static final int ID = 1;
  private static final int ADD = 2;
  private static final int DELETE = 3;
  private static final int MOVE = 4;
  private static final int CHAT = 5;
  private static final int QUIT = 6;
  private static final int TURN = 7;
  private static final int ACCEPT = 8;
  private static final int CHALLENGE = 9;
  private static Hashtable keys = new Hashtable();
  private static String keystrings[] = {
    "", "id", "add", "delete", "move", "chat",
    "quit", "turn", "accept", "challenge"
  };
  static {
    for (int i = 0; i < keystrings.length; i++)
      keys.put(keystrings[i], new Integer(i));
  }
 
  private int lookup(String s) {
    Integer i = (Integer) keys.get(s);
    return i == null ? -1 : i.intValue();
  }
  
  /** 该函数是游戏连接服务器的主循环。它进入一个阻塞调用的readline(),
   *  这个调用在服务器返回一行文字时返回一个字符串。它使用StringTokenizer
   *  将一行文字拆成单词。switch语句基于输入行的第一个单词分配合适的代码。
   **/
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
      case ID:
        id = st.nextToken();
        break;
      case ADD: {
          String id = st.nextToken();
          String hostname = st.nextToken();
          String name = st.nextToken(CRLF);
          scrabblet.add(id, hostname, name);
        }
        break;
      case DELETE:
        scrabblet.delete(st.nextToken());
        break;
      case MOVE: {
          String ch = st.nextToken();
          int x = Integer.parseInt(st.nextToken());
          int y = Integer.parseInt(st.nextToken());
          scrabblet.move(ch, x, y);
        }
        break;
      case CHAT: {
          String from = st.nextToken();
          scrabblet.chat(from, st.nextToken(CRLF));
        }
        break;
      case QUIT: {
          String from = st.nextToken();
          scrabblet.quit(from);
        }
        break;
      case TURN: {
          int score = Integer.parseInt(st.nextToken());
          scrabblet.turn(score, st.nextToken(CRLF));
        }
        break;
      case ACCEPT: {
          String from = st.nextToken();
          int seed = Integer.parseInt(st.nextToken());
          scrabblet.accept(from, seed);
        }
        break;
      case CHALLENGE: {
          String from = st.nextToken();
          scrabblet.challenge(from);
        }
        break;
      }
    }
  }
}
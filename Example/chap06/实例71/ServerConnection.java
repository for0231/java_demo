
import java.io.*;
import java.net.*;
import java.util.*;

/** 
 *  �����װ����������Ͷ���֮���ͨѶ
 **/
class ServerConnection implements Runnable {
  private static final int port = 6564;   // �˿ں�
  private static final String CRLF = "\r\n";
  private BufferedReader in;  // ����������I/O��
  private PrintWriter out;
  private String id, toid = null;  // ����ID�Ͷ���ID
  private Scrabblet scrabblet;
  
  /** ���캯������һ������վ��������һ���׽������ӵ���Ӧ�����ϵĶ˿�
   *  ����ɹ�����InputStreamReader��BufferedReader��װ���룬��PrintWriter
   *  ��װ��������ʧ�ܣ������������һ���쳣
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
  
  /** �÷���֪ͨ������������Ϸ�ߵ����� */
  void setName(String s) {
    out.println("name " + s);
  }
 
  /** �÷������Լ��ӷ��������б���ɾ�� */
  void delete() {
    out.println("delete " + id);
  }
  
  /** �󶨶��ֵ�id */
  void setTo(String to) {
    toid = to;
  }

  void send(String s) {
    if (toid != null)
      out.println("to " + toid + " " + s);
  }
  
  // ����ķ������Ǵӿͻ��������������һ����Ϣ��Ȼ�����������Щ��Ϣ
  // ���͸����֡�

  /** challenge��Ϣ��ʼ������Ϸ */
  void challenge(String destid) {
    setTo(destid);
    send("challenge " + id);
  }
 
  /** accept��Ϣ������Ӧ��ս */
  void accept(String destid, int seed) {
    setTo(destid);
    send("accept " + id + " " + seed);
  }
 
  void chat(String s) {
    send("chat " + id + " " + s);
  }
 
  /** ÿ���ƶ�һ����ĸ������һ��move��Ϣ */
  void move(String letter, int x, int y) {
    send("move " + letter + " " + x + " " + y);
  }
 
  /** ÿ�λغϽ���ʱ����turn��Ϣ */
  void turn(String words, int score) {
    send("turn " + score + " " + words);
  }
 
  /** �ͻ��˳����뿪����ʱ����quit��Ϣ */
  void quit() {
    send("quit " + id);  // tell other player
    out.println("quit"); // unhook
  }

  // �����̹߳���ͻ����������
 
  private Thread t;
 
  void start() {
    t = new Thread(this);
    t.start();
  }

  // ������ʾ�ľ�̬�����;�̬�鱻������ʼ��keys Hashtable�� ����ʹ�����
  // ɢ�б���keystrings�е��ַ���������λ��֮��ӳ��  
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
  
  /** �ú�������Ϸ���ӷ���������ѭ����������һ���������õ�readline(),
   *  ��������ڷ���������һ������ʱ����һ���ַ�������ʹ��StringTokenizer
   *  ��һ�����ֲ�ɵ��ʡ�switch�����������еĵ�һ�����ʷ�����ʵĴ��롣
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
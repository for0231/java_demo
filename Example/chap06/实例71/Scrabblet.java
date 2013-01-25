import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

/**
 *  ��Ҫ��applet��
 **/
public class Scrabblet extends Applet implements ActionListener {
  // ����ʵ����������
  private ServerConnection server;  // �������������
  private String serverName;        // ������������
  private Bag bag;		    // ��Ϸ�й��õ���ĸ��
  private Board board;		    // ����
  private boolean single = false;
  private boolean ourturn;
  private boolean seen_pass = false;
  private Letter theirs[] = new Letter[7];
  private String name;
  private String others_name;
  private Panel topPanel;
  private Label prompt;
  private TextField namefield;
  private Button done;
  private TextField chat;
 
 // ��������������û��������
  private List idList;
  private Button challenge;
  private Canvas ican;
  
  /** ����BorderLayout������splash��Ļ���� */
  public void init() {
    setLayout(new BorderLayout());
    serverName = getCodeBase().getHost();
    if (serverName.equals(""))
      serverName = "localhost";
    ican = new IntroCanvas();
  }
  
  /** ��������ʾ��������ҳ��ʱ���ø÷��� */
  public void start() {
    try {
      showStatus("Connecting to " + serverName);
      server = new ServerConnection(this, serverName);
      server.start();
      showStatus("Connected: " + serverName);

      if (name == null) {
        prompt = new Label("Enter your name here:");
        namefield = new TextField(20);
        namefield.addActionListener(this);
        topPanel = new Panel();
        topPanel.setBackground(new Color(255, 255, 200));
        topPanel.add(prompt);
        topPanel.add(namefield);
        add("North", topPanel);
        add("Center", ican);
      } else {
        if (chat != null) {
          remove(chat);
          remove(board);
          remove(done);
        }
        nameEntered(name);
      }
      validate();
    } catch (Exception e) {
      single = true;
      start_Game((int)(0x7fffffff * Math.random()));
    }
  }
  
  /** �뿪����ʱ���ø÷��� */
  public void stop() {
   if (!single)
     server.quit();
  }
 
  /** �����û�����ʱ���ø÷��� */
  void add(String id, String hostname, String name) {
    delete(id); // in case it is already there.
    idList.add("(" + id + ")  " + name + "@" + hostname);
    showStatus("Choose a player from the list");
  }
  
  /** ���û�������Լ����Ϊ��ѡ����ʱ���ô˷��� */
  void delete(String id) {
    for (int i = 0; i < idList.getItemCount(); i++) {
      String s = idList.getItem(i);
      s = s.substring(s.indexOf("(") + 1, s.indexOf(")"));
      if (s.equals(id)) {
        idList.remove(i);
        break;
      }
    }
    if (idList.getItemCount() == 0 && bag == null)
      showStatus("Wait for other players to arrive.");
  }
  
  /** ����id����ȡ���֣����û�ҵ�id������null */
  private String getName(String id) {
    for (int i = 0; i < idList.getItemCount(); i++) {
      String s = idList.getItem(i);
      String id1 = s.substring(s.indexOf("(") + 1, s.indexOf(")"));
      if (id1.equals(id)) {
        return s.substring(s.indexOf(" ") + 3, s.indexOf("@"));
      }
    }
    return null;
  }
  /** ������һ���û��򱾵��û���սʱ��ServerConnection���ø÷��� */
  void challenge(String id) {
    ourturn = false;
    int seed = (int)(0x7fffffff * Math.random());
    others_name = getName(id);   // ����˭��
    showStatus("challenged by " + others_name);
 
    // ȷ����Ϣ
 
    server.accept(id, seed);
    server.delete();
    start_Game(seed);
  }
  
  /** ������ս */
  void accept(String id, int seed) {
    ourturn = true;
    others_name = getName(id);
    server.delete();
    start_Game(seed);
  }
  
  /** �û��ڶԻ���������ʱ�� ���������ø÷��� */
  void chat(String id, String s) {
    showStatus(others_name + ": " + s);
  }
  
 /** ����ÿ����һ�����񣬵���һ��move�������� */
  void move(String letter, int x, int y) {
    for (int i = 0; i < 7; i++) {
      if (theirs[i] != null && theirs[i].getSymbol().equals(letter)) {
        Letter already = board.getLetter(x, y);
        if (already != null) {
          board.moveLetter(already, 15, 15); 
        }
        board.moveLetter(theirs[i], x, y);
        board.commitLetter(theirs[i]);
        theirs[i] = bag.takeOut();
        if (theirs[i] == null)
          showStatus("No more letters");
        break;
      }
    }
    board.repaint();
  }
  
  /** �����ֵķ���ȫ�������˵��ø÷��� */
  void turn(int score, String words) {
    showStatus(others_name + " played: " + words + " worth " + score);
    done.setEnabled(true);
    board.othersTurn(score);
  }
  
  /** ��һ����ȷ�˳�ʱ������quit */
  void quit(String id) {
    showStatus(others_name + " just quit.");
    remove(chat);
    remove(board);
    remove(done);
    nameEntered(name);
  }
  
  /** ����ʾ�����û������»س�ʱactionPerformed()���ø÷��� */
  private void nameEntered(String s) {
    if (s.equals(""))
      return;
    name = s;
    if (ican != null)
      remove(ican);
    if (idList != null)
      remove(idList);
    if (challenge != null)
      remove(challenge);
    idList = new List(10, false);
    add("Center", idList);
    challenge = new Button("Challenge");
    challenge.addActionListener(this);
    add("North", challenge);
    validate();
    server.setName(name);
    showStatus("Wait for other players to arrive.");
    if (topPanel != null)
      remove(topPanel);
  }
  
  /** �򵥵ĵ���wepick()��theypick()����ʼ��Ϸ */
  private void wepick() {
    for (int i = 0; i < 7; i++) {
      Letter l = bag.takeOut();
      board.addLetter(l);
    }
  }
 
  private void theypick() {
    for (int i = 0; i < 7; i++) {
      Letter l = bag.takeOut();
      theirs[i] = l;
    }
  }
  
  /** �ڵ��û�ģʽ�£�����splashͼ�Σ��������̣���ʼ��Ϸ */
  private void start_Game(int seed) {
    if (single) {
      Frame popup = new Frame("Scrabblet");
      popup.setSize(400, 300);
      popup.add("Center", ican);
      popup.setResizable(false);
      popup.show();
      board = new Board();
      showStatus("no server found, playing solo");
      ourturn = true;
    } else {
      remove(idList);
      remove(challenge);
      board = new Board(name, others_name);
      chat = new TextField();
      chat.addActionListener(this);
      add("North", chat);
      showStatus("playing against " + others_name);
    }

    add("Center", board);
    done = new Button("Done");
    done.addActionListener(this);
    add("South", done);
    validate();

    bag = new Bag(seed);
    if (ourturn) {
      wepick();
      if (!single)
        theypick();
    } else {
      done.setEnabled(false);
      theypick();
      wepick();
    }
    board.repaint();
  }
  
  /** ������challenge��ťʱ���øú��� */
  private void challenge_them() {
    String s = idList.getSelectedItem();
    if (s == null) {
      showStatus("Choose a player from the list then press Challenge");
    } else {
      remove(challenge);
      remove(idList);
      String destid = s.substring(s.indexOf('(')+1, s.indexOf(')'));
      showStatus("challenging: " + destid);
      server.challenge(destid);  
      validate();
    }
  }
  
  /** ������doneʱ���øú��� */
  private void our_turn() {
    String word = board.findwords();
    if (word == null) {
      showStatus("Illegal letter positions");
    } else {
      if ("".equals(word)) {
        if (single)
          return;
        if (seen_pass) {
          done.setEnabled(false);
          server.turn("pass", 0);
          showStatus("You passed");
          seen_pass = false;
        } else {
          showStatus("Press done again to pass");
          seen_pass = true;
          return;
        }
      } else {
        seen_pass = false;
      }
      showStatus(word);
      board.commit(server);
      for (int i = 0; i < 7; i++) {
        if (board.getTray(i) == null) {
          Letter l = bag.takeOut();
          if (l == null)
            showStatus("No more letters");
          else
            board.addLetter(l);
        }
      }
      if (!single) {
        done.setEnabled(false);
        server.turn(word, board.getTurnScore());
      }
      board.repaint();
    }
  }
  
  /** ��ȡ������������� */
  public void actionPerformed(ActionEvent ae) {
    Object source = ae.getSource();
    if(source == chat) {
      server.chat(chat.getText());
      chat.setText("");
    }
    else if(source == challenge) {
      challenge_them();
    }
    else if(source == done) {
      our_turn();
    }
    else if(source == namefield) {
      TextComponent tc = (TextComponent)source;
      nameEntered(tc.getText());
    }
  }
}
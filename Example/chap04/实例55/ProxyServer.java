import java.io.*;
import java.net.*;
import java.util.*;
// main() ���������������������������Ƕ��Proxy��ʵ��
public class ProxyServer {
public static void main(String[] args) {
try {
// ���args������
if ((args.length == 0) || (args.length % 3 != 0))
throw new IllegalArgumentException("Wrong number of args");
// ����Server����
Server s = new Server(null, 12);
// ��ÿ��ѭ������һ�� Proxy��������ӵ� server.
int i = 0;
while(i < args.length) {
String host = args[i++];
int remoteport = Integer.parseInt(args[i++]);
int localport = Integer.parseInt(args[i++]);
s.addService(new Proxy(host, remoteport), localport);
}
}
catch (Exception e) {  // �������ӡ������Ϣ
System.err.println(e);
System.err.println("Usage: java ProxyServer " +"<host> <remoteport> <localport> ...");
System.exit(1);
}
}

// ��������һ��ʵ���˴��������ࡣ���пͻ�������ʱ����serve()����
// ��ʱ�������Ƚ���һ�����Ӳ��ڿͻ��ͷ�������֮�䴫������
// �����ʵ�������������߳���Ϊ�����࣬ һ���̴߳ӿͻ��˿������ݵ���������
// ��һ���ӷ������˵��ͻ��˿������ݡ��̵߳���serve()����������������Щ�̣߳�Ȼ��ȴ�
// �����˳�
public static class Proxy implements Server.Service {
String host;
int port;

// ��ס����������Ͷ˿�
public Proxy(String host, int port) {
this.host = host;
this.port = port;
}
// ����һ���ͻ�������ʱ�������˵����������
public void serve(InputStream in, OutputStream out) {
final InputStream from_client = in;
final OutputStream to_client = out;
final InputStream from_server;
final OutputStream to_server;
// ������ĳһ���������˶˿ڵ����Ӳ����س�����Ϣ
final Socket server;
try {
server = new Socket(host, port);
from_server = server.getInputStream();
to_server = server.getOutputStream();
}
catch (Exception e) {
PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
pw.print("Proxy server could not connect to " + host +":" + port + "\n");
pw.flush();
pw.close();
try { in.close(); } catch (IOException ex) {}
return;
}
// ����һ�����鱣�������̡߳�
final Thread[] threads = new Thread[2];
// ����һ���̴߳ӿͻ��˵��������˿�������
Thread c2s = new Thread() {
public void run() {
byte[] buffer = new byte[2048];
int bytes_read;
try {
while((bytes_read=from_client.read(buffer))!=-1) {
to_server.write(buffer, 0, bytes_read);
to_server.flush();
}
}
catch (IOException e) {}
finally {
// ���߳��������ʱ
try {
server.close();
to_client.close();
from_client.close();
}
catch (IOException e) {}
}
}
};
// ����һ���̴߳ӷ������˵��ͻ��˿�������.
// ��Щ�̺߳������Ǹ���������һ��
Thread s2c = new Thread() {
public void run() {
byte[] buffer = new byte[2048];
int bytes_read;
try {
while((bytes_read=from_server.read(buffer))!=-1) {
to_client.write(buffer, 0, bytes_read);
to_client.flush();
}
}
catch (IOException e) {}
finally {
try {
server.close(); // �رշ�����
to_client.close();
from_client.close();
} catch (IOException e) {}
}
}
};
// ���̱߳����� threads[]�������Ա�������֮������໥����
threads[0] = c2s; threads[1] = s2c;
// �����߳�
c2s.start(); s2c.start();
// �ȴ������˳�
try { c2s.join(); s2c.join(); } catch (InterruptedException e) {}
}
}
}

// ����Ķ��߳�server���ܣ�ʵ�ֶԶ˿ڵļ��������ݴ����ʵʱ����
class Server {   // ����Server ��
// server��״̬��
Map services;
Set connections;
int maxConnections;
ThreadGroup threadGroup;
PrintWriter logStream;
public Server(OutputStream logStream, int maxConnections) { // ���캯��
setLogStream(logStream);
log("Starting server"); // ��¼��־��Ϣ
threadGroup = new ThreadGroup(Server.class.getName());
this.maxConnections = maxConnections;
services = new HashMap();
connections = new HashSet(maxConnections);
}

// һ�������ķ��� �����õ�ǰ��½��������null���رյ�½
public synchronized void setLogStream(OutputStream out) {
if (out != null) logStream = new PrintWriter(out);
else logStream = null;
}

// ��¼��־����
protected synchronized void log(String s) {
if (logStream != null) {
logStream.println("[" + new Date() + "] " + s);
logStream.flush();
}
}

protected void log(Object o) { log(o.toString()); }   // ��ĳ������objectд��log

// ʹserver���ض��˿ڿ��ض�����
public synchronized void addService(Service service, int port)  throws IOException
{
Integer key = new Integer(port);
// �����ĳ���˿��Ƿ��Ѿ�������service
if (services.get(key) != null)
throw new IllegalArgumentException("�˿� " + port +" �Ѿ���ʹ��.");
// �����������������˿ڵ��������
Listener listener = new Listener(threadGroup, port, service);
// �����ڹ�ϣ����
services.put(key, listener);
// дlog
log("�������� " + service.getClass().getName() + " �˿�Ϊ�� " + port);
// ��ʼ����
listener.start();
}

// ʹserverֹͣĳ���˿��ϵķ���
public synchronized void removeService(int port) {
Integer key = new Integer(port);  // ��ϣ��ؼ���
// �ڹ�ϣ���в��Ҷ�ĳ���˿ڵļ�������
final Listener listener = (Listener) services.get(key);
if (listener == null) return;
// ʹ�ü�����ֹͣ
listener.pleaseStop();
// �ӹ�ϣ����ɾ��
services.remove(key);
// дlog.
log("ֹͣ���� " + listener.service.getClass().getName() + " �˿ڣ� " + port);
}
}

// ������һ��������������������ĳһ���ض��˿ڵ����ӡ�
// �������һ����������ʱ�������÷�������addConnection()���������ܻ�ܾ�����
public class Listener extends Thread {
ServerSocket listen_socket;    // �������ӵ��׽���
int port;                      // �˿�
Service service;               // �ڶ˿���ʵ�ֵķ���
volatile boolean stop = false; // ��־�Ƿ�����ֹͣ
// ����һ���������׽���������ĳ���ض��˿ڵ�����
public Listener(ThreadGroup group, int port, Service service) throws IOException
{
super(group, "Listener:" + port);
listen_socket = new ServerSocket(port);
// ����һ�����㳬ʱ�ź�ʹ��accept()���ж�
listen_socket.setSoTimeout(600000);
this.port = port;
this.service = service;
}

// ֹͣ��������
public void pleaseStop() {
this.stop = true;              // ����ֹͣ��־λ
this.interrupt();              // ֹͣ���accept()
try { listen_socket.close(); } // ֹͣ����.
catch(IOException e) {}
}


// �ȴ��������󣬽��ܣ����Ű�socket����sever��addConnection����
public void run() {
while(!stop) {      // ѭ��ֱ��Ҫ��ֹͣ
try {
Socket client = listen_socket.accept();
addConnection(client, service);
}
catch (InterruptedIOException e) {}
catch (IOException e) {log(e);}
}
}

// �����ܿͻ��������Ժ�����������ᱻ����������
// ���ߴ���һ�������࣬���߰�������Ӽ��뵽���е������б��У��ֻ�ر�����
protected synchronized void addConnection(Socket s, Service service) {
// ���������������
if (connections.size() >= maxConnections) {
try {
// ����߿ͻ������ڱ��ܾ�.
PrintWriter out = new PrintWriter(s.getOutputStream());
out.print("���ӱ��ܾ�; " +"������æ�����Ժ�����.\n");
out.flush();
//�ر�����
s.close();
// дlog
log("���ӱ��ܾ���" +s.getInetAddress().getHostAddress() +
":" + s.getPort() + ": �ﵽ���������.");
} catch (IOException e) {log(e);}
}
else {  // ���������δ������
// ����һ���̴߳�������
Connection c = new Connection(s, service);
// ��������ӵ���ǰ�����б���
connections.add(c);
// ������µ�����д��log
log("Connected to " + s.getInetAddress().getHostAddress() +":" + s.getPort() + " on port " +
s.getLocalPort() +" for service " + service.getClass().getName());
// ��ʼ�����߳��ṩ����
c.start();
}
}

// �������߳��˳�ǰ�����������������ĳ���ض��̴߳��߳��б���ɾ��
protected synchronized void endConnection(Connection c) {
connections.remove(c);
log("���ӵ��� " + c.client.getInetAddress().getHostAddress() +":" + c.client.getPort() + " �ر�.");
}

// �ı����е���������
public synchronized void setMaxConnections(int max) {
maxConnections = max;
}

// ��ʾserver��Ϣ
public synchronized void displayStatus(PrintWriter out) {
// ��ʾ���з�����б�
Iterator keys = services.keySet().iterator();
while(keys.hasNext()) {
Integer port = (Integer) keys.next();
Listener listener =	(Listener) services.get(port);
out.print("SERVICE " + listener.service.getClass().getName()+ " ON PORT " + port +
"\n");
}
// ��ʾ������������
out.print("MAX CONNECTIONS: " + maxConnections + "\n");
// ��ʾ���������б�
Iterator conns = connections.iterator();
while(conns.hasNext()) {
Connection c = (Connection)conns.next();
out.print("����: " +c.client.getInetAddress().getHostAddress() +
":" + c.client.getPort() + " �˿�:" +c.client.getLocalPort() +
c.service.getClass().getName() + "\n");
}
}
}

public class Connection extends Thread { // ���߳�������
Socket client;     //��ͻ��˶Ի����׽���
Service service;   //�ṩ���ͻ��˵�service

public Connection(Socket client, Service service) {// ���캯��
super("Server.Connection:" +client.getInetAddress().getHostAddress() +
":" + client.getPort());
this.client = client;
this.service = service;
}

// �ѿͻ��˵��������������ĳ��Service����� serve()����
public void run() {
try {
InputStream in = client.getInputStream();
OutputStream out = client.getOutputStream();
service.serve(in, out);
}
catch (IOException e) {log(e);}
finally { endConnection(this); }
}
}

public interface Service {
public void serve(InputStream in, OutputStream out) throws IOException;
}

// һ���򵥵�service������ͻ�����ʾ��ǰsever�ϵ�ʱ�䲢����رտͻ�������
public static class Time implements Service {
public void serve(InputStream i, OutputStream o) throws IOException {
PrintWriter out = new PrintWriter(o);
out.print(new Date() + "\n");
out.close();
i.close();
}
}

// ��������һ���򵥵�service. ���ӿͻ��˶��������У�Ȼ���ͻ�ȥȻ��ת
// ͬʱ��ʾ��ӭ��Ϣ�����û�����'.'��Ϊһ�еĽ�βʱ�ر�����
public static class Reverse implements Service {
public void serve(InputStream i, OutputStream o) throws IOException {
BufferedReader in = new BufferedReader(new InputStreamReader(i));
PrintWriter out =new PrintWriter(new BufferedWriter(new OutputStreamWriter(o)));
out.print("Welcome to the line reversal server.\n");
out.print("Enter lines.  End with a '.' on a line by itself.\n");
for(;;) {
out.print("> ");
out.flush();
String line = in.readLine();
if ((line == null) || line.equals(".")) break;
for(int j = line.length()-1; j >= 0; j--)
out.print(line.charAt(j));
out.print("\n");
}
out.close();
in.close();
}
}

// ���������һ��HTTPӳ��, �����ؿͻ��� HTTP����
public static class HTTPMirror implements Service {
public void serve(InputStream i, OutputStream o) throws IOException {
BufferedReader in = new BufferedReader(new InputStreamReader(i));
PrintWriter out = new PrintWriter(o);
out.print("HTTP/1.0 200 \n");
out.print("Content-Type: text/plain\n\n");
String line;
while((line = in.readLine()) != null) {
if (line.length() == 0) break;
out.print(line + "\n");
}
out.close();
in.close();
}
}

public static class UniqueID implements Service {
public int id=0;
public synchronized int nextId() { return id++; }
public void serve(InputStream i, OutputStream o) throws IOException {
PrintWriter out = new PrintWriter(o);
out.print("You are client #: " + nextId() + "\n");
out.close();
i.close();
}
}

// ����һ�����������Э�飬ͨ�����Э������ṩ�������뱣���ĶԷ����ʵʱ����
public static class Control implements Service {
  Server server;
  String password;
  boolean connected = false; // �Ƿ���ĳ���ͻ�����
// ����һ���µĿ��Ʒ�����������ĳ���ض���Server����, ����Ҫ
// ������֤�Ի�ȡ���Ȩ�ޣ�ע�����Service�Ĺ�������������
  public Control(Server server, String password) {
    this.server = server;
    this.password = password;
  }

// ���Ǹ����ṩ����ķ�����������ͻ��˵������У�������Ϊ�������Ͳ���
  public void serve(InputStream i, OutputStream o) throws IOException {
// ������
    BufferedReader in = new BufferedReader(new InputStreamReader(i));
    PrintWriter out = new PrintWriter(o);
    String line;
// �û��Ƿ����ṩ���룿
    boolean authorized = false;
// ����Ѿ���һ���ͻ�������,����ͻ��˷���һ����Ϣ���ر�����
// �ڴ�ʹ��һ��synchronized��������ֹ��Դ����
    synchronized (this) {
      if (connected) {
        out.print("ONLY ONE CONTROL CONNECTION ALLOWED.\n");
        out.close();
        return;
      }
      else
        connected = true;
    }

// ������ѭ��:��ȡһ�����Ȼ���������
    for (; ; ) { // ����ѭ��
      out.print("> ");
      out.flush();
      line = in.readLine(); // ��ȡ�û�����
      if (line == null)
        break; // ��� EOF���˳�
      try {
// ʹ��StringTokenizer�������û�����
        StringTokenizer t = new StringTokenizer(line);
        if (!t.hasMoreTokens())
          continue; //�������Ϊ��
// �������ĵ�һ���ʲ�ת����Сд
        String command = t.nextToken().toLowerCase();
// ����ܵĸ�������Ƚϲ�ִ����Ӧ����
        if (command.equals("password")) {
          String p = t.nextToken(); // ��ȡ��һ��
          if (p.equals(this.password)) { // �����Ƿ���ȷ
            out.print("OK\n");
            authorized = true; // ����Ȩ��
          }
          else
            out.print("INVALID PASSWORD\n"); // ����ʧ��
        }
        else if (command.equals("add")) {
// ����Ƿ��ṩ������
          if (!authorized)
            out.print("PASSWORD REQUIRED\n");
          else {
// ��÷�������Ʋ����Զ�̬���롢ʵ�����������쳣
            String serviceName = t.nextToken();
            Class serviceClass = Class.forName(serviceName);
            Service service;
            try {
              service = (Service) serviceClass.newInstance();
            }
            catch (NoSuchMethodError e) {
              throw new IllegalArgumentException(
                  "Service must have a " +
                  "no-argument constructor");
            }
            int port = Integer.parseInt(t.nextToken());
// ���û�в����쳣������ӷ���
            server.addService(service, port);
            out.print("SERVICE ADDED\n"); // ȷ��
          }
        }
        else if (command.equals("remove")) {
          if (!authorized)
            out.print("PASSWORD REQUIRED\n");
          else {
            int port = Integer.parseInt(t.nextToken());
            server.removeService(port); // ȡ������
            out.print("SERVICE REMOVED\n");
          }
        }
        else if (command.equals("max")) { // ������������
          if (!authorized)
            out.print("PASSWORD REQUIRED\n");
          else {
            int max = Integer.parseInt(t.nextToken());
            server.setMaxConnections(max);
            out.print("MAX CONNECTIONS CHANGED\n"); //ȷ��
          }
        }
        else if (command.equals("status")) { // ��ʾ״̬
          if (!authorized)
            out.print("PASSWORD REQUIRED\n");
          else
            server.displayStatus(out);
        }
        else if (command.equals("help")) { // ��������
// ��ʾ�����﷨������Ҫ����
          out.print("COMMANDS:\n" + "\tpassword <password>\n" +
                    "\tadd <service> <port>\n" + "\tremove <port>\n" +
                    "\tmax <max-connections>\n" + "\tstatus\n" + "\thelp\n" +
                    "\tquit\n");
        }
        else if (command.equals("quit"))
          break;
        else
          out.print("UNRECOGNIZED COMMAND\n"); // ���ִ���
      }
      catch (Exception e) {
        out.print("ERROR WHILE PARSING OR EXECUTING COMMAND:\n" + e + "\n");
      }
    }
// �ر����������ӱ�־λ��Ϊfalseʹ�������ͻ�������
    connected = false;
    out.close();
    in.close();
  }
}
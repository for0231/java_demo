import java.io.*;
import java.net.*;
import java.util.*;
// main() 方法启动代理服务器。具体由内嵌的Proxy类实现
public class ProxyServer {
public static void main(String[] args) {
try {
// 检查args参数数
if ((args.length == 0) || (args.length % 3 != 0))
throw new IllegalArgumentException("Wrong number of args");
// 创建Server对象
Server s = new Server(null, 12);
// 对每个循环创建一个 Proxy并把它添加到 server.
int i = 0;
while(i < args.length) {
String host = args[i++];
int remoteport = Integer.parseInt(args[i++]);
int localport = Integer.parseInt(args[i++]);
s.addService(new Proxy(host, remoteport), localport);
}
}
catch (Exception e) {  // 若出错打印错误信息
System.err.println(e);
System.err.println("Usage: java ProxyServer " +"<host> <remoteport> <localport> ...");
System.exit(1);
}
}

// 接下来是一个实现了代理服务的类。当有客户端连接时调用serve()方法
// 此时必须首先建立一个连接并在客户和服务器端之间传输数据
// 这个类实现了两个相似线程作为匿名类， 一个线程从客户端拷贝数据到服务器端
// 另一个从服务器端到客户端拷贝数据。线程调用serve()方法创建和启动这些线程，然后等待
// 它们退出
public static class Proxy implements Server.Service {
String host;
int port;

// 记住代理的主机和端口
public Proxy(String host, int port) {
this.host = host;
this.port = port;
}
// 当有一个客户端连接时服务器端调用这个方法
public void serve(InputStream in, OutputStream out) {
final InputStream from_client = in;
final OutputStream to_client = out;
final InputStream from_server;
final OutputStream to_server;
// 建立到某一个服务器端端口的连接并返回出错信息
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
// 建立一个数组保存两个线程。
final Thread[] threads = new Thread[2];
// 建立一个线程从客户端到服务器端拷贝数据
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
// 当线程任务结束时
try {
server.close();
to_client.close();
from_client.close();
}
catch (IOException e) {}
}
}
};
// 建立一个线程从服务器端到客户端拷贝数据.
// 这些线程和上面那个工作机理一样
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
server.close(); // 关闭服务器
to_client.close();
from_client.close();
} catch (IOException e) {}
}
}
};
// 把线程保存在 threads[]数组中以便匿名类之间可以相互引用
threads[0] = c2s; threads[1] = s2c;
// 启动线程
c2s.start(); s2c.start();
// 等待他们退出
try { c2s.join(); s2c.join(); } catch (InterruptedException e) {}
}
}
}

// 下面的多线程server类框架，实现对端口的监听和数据传输的实时控制
class Server {   // 定义Server 类
// server的状态量
Map services;
Set connections;
int maxConnections;
ThreadGroup threadGroup;
PrintWriter logStream;
public Server(OutputStream logStream, int maxConnections) { // 构造函数
setLogStream(logStream);
log("Starting server"); // 记录日志信息
threadGroup = new ThreadGroup(Server.class.getName());
this.maxConnections = maxConnections;
services = new HashMap();
connections = new HashSet(maxConnections);
}

// 一个公共的方法 来设置当前登陆流，传递null来关闭登陆
public synchronized void setLogStream(OutputStream out) {
if (out != null) logStream = new PrintWriter(out);
else logStream = null;
}

// 记录日志函数
protected synchronized void log(String s) {
if (logStream != null) {
logStream.println("[" + new Date() + "] " + s);
logStream.flush();
}
}

protected void log(Object o) { log(o.toString()); }   // 把某个对象object写入log

// 使server在特定端口开特定服务
public synchronized void addService(Service service, int port)  throws IOException
{
Integer key = new Integer(port);
// 检查在某个端口是否已经有其它service
if (services.get(key) != null)
throw new IllegalArgumentException("端口 " + port +" 已经被使用.");
// 创建监听类来监听端口的连接情况
Listener listener = new Listener(threadGroup, port, service);
// 保存在哈希表中
services.put(key, listener);
// 写log
log("启动服务： " + service.getClass().getName() + " 端口为： " + port);
// 开始监听
listener.start();
}

// 使server停止某个端口上的服务
public synchronized void removeService(int port) {
Integer key = new Integer(port);  // 哈希表关键字
// 在哈希表中查找对某个端口的监听对象
final Listener listener = (Listener) services.get(key);
if (listener == null) return;
// 使得监听器停止
listener.pleaseStop();
// 从哈希表中删除
services.remove(key);
// 写log.
log("停止服务： " + listener.service.getClass().getName() + " 端口： " + port);
}
}

// 下面是一个监听器，它监听来自某一个特定端口的连接。
// 当它获得一个连接请求时，它调用服务器的addConnection()方法来接受或拒绝连接
public class Listener extends Thread {
ServerSocket listen_socket;    // 监听连接的套接字
int port;                      // 端口
Service service;               // 在端口上实现的服务
volatile boolean stop = false; // 标志是否被请求停止
// 创建一个服务器套接字来监听某个特定端口的连接
public Listener(ThreadGroup group, int port, Service service) throws IOException
{
super(group, "Listener:" + port);
listen_socket = new ServerSocket(port);
// 给出一个非零超时信号使得accept()被中断
listen_socket.setSoTimeout(600000);
this.port = port;
this.service = service;
}

// 停止接收连接
public void pleaseStop() {
this.stop = true;              // 设置停止标志位
this.interrupt();              // 停止阻断accept()
try { listen_socket.close(); } // 停止监听.
catch(IOException e) {}
}


// 等待连接请求，接受，接着把socket传给sever的addConnection方法
public void run() {
while(!stop) {      // 循环直到要求停止
try {
Socket client = listen_socket.accept();
addConnection(client, service);
}
catch (InterruptedIOException e) {}
catch (IOException e) {log(e);}
}
}

// 当接受客户端连接以后这个方法将会被监听器调用
// 或者创建一个连接类，或者把这个连接加入到现有的连接列表中，抑或关闭连接
protected synchronized void addConnection(Socket s, Service service) {
// 如果到达连接上限
if (connections.size() >= maxConnections) {
try {
// 则告诉客户端正在被拒绝.
PrintWriter out = new PrintWriter(s.getOutputStream());
out.print("连接被拒绝; " +"服务器忙，请稍后再试.\n");
out.flush();
//关闭连接
s.close();
// 写log
log("连接被拒绝：" +s.getInetAddress().getHostAddress() +
":" + s.getPort() + ": 达到最大连接数.");
} catch (IOException e) {log(e);}
}
else {  // 如果连接数未到上限
// 创建一个线程处理连接
Connection c = new Connection(s, service);
// 并把它添加到当前连接列表中
connections.add(c);
// 把这个新的连接写入log
log("Connected to " + s.getInetAddress().getHostAddress() +":" + s.getPort() + " on port " +
s.getLocalPort() +" for service " + service.getClass().getName());
// 开始连接线程提供服务
c.start();
}
}

// 在连接线程退出前调用这个函数，它把某个特定线程从线程列表中删除
protected synchronized void endConnection(Connection c) {
connections.remove(c);
log("连接到： " + c.client.getInetAddress().getHostAddress() +":" + c.client.getPort() + " 关闭.");
}

// 改变现有的连接上限
public synchronized void setMaxConnections(int max) {
maxConnections = max;
}

// 显示server信息
public synchronized void displayStatus(PrintWriter out) {
// 显示所有服务的列表
Iterator keys = services.keySet().iterator();
while(keys.hasNext()) {
Integer port = (Integer) keys.next();
Listener listener =	(Listener) services.get(port);
out.print("SERVICE " + listener.service.getClass().getName()+ " ON PORT " + port +
"\n");
}
// 显示现有连接上限
out.print("MAX CONNECTIONS: " + maxConnections + "\n");
// 显示现有连接列表
Iterator conns = connections.iterator();
while(conns.hasNext()) {
Connection c = (Connection)conns.next();
out.print("连接: " +c.client.getInetAddress().getHostAddress() +
":" + c.client.getPort() + " 端口:" +c.client.getLocalPort() +
c.service.getClass().getName() + "\n");
}
}
}

public class Connection extends Thread { // 多线程连接类
Socket client;     //与客户端对话的套接字
Service service;   //提供给客户端的service

public Connection(Socket client, Service service) {// 构造函数
super("Server.Connection:" +client.getInetAddress().getHostAddress() +
":" + client.getPort());
this.client = client;
this.service = service;
}

// 把客户端的输入输出流传给某个Service对象的 serve()方法
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

// 一个简单的service，它向客户端显示当前sever上的时间并负责关闭客户端连接
public static class Time implements Service {
public void serve(InputStream i, OutputStream o) throws IOException {
PrintWriter out = new PrintWriter(o);
out.print(new Date() + "\n");
out.close();
i.close();
}
}

// 这是另外一个简单的service. 它从客户端读入输入行，然后发送回去然后反转
// 同时显示欢迎信息，当用户键入'.'作为一行的结尾时关闭连接
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

// 这个服务是一个HTTP映射, 它返回客户的 HTTP请求
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

// 解析一个基于命令的协议，通过这个协议可以提供具有密码保护的对服务的实时控制
public static class Control implements Service {
  Server server;
  String password;
  boolean connected = false; // 是否有某个客户连接
// 创建一个新的控制服务，它将控制某个特定的Server对象, 并需要
// 密码验证以获取相关权限，注意这个Service的构造器包含参数
  public Control(Server server, String password) {
    this.server = server;
    this.password = password;
  }

// 这是负责提供服务的方法，它读入客户端的命令行，并解析为相关命令和参数
  public void serve(InputStream i, OutputStream o) throws IOException {
// 创建流
    BufferedReader in = new BufferedReader(new InputStreamReader(i));
    PrintWriter out = new PrintWriter(o);
    String line;
// 用户是否已提供密码？
    boolean authorized = false;
// 如果已经有一个客户端连接,则向客户端发送一个消息并关闭连接
// 在此使用一个synchronized区段来防止资源竞争
    synchronized (this) {
      if (connected) {
        out.print("ONLY ONE CONTROL CONNECTION ALLOWED.\n");
        out.close();
        return;
      }
      else
        connected = true;
    }

// 这是主循环:读取一条命令，然后解析处理
    for (; ; ) { // 无限循环
      out.print("> ");
      out.flush();
      line = in.readLine(); // 获取用户输入
      if (line == null)
        break; // 如果 EOF则退出
      try {
// 使用StringTokenizer来解析用户命令
        StringTokenizer t = new StringTokenizer(line);
        if (!t.hasMoreTokens())
          continue; //如果输入为空
// 获得输入的第一个词并转换成小写
        String command = t.nextToken().toLowerCase();
// 与可能的各种命令比较并执行相应操作
        if (command.equals("password")) {
          String p = t.nextToken(); // 获取下一个
          if (p.equals(this.password)) { // 密码是否正确
            out.print("OK\n");
            authorized = true; // 授予权限
          }
          else
            out.print("INVALID PASSWORD\n"); // 否则失败
        }
        else if (command.equals("add")) {
// 检查是否提供了密码
          if (!authorized)
            out.print("PASSWORD REQUIRED\n");
          else {
// 获得服务的名称并尝试动态载入、实例化并处理异常
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
// 如果没有产生异常，则添加服务
            server.addService(service, port);
            out.print("SERVICE ADDED\n"); // 确认
          }
        }
        else if (command.equals("remove")) {
          if (!authorized)
            out.print("PASSWORD REQUIRED\n");
          else {
            int port = Integer.parseInt(t.nextToken());
            server.removeService(port); // 取消服务
            out.print("SERVICE REMOVED\n");
          }
        }
        else if (command.equals("max")) { // 设置连接上限
          if (!authorized)
            out.print("PASSWORD REQUIRED\n");
          else {
            int max = Integer.parseInt(t.nextToken());
            server.setMaxConnections(max);
            out.print("MAX CONNECTIONS CHANGED\n"); //确认
          }
        }
        else if (command.equals("status")) { // 显示状态
          if (!authorized)
            out.print("PASSWORD REQUIRED\n");
          else
            server.displayStatus(out);
        }
        else if (command.equals("help")) { // 帮助命令
// 显示命令语法，不需要密码
          out.print("COMMANDS:\n" + "\tpassword <password>\n" +
                    "\tadd <service> <port>\n" + "\tremove <port>\n" +
                    "\tmax <max-connections>\n" + "\tstatus\n" + "\thelp\n" +
                    "\tquit\n");
        }
        else if (command.equals("quit"))
          break;
        else
          out.print("UNRECOGNIZED COMMAND\n"); // 出现错误
      }
      catch (Exception e) {
        out.print("ERROR WHILE PARSING OR EXECUTING COMMAND:\n" + e + "\n");
      }
    }
// 关闭流并把连接标志位设为false使得其它客户端连接
    connected = false;
    out.close();
    in.close();
  }
}
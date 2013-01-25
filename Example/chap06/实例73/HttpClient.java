import java.io.*;
import java.net.*;
/**
 * 这个程序可以连接到一个网络服务器并且能够从这个服务器下载指定的URL。在
 * 程序中直接使用HTTP协议
 */
public class HttpClient {
	public static void main(String[] args) {
		try {
			// 测试参数
			if ((args.length != 1) && (args.length != 2))
				throw new IllegalArgumentException("Wrong number of args");
			// 定义一个输出流，下载的URL的内容将来被写入这个流
			OutputStream to_file;
			if (args.length == 2) to_file = new FileOutputStream(args[1]);
			else to_file = System.out;
			// 现在使用URL类来把用户指定的URL解析成几个部分
			URL url = new URL(args[0]);
			String protocol = url.getProtocol();
			if (!protocol.equals("http")) // 检验是否满足我们支持的协议
				throw new IllegalArgumentException("Must use 'http:' protocol");
			String host = url.getHost();
			int port = url.getPort();
			if (port == -1) port = 80; //如果没有指定端口，用我们默认得端口
			String filename = url.getFile();
			// 打开一个连接到指定主机和端口的网络socket连接
			Socket socket = new Socket(host, port);
			// 通过socket来获得输入和输出流
			InputStream from_server = socket.getInputStream();
			PrintWriter to_server = new PrintWriter(socket.getOutputStream());
			
			// 发送HTTP GET命令给网络服务器，指定要下载的文件
			// 这里使用了一个老的版本非常简单的HTTP协议
			to_server.print("GET " + filename + "\n\n");
			to_server.flush(); // 立即发送
			// 现在读取服务器的响应，把接收到的内容写入文件
			byte[] buffer = new byte[4096];
			int bytes_read;
			while((bytes_read = from_server.read(buffer)) != -1)
				to_file.write(buffer, 0, bytes_read);
			// 当服务器关闭连接时，我们也关闭我们的stuff
			socket.close();
			to_file.close();
		}
		catch (Exception e) { // 发布引起的错误
			System.err.println(e);
			System.err.println("Usage: java HttpClient <URL> [<filename>]");
		}
	}
}
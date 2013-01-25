import java.io.*;
import java.net.*;
/**
 * 这个程序是一个非常简单的Web Server。当它接受到一个HTTP请求的时候，它发送
 * 这个请求给请求者作为应答。当你想看一个Web客户端是否正在请求时，这将是很有
 * 有趣的。
 */
public class HttpMirror {
	public static void main(String args[]) {
		try {
			// 获取要监听的端口
			int port = Integer.parseInt(args[0]);
			// 创建一个ServerSocket来监听端口
			ServerSocket ss = new ServerSocket(port);
			// 现在进入一个无限循环，等待或者处理连接
			for(;;) {
				// 等待一个客户来连接。这个方法将被阻塞。
				// 当它返回时，这个socket将被连接到客户端
				Socket client = ss.accept();
				// 获取输入和输出流来同客户端交流
				BufferedReader in = new BufferedReader(
				new InputStreamReader(client.getInputStream()));
				PrintWriter out = new PrintWriter(client.getOutputStream());
				// 开始发送我们的应答，使用HTTP1.0协议
				out.print("HTTP/1.0 200 \n"); // 版本和状态编号
				out.print("Content-Type: text/plain\n"); // 数据类型
				out.print("\n"); // 头的底部
				// 现在，从客户端读取HTTP请求，再把这个请求发送回客户端作为
				// 我们应答的一部分。客户端并没有取消连接，所以我们永远不能
				// 得到EOF。在标头的最后发送了一个空行，所以当我们看到空行，
				// 就停止读取。这意味着我们不能反映POST请求的内容。注意，这个
				// readline()能够在unix, windows 和 Mac终端上正常工作。
				String line;
				while((line = in.readLine()) != null) {
					if (line.length() == 0) break;
					out.print(line + "\n");
				}
				// 关闭socket，中断与客户端的连接，关闭输入和输出流
				out.close(); 
				in.close(); 
				client.close(); 
			} 
		}
		// 如果有错误发生，那么显示错误信息
		catch (Exception e) {
			System.err.println(e);
			System.err.println("Usage: java HttpMirror <port>");
		}
	}
}
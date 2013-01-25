import java.io.*;
import java.net.*;
/**
 * 这个类实现了一个简单的单线程的代理服务器
 */
public class SimpleProxyServer {
	/** 主函数解析参数并把参数传递给runServer */
	public static void main(String[] args) throws IOException {
		try {
			// 检查参数的个数
			if (args.length != 3)
				throw new IllegalArgumentException("Wrong number of args.");
			// 获得命令行参数: 我们要代理的主机和端口
			String host = args[0];
			int remoteport = Integer.parseInt(args[1]);
			int localport = Integer.parseInt(args[2]);
			// 显示开始信息
			System.out.println("Starting proxy for " + host + ":" +
				remoteport + " on port " + localport);
			// 开始运行这个服务器
			runServer(host, remoteport, localport); // 永不返回
		}
		catch (Exception e) {
			System.err.println(e);
			System.err.println("Usage: java SimpleProxyServer " +
				"<host> <remoteport> <localport>");
		}
	}

	/**
	 * 这个方法运行一个单线程的代理服务器，它在指定的本地端口上
	 * 代理一个主机上的远程端口。它用不返回。
	 */
	public static void runServer(String host, int remoteport, int localport)
		throws IOException {
		// 创建一个ServerSocket来监听连接
		ServerSocket ss = new ServerSocket(localport);
		// 为客户端到服务器和服务器到客户端创建缓冲区
		// 我们指定缓冲区为final，使它能够在下面的匿名类中能够被使用
		// 注意在每个方向上交换量上的假定
		final byte[] request = new byte[1024];
		byte[] reply = new byte[4096];
		// 这是一个用不返回的服务器，故进入一个无限循环
		while(true) {
			// 保持与客户端和与服务器连接的socket的变量
			Socket client = null, server = null;
			try {
				// 等待一个本地端口的连接
				client = ss.accept();
				// 获取客户流，把他们定义成final使得他们在下面的匿名
				// 类中能够被使用
				final InputStream from_client = client.getInputStream();
				final OutputStream to_client = client.getOutputStream();
				// 建立一个与真实服务器的连接
				// 如果我们不能连上服务器，发送一个错误给客户端
				// 取消连接，等待下次连接
				try { server = new Socket(host, remoteport); }
				catch (IOException e) {
					PrintWriter out = new PrintWriter(to_client);
					out.print("Proxy server cannot connect to " + host + ":"+
						remoteport + ":\n" + e + "\n");
					out.flush();
					client.close();
					continue;
				}
				// 获得服务器的流
				final InputStream from_server = server.getInputStream();
				final OutputStream to_server = server.getOutputStream();
				// 创建一个线程读取客户端的请求，把请求传递给服务器，我们不得不
				// 使用一个独立的线程，因为请求和响应是异步的
				Thread t = new Thread() {
					public void run() {
						int bytes_read;
						try {
							while((bytes_read=from_client.read(request))!=-1) {
								to_server.write(request, 0, bytes_read);
								to_server.flush();
							}
						}
						catch (IOException e) {}
						// 客户端关闭了同我们的连接，那么也就关闭了我们与服务器的
						// 连接。这将造成主线程中服务器与客户端之间的循环退出
						try {to_server.close();} catch (IOException e) {}
					}
				};
				// 开始客户端到服务器请求线程的运行
				t.start();
				// 同时，在主线程中，读取服务器的响应，并把它们传递给客户端。这将
				// 与上面提到的客户端到服务器的请求线程并行执行
				int bytes_read;
				try {
					while((bytes_read = from_server.read(reply)) != -1) {
						to_client.write(reply, 0, bytes_read);
						to_client.flush();
					}
				}
				catch(IOException e) {}
				// 服务器关闭同我们的连接，那么我们关闭与客户端的连接
				// 这将使另外一个线程退出
				to_client.close();
			}
			catch (IOException e) { System.err.println(e); }
			finally { // 不管发生什么关闭线程
				try {
					if (server != null) server.close();
					if (client != null) client.close();
				}
				catch(IOException e) {}
			}
		}
	}
}

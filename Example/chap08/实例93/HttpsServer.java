package javasecurity;
import java.io.*;
import java.net.*;
import javax.net.*;
import javax.net.ssl.*;
import java.security.*;
import java.util.*;
public class HttpsServer
{
	// keystore文件名
	String keystore = "serverkeys";
	// keystore密码 
	char keystorepass[] = "hellothere".toCharArray();
	// key密码
	char keypassword[] = "hiagain".toCharArray();
	// 使用缺省的433端口
	public static final int HTTPS_PORT = 443;
	public ServerSocket getServer() throws Exception
	{
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(new FileInputStream(keystore), keystorepass);
		KeyManagerFactory kmf = 
			KeyManagerFactory.getInstance("SunX509");
		kmf.init(ks, keypassword);
		SSLContext sslcontext = SSLContext.getInstance("SSLv3");
		sslcontext.init(kmf.getKeyManagers(), null, null);
		ServerSocketFactory ssf = 
			sslcontext.getServerSocketFactory();
		// 建立SSL Socket连接
		SSLServerSocket serversocket =
			(SSLServerSocket) ssf.createServerSocket(HTTPS_PORT);
		return serversocket;
	}
	public void run()
	{
		ServerSocket listen;
		try
		{
			// 为每一个连接建立线程
			listen = getServer();
			while (true)
			{
				Socket client = listen.accept();
				ProcessConnection cc = new ProcessConnection(client);
			}
		}
		catch (Exception e)
		{
			System.out.println("Exception: " + e.getMessage());
		}
	}
	public static void main(String argv[]) throws Exception
	{
		HttpsServer https = new HttpsServer();
		https.run();
	}
}
class ProcessConnection extends Thread
{
	Socket client;
	BufferedReader is;
	DataOutputStream os;
	public ProcessConnection(Socket s)
	{ 
		client = s;
		try
		{
			// 客户端请求流
			is =
				new BufferedReader(
					new InputStreamReader(client.getInputStream()));
			// 服务器响应流 
			os = new DataOutputStream(client.getOutputStream());
		}
		catch (IOException e)
		{
			System.out.println("Exception: " + e.getMessage());
		}
		this.start();
	}
	public void run()
	{
		try
		{
			// 得到客户端请求并解析请求
			String request = is.readLine();
			System.out.println("Request: " + request);
			StringTokenizer st = new StringTokenizer(request);
			if ((st.countTokens() >= 2) && 
					st.nextToken().equals("GET"))
			{
				if ((request = st.nextToken()).startsWith("/"))
					request = request.substring(1);
				if (request.equals(""))
					request = request + "index.html";
				File f = new File(request);
				shipDocument(os, f);
			}
			else
			{
				os.writeBytes("400 Bad Request");
			}
			client.close();
		}
		catch (Exception e)
		{
			System.out.println("Exception: " + e.getMessage());
		}
	}
	public static void shipDocument(DataOutputStream out, File f)
		throws Exception
	{
		try
		{
			DataInputStream in = 
				new DataInputStream	(new FileInputStream(f));
			int len = (int) f.length();
			byte[] buf = new byte[len];
			in.readFully(buf);
			in.close();
			out.writeBytes("HTTP/1.0 200 OK\r\n");
			out.writeBytes("Content-Length: " + f.length() + "\r\n");
			out.writeBytes("Content-Type:text/html\r\n\r\n");
			out.write(buf);
			out.flush();
		}
		catch (Exception e)
		{
			// 回写错误信息
			out.writeBytes("<html><head><title>error</title>"+
					"</head><body>\r\n\r\n");
			out.writeBytes("HTTP/1.0 400 " + e.getMessage() + "\r\n");
			out.writeBytes("Content-Type: text/html\r\n\r\n");
			out.writeBytes("</body></html>");
			out.flush();
		}
		finally
		{
			out.close();
		}
	}
}

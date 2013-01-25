package javasecurity;
import java.io.*;
import java.net.*;
import java.security.KeyStore;
import javax.net.*;
import javax.net.ssl.*;
import javax.security.cert.X509Certificate;
public class ClassFileServer extends ClassServer
{
	private String docroot;
	private static int DefaultServerPort = 2001;
	public ClassFileServer(ServerSocket ss, String docroot) 
		throws IOException
	{
		super(ss);
		this.docroot = docroot;
	}
	public byte[] getBytes(String path) throws IOException
	{
		System.out.println("reading: " + path);
		File f = new File(docroot + File.separator + path);
		int length = (int) (f.length());
		if (length == 0)
		{
			throw new IOException("File length is zero: " + path);
		}
		else
		{
			FileInputStream fin = new FileInputStream(f);
			DataInputStream in = new DataInputStream(fin);
			byte[] bytecodes = new byte[length];
			in.readFully(bytecodes);
			return bytecodes;
		}
	}
	public static void main(String args[])
	{
		System.out.println
			("USAGE: java ClassFileServer port docroot [TLS[true]]");
		System.out.println("");
		System.out.println(
			"If the third argument is TLS, it will start as\n"
				+ "a TLS/SSL file server, otherwise, it will be\n"
				+ "an ordinary file server. \n"
				+ "If the fourth argument is true,it will require\n"
				+ "client authentication as well.");
		int port = DefaultServerPort;
		String docroot = "";
		if (args.length >= 1)
		{
			port = Integer.parseInt(args[0]);
		}
		if (args.length >= 2)
		{
			docroot = args[1];
		}
		String type = "PlainSocket";
		if (args.length >= 3)
		{
			type = args[2];
		}
		try
		{
			ServerSocketFactory ssf =
				ClassFileServer.getServerSocketFactory(type);
			ServerSocket ss = ssf.createServerSocket(port);
			if (args.length >= 4 && args[3].equals("true"))
			{
				((SSLServerSocket) ss).setNeedClientAuth(true);
			}
			new ClassFileServer(ss, docroot);
		}
		catch (IOException e)
		{
			System.out.println(
				"Unable to start ClassServer: " + e.getMessage());
			e.printStackTrace();
		}
	}
	private static ServerSocketFactory 
		getServerSocketFactory(String type)
	{
		if (type.equals("TLS"))
		{
			SSLServerSocketFactory ssf = null;
			try
			{
				// 建立用于服务器认证的钥匙管理 
				SSLContext ctx;
				KeyManagerFactory kmf;
				KeyStore ks;
				// 设置密码，为方便起见放在代码中
				char[] passphrase = "passphrase".toCharArray();
				ctx = SSLContext.getInstance("TLS");
				// 采用SunX509加密算法
				kmf = KeyManagerFactory.getInstance("SunX509");
				// 钥匙存储名字.可以用keytool工具产生  
				ks = KeyStore.getInstance("JKS");  
				// 得到钥匙文件
				ks.load(new FileInputStream("testkeys"),passphrase);
				// 存储钥匙文件.
				kmf.init(ks, passphrase);
				ctx.init(kmf.getKeyManagers(), null, null);
				ssf = ctx.getServerSocketFactory();
				return ssf;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			return ServerSocketFactory.getDefault();
		}
		return null;
	}
}

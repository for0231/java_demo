package javasecurity;
import java.io.*;
import java.net.*;
import javax.net.*;
public abstract class ClassServer implements Runnable
{
	private ServerSocket server = null;
	protected ClassServer(ServerSocket ss)
	{
		server = ss;
		newListener();
	}
	public abstract byte[] getBytes(String path)
		throws IOException, FileNotFoundException;
	public void run()
	
	{
		Socket socket;
		// 开始接收连接 
		try
		{
			socket = server.accept();
		}
		catch (IOException e)
		{
			System.out.println
				("Class Server died: " + e.getMessage());
			e.printStackTrace();
			return;
		}
		// 为下一个连接新建一个线程 
		newListener();
		try
		{
			DataOutputStream out =
				new DataOutputStream(socket.getOutputStream());
			try
			{
				// 得到路径信息 
				BufferedReader in =
					new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				String path = getPath(in);
				// 接收字节 
				byte[] bytecodes = getBytes(path);
				// 发送字节 
				try
				{
					out.writeBytes("HTTP/1.0 200 OK\r\n");
					out.writeBytes
						("Content-Length: "+bytecodes.length+"\r\n");
					out.writeBytes("Content-Type: text/html\r\n\r\n");
					out.write(bytecodes);
					out.flush();
				}
				catch (IOException ie)
				{
					ie.printStackTrace();
					return;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				// 回写错误信息 
				out.writeBytes
					("HTTP/1.0 400 " + e.getMessage() + "\r\n");
				out.writeBytes
					("Content-Type: text/html\r\n\r\n");
				out.flush();
			}
		}
		catch (IOException ex)
		{
			System.out.println
				("error writing response: " + ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				socket.close();
			}
			catch (IOException e)
			{
			}
		}
	}
	private void newListener()
	{
		(new Thread(this)).start();
	}
	private static String getPath(BufferedReader in) 
		throws IOException
	{
		String line = in.readLine();
		System.out.println("line ===============" + line);
		String path = "";
		// 从GET这一行中抽取信息
		if (line.startsWith("GET /"))
		{
			line = line.substring(5, line.length() - 1).trim();
			int index = line.indexOf(' ');
			if (index != -1)
			{
				path = line.substring(0, index);
			}
		}
		// 跳过文件头
		do
		{
			line = in.readLine();
		}
		while ((line.length() != 0)
			&& (line.charAt(0) != '\r')
			&& (line.charAt(0) != '\n'));
		if (path.length() != 0)
		{
			return path;
		}
		else
		{
			throw new IOException("Malformed Header");
		}
	}
}

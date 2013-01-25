package javasecurity;
import java.net.*;
import java.io.*;
import javax.net.ssl.*;
public class SSLSocketClient
{
	public static void main(String[] args) throws Exception
	{
		try
		{
			// 建立SSL Socket连接的Factory
			SSLSocketFactory factory =
				(SSLSocketFactory) SSLSocketFactory.getDefault();
			// 建立与localhost 2001端口的连接
			SSLSocket socket =
				(SSLSocket) factory.createSocket("localhost", 2001);
			socket.startHandshake();
			PrintWriter out =
				new PrintWriter(
					new BufferedWriter(
						new OutputStreamWriter
							(socket.getOutputStream())));
			// 发送请求
			out.println
				("GET http://localhost:2001/index.html HTTP/1.1");
			out.println();
			out.flush();
			
			if (out.checkError())
				System.out.println(
					"SSLSocketClient: java.io.PrintWriter error");
			// 读取回应信息 
			BufferedReader in =
				new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				System.out.println(inputLine);
			in.close();
			out.close();
			socket.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

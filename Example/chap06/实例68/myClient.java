import java.net.*;
import java.io.*;
// 这个是客户端程序

public class myClient
{
	public static void main(String[] args) 
	                      throws Exception
	{
		// 我们通过本机测试这个程序,所以用null表示本机
		// 得到本机ip
		InetAddress addr = InetAddress.getByName(null);
		// 下面的语句和上面的是等效的
		// InetAddress addr = InetAddress.getByName("localhost");
		// InetAddress addr = InetAddress.getByName("127.0.0.1");
		System.out.println("这台机子的ip地址是:"+addr);
		// 请求和服务器建立一个socket连接
		Socket socket = new Socket(addr, myServer.PORT);
		try{
			System.out.println("套接字(socket) = " + socket);
			BufferedReader in = 
			    new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out =
			   new PrintWriter(
			    new BufferedWriter(
			    	new OutputStreamWriter(socket.getOutputStream())),true);
		 for(int i=0; i<5; i++){
		   out.println("你好"+i);
		   String str = in.readLine();
		   System.out.println(str);
		 }
		 out.println("END");
		}
		
		catch(IOException e){
		 System.out.println(e.toString());
		}
		
		finally{
		  System.out.println("closing...");
		  socket.close();
		}
	}
}
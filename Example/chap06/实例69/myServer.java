import java.net.*;
import java.io.*;
// 这个是服务器端程序

public class myServer
{
	// 设定服务程序端口号,大于1024
	public static final int PORT = 8080;
	public static void main(String[] args)
	   throws IOException
	{
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Started:"+s);
		try{
		 Socket socket = s.accept();
		 try{
		   System.out.println("连接被接受"+socket);
		   BufferedReader in =
		    new BufferedReader(new InputStreamReader(socket.getInputStream()));
		   PrintWriter out = 
		    new PrintWriter(
		     new BufferedWriter(
		    	new OutputStreamWriter(socket.getOutputStream())),true);
		   while(true){
		   	 String str = in.readLine();
		   	 if(str.endsWith("END")) break;
		   	 System.out.println("Echoing: "+str);
		   	 out.println(str);
		   	} 
		   }
		   catch(Exception e)
		    { System.out.println(e.toString());}
		 finally{
		    System.out.println("closing ...");
		    socket.close();
		  }
		}
		finally{
		 s.close();
		}
	}
}
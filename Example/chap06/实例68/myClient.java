import java.net.*;
import java.io.*;
// ����ǿͻ��˳���

public class myClient
{
	public static void main(String[] args) 
	                      throws Exception
	{
		// ����ͨ�����������������,������null��ʾ����
		// �õ�����ip
		InetAddress addr = InetAddress.getByName(null);
		// ���������������ǵ�Ч��
		// InetAddress addr = InetAddress.getByName("localhost");
		// InetAddress addr = InetAddress.getByName("127.0.0.1");
		System.out.println("��̨���ӵ�ip��ַ��:"+addr);
		// ����ͷ���������һ��socket����
		Socket socket = new Socket(addr, myServer.PORT);
		try{
			System.out.println("�׽���(socket) = " + socket);
			BufferedReader in = 
			    new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out =
			   new PrintWriter(
			    new BufferedWriter(
			    	new OutputStreamWriter(socket.getOutputStream())),true);
		 for(int i=0; i<5; i++){
		   out.println("���"+i);
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
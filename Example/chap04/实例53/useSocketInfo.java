import java.net.*; 
import java.io.*; 
public class useSocketInfo 
{
public static void main(String[] args) {
for (int i = 0; i < args.length; i++) {
try {
// ����Socket����
Socket theSocket = new Socket(args[i], 80); 
// ����˿���Ϣ
System.out.println("���ӵ� " + theSocket.getInetAddress()
+ " on port "  + theSocket.getPort() + " from port " 
+ theSocket.getLocalPort() + " of " + theSocket.getLocalAddress());
}  // end try
catch (UnknownHostException e) 	{
System.err.println("�����ҵ� " + args[i]); 
}
catch (SocketException e) {
System.err.println("Could not connect to " + args[i]); 
}
catch (IOException e) 	{
System.err.println(e); 
}
}
}
}

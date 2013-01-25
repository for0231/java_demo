import java.net.*;
public class SimpleUDP{
public static void main(String[] main)
throws UnknownHostException,SocketException, java.io.IOException
{
int port=5264;
// ����datagram �׽���
DatagramSocket socket=new DatagramSocket(port);
socket.setSoTimeout(5000);
// ����datagram payload��localhost
String outMessage="Hello UDP world!";
byte[] data=outMessage.getBytes();
DatagramPacket packet=new DatagramPacket(data,data.length,
InetAddress.getByName("localhost"),port);
// ����datagram
System.out.println("Sending message:"+outMessage);
socket.send(packet);
// ׼������datagram
packet.setData(new byte[512]);
packet.setLength(512);
// ���� datagram(���ܳ�ʱ)
System.out.println("Waiting for datagram...");
socket.receive(packet);
// ��ӡ���
String inMessage=new String(packet.getdata(),0,packet.getLength());
System.out.println("Received message:"+inMessage);
socket.close();
}
}

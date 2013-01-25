import java.net.*;
public class SimpleUDP{
public static void main(String[] main)
throws UnknownHostException,SocketException, java.io.IOException
{
int port=5264;
// 创建datagram 套接字
DatagramSocket socket=new DatagramSocket(port);
socket.setSoTimeout(5000);
// 创建datagram payload和localhost
String outMessage="Hello UDP world!";
byte[] data=outMessage.getBytes();
DatagramPacket packet=new DatagramPacket(data,data.length,
InetAddress.getByName("localhost"),port);
// 发送datagram
System.out.println("Sending message:"+outMessage);
socket.send(packet);
// 准备接收datagram
packet.setData(new byte[512]);
packet.setLength(512);
// 接收 datagram(可能超时)
System.out.println("Waiting for datagram...");
socket.receive(packet);
// 打印结果
String inMessage=new String(packet.getdata(),0,packet.getLength());
System.out.println("Received message:"+inMessage);
socket.close();
}
}

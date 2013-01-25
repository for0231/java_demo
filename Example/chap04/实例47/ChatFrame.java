package chat; 
import java.net.*; 
import java.io.*; 
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
import com.borland.jbcl.layout.*; 
public class ChatFrame extends JFrame {// 继承Frame类
JPanel contentPane; 
Label label1 = new Label();
Label label2 = new Label();
TextField textField1 = new TextField();
TextField textField2 = new TextField();
Button button1 = new Button();
TextArea textArea1 = new TextArea();
// 构建框架
DatagramPacket sendpacket,receivepacket;//声明发送和接收数据包
DatagramSocket sendsocket,receivesocket;//声明发送和接收DatagramSocket
public ChatFrame() {// 构造函数
enableEvents(AWTEvent.WINDOW_EVENT_MASK); 
try {
jbInit();
}
catch(Exception e) {
e.printStackTrace();
}
}

// 组件初始化
private void jbInit() throws Exception {
contentPane = (JPanel) this.getContentPane();
label1.setText("发送信息：");
label1.setBounds(new Rectangle(13, 59, 60, 21)); 
contentPane.setLayout(null); 
this.setSize(new Dimension(363, 275)); 
this.setTitle("利用UDP实现聊天室");
label2.setText("请输入对方IP地址：");
label2.setBounds(new Rectangle(12, 19, 109, 23)); 
try {
sendsocket=new DatagramSocket();//初始化
receivesocket=new DatagramSocket(5001); 
}
catch(SocketException se){ 
se.printStackTrace() ; 
System.exit(0); 
}
button1.setLabel("发送");
button1.setBounds(new Rectangle(280, 59, 59, 21)); 
button1.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(ActionEvent e) {
button1_actionPerformed(e); 
}
});
textField2.setBounds(new Rectangle(123, 20, 144, 19)); 
textArea1.setBounds(new Rectangle(11, 96, 333, 134)); 
textField1.setBounds(new Rectangle(76, 57, 191, 19)); 
contentPane.add(label2, null); 
contentPane.add(label1, null); 
contentPane.add(textField1, null); 
contentPane.add(button1, null); 
contentPane.add(textField2, null); 
contentPane.add(textArea1, null); 
}

// 使得在窗口被关闭时可以退出
protected void processWindowEvent(WindowEvent e) {
super.processWindowEvent(e); 
if (e.getID() == WindowEvent.WINDOW_CLOSING) {
System.exit(0); 
}
}

// 等待数据
public void waitforpackets()
{
while(true){ 
try{
byte[]array=new byte[100]; 
receivepacket=new DatagramPacket(array,array.length ); 
receivesocket.receive(receivepacket);     // 接收数据
textArea1.append("\nfrom "+receivepacket.getAddress() +" : ");
byte data[]=receivepacket.getData() ; // 得到数据
String received=new String(data,0); 
textArea1.append(received);  //显示数据
}
catch (IOException se){ 
textArea1.append(se.toString() +"\n");
se.printStackTrace() ; 
}
}
}

// 按钮事件处理代码
void button1_actionPerformed(ActionEvent e) {
String str=textField2.getText(); //获得文本框数据
if(str.compareTo("")!=0 ){ // 如果为空
try {
textArea1.append("\nto "+textField2.getText() +" : "+textField1.getText() ); 
String s=textField1.getText() ; 
byte data[]=new byte[100]; 
s.getBytes(0,s.length() ,data,0); 
sendpacket=new 
DatagramPacket(data,s.length() ,InetAddress.getByName(textField2.getText()),5000); 
sendsocket.send(sendpacket); 
}
catch(IOException exc){ 
textArea1.append(exc.toString() +"\n");
exc.printStackTrace() ; 
}
}
else
textArea1.setText("please input your friend's IP first!"); 
}
}

package sendandrecievedata; 
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
import java.net.*; 
public class sendandrecievedataInMGFrame extends JFrame {
private JPanel contentPane; 
private TextField textField1 = new TextField();
private Button button1 = new Button();
private Label label1 = new Label();
private Label label2 = new Label();
int port;                               // �����鲥ʹ�õĶ˿�
MulticastSocket socket;                  // ���������鲥��ʹ�õ�MulticastSocket��
InetAddress group;                      // ���������鲥��ʹ�õ��鲥���ַ
DatagramPacket packet;                  // �������ͺͽ���������ʹ�õ�DatagramPacket��

//���촰��
public sendandrecievedataInMGFrame() {
enableEvents(AWTEvent.WINDOW_EVENT_MASK); 
try {
jbInit();
}
catch(Exception e) {
e.printStackTrace();
}
}

// �ؼ���ʼ��
private void jbInit() throws Exception{
contentPane = (JPanel) this.getContentPane();            //��ƴ��岼��
textField1.setBounds(new Rectangle(88, 127, 240, 32)); 
contentPane.setLayout(null); 
this.setSize(new Dimension(400, 300)); 
this.setTitle("�鲥���з��ͺͽ�������");
button1.setLabel("��������");
button1.setBounds(new Rectangle(166, 186, 88, 29)); 
button1.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(ActionEvent e) {
button1_actionPerformed(e); 
}
});
    label1.setBounds(new Rectangle(88, 84, 240, 32)); 
    label2.setBounds(new Rectangle(88, 41, 220, 32)); 
    contentPane.add(textField1, null); 
    contentPane.add(button1, null); 
    contentPane.add(label1, null); 
    contentPane.add(label2, null); 
    createMulticastGroupAndJoin();
}
// �رմ���ʱ�˳�
protected void processWindowEvent(WindowEvent e) {
super.processWindowEvent(e); 
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
        System.exit(0); 
    }
}

public void createMulticastGroupAndJoin()          // ����һ���鲥�鲢�������ĺ���
{
try {
        port = 5000;                               // �����鲥��ļ����˿�Ϊ5000
        group = InetAddress.getByName("239.0.0.0");    // �����鲥��ĵ�ַΪ239.0.0.0
        socket = new MulticastSocket(port);   // ��ʼ��MulticastSocket�ಢ���˿ں���֮����
        socket.setTimeToLive(1);                // �����鲥���ݱ��ķ��ͷ�ΧΪ��������
        socket.setSoTimeout(10000);             // �����׽��ֵĽ������ݱ����ʱ��
        socket.joinGroup(group);              // ������鲥��
        label2.setText("�Ѽ����ַΪ239.0.0.0���鲥��");
}
    catch(Exception e1) {
        System.out.println("Error: " + e1);               // ��׽�쳣���
    }
}

public void sendData()                               // ���鲥�鷢�����ݵĺ���
{
try {
byte[] data = textField1.getText().getBytes(); // ���û�Ҫ���͵�����ת��Ϊ�ֽ���ʽ��
// �洢��������
packet = new DatagramPacket(data, data.length, group, port); // ��ʼ��DatagramPacket
socket.send(packet);         // ͨ��MulticastSocketʵ���˿����鲥�鷢������
}
catch(Exception e1) 
{
System.out.println("Error: " + e1);                   // ��׽�쳣���
}
}

public String recieveData()                             // ���鲥��������ݵĺ���
{
String message; 
try {
packet.setData(new byte[512]);  // �趨�������ݵ�DatagramPacketʵ���������С
packet.setLength(512);   // �趨�������ݵ�DatagramPacketʵ���ĳ���
socket.receive(packet);       // ͨ��MulticastSocketʵ���˿ڴ��鲥���������
// �����ܵ�����ת�����ַ�����ʽ
}
catch(Exception e1) {
System.out.println("Error: " + e1);                   // ��׽�쳣���
message = "Error: " + e1; 
}
return message; 
}

// ��ť1�¼�����
void button1_actionPerformed(ActionEvent e) {
sendData();                                             // �������鲥�鷢������
String message = recieveData();                         // Ȼ����鲥����ܴ�����
label1.setText("Data recieved: '" + message + "'");     // ������ʾ���յ�������
}
}

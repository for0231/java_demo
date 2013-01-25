package chat; 
import java.net.*; 
import java.io.*; 
import javax.swing.UIManager; 
import java.awt.*; 

public class ChatApp {
boolean packFrame = false; 
// ����Ӧ�ó���
public ChatApp() {
ChatFrame frame = new ChatFrame();
// ������Ƿ����Ԥ�趩�Ĵ�С
if (packFrame) {
frame.pack();
}
else {
frame.validate();
}
// �Ѵ�����������
Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
Dimension frameSize = frame.getSize();
if (frameSize.height > screenSize.height) {
frameSize.height = screenSize.height; 
}
if (frameSize.width > screenSize.width) {
frameSize.width = screenSize.width; 
}
frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - 
frameSize.height) / 2); 
frame.setVisible(true); 
frame.waitforpackets();
}

// Main ����
public static void main(String[] args) {
try {
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
}
catch(Exception e) {
e.printStackTrace();
}
new ChatApp();
}
}

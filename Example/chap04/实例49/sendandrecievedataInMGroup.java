package sendandrecievedata; 
import javax.swing.UIManager; 
import java.awt.*; 
public class sendandrecievedataInMGroup {
private boolean packFrame = false; 

// ����Ӧ�ó���
public sendandrecievedataInMGroup() {
sendandrecievedataInMGFrame frame = new sendandrecievedataInMGFrame();
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
}

// Main ����
public static void main(String[] args) {
try {
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
}
catch(Exception e) {
e.printStackTrace();
}
 new sendandrecievedataInMGroup();
}
}

package sendandrecievedata; 
import javax.swing.UIManager; 
import java.awt.*; 
public class sendandrecievedataInMGroup {
private boolean packFrame = false; 

// 构建应用程序
public sendandrecievedataInMGroup() {
sendandrecievedataInMGFrame frame = new sendandrecievedataInMGFrame();
    if (packFrame) {
        frame.pack();
    }
    else {
        frame.validate();
    }
    // 把窗口置于中心
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

// Main 方法
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

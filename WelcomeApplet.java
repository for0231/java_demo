import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomeApplet extends Applet implements ActionListener
{
  Label lblName;
  TextField txtName;
  TextField txtDisp;

  public void init()
  {
    lblName = new Label("请输入您的名字");
    txtName = new TextField(8);
    txtDisp = new TextField(20);
    add(lblName);
    add(txtName);
    add(txtDisp);
    txtName.addActionListener(this);
  }
  
  public void actionPerformed(ActionEvent e) 
  {
    txtDisp.setText(txtName.getText() + "欢迎你来到Java世界");
  }

  public static void main(String args[]) 
  {
    Frame f = new Frame("欢迎");
    f.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        System.exit(0);
      }
    });
    WelcomeApplet a = new WelcomeApplet();
    a.init();
    f.add("Center", a);
    f.setSize(400, 300);
    f.show();
    a.start();
  }
}

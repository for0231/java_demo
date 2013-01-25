import java.awt.*;
import java.applet.event.*;
import java.applet.*;
import java.applet.Applet;

public class UseButton extends Applet implements ActionListener
{
  String str1 =  new String();
  // 声明按钮对象
  Button b1,b2;
  Color c;
  public void init()
  {
    // 构造对象
    b1 = new Button();
    b2 = new Button("按钮对象2");
    // 添加事件监听者
    b1.addActionListener(this);
    b2.addActionListener(this);
    // 添加到界面
    this.add(b1);
    this.add(b2);
  }

  public void start()
  {
    b1.setLabel("按钮对象1");
    str1 = b2.getLabel();
    repaint();
  }

  public void paint(Graphics g)
  {
    g.setColor(c);
    g.drawString(str1, 20, 30);
  }

  public void actionPerformed(ActionEvent e)
  {
    String arg = e.getActionCommand();
    if (arg == "按钮对象1")
    {
      c = Color.red;
      str1 = "按钮对象1";
    }
    else if (arg == "按钮对象2")
    {
      c = Color.blue;
      str1 = "按钮对象2";
    }
    repaint();
  }
}

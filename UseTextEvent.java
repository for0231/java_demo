import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.applet.Applet;
public class UseTextEvent extends Applet implements ActionListener, TextListener
{
  TextField tOld;
  TextArea  tNew;
  Panel p;
  public void init()
  {
    tOld = new TextField(25);
    tNew = new TextArea(8,25);
    // 添加事件监听者
    tOld.addActionListener(this);
    tOld.addTextListener(this);
    // 设置界面
    p = new Panel(new BorderLayout());
    p.add(tOld, BorderLayout.NORTH);
    p.add(tNew, BorderLayout.SOUTH);
    add(p);
  }

  // 响应文本事件
  public void textValueChanged(TextEvent e)
  {
    if (e.getSource() == tOld)
    {
      tNew.setText(tOld.getText());
    }
  }

  // 响应动作事件
  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == tOld)
    {
      tNew.setText("");
    }
  }
}

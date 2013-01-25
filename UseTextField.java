import java.awt.*;
import java.applet.*;
import java.applet.Applet;
public class UseTextField extends Applet
{
  String str1 = new String();
  int i1 = 0, i2 = 0;
  TextField tf1, tf2, tf3, tf4;
  public void init()
  {
    tf1 = new TextField();
    tf2 = new TextField(20);
    tf3 = new TextField("文本对象３");
    tf4 = new TextField("文本对象４");
    add(tf1);
    add(tf2);
    add(tf3);
    add(tf4);
  }

  public void start()
  {
    tf1.setText("文本对象１");
    tf2.setText("文本对象２");
    str1 = tf3.getText();
    i1 = tf3.getColumns();
    i2 = tf4.getColumns();
    tf4.setEditable(false);
    repaint();
  }
  public void paint(Graphics g)
  {
    g.drawString("第三个对象的文本内容为:　" + str1, 20, 140);
    g.drawString("第三个对象的列数为: " + i1, 20, 160);
    g.drawString("第四个对象的列数为: " + i2, 20, 180);
  }
}

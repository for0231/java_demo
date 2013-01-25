import java.awt.*;
import java.applet.*;
import java.applet.Applet;
public class UseLabel extends Applet
{
  String str1 = new String();
  int i1 = 0;
  Label l1; //声明对象
  Label l2;
  Label l3;
  public void init()
  {
    l1 = new Label();
    l2 = new Label("标签对象２");
    l3 = new Label("标签对象３", Label.CENTER);
    this.add(l1);
    this.add(l2);
    this.add(l3);
  }

  public void start()
  {
    l1.setText("标签对象１");
    str1 = l2.getText();
    i1 = l3.getAlignment();
    repaint();
  }

  public void paint(Graphics g)
  {
    g.drawString("获取第二个对象的文本: " + str1, 40, 60);
    g.drawString("标签对象３的对齐方式: " + i1, 40, 80);
  }
}

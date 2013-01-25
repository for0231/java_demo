import java.awt.*;
import java.applet.*;
import java.applet.Applet;
public class UseScrollbar extends Applet
{
  int i1 = 0;
  int i2 = 0;
  int i3 = 0;
  int i4 = 0;
  int i5 = 0;
  Scrollbar s1; //声明对象
  Scrollbar s2;
  Scrollbar s3;
  public void init()
  {
    s1 = new Scrollbar();
    s2 = new Scrollbar(Scrollbar.HORIZONTAL);
    s3 = new Scrollbar(Scrollbar.VERTICAL, 50, 0, 10, 500);
    this.add(s1);
    this.add(s2);
    this.add(s3);
  }
  public void start()
  {
    i1 = s1.getOrientation();
    i2 = s2.getOrientation();
    i3 = s3.getValue();
    i4 = s3.getMinimum();
    i5 = s3.getMaximum();
    repaint();
  }

  public void paint(Graphics g)
  {
    g.drawString("第一个对象的方向: " + i1, 40,80);
    g.drawString("第二个对象的方向: " + i2, 40,100);
    g.drawString("第三个对象的滑块值: " + i3, 40,120);
    g.drawString("第三个对象的最小值: " + i4, 40, 140);
    g.drawString("第三个对象的最大值: " + i5, 40, 160);
  }
}

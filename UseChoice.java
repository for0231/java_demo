import java.awt.*;
import java.applet.*;
import java.applet.Applet;
public class UseChoice extends Applet
{
  String str1 = new String();
  String str2 = new String();
  int count = 0;
  int i1 = 0;

  boolean b1 = false;
  Choice c1; // 声明组合框对象
  public void init()
  {
    // 初始化组合框对象
    c1 = new Choice();
    c1.add("语文");
    c1.add("数学");
    c1.add("物理");
    c1.add("化学");
    c1.add("生物");
    c1.select(4);
    this.add(c1);
  }
  public void start()
  {
    
    count = c1.getItemCount();
    str1  = c1.getItem(2);
    str2  = c1.getSelectedItem();
    i1    = c1.getSelectedIndex();
    operate();
    repaint();
  }

  public void paint(Graphics g)
  {
    g.drawString("元素总个数为: " + count, 10,80);
    g.drawString("第２项元素为: " + str1, 10, 100);
    g.drawString("选中项的元素为: " + str2, 10, 120);
    g.drawString("选中项的位置为: " + i1, 10, 140);
  }

  public void operate()
  {
    c1.insert("英语", 3);
    c1.remove("生物");
  }
}

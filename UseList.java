import java.awt.*;
import java.applet.*;
import java.applet.Applet;
public class UseList extends Applet
{
  String str1 = new String();
  String str2 = new String();
  int i1 = 0, i2 = 0;
  List l1,l2,l3;
  public void init()
  {
    l1 = new List();
    l2 = new List(5);
    l3 = new List(5, true);
    // 列表添加内容
    l1.add("苹果");
    l1.add("香蕉");
    l1.add("梨");
    l2.add("语文");
    l2.add("数学");
    l2.add("英语");
    l2.add("化学");
    l3.add("钢笔");
    l3.add("铅笔");
    l1.select(1);
    l3.select(1);
    this.add(l1);
    this.add(l2);
    this.add(l3);
  }

  public void start()
  {
    str1 = l1.getItem(2);
    i1 = l1.getItemCount();
    l2.replaceItem("英语", 2);
    str2 = l3.getSelectedItem();
    repaint();
  }

  public void paint(Graphics g)
  {
    g.drawString("第一个对象的索引为２的元素: " + str1, 40, 100);
    g.drawString("第一个对象的元素个数: " + i1, 40, 120);
    g.drawString("第三个对象选中的元素为: " + str2, 40, 140);
  }
}

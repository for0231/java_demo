import java.awt.*;
import java.applet.*;
import java.applet.Applet;

public class UseTextArea extends Applet
{
  String str1 = new String();
  String str2 = new String();
  int i1,i2,i3;
  TextArea t1,t2,t3,t4,t5; //声明对象
  public void init()
  {
    t1 = new TextArea();
    t2 = new TextArea(2, 20);
    t3 = new TextArea("文本区域对象3");
    t4 = new TextArea("文本区域对象4", 3, 10);
    t5 = new TextArea("文本区域对象5", 2, 24, TextArea.SCROLLBARS_BOTH);
    this.add(t1);
    this.add(t2);
    this.add(t3);
    this.add(t4);
    this.add(t5);
  }

  public void start()
  {
    t1.setText("文本区域对象1");
    t2.append("文本区域对象2");
    t3.insert("\"插入3\"", 4);
    str1 = t3.getText();
    t4.replaceRange("\"替换\"", 2, 6);
    str2 = t4.getText();
    i1 = t4.getRows();
    i2 = t5.getColumns();
    i3 = t5.getScrollbarVisibility();
    //t5.setEditable(false);
    repaint();
  }

  public void paint(Graphics g)
  {
    g.drawString("第三个对象的文本内容为: " + str1, 40, 400);
    g.drawString("第四个对象的文本内容为: " + str2, 40, 420);
    g.drawString("第四个对象的行数为: " + i1, 40, 440);
    g.drawString("第五个对象的列数为: " + i2, 40, 460);
    g.drawString("第五个对象的滚动条情况为: " + i3, 40, 480);
  }
}

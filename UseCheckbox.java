import java.awt.*;
import java.applet.*;
import java.applet.Applet;
public class UseCheckbox extends Applet
{
  String str1 = new String();
  boolean b1 = false;
  boolean b2 = false;

  Checkbox c1,c2,c3;
  Checkbox cRadio1, cRadio2;
  CheckboxGroup c;
  public void init()
  {
    c1 = new Checkbox();
    c2 = new Checkbox("复选框对象2");
    c3 = new Checkbox("复选框对象3", true);
    // 构造单选按钮
    c = new CheckboxGroup();
    cRadio1 = new Checkbox("单选按钮1", c, false);
    cRadio2 = new Checkbox("单选按钮2", c, true);
    // 添加到界面
    this.add(c1);
    this.add(c2);
    this.add(c3);
    this.add(cRadio1);
    this.add(cRadio2);
  }
  public void start()
  {
    c1.setLabel("复选框对象1");
    str1 = c2.getLabel();
    b1 = c3.getState();
    b2 = cRadio1.getState();
    repaint();
  }
  public void paint(Graphics g)
  {
    g.drawString("获取第二个对象的标签: " + str1, 40, 80);
    g.drawString("复选框3的状态为: " + b1, 40, 100);
    g.drawString("单选按钮1的状态为: " + b2, 40, 120);
  }
}

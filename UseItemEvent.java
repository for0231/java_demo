import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.applet.Applet;
public class UseItemEvent extends Applet implements ItemListener
{
  Checkbox cDisp;
  Button btnDisp;
  Choice cFont;
  public void init()
  {
    cDisp = new Checkbox("红色");
    btnDisp = new Button("颜色显示");
    cFont = new Choice();
    cFont.add("10");
    cFont.add("12");
    cFont.add("14");
    // 添加事件
    cDisp.addItemListener(this);
    cFont.addItemListener(this);
    add(cDisp);
    add(cFont);
    add(btnDisp);
  }

  // 接口事件
  public void itemStateChanged(ItemEvent e)
  {
    Checkbox temp;
    Choice temp2;
    Font oldF;
    // 复选框
    if (e.getItemSelectable() instanceof Checkbox)
    {
      temp = (Checkbox)(e.getItemSelectable());
      // 选中为红色, 否则为蓝色
      if (temp.getState()) 
        btnDisp.setBackground(Color.red);
      else
        btnDisp.setBackground(Color.blue);
    }

    // 组合框
    if (e.getItemSelectable() instanceof Choice)
    {
      oldF = btnDisp.getFont();
      temp2 = (Choice)(e.getItemSelectable());
      String s = temp2.getSelectedItem();

      // 设置字体
      btnDisp.setFont(new Font(oldF.getName(), oldF.getStyle(), Integer.parseInt(s)));
    }
  }
}

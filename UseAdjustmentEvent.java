import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.applet.Applet;
public class UseAdjustmentEvent extends Applet implements AdjustmentListener
{
  Scrollbar s;
  TextArea txtValue;
  Panel p;
  public void init()
  {
    s = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 10, 36);
    // 添加监听者
    s.addAdjustmentListener(this);
    txtValue = new TextArea(5,25);
    // 界面布局
    p = new Panel(new BorderLayout());
    p.add(s, BorderLayout.NORTH);
    p.add(txtValue, BorderLayout.SOUTH);
    add(p);
  }

  public void start()
  {
    
  }

  public void adjustmentValueChanged(AdjustmentEvent e)
  {
    int value;
    Font oldF;
    if (e.getAdjustable() == s)
    {
      // 得到滚动条的值
      value = e.getValue();
      // 将值写入到文本区域
      txtValue.setText(new Integer(value).toString());
      // 按照滚动条的值设置字体
      oldF = txtValue.getFont();
      txtValue.setFont(new Font(oldF.getName(), oldF.getStyle(), value));
    }
  }
}

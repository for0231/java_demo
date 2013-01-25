import java.applet.*;
import java.applet.Applet;
import java.awt.event.*;
public class ShowStatus extends Applet
{
  int count = 0;
  public void init()
  {
  }
  public boolean mouseClicked(MouseEvent e)
  {
    count++;
    getAppletContext().showStatus("你好,你已经点击了" + count + "次了!");

    return true;
  }
}

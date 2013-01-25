import java.awt.*;
import java.applet.Applet;
import java.util.Date;

public class UseApplet extends Applet
{
  String strTime = new String();
  public void init()
  {
    
  }
  public void start()
  {
    Date d = new Date();
    strTime = d.toString();
    repaint();
  }
  public void pain(Graphics g)
  {
    g.drawString("当前时间为: " + strTime, 20, 30);
  }
}

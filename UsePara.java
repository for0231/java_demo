import java.awt.*;
import java.applet.Applet;
import java.util.Date;
public class UsePara extends Applet
{
  String strTime = new String();
  String strUser = new String();
  public void init()
  {
    strUser = getParameter("USER");
  }
  public void start()
  {
    Date d = new Date();
    strTime = d.toString();
    repaint();
  }
  public void pain(Graphics g)
  {
    g.setColor(Color.red);
    g.drawString(strUser + "你好, 当前时间为: " + strTime, 20, 30);
  }
}

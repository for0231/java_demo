import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import java.util.Date;
public class AppPara extends Applet
{
  String strTime = new String();
  static String strUser = new String();
  static boolean inApplet = true;
  public void init()
  {
    if (inApplet)
    { 
      strUser = getParameter("USER");
    }
  }
  public void start()
  {
    Date d = new Date();
    strTime = d.toString();
    repaint();
  }
  public void paint(Graphics g)
  {
    g.setColor(Color.red);
    g.drawString(strUser + "你好,当前的时间为: " + strTime, 20, 30);
  }

  public static void main(String[] args)
  {
    inApplet = false;
    if (args.length < 1) 
    {
      System.out.println("缺少用户参数");
      System.exit(0);
    }
    else
      strUser = new String(args[0]);
    Frame f=new Frame("时间");
    f.addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent evt)
      {
        System.exit(0);
      }
    });

    AppPara a = new AppPara();
    a.init();
    f.add("Center", a);
    f.setSize(400,200);
    f.show();
    a.start();
  }
}

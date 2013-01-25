import java.applet.*;
import java.applet.Applet;
import java.net.URL;

public class ShowHtml extends Applet
{
  public void init()
  {
  }
  public void start()
  {
    try
    {
      URL urlName = new URL("http://www.xjtu.edu.cn");
      getAppletContext().showDocument(urlName, "left");
      urlName = new URL("http://www.sina.com.cn");
      getAppletContext().showDocument(urlName, "right");
    }
    catch(MALURLException e)
    {
      System.out.println(e.getMessage());
    }
  }
}

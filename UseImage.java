import java.awt.*;
import java.applet.Applet;
import java.net.*;
public class UseImage extends Applet
{
  // 定义图像对象
  Image testImage;
  public void init()
  {
    testImage = getImage(getDocumentBase(), "image/index.gif");
  }
  public void pain(Graphics g) 
  {
    g.drawImage(testImage,0,0,this);
  }
}

import java.awt.*;
import java.applet.*;
import java.awt.geom.*;
import java.awt.font.*;
import java.awt.image.*;
import java.net.*;
public class GUIImage extends Applet
{
  public void paint(Graphics oldg)
  {
    Graphics2D g = (Graphics2D)oldg;
    try
    {
      URL imgURL = new URL(getDocumentBase(), "smaple.gif");
      Image img = getImage(imgURL);
      int   h   = img.getHeight(this);
      int   w   = img.getWidth(this);
      // 构造缓冲图像对象
      BufferedImage buf = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
      // 放入临时图形类
      Graphics tmpG = buf.createGraphics();
      tmpG.drawImage(img, 10, 10, this);
      g.drawImage(buf, 10, 20, this);

      // 设置透明颜色对象
      Color transBlue = new Color(0,0,255,100);
      g.setColor(transBlue);
      GeneralPath path = new GeneralPath();
      path.moveTo(60,0);
      path.lineTo(50,100);
      path.curveTo(160,230,240,140,200,100);
      g.fill(path);
      transBlue = new Color(0,0,255,240);
      g.fill(new Ellipse2D.Float(100,100,50,50));
      Rectangle2D rect = new Rectangle2D.Float(0,0,h,w);
      // 图片填充
      TexturePaint t= new TexturePaint(buf, rect);
      g.setPaint(t);
      g.fill(new Ellipse2D.Float(100,50,60,60));
    }
    catch (Exception e)
    {
      System.out.println("Error: " + e.getMessage());
    }
  }
}

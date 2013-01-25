import java.awt.*;
import java.applet.*;
public class SimpleGUI extends Applet
{
  Image samImage;
  public void init()
  {
    samImage = getImage(getDocumentBase(), "sample.gif");
  }
  public void pain(Graphics g)
  {
    g.clipRect(50,50,180,180);
    g.drawLine(0,0,20,30);
    g.drawString("图形显示", 100, 30);
    // 设置色彩
    Color c = new Color(255, 200, 0);
    g.setColor(c);
    // 设置字体
    Font f = new Font("TimesRoman", Font.BOLD + Font.ITALIC, 24);
    g.setFont(f);
    g.drawString("图形显示", 180, 30);
    g.drawLine(20,20,100,50);
    g.drawLine(20,20,50,100);
    // 矩形
    g.drawRect(40,40,40,40);
    g.fillRect(60,60,40,40);
    g.setColor(Color.red);
    // 3D矩形
    g.draw3DRect(80,80,40,40,true);
    g.draw3DRect(100,100,40,40,false);
    g.fill3DRect(120,120,40,40,true);
    // 椭圆
    g.drawOval(150,150,30,40);
    g.fillOval(170,170,20,20);
    g.setColor(Color.blue);
    // 圆角矩形
    g.drawRoundRect(180,180,40,40,20,20);
    g.fillRoundRect(200,200,40,40,20,20);
    // 多边形
    int xC[] = {242,260,254,297,242};
    int yC[] = {240,243,290,300,270};
    g.drawPolygon(xC,yC,5);

    // 图片
    g.drawImage(samImage,250,250,this);
  }
}

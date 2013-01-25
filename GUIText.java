import java.awt.*;
import java.applet.*;
import java.awt.geom.*;
import java.awt.font.*;
public class GUIText extends Applet
{
  public void paint(Graphics oldg)
  {
    Graphics2D g = (Graphics2D) oldg;
    // 设置字体
    Font f1 = new Font("Courier", Font.PLAIN, 24);
    Font f2 = new Font("helvetica", Font.BOLD, 24);
    FontRenderContext frc = g.getFontRenderContext();
    String str = new String("这是一个文本布局类的实现");
    String str2 = new String("扩充绘制文本的功能");
    // 构造文本布局对象
    TextLayout layout = new TextLayout(str, f1, frc);
    Point2D loc = new Point2D.Float(20,50);

    // 绘制文本
    layout.draw(g,(float)loc.getX(), (float)loc.getY());
    // 设置边框
    Rectangle2D bounds = layout.getBounds();
    bounds.setRect(bounds.getX() + loc.getX(),
                   bounds.getY() + loc.getY(),
                   bounds.getWidth(),
                   bounds.getHeight());
    g.draw(bounds);
    layout = new TextLayout(str2, f2, frc);
    g.setColor(Color.red);
    layout.draw(g, 20, 80);
  }
}

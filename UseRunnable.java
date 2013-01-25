import java.awt.*;
import java.applet.Applet;
public class UseRunnable extends Applet implements Runnable
{
  Thread t;
  Image imgs[];
  int high,h1,h2,h3;
  public void init()
  {
    high = getHeight()/3;
    h1 = high;
    h2 = high * 2;
    h3 = high * 3;
    imgs = new Image[3];
    for(int i = 0; i < 3; i++)
    {
      imgs[i] = getImage(getDocumentBase(), "image" + (i+1) +".gif");
    }
  }

  public void start()
  {
    t = new Thread(this);
    t.start();
  }

  public void stop()
  {
    t = null;
  }

  // 实现接口的run方法, 获得动画效果
  public void run()
  {
    while(t != null)
    {
      try {
        Thread.sleep(100);
        repaint();
        h1--;
        // 上移,到顶点时睡眠
        if ( h1 == 0)
        {
          Thread.sleep(3000);
          h2 = high;
        }

        // 上移,到顶点时睡眠
        h2--;
        if (h2 == 0)
        {
          Thread.sleep(3000);
          h3 = high;
        }

        // 上移,到顶点时睡眠
        h3--;
        if (h3 == 0)
        {
          Thread.sleep(3000);
          h1 = high;
        }
      }
      catch (InterruptedException e)
      {
        System.out.println(e.getMessage());
      }
    }
  }

  public void paint(Graphics g)
  {
    // 三幅图片依次显示
    g.drawImage(imgs[0], 0, h1, this);
    g.drawImage(imgs[1], 0, h2, this);
    g.drawImage(imgs[2], 0, h3, this);
  }

  public void update(Graphics g)
  {
    paint(g);
  }
}

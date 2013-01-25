
import java.awt.*;
import java.awt.event.*;

/**
 *  重载paint方法，显示游戏的名字
 **/
class IntroCanvas extends Canvas {
  private Color pink = new Color(255, 200, 200);
  private Color blue = new Color(150, 200, 255);
  private Color yellow = new Color(250, 220, 100);

  private int w, h;
  private int edge = 16;
  private static final String title = "Scrabblet";
  
  private Font namefont, titlefont, bookfont;

  IntroCanvas() {
    setBackground(yellow);
    titlefont = new Font("SansSerif", Font.BOLD, 58);
    namefont = new Font("SansSerif", Font.BOLD, 18);
    bookfont = new Font("SansSerif", Font.PLAIN, 12);
    addMouseListener(new MyMouseAdapter());
  }
  
  /** 以按选择的等量偏移绘制居中文字 */
  private void d(Graphics g, String s, Color c, Font f, int y, int off) {
    g.setFont(f);
    FontMetrics fm = g.getFontMetrics();
    g.setColor(c);
    g.drawString(s, (w - fm.stringWidth(s)) / 2 + off, y + off);
  }

  public void paint(Graphics g) {
    Dimension d = getSize();
    w = d.width;
    h = d.height;
    g.setColor(blue);
    g.fill3DRect(edge, edge, w - 2 * edge, h - 2 * edge, true);
    d(g, title, Color.black, titlefont, h / 2, 1);
    d(g, title, Color.white, titlefont, h / 2, -1);
    d(g, title, pink, titlefont, h / 2, 0);
    
  }
  
  /** 在单用户模式中取消弹出的帧 */
  class MyMouseAdapter extends MouseAdapter {
    public void mousePressed(MouseEvent me) {
      ((Frame)getParent()).setVisible(false);
    }
  }
}
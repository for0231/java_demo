
import java.awt.*;

/** 
 * 封装字母的位置和外观，不涉及游戏和棋盘
 **/
class Letter {
  // 每个字符的宽度和高度
  static int w, h; 
  private static Font font, smfont;
  private static int y0, ys0;
  private static int lasth = -1;
  static final int NORMAL = 0;
  static final int DIM = 1;
  static final int BRIGHT = 2;
  private static Color colors[][] = {
    mix(250, 220, 100),   
    mix(200, 150, 80),    
    mix(255, 230, 150)   
  };
 
  /** 输入一组RGB值，转换成三种颜色，用以提供3-D效果的高亮度和低亮度 */
  private static Color mix(int r, int g, int b)[] {
    Color arr[] = new Color[3];
 
    arr[NORMAL] = new Color(r, g, b);
    arr[DIM] = gain(arr[0], .71);
    arr[BRIGHT] = gain(arr[0], 1.31);
    return arr;
  }
  
  /** 确认颜色值在合法范围之内 */
  private static int clamp(double d) {
    return (d < 0) ? 0 : ((d > 255) ? 255 : (int) d);
  }
  
  /** 加重或者降低一个给定颜色的亮度 */
  private static Color gain(Color c, double f) {
    return new Color(
      clamp(c.getRed() * f),
      clamp(c.getGreen() * f),
      clamp(c.getBlue() * f));
  }
  private boolean valid = false;
 
  // 定义一些变量来保存字母的位置
  private Point tile = null;
  int x, y;              
  private int x0;         
  private int w0;         
  private int xs0;        
  private int ws0;        
  private int gap = 1;    
  private String symbol;
  private int points;

  Letter(char s, int p) {
    symbol = "" + s;  // 保持要显示字母的字符串
    points = p;       // 这个字符的分数值
  }

  String getSymbol() {
    return symbol;
  }

  int getPoints() {
    return points;
  }

  /** 指示字母方块绘制的位置 */
  void move(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /** 参数为空，这这个字符没有被使用，否则，该方法指示这个字母占据的
   *  棋盘位置坐标
   **/
  void remember(Point t) {
    if (t == null) {
      tile = t;
    } else {
      tile = new Point(t.x, t.y);
    }
  }

  Point recall() {
    return tile;
  }
  
  /** 棋盘调用该方法来指示字母的大小 */
  static void resize(int w0, int h0) {
    w = w0;
    h = h0;
  }
  
  /** 判断参数值是否在Letter的范围之内 */
  boolean hit(int xp, int yp) {
    return (xp >= x && xp < x + w && yp >= y && yp < y + h);
  }
  
  private int font_ascent;
  /** 该方法转载字体，确定字体大小，决定绘制的位置 */
  void validate(Graphics g) {
    FontMetrics fm;
    if (h != lasth) {
      font = new Font("SansSerif", Font.BOLD, (int)(h * .6));
      g.setFont(font);
      fm = g.getFontMetrics();
      font_ascent = fm.getAscent();
 
      y0 = (h - font_ascent) * 4 / 10 + font_ascent;
 
      smfont = new Font("SansSerif", Font.BOLD, (int)(h * .3));
      g.setFont(smfont);
      fm = g.getFontMetrics();
      ys0 = y0 + fm.getAscent() / 2;
      lasth = h;
    }
    if (!valid) {
      valid = true;
      g.setFont(font);
      fm = g.getFontMetrics();
      w0 = fm.stringWidth(symbol);
      g.setFont(smfont);
      fm = g.getFontMetrics();
      ws0 = fm.stringWidth("" + points);
      int slop = w - (w0 + gap + ws0);
      x0 = slop / 2;
      if (x0 < 1)
        x0 = 1;
      xs0 = x0 + w0 + gap;
      if (points > 9)
        xs0--;
    }
  }
  
  /** 棋盘调用这个方法，其中参数i代表NORMAL, BRIGHT, DIM中的一个 */
  void paint(Graphics g, int i) {
    Color c[] = colors[i];
    validate(g);
    g.setColor(c[NORMAL]);
    g.fillRect(x, y, w, h);
    g.setColor(c[BRIGHT]);
    g.fillRect(x, y, w - 1, 1);
    g.fillRect(x, y + 1, 1, h - 2);
    g.setColor(Color.black);
    g.fillRect(x, y + h - 1, w, 1);
    g.fillRect(x + w - 1, y, 1, h - 1);
    g.setColor(c[DIM]);
    g.fillRect(x + 1, y + h - 2, w - 2, 1);
    g.fillRect(x + w - 2, y + 1, 1, h - 3);
    g.setColor(Color.black);
    if (points > 0) {
      g.setFont(font);
      g.drawString(symbol, x + x0, y + y0);
      g.setFont(smfont);
      g.drawString("" + points, x + xs0, y + ys0);
    }
  }
}
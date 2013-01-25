
import java.awt.*;

/** 
 * ��װ��ĸ��λ�ú���ۣ����漰��Ϸ������
 **/
class Letter {
  // ÿ���ַ��Ŀ�Ⱥ͸߶�
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
 
  /** ����һ��RGBֵ��ת����������ɫ�������ṩ3-DЧ���ĸ����Ⱥ͵����� */
  private static Color mix(int r, int g, int b)[] {
    Color arr[] = new Color[3];
 
    arr[NORMAL] = new Color(r, g, b);
    arr[DIM] = gain(arr[0], .71);
    arr[BRIGHT] = gain(arr[0], 1.31);
    return arr;
  }
  
  /** ȷ����ɫֵ�ںϷ���Χ֮�� */
  private static int clamp(double d) {
    return (d < 0) ? 0 : ((d > 255) ? 255 : (int) d);
  }
  
  /** ���ػ��߽���һ��������ɫ������ */
  private static Color gain(Color c, double f) {
    return new Color(
      clamp(c.getRed() * f),
      clamp(c.getGreen() * f),
      clamp(c.getBlue() * f));
  }
  private boolean valid = false;
 
  // ����һЩ������������ĸ��λ��
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
    symbol = "" + s;  // ����Ҫ��ʾ��ĸ���ַ���
    points = p;       // ����ַ��ķ���ֵ
  }

  String getSymbol() {
    return symbol;
  }

  int getPoints() {
    return points;
  }

  /** ָʾ��ĸ������Ƶ�λ�� */
  void move(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /** ����Ϊ�գ�������ַ�û�б�ʹ�ã����򣬸÷���ָʾ�����ĸռ�ݵ�
   *  ����λ������
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
  
  /** ���̵��ø÷�����ָʾ��ĸ�Ĵ�С */
  static void resize(int w0, int h0) {
    w = w0;
    h = h0;
  }
  
  /** �жϲ���ֵ�Ƿ���Letter�ķ�Χ֮�� */
  boolean hit(int xp, int yp) {
    return (xp >= x && xp < x + w && yp >= y && yp < y + h);
  }
  
  private int font_ascent;
  /** �÷���ת�����壬ȷ�������С���������Ƶ�λ�� */
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
  
  /** ���̵���������������в���i����NORMAL, BRIGHT, DIM�е�һ�� */
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
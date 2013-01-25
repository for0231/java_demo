import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class LineStyles extends JPanel {
  private GeneralPath path;
  private static int x = 30, deltaX = 150, y = 300, deltaY = 250,
                     thickness = 40;
  private Circle p1Large, p1Small, p2Large, p2Small, p3Large, p3Small;
  private int compositeType = AlphaComposite.SRC_OVER;
  private AlphaComposite transparentComposite =
    AlphaComposite.getInstance(compositeType, 0.4F);
  private int[] caps =
    { BasicStroke.CAP_SQUARE, BasicStroke.CAP_BUTT,
      BasicStroke.CAP_ROUND };
  private String[] capNames =
    { "CAP_SQUARE", "CAP_BUTT", "CAP_ROUND" };
  private int[] joins =
    { BasicStroke.JOIN_MITER, BasicStroke.JOIN_BEVEL,
      BasicStroke.JOIN_ROUND };
  private String[] joinNames =
    { "JOIN_MITER", "JOIN_BEVEL", "JOIN_ROUND" };

  public LineStyles() {
    path = new GeneralPath();
    path.moveTo(x, y);
    p1Large = new Circle(x, y, thickness/2);
    p1Small = new Circle(x, y, 2);
    path.lineTo(x + deltaX, y - deltaY);
    p2Large = new Circle(x + deltaX, y - deltaY, thickness/2);
    p2Small = new Circle(x + deltaX, y - deltaY, 2);
    path.lineTo(x + 2*deltaX, y);
    p3Large = new Circle(x + 2*deltaX, y, thickness/2);
    p3Small = new Circle(x + 2*deltaX, y, 2);
    setForeground(Color.blue);
    setFont(new Font("SansSerif", Font.BOLD, 20));
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D)g;
    g2d.setColor(Color.blue);
    for(int i=0; i<caps.length; i++) {
      BasicStroke stroke =
        new BasicStroke(thickness, caps[i], joins[i]);
      g2d.setStroke(stroke);
      g2d.draw(path);
      labelEndPoints(g2d, capNames[i], joinNames[i]);
      g2d.translate(3*x + 2*deltaX, 0);
    }
  }

  // Draw translucent circles to illustrate actual endpoints.
  // Include text labels to shold cap/join style.

  private void labelEndPoints(Graphics2D g2d,
                              String capLabel, String joinLabel) {
    Paint origPaint = g2d.getPaint();
    Composite origComposite = g2d.getComposite();
    g2d.setPaint(Color.red);
    g2d.setComposite(transparentComposite);
    g2d.fill(p1Large);
    g2d.fill(p2Large);
    g2d.fill(p3Large);
    g2d.setPaint(Color.yellow);
    g2d.setComposite(origComposite);
    g2d.fill(p1Small);
    g2d.fill(p2Small);
    g2d.fill(p3Small);
    g2d.setPaint(Color.black);
    g2d.drawString(capLabel, x + thickness - 5, y + 5);
    g2d.drawString(joinLabel, x + deltaX + thickness - 5, y - deltaY);
    g2d.setPaint(origPaint);
  }

  public static void main(String[] args) {
    WindowUtilities.openInJFrame(new LineStyles(),
                                 9*x + 6*deltaX, y + 60);
  }
}

class Circle extends Ellipse2D.Double {
  public Circle(double centerX, double centerY, double radius) {
    super(centerX - radius, centerY - radius, 2.0*radius, 2.0*radius);
  }
}
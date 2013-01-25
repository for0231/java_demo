import java.awt.*;
import java.applet.*;
import java.awt.geom.*; // New Java2D package in Java 1.2

public class GradientPaintDemo extends Applet {

  public void paint(Graphics g) {

    // The context that is passed is actually
    // the Graphics2D sub-class of Graphics. So
    // we can cast it to Graphics2D and take advantage
    // of the additional tools that it has.
    Graphics2D g2 = (Graphics2D)g;

    // Create an ellipse object
    Ellipse2D e = new Ellipse2D.Float(20, 20, 60, 100);

    // Grade the colors red to blue from point (40,40)
    // to point (60,60). The last argument is "true" so
    // repeat the pattern, i.e. cyclic is true.
    GradientPaint gp = new GradientPaint(40, 40, Color.red,
        60, 60, Color.blue, true);

    // Set the paint to the gradient pattern
    g2.setPaint(gp);
    // and then fill the ellipse.
    g2.fill(e);

    // Move the ellipse 80 pixels to the right
    e.setFrame(100, 20, 60, 100);
    // Set a thick stroke and then draw it with the current
    // gradient paint.
    g2.setStroke(new BasicStroke(8));
    g2.draw(e);

    // Move the ellipse another 80 pixels to the right
    e.setFrame(180, 20, 60, 100);
     // Grade the colors red to blue from point (120,120)
    // to point (140,140). Here we set cyclic to false.
    gp = new GradientPaint(120, 120, Color.red,
        140, 140, Color.blue, false);
    g2.setPaint(gp);
    g2.fill(e);


  }
}
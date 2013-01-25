import java.awt.*;
import java.applet.*;
import java.awt.geom.*; // New Java2D package in Java 1.2

public class ShapesDemo2 extends Applet {

  public void paint(Graphics g) {

    // The context that is passed is actually
    // the Graphics2D sub-class of Graphics. So
    // we can cast it to Graphics2D and take advantage
    // of the additional tools that it has.
    Graphics2D g2 = (Graphics2D)g;

    // Options on the rendering, i.e. the actual pixel settings
    // of the drawing, can be chosen via RenderingHints
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);

   // Create the two shapes, a circle and a square.
    Shape shape1 = new Ellipse2D.Double(20.0, 45.0, 30.0, 30.);
    Shape shape2 = new Rectangle2D.Double(35.0, 35.0, 30.0, 30.0);

    // Now draw the shapes.
    g2.draw(shape1);
    g2.draw(shape2);

    // Shift the drawing origin 60 pixels to the right to make
    // room for the next drawing
    AffineTransform at = AffineTransform.getTranslateInstance(60,0);
    g2.transform(at);

    // Now we will create Area objects from the Shape objects.
    Area area1 = new Area(shape1);
    Area area2 = new Area(shape2);

    // And then combine these Area objects in several ways:

    // First we simple add the Area objects
    area1.add(area2);

    // In graphics2D use setPaint instead of setColor
    g2.setPaint(Color.red);

    // This will fill the resulting combined shape with blue
    g2.fill(area1);

    // Draw the outline of the resulting Area.
    g2.setPaint(Color.blue);
    g2.draw(area1);

    // Another 60 pixel shift.
    g2.transform(at);
    // Make another set of area objects
    area1 = new Area(shape1);
    area2 = new Area(shape2);
    // Get the intersection of the two Area objects and then draw it.
    area1.intersect(area2);
    g2.setPaint(Color.red);
    g2.fill(area1);
    g2.setPaint(Color.blue);
    g2.draw(area1);

    // Another 60 pixel shift.
    g2.transform(at);
    area1 = new Area(shape1);
    area2 = new Area(shape2);
    // Subtract area2 from area1 and then draw the result.
    area1.subtract(area2);
    g2.setPaint(Color.red);
    g2.fill(area1);
    g2.setPaint(Color.blue);
    g2.draw(area1);

    // Another 60 pixel shift.
    g2.transform(at);
    area1 = new Area(shape1);
    area2 = new Area(shape2);
    // Combine with an XOR operation.
    area1.exclusiveOr(area2);
    g2.setPaint(Color.red);
    g2.fill(area1);
    g2.setPaint(Color.blue);
    g2.draw(area1);

  }
}
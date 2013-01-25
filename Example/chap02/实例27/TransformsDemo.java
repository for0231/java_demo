import java.awt.*;
import java.applet.*;
import java.awt.geom.*; // New Java2D package in Java 1.2

public class TransformsDemo extends Applet {

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

   // Create a rectangle.
    Shape shape1 =  new Rectangle2D.Double(25.0, 25.0, 30.0, 50.0);
    // Now draw it.
    g2.draw(shape1);

    // Shift the drawing origin 80 pixels to the right to make
    // room for the next drawing
    AffineTransform at = AffineTransform.getTranslateInstance(80,0);
    g2.transform(at);

    // Create a rotation transform of 30degrees CCW around the
    // the corner of the rectangle.
    AffineTransform  atx =
       AffineTransform.getRotateInstance(-Math.PI/6,40,50);
    // Take the shape object and create a rotated version
    Shape atShape = atx.createTransformedShape(shape1);
    g2.draw(atShape);

    // Another 80 pixel shift.
    g2.transform(at);
    // Create a scaling transform
    atx = AffineTransform.getScaleInstance(1.5,1.0);
    // Take the shape object and create a scaled version
    atShape = atx.createTransformedShape(shape1);
    g2.draw(atShape);

    // Another 80 pixel shift.
    g2.transform(at);
    // Create a shear transform
    atx = AffineTransform.getShearInstance(0.0,0.5);
    // Take the shape object and create a sheared version
    atShape = atx.createTransformedShape(shape1);
    g2.draw(atShape);

    // Another 80 pixel shift.
    g2.transform(at);
    // Illustrate compound transforms
    // First transform object
    atx = new AffineTransform();
    // Then set to a shear transform
    atx.setToShear(0.0,0.5);
    // and then rotated  about the current origin
    atx.rotate(-Math.PI/6,40,50);
    // Now apply to the drawing frame
    atShape = atx.createTransformedShape(shape1);
    g2.draw(atShape);

  }
}
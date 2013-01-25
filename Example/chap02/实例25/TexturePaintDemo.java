import java.awt.*;
import java.awt.geom.*;
import java.net.*;
import java.awt.image.BufferedImage;
import java.applet.*;

public class TexturePaintDemo extends Applet {

  private BufferedImage bImage;

  public void init(){

    // Get the image for the texture.
    URL url=null;
    try {
      url = new URL(getCodeBase(),"monaSmall.gif");
    } catch(MalformedURLException e) {
        String msg = "Error loading image duke.gif";
        System.err.println(msg);
        showStatus(msg);
        System.exit(0);
    }
    // Grab an image using this trick with ImageIcon which
    // doesn't return until the image is loaded.
    Image img = new javax.swing.ImageIcon(url).getImage();

    // Need a BufferedImage for the texture class.
    // So create one of same size as the Image and
    // draw the Image onto it.
    bImage = new BufferedImage(img.getWidth(null),img.getHeight(null),
                   BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = bImage.createGraphics();
    g2.drawImage(img,null,null);
  }

  public void paint(Graphics g) {

    Graphics2D g2 = (Graphics2D)g;

    // Create a texture rectangle the same size as the texture image.
    Rectangle2D tr = new Rectangle2D.Double(0, 0,
                      bImage.getWidth(), bImage.getHeight());

    // Create the TexturePaint.
    TexturePaint tp = new TexturePaint(bImage, tr);

    // Create a round rectangle.
    RoundRectangle2D r = new RoundRectangle2D.Float(25, 35, 150, 150, 25, 25);

    // Now fill the round rectangle with the texture.
    g2.setPaint(tp);
    g2.fill(r);


    // Create an ellipse object and fill it with the texture.
    Ellipse2D e = new Ellipse2D.Float(200, 35, 100, 150);
    g2.fill(e);

    // Create a rectangle object and then set to a thick stroke
    // so that the current texture will be visible.
    Rectangle2D r2 = new Rectangle2D.Double(320, 35, 150, 150);
    g2.setStroke(new BasicStroke(20));
    g2.draw(r2);

  }
}
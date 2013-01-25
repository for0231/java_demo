import java.awt.Graphics;
import java.applet.Applet;
public class HelloApplet extends Applet {
  public void pain(Graphics g) {
    g.drawString("Hello World!", 50,25);
  }
}

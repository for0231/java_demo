import java.awt.*;
import java.applet.Applet;
public class UseAudio extends Applet
{
  public void init()
  {
  }
  public void start()
  {
    getAudioClip(getDocumentBase(), "tiger.au").play();
  }
}

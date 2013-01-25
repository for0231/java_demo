  package mine;

  import com.siemens.mp.game.*;
  import javax.microedition.midlet.*;
  import javax.microedition.lcdui.*;

  public class Mine extends MIDlet
  {
      private static Display display;

      public static void main(String args[])
      {
          Mine app = new Mine();
          app.startApp();
      }

      public Mine()
      {
      }

      protected void pauseApp()
      {
      }

      protected void startApp()
      {
          Light.setLightOn();   //  π±≥æ∞µ∆≥§¡¡
          display = Display.getDisplay(this);

          MGame pStart = new MGame(this);
          pStart.activate(display);
      }

      protected void destroyApp(boolean parm1)
      {
          display.setCurrent(null);
          notifyDestroyed();
          Light.setLightOff();  // πÿ±’±≥æ∞µ∆≥§¡¡
      }

  }

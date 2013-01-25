import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
/**
 * Scribble application for phone and PDA.
 */
public class Scribble extends MIDlet
{
   /**
    * Signals the MIDlet to start and enter the Active state.
    */
   protected void startApp() 
   {
      // Since we are drawing on the screen and need to control the position
      // of the "cursor", we need to use the Canvas.
      // has to be final since listener is using it
      final Canvas screen = new Canvas()
      {
         int m_x = getWidth() / 2;
         int m_prevX = getWidth() / 2;
         int m_y = getHeight() / 2;
         int m_prevY = getHeight() / 2;
         // since the paint method is not implemented, then we need to
         protected void paint(Graphics g)
         {
            // draw a line from the last position to the new
            g.drawLine(m_prevX, m_prevY, m_x, m_y);
            // make the new position the old position
            m_prevX = m_x;
            m_prevY = m_y;
         }
         // since the whole idea of this app is to draw on the screen then
         // we need to implement our own keyPressed method
         protected void keyPressed(int keyCode)
         {
            // the codes coming back do not map exactly to the GAME ACTION keys
            // we need to map them using getGameAction
            switch(getGameAction(keyCode))
            {
            case UP:
               if (m_y > 0) m_y--;
               break;
            case LEFT:
               if (m_x > 0) m_x--;
               break;
            case DOWN:
               if (m_y < getHeight() - 1) m_y++;
               break;
            case RIGHT:
               if (m_x < getWidth() - 1) m_x++;
               break;
            }
            // we wantto show the result, so we ask the canvas to repaint
            repaint();
         }
         // this method is for devices with pointers (PDAs) 
         protected void pointerDragged(int x, int y)
         {
            m_y = y;
            m_x = x;
            repaint();
         }
      };
      // we need to be able to exit the application, for this we use a 
      // CommandListener, since this is an interface with the commandAction
      // method, we need to implement the method
      CommandListener listener = new CommandListener()
      {
         // very simply, if any EXIT type command was issued then ask the 
         // MIDlet to destroy itself
         public void commandAction(Command c, Displayable d) 
         {
            if (c.getCommandType() == Command.EXIT)
            {
               destroyApp(true);
            }
         }
      };
      // this is the command that we are using the ComamndListener for, we 
      // simply add it to the Canvas
      screen.addCommand(new Command("Exit", Command.EXIT, 1));
      // we also need to tell the Canvas to use the command ComamndListener 
      // we just implemented
      screen.setCommandListener(listener);
      // finally we tell the Display Manager to display the Canvas
      Display.getDisplay(this).setCurrent(screen);
   }
   /**
    * Signals the MIDlet to stop and enter the Paused state.
    */
   protected void pauseApp() 
   {
      // we need to implement this since it is abstract in the MIDlet
   }
   /**
    * Signals the MIDlet to terminate and enter the Destroyed state.
    */
   protected void destroyApp(boolean unconditional) 
   {
      // notify the application management that it has entered into the 
      // Destroyed state, all resources held by the MIDlet will be considered 
      // eligible for reclamation. 
      // Normally, we would performed some clean-up operations here (releasing of 
      // resources etc.) but we have nothing to release
      notifyDestroyed();
   }
}


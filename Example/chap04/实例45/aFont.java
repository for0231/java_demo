import java.awt.*;
import java.applet.*;

public class aFont extends Applet
{
  Choice    lFont	    = new Choice();
  Choice    lSize	    = new Choice();
  Choice    lStyle	  = new Choice();

  TextField sString   = new TextField ("Javaside.com font test");
  TextField sChar     = new TextField ("20AC");

  Font f = null ;

  public void init ()
  {
	// Search all font name available in this JVM
    String [ ] arFont = getToolkit().getFontList();
    for (int i = 0; i < arFont.length; i++)
      lFont.addItem (arFont[i]) ;

    // Init Choices
    lSize.addItem ("7") ;
    lSize.addItem ("8") ;
    lSize.addItem ("9") ;
    lSize.addItem ("10") ;
    lSize.addItem ("11") ;
    lSize.addItem ("12") ;
    lSize.addItem ("14") ;
    lSize.addItem ("16") ;
    lSize.addItem ("18") ;
    lSize.addItem ("20") ;
    lSize.addItem ("24") ;
    lSize.addItem ("28") ;
    lSize.addItem ("32") ;
    lSize.addItem ("36") ;
    lSize.addItem ("40") ;
    lSize.select(10);

    lStyle.addItem ("PLAIN") ;
    lStyle.addItem ("BOLD") ;
    lStyle.addItem ("ITALIC") ;
    lStyle.addItem ("BOLD+ITALIC") ;

	// It's more simple to use null Layout
    setLayout(null) ;

    int iY = 10 ;
    Label l = new Label("Font") ;
    add( l ) ;
    l.reshape(5, iY, 40, 20) ;
    add(lFont) ;
    lFont.reshape(50, iY, 100, 20);

    l = new Label("Size") ;
    add( l ) ;
    l.reshape(170, iY, 40, 20) ;
    add(lSize) ;
    lSize.reshape(220, iY, 60, 20);

    iY += 22  ;
    l = new Label("Style") ;
    add( l ) ;
    l.reshape(5, iY, 40, 20) ;
    add(lStyle) ;
    lStyle.reshape(50, iY, 100, 20);

    iY += 22  ;
    l = new Label("Texte") ;
    add( l ) ;
    l.reshape(5, iY, 40, 20) ;
    add(sString) ;
    sString.reshape(50, iY, 100, 20);
    l = new Label("char") ;
    add( l ) ;
    l.reshape(170, iY, 40, 20) ;
    add(sChar) ;
    sChar.reshape(220, iY, 60, 20);

    repaint() ;
  }

  //
  public boolean handleEvent (Event event)
  {
    if (    event.id == Event.ACTION_EVENT
        && ( (event.target == sString) ||
             (event.target == sChar) ||
             (event.target == lFont) ||
             (event.target == lSize) ||
             (event.target == lStyle) )
       )
    {
       repaint() ;
       return true;
    }
    return super.handleEvent (event);
  }

  public void paint(Graphics g) {
		 // chose Font with selected value
         f = new Font (lFont.getSelectedItem (),
					   lStyle.getSelectedIndex(),
					   Integer.parseInt(lSize.getSelectedItem())
					  );


		 // Clear Background
         g.setColor( Color.white ) ;
         g.fillRect(0,85,400,200);
         g.setColor( Color.black ) ;

         if (f != null) {
              g.setFont(f);
         }

         g.drawString(sString.getText(), 20, 120);

		 // Get Unicode char format FFFF in textfield sChar
         String s = sChar.getText() ;
         char c ;
         try {
                c = (char)Integer.parseInt(s, 16) ;
                if (Character.isDefined(c))
                      g.drawString("char \\u"+s + " is " + c, 20, 180);
                else
                      g.drawString("char \\u"+s + " not exist" , 20, 180);
         }
         catch(Exception e) {	// Can parse this string
                      g.drawString(""+e , 20, 180);
         }

  }
}

// End...
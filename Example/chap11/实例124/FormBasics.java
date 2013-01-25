/*-----------------------------------------------------------
* FormBasics.java
*
* Juggling components on a Form
*-----------------------------------------------------------*/
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class FormBasics extends MIDlet implements CommandListener
{
  private Display display;	    // Reference to display object 
  private Form fmMain;         // The main form
  private Command cmInsert;    // Command to insert items
  private Command cmExit;      // Command to exit
  private DateField dfDate;    // Display date
  private TextField tfSize;    // Product size
  private TextField tfQuantity;// Product quantity
  private int dateIndex;        // Index of dfDate 

  public FormBasics()
  {
    display = Display.getDisplay(this);

    // Create the date and populate with current date
    dfDate = new DateField("", DateField.DATE);
    dfDate.setDate(new java.util.Date());

    // Define two textfields and two commands
    tfSize = new TextField("Size", "Small", 5, TextField.ANY); 
    tfQuantity = new TextField("Quantity:", "1", 2, TextField.NUMERIC); 
    cmInsert = new Command("Insert", Command.SCREEN, 2);
    cmExit = new Command("Exit", Command.EXIT, 1);

    // Create the form, add commands
    fmMain = new Form("Form Data...");
    fmMain.addCommand(cmExit);
    fmMain.addCommand(cmInsert);    
  
    // Append date to form & save index value where it was inserted
    dateIndex = fmMain.append(dfDate);

    // Capture events
    fmMain.setCommandListener(this);    
  }

  // Called by application manager to start the MIDlet.
  public void startApp()
  {
    display.setCurrent(fmMain);
  }

  public void pauseApp()
  { }
  
  public void destroyApp(boolean unconditional)
  { }

  public void commandAction(Command c, Displayable s)
  {
    if (c == cmInsert)
    {
      // One item on form, insert textfield prior to datefield
      if (fmMain.size() == 1)
      {
        fmMain.insert(dateIndex, tfQuantity);     
        dateIndex += 1;   // Date index has changed, update it
      }
      // If two items and last item is datefield, replace it
      else if (fmMain.size() == 2 && fmMain.get(1) == dfDate)
        fmMain.set(dateIndex, tfSize);
    }
    else if (c == cmExit)
 	  {
	    destroyApp(false);
	    notifyDestroyed();
	  }	   
  }
}
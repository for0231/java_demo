import java.awt.*;
import java.lang.*;

public class fire extends java.applet.Applet implements Runnable {
  boolean first=true;
  int ROWS = 50;
  int COLS = 64;
  int HIDDEN = 4;
  int ROWS_SEED = 4;
  int ROWS_RESEED = 48;
  int MAX_SEED = 8;
  int PALETTE_SIZE = 64;
  int COOLING_LIMIT = 32;
  int COOLING_ROWS = 42;
  int COOLING_FACTOR = 2;
  java.awt.Color palette[] = new java.awt.Color[PALETTE_SIZE];
  byte Buffer[],Buffer2[];
  String message,textfont;
  int textsize,textX,textY;
  Color textcolor;
  Image offScrImage=null;
  Graphics offScrGC;
  Dimension offScrSize;

  Thread kicker=null;

  public String getAppletInfo() {
    return "Fire applet by Javier Rodriguez <jrodrig@data.net.mx>";
  }

  public String[][] getParameterInfo() {
    String[][] info = {
	{"coolingrows",		"int",		"number of rows to cool"},
	{"coolingfactor",	"int",		"cooling factor"},
	{"coolinglimit",	"int",		"cooling threshold"},
	{"soundtrack",		"url",		"background sound"},
	{"text",		"String",	"message"},
	{"textcolor",		"String",	"text color"},
	{"textfont",		"String",	"text font"},
	{"textsize",		"int",		"text size"}
    };
    return info;
  }

  public void init() {
    int r,i;
    String aux;
    // Set some constants
    COLS = size().width;
    ROWS = size().height + HIDDEN;
    // Get parameters
    aux=getParameter("coolinglimit");
    if((aux!=null)&&(aux.endsWith("%"))) { aux=aux.substring(0,aux.length()-1); }
    COOLING_LIMIT = (aux==null)?(int)(PALETTE_SIZE*0.5):(int)(PALETTE_SIZE*(Integer.valueOf(aux).intValue())/100);
    aux=getParameter("coolingrows");
    if((aux!=null)&&(aux.endsWith("%"))) { aux=aux.substring(0,aux.length()-1); }
    COOLING_ROWS = (aux==null)?(int)(ROWS*0.8):(int)(ROWS*(Integer.valueOf(aux).intValue())/100);
    aux=getParameter("coolingfactor");
    COOLING_FACTOR= (aux==null)?2:(int)(Integer.valueOf(aux).intValue());
    ROWS_RESEED = (int)(ROWS*0.96);
    // Get text parameters
    aux = getParameter("text");
    message=(aux==null)?"":aux;
    aux = getParameter("textfont");
    textfont=(aux==null)?"TimesRoman":aux;
    aux=getParameter("textsize");
    textsize=(aux==null)?18:(Integer.valueOf(aux).intValue());
    aux=getParameter("textcolor");
    textcolor=hexColor(aux,Color.white);
    // Setup buffers;
    Buffer = new byte[COLS*ROWS];
    Buffer2 = new byte[COLS*ROWS];
    // Setup palette
    for(i=0; i<16; ++i)
      palette[i]= new Color(16*i,0,0);
    for(i=0; i<16; ++i)
      palette[16+i] = new Color(255, 16*i, 0);
    for(i=0; i<32; ++i)
      palette[32+i] = new Color(255,255,8*i);
    // Setup text
    Font myFont=new Font(textfont, Font.BOLD, textsize);
    FontMetrics myMetrix=getFontMetrics(myFont);
    int textH=myMetrix.getHeight();
    int textW=myMetrix.stringWidth(message);
    textX=(int)((COLS-textW)/2);
    textY=ROWS-HIDDEN-(int)((ROWS-HIDDEN-textH)/2)-myMetrix.getDescent();
    setFont(myFont);
    // Seed image
    for(r=COLS*(ROWS-ROWS_SEED); r<(ROWS*COLS); ++r) {
      Buffer[r]=(byte)(Math.random()*(PALETTE_SIZE-1));
    }
  }

void MainLoop() {
  int r,a,i;
  for(r=COLS+1;r<(COLS*(ROWS-1))-1;++r) {
    a=Buffer[r-COLS-1]+Buffer[r-COLS]+Buffer[r-COLS+1]+Buffer[r-1]+Buffer[r+1]+
      Buffer[r+COLS-1]+Buffer[r+COLS]+Buffer[r+COLS+1];
//    a=(a>>3)%PALETTE_SIZE;
    a=(a>>3);
    // Cool flames
    if(a<COOLING_LIMIT) {
      if((r<COOLING_ROWS*COLS)&&(a>COOLING_FACTOR)) a-=COOLING_FACTOR;
    }
    Buffer2[r]=(byte)(a);
  }
  // Seed at base
  for(r=COLS*(ROWS_RESEED);r<COLS*(ROWS);++r) {
    a=Buffer2[r];
    Buffer2[r]=(byte)((a-(Math.random()*MAX_SEED))%(PALETTE_SIZE*1.1));
    //Buffer2[r]=(byte)((a-(Math.random()*MAX_SEED))%PALETTE_SIZE);
  }
  // Scroll image
  for(i=0;i<COLS*(ROWS-1);++i) 
    Buffer[i]=Buffer2[i+COLS];
}

  public final synchronized void update(Graphics g) {
    // Setup off-screen buffer
    Dimension d=size();
    if((offScrImage==null)||(d.width!=offScrSize.width)||(d.height!=offScrSize.height)) {
      offScrImage=createImage(d.width,d.height);
      offScrSize=d;
      offScrGC=offScrImage.getGraphics();
      offScrGC.setFont(getFont());
    }
    if (offScrGC!=null) {
      offScrGC.fillRect(0,0,d.width,d.height);
      paint(offScrGC);
      g.drawImage(offScrImage,0,0,null);
    }
  }

public void paint(Graphics g) {
    int a;
    Color c;
    // Do main loop
    MainLoop();
    // Copy buffer to off-screen buffer
    for(int y=0;y<(ROWS-HIDDEN);++y)
      for(int x=0;x<COLS;++x) {
        a=Buffer[y*COLS+x];
        a=a<0?-a:a; // Patch nasty bug 
        a=a<(PALETTE_SIZE-1)?(a):(PALETTE_SIZE-1);
        c=palette[a];
        try {
          offScrGC.setColor(c);
          offScrGC.drawLine(x,y,x+1,y);
        } catch (Exception e) { }
      }
    try {
    // Write text
      offScrGC.setColor(textcolor);
      offScrGC.drawString(message,textX,textY);
    // Paint off-screen buffer
      g.drawImage(offScrImage,0,0,this);
    } catch (Exception e) { }

  }

  public void start() {
    if (kicker==null) {
      kicker=new Thread(this);
//      kicker.setPriority(kicker.MAX_PRIORITY);
      kicker.start();
    }
  }

  public void stop() {
    kicker=null;
  }

  public void run() {
    while(kicker!=null) {
      repaint();
      try {kicker.sleep(15);} catch (InterruptedException e) {}
    }
  }

  // Place fire bubble on click
  public boolean mouseDown(java.awt.Event evt, int x, int y) {
    int i;
    i=x+y*COLS; 
    if(i>81) {
      Buffer[i]=(byte)255;
      Buffer[i-COLS]=(byte)255;
      Buffer[i+COLS]=(byte)255;
      Buffer[i-1]=(byte)255;
      Buffer[i+1]=(byte)255;
    }
    return true;
  }

  public Color hexColor(String hex, Color std) {
    try {
      Integer rgb=new Integer(0);
      hex.replace('#',' ');
      hex.trim();
      rgb=Integer.valueOf(hex,16);
      return new Color(rgb.intValue());
    } catch (Exception e) {
      return std;
    }
  }
}

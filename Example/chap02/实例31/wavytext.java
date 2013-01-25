// Source File Name:   WavyText.java

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.*;
import java.io.PrintStream;
import java.net.URL;

public class WavyText extends Applet
    implements Runnable
{

    public void init()
    {
        setBackground(Color.black);
        if(getParameter("delay") == null)
            delay = 100;
        else
            delay = (new Integer(getParameter("delay"))).intValue();
        if(getParameter("strip") == null)
            strip = 1;
        else
            strip = (new Integer(getParameter("strip"))).intValue();
        if(getParameter("theta") == null)
            theta = 0.1F;
        else
            theta = Float.valueOf(getParameter("theta")).floatValue();
        if(getParameter("offset") == null)
            xoffset = 0;
        else
            xoffset = (new Integer(getParameter("offset"))).intValue();
        if(getParameter("peak") == null)
            peak = 0;
        else
            peak = (new Integer(getParameter("peak"))).intValue();
        pageurl = getParameter("url");
        ang = 0.0F;
    }

    Color getTextColor()
    {
        String s = getParameter("color");
        if(s == null)
            return Color.white;
        int k;
        int l;
        int i1;
        try
        {
            int i = s.indexOf(',');
            int j = s.indexOf(',', i + 1);
            k = Integer.parseInt(s.substring(0, i));
            l = Integer.parseInt(s.substring(i + 1, j));
            i1 = Integer.parseInt(s.substring(j + 1));
        }
        catch(Exception _ex)
        {
            System.out.println("Invalid 'rgb' specification.");
            return Color.white;
        }
        return new Color(k, l, i1);
    }

    void load_gifs()
    {
        gif = createImage(size().width, size().height);
        Graphics g = gif.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, size().width, size().height);
        int i;
        if(getParameter("size") == null)
            i = 24;
        else
            i = (new Integer(getParameter("size"))).intValue();
        String s = getParameter("text");
        if(s == null)
            s = "No Text Specified";
        g.setColor(getTextColor());
        g.setFont(new Font("TimesRoman", 1, i));
        g.drawString(s, 5, size().height - 8);
        make_imgs();
    }

    void make_imgs()
    {
        gifsec = new Image[gif.getHeight(this) / strip + 1];
        for(int i = 0; i < gif.getHeight(this) / strip; i++)
        {
            gifsec[i] = createImage(gif.getWidth(this), strip);
            Graphics g = gifsec[i].getGraphics();
            g.drawImage(gif, 0, -i * strip, this);
        }

        ang += 0.10000000000000001D;
    }

    void draw_imgs(Graphics g)
    {
        if(gif == null)
            return;
        g.setColor(Color.black);
        g.fillRect(0, 0, size().width, size().height);
        for(int i = 0; i < gif.getHeight(this) / strip; i++)
        {
            float f = ang + (float)i * theta;
            int j = (int)(Math.sin(f) * (double)peak);
            if(gifsec[i] != null)
                g.drawImage(gifsec[i], xoffset + j, i * strip, this);
        }

        ang += 0.10000000000000001D;
    }

    public void update(Graphics g)
    {
        if(curpic == null)
        {
            curpic = createImage(size().width, size().height);
            curpicgr = curpic.getGraphics();
            curpicgr.setColor(Color.black);
            curpicgr.fillRect(0, 0, size().width, size().height);
            return;
        }
        if(!paused)
            draw_imgs(curpicgr);
        g.draw3DRect(0, 0, size().width - 1, size().height - 1, true);
        g.drawImage(curpic, 0, 0, this);
    }

    public void paint(Graphics g)
    {
        update(g);
    }

    public void run()
    {
        while(killme != null) 
        {
            repaint();
            try
            {
                Thread.sleep(delay);
            }
            catch(Exception _ex) { }
        }
    }

    public void start()
    {
        load_gifs();
        if(killme == null)
        {
            killme = new Thread(this);
            killme.start();
        }
    }

    public void stop()
    {
        killme = null;
    }

    public boolean mouseUp(Event event, int i, int j)
    {
        if(pageurl != null)
            try
            {
                getAppletContext().showDocument(new URL(getDocumentBase(), pageurl));
            }
            catch(Exception _ex)
            {
                System.out.println("WavyText Error: Could not show page");
            }
        return true;
    }

    public boolean mouseEnter(Event event, int i, int j)
    {
        requestFocus();
        if(pageurl != null)
            getAppletContext().showStatus(pageurl);
        else
            getAppletContext().showStatus("");
        return true;
    }

    public boolean mouseExit(Event event, int i, int j)
    {
        getAppletContext().showStatus("");
        return true;
    }

    public boolean keyDown(Event event, int i)
    {
        paused = !paused;
        return true;
    }

    public WavyText()
    {
        paused = false;
    }

    Image gif;
    Image gifsec[];
    Image curpic;
    Graphics curpicgr;
    float ang;
    float theta;
    String pageurl;
    int delay;
    int strip;
    int xoffset;
    int peak;
    Thread killme;
    boolean paused;
}
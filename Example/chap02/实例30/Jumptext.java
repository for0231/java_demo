// Source File Name:   Jumptext.java

import java.applet.Applet;
import java.awt.*;
import java.util.StringTokenizer;

public class Jumptext extends Applet
    implements Runnable
{

    public void init()
    {
        Dimension dimension = size();
        String s = getParameter("speed");
        speed = s != null ? Integer.valueOf(s).intValue() : 50;
        font1 = new Font("TimesRoman", 2, 24);
        s = getParameter("message1");
        message1 = s != null ? s : "CATNET Internet Service";
        lastX1 = (int)(Math.random() * (double)(dimension.width - 1));
        lastY1 = (int)((double)(dimension.height - font1.getSize() - 1) * Math.random());
        directX1 = 3;
        directY1 = 3;
        font2 = new Font("TimesRoman", 0, 20);
        s = getParameter("message2");
        message2 = s != null ? s : "System Intelligent";
        lastX2 = (int)(Math.random() * (double)(dimension.width - 1));
        lastY2 = (int)((double)(dimension.height - font2.getSize() - 1) * Math.random());
        directX2 = -3;
        directY2 = -3;
    }

    public void start()
    {
        setBackground(Color.black);
        if(blinker == null)
        {
            blinker = new Thread(this, "Blink");
            blinker.start();
        }
    }

    public void paint(Graphics g)
    {
        Dimension dimension = size();
        g.setColor(Color.black);
        g.setFont(font1);
        FontMetrics fontmetrics = g.getFontMetrics();
        int k = fontmetrics.stringWidth(" ");
        int i = lastX1;
        int j = lastY1;
        for(StringTokenizer stringtokenizer = new StringTokenizer(message1); stringtokenizer.hasMoreTokens();)
        {
            String s = stringtokenizer.nextToken();
            int l = fontmetrics.stringWidth(s) + k;
            if(i > dimension.width)
                i -= dimension.width;
            g.setColor(new Color((int)(Math.random() * 256D), (int)(Math.random() * 256D), (int)(Math.random() * 256D)));
            g.drawString(s, i, j);
            i += l;
        }

        if(Math.random() > 0.98999999999999999D)
            directX1 = -directX1;
        lastX1 += directX1;
        if(lastX1 >= dimension.width)
            lastX1 = 0;
        else
        if(lastX1 < 0)
            lastX1 = dimension.width - 1;
        lastY1 += directY1;
        if(lastY1 >= dimension.height)
            directY1 = -3;
        else
        if(lastY1 < font1.getSize())
            directY1 = 3;
        g.setColor(Color.black);
        g.setFont(font2);
        fontmetrics = g.getFontMetrics();
        k = fontmetrics.stringWidth(" ");
        i = lastX2;
        j = lastY2;
        for(StringTokenizer stringtokenizer1 = new StringTokenizer(message2); stringtokenizer1.hasMoreTokens();)
        {
            String s1 = stringtokenizer1.nextToken();
            int i1 = fontmetrics.stringWidth(s1) + k;
            if(i > dimension.width)
                i -= dimension.width;
            g.setColor(new Color((int)(Math.random() * 256D), (int)(Math.random() * 256D), (int)(Math.random() * 256D)));
            g.drawString(s1, i, j);
            i += i1;
        }

        if(Math.random() > 0.98999999999999999D)
            directX2 = -directX2;
        lastX2 += directX2;
        if(lastX2 >= dimension.width)
            lastX2 = 0;
        else
        if(lastX2 < 0)
            lastX2 = dimension.width - 1;
        lastY2 += directY2;
        if(lastY2 >= dimension.height)
        {
            directY2 = -3;
            return;
        }
        if(lastY2 < font1.getSize())
            directY2 = 3;
    }

    public void stop()
    {
        blinker = null;
        blinker.stop();
    }

    public void run()
    {
        while(blinker != null) 
        {
            repaint();
            try
            {
                Thread.sleep(speed);
            }
            catch(InterruptedException _ex) { }
        }
    }

    public Jumptext()
    {
    }

    Thread blinker;
    String message1;
    String message2;
    Font font1;
    Font font2;
    int speed;
    int lastX1;
    int lastY1;
    int directX1;
    int directY1;
    int lastX2;
    int lastY2;
    int directX2;
    int directY2;
}
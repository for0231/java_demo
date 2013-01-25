// Source File Name:   Jannal.java
import java.applet.Applet;
import java.awt.*;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

public class Jannal extends Applet
    implements Runnable
{

    public void init()
    {
        w = size().width;
        h = size().height;
        dx = 5;
        dy = 5;
        rest = 50;
        bgc = Color.black;
        String s = getParameter("SIZE");
        if(s != null)
            esize = Integer.parseInt(s);
        s = getParameter("IMAGE");
        if(s != null)
            imgfile = s;
        s = getParameter("SPEED");
        if(s != null)
            rest = Integer.parseInt(s);
        img = getImage(getCodeBase(), imgfile);
        tracker = new MediaTracker(this);
        tracker.addImage(img, 0);
        tracker.checkID(0, true);
        repaint();
        try
        {
            tracker.waitForID(0);
        }
        catch(InterruptedException _ex) { }
        osi = createImage(w, h);
        top = makeTopImage();
        loaded = true;
    }

    Image makeTopImage()
    {
        int k = esize;
        Image image = createImage(k, k);
        Graphics g = image.getGraphics();
        g.setColor(bgc);
        g.fillRect(0, 0, k, k);
        g.setColor(Color.red);
        g.fillOval(0, 0, k, k);
        int j = 0xff000000;
        int ai[] = new int[k * k];
        PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, k, k, ai, 0, k);
        try
        {
            pixelgrabber.grabPixels();
        }
        catch(InterruptedException _ex) { }
        int i = 0;
        for(int l = 0; l < k; l++)
        {
            for(int i1 = 0; i1 < k; i1++)
            {
                if(ai[i] != j)
                    ai[i] = 0xffffff;
                i++;
            }

        }

        MemoryImageSource memoryimagesource = new MemoryImageSource(k, k, ai, 0, k);
        image = createImage(memoryimagesource);
        return image;
    }

    public void start()
    {
        if(zoomer == null)
        {
            zoomer = new Thread(this);
            zoomer.start();
        }
    }

    public void stop()
    {
        if(zoomer != null)
        {
            zoomer.stop();
            zoomer = null;
        }
    }

    public void delay(int i)
    {
        try
        {
            Thread.sleep(i);
            return;
        }
        catch(InterruptedException _ex)
        {
            return;
        }
    }

    public void run()
    {
        int i = (int)((Math.random() * (double)w) / 2D);
        int j = (int)((Math.random() * (double)h) / 2D);
        int k = (int)(Math.random() * 3D);
        while(loaded) 
        {
            if(k == 0)
            {
                if(i < w - esize)
                {
                    i += dx;
                } else
                {
                    k = 1;
                    continue;
                }
                if(j < h - esize)
                {
                    j += dy;
                } else
                {
                    k = 3;
                    continue;
                }
            }
            if(k == 1)
            {
                if(i > 0)
                {
                    i -= dx;
                } else
                {
                    k = 0;
                    continue;
                }
                if(j < h - esize)
                {
                    j += dy;
                } else
                {
                    k = 2;
                    continue;
                }
            }
            if(k == 2)
            {
                if(i > 0)
                {
                    i -= dx;
                } else
                {
                    k = 3;
                    continue;
                }
                if(j > 0)
                {
                    j -= dx;
                } else
                {
                    k = 1;
                    continue;
                }
            }
            if(k == 3)
            {
                if(i < w - esize)
                {
                    i += dx;
                } else
                {
                    k = 2;
                    continue;
                }
                if(j > 0)
                {
                    j -= dy;
                } else
                {
                    k = 0;
                    continue;
                }
            }
            replace(i, j);
            delay(rest);
        }
    }

    public boolean mouseMove(Event event, int i, int j)
    {
        stop();
        replace(i - esize / 2, j - esize);
        return true;
    }

    public boolean mouseDrag(Event event, int i, int j)
    {
        return true;
    }

    public boolean mouseEnter(Event event, int i, int j)
    {
        return true;
    }

    public boolean mouseExit(Event event, int i, int j)
    {
        start();
        return true;
    }

    public boolean mouseDown(Event event, int i, int j)
    {
        stop();
        Graphics g = getGraphics();
        g.drawImage(img, 0, 0, w, h, null);
        g.drawString("This applet is written by Elango", 20, 20);
        g.drawString("Email : elangos@hotmail.com", 20, 30);
        return true;
    }

    public void replace(int i, int j)
    {
        Graphics g = osi.getGraphics();
        g.setColor(bgc);
        g.fillRect(0, 0, w, h);
        g.clipRect(i, j, esize, esize);
        g.drawImage(img, 0, 0, w, h, this);
        g.drawImage(top, i, j, this);
        repaint();
    }

    public void paint(Graphics g)
    {
        if(!tracker.checkAll())
        {
            g.drawString("Please wait, images loading...", 20, h / 2);
            return;
        } else
        {
            g.drawImage(osi, 0, 0, this);
            return;
        }
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    public String getAppletInfo()
    {
        return "Name: Jannal Applet\r\nAuthor: Elango\r\nEmail: elangos@hotmail.com\r\n";
    }

    public Jannal()
    {
        imgfile = "taj.jpg";
        loaded = false;
        esize = 50;
    }

    Thread zoomer;
    Image img;
    Image osi;
    Image top;
    String imgfile;
    MediaTracker tracker;
    int w;
    int h;
    int rest;
    boolean loaded;
    int esize;
    int dx;
    int dy;
    Color bgc;
}
// Source File Name:   house.java
import java.applet.Applet;
import java.awt.*;
public class house extends Applet
{
    public void init()
    {
        setBackground(Color.black);
    }
    public void paint(Graphics g)
    {
        byte byte0 = 110;
        byte byte1 = 95;
        showStatus("My Homepage");
        calc(g, 16, byte0, byte1, 0, 0, 0);
    }

    static void calc(Graphics g, int i, int j, int k, int l, int i1, int j1)
    {
        int k1 = i1;
        int l1 = l;
        int i2 = j1;
        int j2 = i;
        int k2 = l + i;
        int l2 = i1 + i;
        int i3 = j1 + i;
        int j3 = j2 << 1;
        int k3 = j3 << 1;
        for(int l3 = 0; l3 < 8; l3++)
        {
            if(l3 == 1)
            {
                j -= j3;
                k += j2;
                l1 = k2;
            }
            if(l3 == 2)
            {
                j += k3;
                l1 = l;
                k1 = l2;
            }
            if(l3 == 3)
            {
                j -= j3;
                k += j2;
                l1 = k2;
                k1 = l2;
            }
            if(l3 == 4)
            {
                k -= k3;
                l1 = l;
                k1 = i1;
                i2 = i3;
            }
            if(l3 == 5)
            {
                j -= j3;
                k += j2;
                l1 = k2;
                k1 = i1;
                i2 = i3;
            }
            if(l3 == 6)
            {
                j += k3;
                l1 = l;
                k1 = l2;
                i2 = i3;
            }
            if(l3 == 7)
            {
                j -= j3;
                k += j2;
                l1 = k2;
                k1 = l2;
                i2 = i3;
            }
            if(i == 1)
                draw(l3, g, j, k, l1, k1, i2, j2);
            else
                calc(g, i >> 1, j, k, l1, k1, i2);
        }

    }

    static void draw(int i, Graphics g, int j, int k, int l, int i1, int j1, int k1)
    {
        boolean flag = false;
        byte byte0 = 1;
        byte byte1 = 4;
        int ai[] = new int[byte1];
        int ai1[] = new int[byte1];
        byte byte2 = 63;
        byte byte3 = 127;
        char c = '\200';
        boolean flag1 = false;
        boolean flag2 = false;
        int l1 = k1 << 1;
        flag = false;
        byte0 = 1;
        if(l == 1)
            flag = true;
        if(l == 30)
            flag = true;
        if(i1 == 0)
            flag = true;
        if(i1 == 30)
            flag = true;
        if(j1 < 3)
            flag = true;
        if(i1 % 4 == 0 && j1 == 14)
            flag = true;
        if(i1 == 30 && j1 > 16 && l + j1 < 47)
            flag = true;
        if(i1 == 1 && j1 > 16 && l + j1 < 47)
            flag = true;
        if(i1 == 30 && j1 > 16 && j1 - l < 16)
            flag = true;
        if(l + j1 > 48)
            flag = false;
        if(j1 - l > 16)
            flag = false;
        if(l > 11 && l < 17 && i1 == 30 && j1 < 13 && j1 > 1)
            flag = false;
        if(l > 4 && l < 8 && i1 == 30 && j1 < 14 && j1 > 5)
            flag = false;
        if(l > 20 && l < 25 && i1 == 30 && j1 < 14 && j1 > 5)
            flag = false;
        if(l + j1 == 48)
        {
            flag = true;
            byte0 = 2;
        }
        if(j1 - l == 16)
        {
            flag = true;
            byte0 = 2;
        }
        if(i1 < 4 && l > 27 && j1 < 27)
            flag = true;
        if(i1 > 0 && i1 < 3 && l > 28 && l < 31 && j1 < 30)
            flag = true;
        if(flag)
        {
            char c1;
            byte byte4;
            byte byte5;
            if(byte0 == 2)
            {
                c1 = '\0';
                byte4 = 38;
                byte5 = 25;
            } else
            {
                c1 = '\200';
                byte4 = 5;
                byte5 = 25;
            }
            ai[0] = j;
            ai1[0] = k - l1;
            ai[1] = j - l1;
            ai1[1] = k - 3 * k1;
            ai[2] = j;
            ai1[2] = k - 4 * k1;
            ai[3] = j + l1;
            ai1[3] = k - 3 * k1;
            g.setColor(new Color(byte3 + c1, byte3 + byte5, byte3 + byte4));
            g.fillPolygon(ai, ai1, byte1);
            ai[0] = j;
            ai1[0] = k;
            ai[1] = j - l1;
            ai1[1] = k - k1;
            ai[2] = j - l1;
            ai1[2] = k - 3 * k1;
            ai[3] = j;
            ai1[3] = k - l1;
            g.setColor(new Color(byte2 + c1, byte2 + byte5, byte2 + byte4));
            g.fillPolygon(ai, ai1, byte1);
            ai[0] = j;
            ai1[0] = k;
            ai[1] = j + l1;
            ai1[1] = k - k1;
            ai[2] = j + l1;
            ai1[2] = k - 3 * k1;
            ai[3] = j;
            ai1[3] = k - l1;
            g.setColor(new Color(c1, byte5, byte4));
            g.fillPolygon(ai, ai1, byte1);
        }
    }
    public void destroy()
    {
        System.exit(0);
    }
    public house()
    {
    }
}
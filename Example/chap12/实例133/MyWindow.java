import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import javax.swing.Timer;

public class MyWindow extends Canvas implements ActionListener
{
    static {
        // Load the library that contains the paint code.
        System.loadLibrary("MyWindow");
    }
    
    public final static int TIMER_SECONDS = 100;

    public static void main( String[] argv )
    {
        Frame f = new Frame();
        f.setSize(300,400);
        f.setLayout(new BorderLayout());
        f.setTitle("OpenGL from Java");
        
        MyWindow w = new MyWindow();
        f.add(w,BorderLayout.CENTER);
        f.add(new Label("Hello World"),BorderLayout.SOUTH);
        f.setBounds(300,300,300,300);
        f.setVisible(true);
    }

    private boolean bInitOpenGL = false;
    public void actionPerformed(ActionEvent evt) 
    {
        if(bInitOpenGL == false)
        {
            bInitOpenGL = true;
            initializeOpenGL();
        }
        paintOpenGL();
    }   

    public void addNotify()
    {
        super.addNotify();
        javax.swing.Timer timer = new Timer(TIMER_SECONDS,this);
        timer.start(); 
    }

    public void removeNotify()
    {
        super.removeNotify();
        cleanupOpenGL();
    }

    // native entry point for Painting
    public native void paintOpenGL();

    // native entry point for enabling OpenGL calls.
    public native void initializeOpenGL();

    // native entry point for disabling OpenGL calls.
    public native void cleanupOpenGL();
}

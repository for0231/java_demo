//显示简单颜色
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ShowColors extends JFrame{
public ShowColors()
{
super( "Using colors" );
setSize( 400, 130 );
show();
}
public void paint( Graphics g )
{
//用整数设置新的画图颜色
g.setColor( new Color( 255, 0, 0 ) );
g.fillRect( 25, 25, 100, 20 );
g.drawString( "Current RGB: " + g.getColor(), 130, 40 );
//用浮点数设置新的画图颜色
g.setColor( new Color( 0.0f, 1.0f, 0.0f ) );
g.fillRect( 25, 50, 100, 20 );
g.drawString( "Current RGB: " + g.getColor(), 130, 65 );
// 用静态Color objects设定新的画图颜色
g.setColor( Color.blue );
g.fillRect( 25, 75, 100, 20 );
g.drawString( "Current RGB: " + g.getColor(), 130, 90 );
// 分别显示RGB值
Color c = Color.magenta;
g.setColor( c );
g.fillRect( 25, 100, 100, 20 );
g.drawString( "RGB values: " + c.getRed() + ", " +
c.getGreen() + ", " + c.getBlue(), 130, 115 );
}
public static void main( String args[] )
{
ShowColors app = new ShowColors();
app.addWindowListener(
new WindowAdapter() {
public void windowClosing( WindowEvent e )
{
System.exit( 0 );
}
}
);
}
}
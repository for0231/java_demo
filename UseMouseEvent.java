import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.applet.Applet;

public class UseMouseEvent extends Applet implements MouseListener, MouseMotionListener
{
  Button btn;
  public void init()
  {
    btn = new Button("演示鼠标事件");
    add(btn);
    // 给按钮添加鼠标事件和鼠标移动事件
    btn.addMouseListener(this);
    btn.addMouseMotionListener(this);
    // 给画布添加鼠标事件和鼠标移动事件 
    this.addMouseListener(this);
    this.addMouseMotionListener(this);
  }

  // 单击事件
  public void mouseClicked(MouseEvent e)
  {
    Point p = new Point();
    if (e.getSource() == btn)
    {
      if (e.getClickCount() == 1)
        btn.setLabel("单击鼠标");
      else if (e.getClickCount() == 2)
        btn.setLabel("双击鼠标");
    }
    else {
      if (e.getClickCount() == 1)
      {
        p = e.getPoint();
        showStatus(p.x + ", " + p.y + "单击鼠标");
      }
      else if (e.getClickCount() == 2)
      {
        p = e.getPoint();
        showStatus(p.x + ", " + p.y + "双击鼠标");
      }
    }
  }
  public void mouseEntered(MouseEvent e)
  {
    if (e.getSource() == btn)
      btn.setLabel("进入Button");
    else
      showStatus("进入Applet");
  }

  public void mouseExited(MouseEvent e)
  {
    if (e.getSource() == btn)
      btn.setLabel("退出Button");
    else
      showStatus("退出Applet");
  }

  // 按下事件
  public void mousePressed(MouseEvent e)
  {
    if (e.getSource() == btn)
        btn.setLabel("按下鼠标");
    else 
      showStatus("按下鼠标");
  }

  // 释放事件
  public void mouseReleased(MouseEvent e)
  {
    if (e.getSource() == btn)
      btn.setLabel("松开鼠标");
    else
      showStatus("松开鼠标");
  }

  // 移动事件
  public void mouseMoved(MouseEvent e)
  {
    if (e.getSource() == btn)
      btn.setLabel("移动鼠标");
    else
      showStatus("移动鼠标, 新位置" + e.getX()+ "," + e.getY());
  }

  // 拖动事件
  public void mouseDragged(MouseEvent e)
  {
    if (e.getSource() == btn)
      btn.setLabel("拖动鼠标");
    else 
      showStatus("拖动鼠标");
  }
}

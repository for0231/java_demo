import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.io.*;

public class AppletSecurity extends Applet
{
  TextField fileNameField;
  TextArea fileArea;
  public void init()
  {
    Label lblName = new Label("文件名: ");
    Label lblContext = new Label("文件内容: ");
    fileNameField = new TextField(35);
    fileNameField.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        loadFile(fileNameField.getText());
      }
    });
    fileArea = new TextArea(10, 35);
    add(lblName);
    add(fileNameField);
    add(lblContext);
    add(fileArea);
  }

  public void loadFile(String fileName)
  {
    try
    {
      BufferedReader reader = new BufferedReader(new FileReader(fileName));
      String context = new String();
      while ((context = reader.readLine()) != null)
      {
        fileArea.append(context + "\n");
      }
      reader.close();
    }
    catch(IOException ie)
    {
      fileArea.append("IO错误: " + ie.getMessage());
    }
    catch(SecurityException se)
    {
      fileArea.append("安全访问错误: " + se.getMessage());
    }
  }
}

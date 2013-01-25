import java.io.IOException;
import java.net.URL;
import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
public class HtmlBrowser extends JFrame {
JPanel contentPane;                // 包含整个框架的容器
BorderLayout borderLayoutAll = new BorderLayout();
JLabel jLabelPrompt = new JLabel();    // 状态提示框
JPanel jPanelMain = new JPanel();
BorderLayout borderLayoutMain = new BorderLayout();
JTextField textFieldURL = new JTextField();        // URL输入框
JEditorPane jEditorPane = new JEditorPane();      // 显示网页内容的容器

public HtmlBrowser() {  // 定义构造方法
try {
jbInit();                              // 初始化并显示界面
}
catch(Exception e) {
e.printStackTrace();
}
}

private void jbInit() throws Exception  {        // 界面初始化
contentPane = (JPanel)getContentPane();
contentPane.setLayout(borderLayoutAll);
jPanelMain.setLayout(borderLayoutMain);
jLabelPrompt.setText("请输入URL");
textFieldURL.setText(""); // 清空文本框
textFieldURL.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(ActionEvent e) {
textFieldURL_actionPerformed(e); }
});
jEditorPane.setEditable(false); // 设置不可编辑
jEditorPane.addHyperlinkListener(new javax.swing.event.HyperlinkListener() {
public void hyperlinkUpdate(HyperlinkEvent e) {
jEditorPane_hyperlinkUpdate(e);
        }
});
JScrollPane scrollPane = new JScrollPane();
scrollPane.getViewport().add(jEditorPane);
jPanelMain.add(textFieldURL, "North");
jPanelMain.add(scrollPane, "Center");
contentPane.add(jLabelPrompt, "North");
contentPane.add(jPanelMain, "Center");
enableEvents(AWTEvent.WINDOW_EVENT_MASK);
this.setSize(new Dimension(600, 500));
this.setTitle("迷你IE ");
this.setVisible(true);
}

void textFieldURL_actionPerformed(ActionEvent e) {       // 输入地址后响应回车
try {
jEditorPane.setPage(textFieldURL.getText());           // 显示URL
}
    catch(IOException ex) {
JOptionPane msg = new JOptionPane();
JOptionPane.showMessageDialog(this, "URL地址不正确："+textFieldURL.getText(), "输入不正确！", 0);
    }
}

void jEditorPane_hyperlinkUpdate(HyperlinkEvent e) {        // 响应页面打开超链接消息
if(e.getEventType() == javax.swing.event.HyperlinkEvent.EventType.ACTIVATED) {
try {
URL url = e.getURL();                       // 从消息中得到URL
jEditorPane.setPage(url);                            // 显示页面内容
textFieldURL.setText(url.toString());                   // 显示URL
}
catch(IOException io){
JOptionPane msg = new JOptionPane();
JOptionPane.showMessageDialog(this, "打开该链接失败！", "输入不正确！", 0);
}
}
}

protected void processWindowEvent(WindowEvent e) { //处理窗体事件
super.processWindowEvent(e);
if (e.getID() == WindowEvent.WINDOW_CLOSING) {
System.exit(0);                            // 关闭
}
}

public static void main(String[] args) { // Main函数
    new HtmlBrowser();
}
}

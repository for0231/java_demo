import java.io.IOException;
import java.net.URL;
import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
public class HtmlBrowser extends JFrame {
JPanel contentPane;                // ����������ܵ�����
BorderLayout borderLayoutAll = new BorderLayout();
JLabel jLabelPrompt = new JLabel();    // ״̬��ʾ��
JPanel jPanelMain = new JPanel();
BorderLayout borderLayoutMain = new BorderLayout();
JTextField textFieldURL = new JTextField();        // URL�����
JEditorPane jEditorPane = new JEditorPane();      // ��ʾ��ҳ���ݵ�����

public HtmlBrowser() {  // ���幹�췽��
try {
jbInit();                              // ��ʼ������ʾ����
}
catch(Exception e) {
e.printStackTrace();
}
}

private void jbInit() throws Exception  {        // �����ʼ��
contentPane = (JPanel)getContentPane();
contentPane.setLayout(borderLayoutAll);
jPanelMain.setLayout(borderLayoutMain);
jLabelPrompt.setText("������URL");
textFieldURL.setText(""); // ����ı���
textFieldURL.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(ActionEvent e) {
textFieldURL_actionPerformed(e); }
});
jEditorPane.setEditable(false); // ���ò��ɱ༭
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
this.setTitle("����IE ");
this.setVisible(true);
}

void textFieldURL_actionPerformed(ActionEvent e) {       // �����ַ����Ӧ�س�
try {
jEditorPane.setPage(textFieldURL.getText());           // ��ʾURL
}
    catch(IOException ex) {
JOptionPane msg = new JOptionPane();
JOptionPane.showMessageDialog(this, "URL��ַ����ȷ��"+textFieldURL.getText(), "���벻��ȷ��", 0);
    }
}

void jEditorPane_hyperlinkUpdate(HyperlinkEvent e) {        // ��Ӧҳ��򿪳�������Ϣ
if(e.getEventType() == javax.swing.event.HyperlinkEvent.EventType.ACTIVATED) {
try {
URL url = e.getURL();                       // ����Ϣ�еõ�URL
jEditorPane.setPage(url);                            // ��ʾҳ������
textFieldURL.setText(url.toString());                   // ��ʾURL
}
catch(IOException io){
JOptionPane msg = new JOptionPane();
JOptionPane.showMessageDialog(this, "�򿪸�����ʧ�ܣ�", "���벻��ȷ��", 0);
}
}
}

protected void processWindowEvent(WindowEvent e) { //�������¼�
super.processWindowEvent(e);
if (e.getID() == WindowEvent.WINDOW_CLOSING) {
System.exit(0);                            // �ر�
}
}

public static void main(String[] args) { // Main����
    new HtmlBrowser();
}
}

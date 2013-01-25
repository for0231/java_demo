import java.applet.Applet; 
import java.awt.*; 
import java.io.*; 
import java.net.*; 
public class WebSendMail extends Applet{
// 初始化界面
public void init(){
String s = getParameter("BgColor");
if(s == null) 
s = "0,0,0";
bgcolor = param.decodeColor(s); 
String s1 = getParameter("FgColor");
if(s1 == null) 
s1 = "255,255,255";
fgcolor = param.decodeColor(s1); 
in_mailto = getParameter("MailTo");
if(in_mailto == null) 
in_mailto = "";
in_website = getParameter("Website");
if(in_website == null) 
in_website = "";
d = size();
SuperCedeInit();
}

public void sendMsg() { // 处理消息发送
try {
button_Send.disable();
button_Clear.disable();
host = getCodeBase().getHost();
if(host.length() == 0) 
host = "localhost";
textArea_Log.appendText("Connecting. '" + host + "'\n");
Socket socket = new Socket(host, 25); 
textArea_Log.appendText("Connect OK '" + host + "'\n");
// 创建输入输出流
DataOutputStream dataoutputstream = new DataOutputStream(socket.getOutputStream());
DataInputStream datainputstream = new DataInputStream(socket.getInputStream());
textArea_Log.appendText("Send Start '" + textField_Subject.getText() + "'\n");
textArea_Log.appendText(datainputstream.readLine() + "\n");
dataoutputstream.writeBytes("HELO " + host + "\n");
textArea_Log.appendText(datainputstream.readLine() + "\n");
dataoutputstream.writeBytes("MAIL FROM: " + textField_From.getText() + "\n");
textArea_Log.appendText(datainputstream.readLine() + "\n");
dataoutputstream.writeBytes("RCPT TO: " + textField_To.getText() + "\n");
textArea_Log.appendText(datainputstream.readLine() + "\n");
dataoutputstream.writeBytes("DATA\n");
textArea_Log.appendText(datainputstream.readLine() + "\n");
dataoutputstream.writeBytes("X-Mailer: WebSendMail\n");
dataoutputstream.writeBytes("X-WebSite: " + in_website + "\n");
dataoutputstream.writeBytes("Subject: " + textField_Subject.getText() + "\n" + 
textArea_Msg.getText() + "\n");
dataoutputstream.writeBytes(".\nQUIT\n");
textArea_Log.appendText(datainputstream.readLine() + "\n------------------------------\n");
socket.close();
button_Send.enable();
button_Clear.enable();
button_Send.enable();
button_Clear.enable();
return; 
}
catch(UnknownHostException _ex) {
// 处理连接异常
textArea_Log.appendText("Connect Error '" + host + "'\n");
return; 
}
catch(IOException _ex) {
button_Send.enable();
}
button_Clear.enable();
textArea_Log.appendText("Send Error '" + textField_Subject.getText() + "'\n");
}

public void paint(Graphics g){} 

protected void Button_SendClicked(Event event) {
// 处理发送按钮点击事件
if(textField_From.getText().length() == 0){ 
// 如果信件来源为空
textArea_Log.appendText("Input? 'From'\n");
return; 
}
if(textField_To.getText().length() == 0) {
// 如果信件目的地为空
textArea_Log.appendText("Input? 'To'\n");
return; 
}
if(textField_Subject.getText().length() == 0){ 
// 如果信件主题为空
textArea_Log.appendText("Input? 'Subject'\n");
return; 
}
if(textArea_Msg.getText().length() == 0) {
// 如果信件内容为空
textArea_Log.appendText("Input? 'Message'\n");
return; 
}
else {
sendMsg();
return; 
}
}

protected void Button_ClearClicked(Event event) {// 清除按钮点击处理，清空所有已填内容
textField_Subject.setText("");
textField_From.setText("");
textField_To.setText(in_mailto); 
textArea_Msg.setText("");
}

protected void Button_ClearLogClicked(Event event) {
// 清空日志
textArea_Log.setText("");
}

// 调用或重载超类相关方法实现初始化、开始、结束以及事件处理等
public boolean handleEvent(Event event) {
return SuperCedeEvent(event); 
}

public void start() {
SuperCedeStart();
}

public void stop() {
SuperCedeStop();
}

public String getAppletInfo() {
return "WebSendMail";
}

private final void SuperCedeInit() {
// 详细的界面设置
setLayout(null); 
setForeground(fgcolor); 
setBackground(bgcolor); 
addNotify();
resize(d.width, d.height); 
label4 = new Label("From :", 2); 
label4.setForeground(fgcolor); 
add(label4); 
int i = insets().left; 
label4.reshape(i, insets().top, 50, 22); 
textField_From = new TextField("");
textField_From.setEditable(true); 
textField_From.setForeground(new Color(0, 0, 0)); 
textField_From.setBackground(new Color(255, 255, 255)); 
add(textField_From); 
int j = insets().left + 50; 
textField_From.reshape(j, insets().top, d.width - 50, 22); 
label3 = new Label("To :", 2); 
label3.setForeground(fgcolor); 
add(label3); 
int k = insets().left; 
label3.reshape(k, insets().top + 22, 50, 22); 
textField_To = new TextField(in_mailto); 
textField_To.setEditable(true); 
textField_To.setForeground(new Color(0, 0, 0)); 
textField_To.setBackground(new Color(255, 255, 255)); 
add(textField_To); 
int l = insets().left + 50; 
textField_To.reshape(l, insets().top + 22, d.width - 50, 22); 
label_Subject = new Label("Subject :", 2); 
label_Subject.setForeground(fgcolor); 
add(label_Subject); 
label_Subject.reshape(0, insets().top + 44, 50, 22); 
textField_Subject = new TextField("");
textField_Subject.setEditable(true); 
textField_Subject.setForeground(new Color(0, 0, 0)); 
textField_Subject.setBackground(new Color(255, 255, 255)); 
add(textField_Subject); 
int i1 = insets().left + 50; 
textField_Subject.reshape(i1, insets().top + 44, d.width - 50, 22); 
textArea_Msg = new TextArea("");
textArea_Msg.setEditable(true); 
textArea_Msg.setForeground(new Color(0, 0, 0)); 
textArea_Msg.setBackground(new Color(255, 255, 255)); 
add(textArea_Msg); 
textArea_Msg.reshape(0, insets().top + 66, d.width, d.height - 170); 
textArea_Log = new TextArea("");
textArea_Log.setEditable(true); 
textArea_Log.setForeground(new Color(0, 0, 0)); 
textArea_Log.setBackground(new Color(192, 192, 192)); 
add(textArea_Log); 
textArea_Log.reshape(0, d.height - 105, d.width, 80); 
button_Send = new Button("Send");
button_Send.setForeground(new Color(128, 0, 0)); 
button_Send.setBackground(new Color(192, 192, 192)); 
add(button_Send); 
button_Send.reshape(d.width / 2 - 105, d.height - 25, 70, 25); 
button_Clear = new Button("Clear");
button_Clear.setForeground(new Color(0, 128, 0)); 
button_Clear.setBackground(new Color(192, 192, 192)); 
add(button_Clear); 
button_Clear.reshape(d.width / 2 - 35, d.height - 25, 70, 25); 
button_ClearLog = new Button("Clear Log");
button_ClearLog.setForeground(new Color(0, 0, 128)); 
button_ClearLog.setBackground(new Color(192, 192, 192)); 
add(button_ClearLog); 
button_ClearLog.reshape(d.width / 2 + 35, d.height - 25, 70, 25); 
super.init();
}

private final boolean SuperCedeEvent(Event event) {
if(event.target == button_Send && event.id == 1001) {
Button_SendClicked(event); 
return true; 
}
        if(event.target == button_Clear && event.id == 1001) {
            Button_ClearClicked(event); 
            return true; 
        }
        if(event.target != button_ClearLog || event.id != 1001) {
            return super.handleEvent(event); 
        } 
else {
            Button_ClearLogClicked(event); 
            return true; 
        }
    }
    private final void SuperCedeStart(){}
    private final void SuperCedeStop() {}
    
public WebSendMail() { // 发送邮件
        param = new CommonApplet();
        in_mailto = null; 
    }

// 成员变量
Color fgcolor; 
Color bgcolor; 
String host; 
Dimension d; 
CommonApplet param; 
String in_mailto; 
String in_website; 
Button button_Send; 
Label label_Subject; 
Label label3; 
TextField textField_Subject; 
TextField textField_From; 
TextField textField_To; 
TextArea textArea_Msg; 
Button button_Clear; 
Button button_ClearLog; 
Label label4; 
TextArea textArea_Log; 
}

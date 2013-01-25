import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
// 扩展MIDlet类来构建我们的自定义MIDlet
public class FundTracker extends MIDlet implements 
CommandListener {
//显示管理者变量
private Display display = null; 
//MIDlet的表单变量
private RequestForm reqForm = null;
//MIDlet构建器
public FundTracker () {
display = Display.getDisplay(this);
reqForm = new RequestForm("Fund Tracker");
reqForm.initForm();
reqForm.setCommandListener(this);
}
//开始 MIDlet 应用程序
protected void startApp() {
display.setCurrent(reqForm);
}
//暂停 Midlet
protected void pauseApp() {
}
//销毁Midlet
protected void destroyApp(boolean unconditional) {
}
//通过监听者响应命令
public void commandAction(Command c, Displayable s) {
if (c == reqForm.getExitCommand()) {
destroyApp(false);
notifyDestroyed();
return;
}
if ((c == reqForm.getGetCommand()) && (reqForm.getSymField().getString().length() > 0)) {
getAndDisplayQuote();
} else
{
reqForm.getMsgString().setText("Symbol required");
}
}
//储存由#分开的成对的基金字符串和报价字符串
private void storeQuote (String fund, String newQuote) {
//数据库变量 
RecordStore quoteDB = null;
try {
quoteDB = RecordStore.openRecordStore("FundQuotes", true);
byte[] data = (fund + "#" + newQuote).getBytes();
int size = data.length;
quoteDB.addRecord(data, 0, size);
quoteDB.closeRecordStore();
}
catch (Exception recordException) {
System.out.println("Unable to store quote and/or use Fund Quote database.");
}
}
//通过QuoteService类取回提交的代号表示的基金报价
private void getAndDisplayQuote(){
String fundSymbol = reqForm.getSymField().getString();
if (fundSymbol.length() > 0) {
String theQuote = QuoteService.getQuote(fundSymbol);
if (theQuote != null) {
storeQuote(fundSymbol, theQuote);
reqForm.getMsgString().setText(theQuote);
}
else
reqForm.getMsgString().setText("No quote" + '\n' + "Check Symbol");
}
} 
}
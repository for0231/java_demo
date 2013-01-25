import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
// ��չMIDlet�����������ǵ��Զ���MIDlet
public class FundTracker extends MIDlet implements 
CommandListener {
//��ʾ�����߱���
private Display display = null; 
//MIDlet�ı�����
private RequestForm reqForm = null;
//MIDlet������
public FundTracker () {
display = Display.getDisplay(this);
reqForm = new RequestForm("Fund Tracker");
reqForm.initForm();
reqForm.setCommandListener(this);
}
//��ʼ MIDlet Ӧ�ó���
protected void startApp() {
display.setCurrent(reqForm);
}
//��ͣ Midlet
protected void pauseApp() {
}
//����Midlet
protected void destroyApp(boolean unconditional) {
}
//ͨ����������Ӧ����
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
//������#�ֿ��ĳɶԵĻ����ַ����ͱ����ַ���
private void storeQuote (String fund, String newQuote) {
//���ݿ���� 
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
//ͨ��QuoteService��ȡ���ύ�Ĵ��ű�ʾ�Ļ��𱨼�
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
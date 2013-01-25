//LogMessage.java
package ejbinterop;

import java.io.Serializable;
import java.util.Date;
import java.text.*;
//һ��������־��Ϣ��ӡ����
public class LogMessage implements Serializable
{
    private String message;
    private long datetime;
	//�ڹ��캯���л�ȡ��ǰ�����ں�ʱ��
    public LogMessage(String msg) {
        message = msg;
        datetime = (new Date()).getTime();
    }
	//����һ����ʽ�����ַ���������ʾ��Ϣ
    public String toString() {
        StringBuffer sbuf = new StringBuffer();
        DateFormat dformat
            = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
	DateFormat.LONG);
        FieldPosition fpos = new
            FieldPosition(DateFormat.DATE_FIELD);
        dformat.format(new Date(datetime), sbuf, fpos);
        sbuf.append(": ");
        sbuf.append(message);
        return sbuf.toString();
    }
}
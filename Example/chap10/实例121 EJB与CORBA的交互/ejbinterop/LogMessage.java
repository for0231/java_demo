//LogMessage.java
package ejbinterop;

import java.io.Serializable;
import java.util.Date;
import java.text.*;
//一个处理日志信息打印的类
public class LogMessage implements Serializable
{
    private String message;
    private long datetime;
	//在构造函数中获取当前的日期和时间
    public LogMessage(String msg) {
        message = msg;
        datetime = (new Date()).getTime();
    }
	//产生一个格式化的字符串用于显示信息
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
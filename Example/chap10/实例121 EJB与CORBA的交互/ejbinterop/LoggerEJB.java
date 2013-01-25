//LoggerEJB.java
package ejbinterop;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;
import java.io.*;

//接受简单的字符串日志信息并在服务器上显示出来
public class LoggerEJB implements SessionBean {

    public LoggerEJB() {}
    public void ejbCreate() {}
    public void ejbRemove() {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void setSessionContext(SessionContext sc) {}

    //将服务器提供的信息和当前的时间记入日志
    public void logString(String message) {
        LogMessage msg = new LogMessage(message);

        System.out.println(msg);
    }
}
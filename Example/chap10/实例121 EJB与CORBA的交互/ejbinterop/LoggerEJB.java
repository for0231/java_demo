//LoggerEJB.java
package ejbinterop;

import javax.ejb.*;
import java.util.*;
import java.rmi.*;
import java.io.*;

//���ܼ򵥵��ַ�����־��Ϣ���ڷ���������ʾ����
public class LoggerEJB implements SessionBean {

    public LoggerEJB() {}
    public void ejbCreate() {}
    public void ejbRemove() {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void setSessionContext(SessionContext sc) {}

    //���������ṩ����Ϣ�͵�ǰ��ʱ�������־
    public void logString(String message) {
        LogMessage msg = new LogMessage(message);

        System.out.println(msg);
    }
}
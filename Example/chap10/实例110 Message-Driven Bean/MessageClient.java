//��Ϣ�ͻ��˳���
package examples.Message;
import javax.jms.*;
import javax.naming.*;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
public class MessageClient {
    public static void main(String[] args) {
        Context                 jndiContext = null;
        QueueConnectionFactory  queueConnectionFactory = null;
        QueueConnection         queueConnection = null;
        QueueSession            queueSession = null;
        Queue                   queue = null;
        QueueSender             queueSender = null;
        MapMessage             message = null;
        final int               NUM_MSGS;
        //��ȡJNDI�����ģ�ͬʱ�鿴����factory�Ͷ���		
        try {
            jndiContext = new InitialContext();
			Object tmp = jndiContext.lookup("ConnectionFactory");
			queueConnectionFactory = (QueueConnectionFactory) tmp;			
			jndiContext.lookup("java:comp/env/QueueConnectionFactory");	
			queue = (Queue) jndiContext.lookup("queue/A");			
        } catch (NamingException e) {			
            System.out.println("JNDI lookup failed: " + e.toString());
            System.exit(1);
        }
        //ͨ�����Ӵ����Ự�����������ߺ�ӳ����Ϣ��
		//������Ϣ��ͬʱ���ı����������޸�
		//����end-of-messages��Ϣ�����ر�����		
        try {
          //��������
          queueConnection = queueConnectionFactory.createQueueConnection();	  
          //ͨ�����Ӵ����Ự
          queueSession =queueConnection.createQueueSession(false,QueueSession.AUTO_ACKNOWLEDGE);
		  queueConnection.start(); 
          //����������
          queueSender = queueSession.createSender(queue);
          //����Mapӳ����Ϣ
          message = queueSession.createMapMessage(); 
          //����һЩName/Value��
          message.setString ("OrderID", "1"); 
          message.setInt ("ItemID", 5);	 
          message.setInt ("Quantity", 50); 
          message.setDouble ("UnitPrice", 5.00); 
          message.setString ("emailID", "jwu@webjet.com.cn");	  
          //������Ϣ
          queueSender.send (message);		  
        } catch (JMSException e) {
          System.out.println("Exception occurred: " + e.toString());
        }
        finally {
          if (queueConnection != null) {
            try {
              queueConnection.close();
            } catch (JMSException e) {}
          }
        }
    }
}

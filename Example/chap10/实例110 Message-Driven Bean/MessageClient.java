//消息客户端程序
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
        //获取JNDI上下文，同时查看连接factory和队列		
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
        //通过连接创建会话。创建发送者和映射消息。
		//发送消息，同时对文本进行少量修改
		//发送end-of-messages消息，最后关闭连接		
        try {
          //创建连接
          queueConnection = queueConnectionFactory.createQueueConnection();	  
          //通过连接创建会话
          queueSession =queueConnection.createQueueSession(false,QueueSession.AUTO_ACKNOWLEDGE);
		  queueConnection.start(); 
          //创建发送者
          queueSender = queueSession.createSender(queue);
          //创建Map映射消息
          message = queueSession.createMapMessage(); 
          //定义一些Name/Value对
          message.setString ("OrderID", "1"); 
          message.setInt ("ItemID", 5);	 
          message.setInt ("Quantity", 50); 
          message.setDouble ("UnitPrice", 5.00); 
          message.setString ("emailID", "jwu@webjet.com.cn");	  
          //发送消息
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

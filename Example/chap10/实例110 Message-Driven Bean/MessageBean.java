//MessageBean.java
//һ�����չ��ﶩ������Ϣ����Bean,�����������ͬʱͨ��e-mail����ʽ
//���ͻ���һ����л��Ϣ
package examples.Message;
import java.io.Serializable;
import java.rmi.RemoteException; 
import javax.ejb.*;
import javax.naming.*;
import javax.jms.*;
import java.util.*;
import java.text.*;
import javax.mail.*;
import javax.activation.*;
import javax.mail.internet.*;
//��Ϣ����Bean����ʵ�������ӿ�MessageDrivenBean��MessageListener
public class MessageBean implements MessageDrivenBean, 
    MessageListener {
    private transient MessageDrivenContext context = null;
    //һ�������εĹ��캯��
    public MessageBean() {}    
//�ڶ��󴴽��Ĺ����н�����������
    public void setMessageDrivenContext(MessageDrivenContext context) 
    {
        this.context = context;
    }   
	// onMessage��������������Ϣ����������ǿ��ת��Ϊ���ʵ���Ϣ����
	//ͬʱ��ӡ����Ϣ�����ݡ�ͬʱһ��mail note�������͸���Ϣ������
    public void onMessage(javax.jms.Message msg) {
        try {
          if (msg instanceof MapMessage) {
            MapMessage map = (MapMessage) msg;
            System.out.println("Order received: ");
			System.out.println ("Order ID: " + map.getString("OrderID") + " Item ID: " 
				+ map.getInt("ItemID") +	" Quanity: " + map.getInt("Quantity")
					+ " Unit Price: " + map.getDouble("UnitPrice"));	
            sendNote (map.getString("emailID"));			
          }
          else {
            System.out.println ("wrong message type");
          }
        } catch (Throwable te) {
          te.printStackTrace();
        }
    }

	//����һ��e-mail֪ͨ����recipient����ȷ����e-mail�˺�
    private void sendNote(String recipient) {
        try {
          //�õ�����������
          Context initial = new InitialContext();		  
          // ��ѯmail �������ĻỰ
          javax.mail.Session session = (javax.mail.Session)	initial.lookup("java:comp/env/MailSession");
          //����һ���µ�mail����
          javax.mail.Message msg = new MimeMessage(session);		  
          //����mail������ 
          msg.setFrom();		  
		  msg.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(recipient, false));		 
          msg.setSubject("Order Confirmation");		  
          DateFormat dateFormatter
             = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);
		  Date timeStamp = new Date();		 
          String messageText = "Thank you for your order." + '\n'
			+ "We received your order on " + dateFormatter.format(timeStamp) + "."; 
          msg.setText(messageText); 
          msg.setSentDate(timeStamp);	
          //����mail��Ϣ
          Transport.send(msg);		  
        } catch(Exception e) {
          throw new EJBException(e.getMessage());
        }
    }
    public void ejbCreate() {}
    public void ejbRemove() {}
}

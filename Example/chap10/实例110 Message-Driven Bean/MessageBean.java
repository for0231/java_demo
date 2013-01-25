//MessageBean.java
//一个接收购物订单的消息驱动Bean,处理这个订单同时通过e-mail的形式
//给客户发一个感谢消息
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
//消息驱动Bean必须实现两个接口MessageDrivenBean和MessageListener
public class MessageBean implements MessageDrivenBean, 
    MessageListener {
    private transient MessageDrivenContext context = null;
    //一个不带参的构造函数
    public MessageBean() {}    
//在对象创建的过程中将被容器调用
    public void setMessageDrivenContext(MessageDrivenContext context) 
    {
        this.context = context;
    }   
	// onMessage函数方法接收消息参数，将其强制转型为合适的消息类型
	//同时打印出消息的内容。同时一个mail note将被发送给消息发送者
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

	//发送一个e-mail通知给由recipient参数确定的e-mail账号
    private void sendNote(String recipient) {
        try {
          //得到名字上下文
          Context initial = new InitialContext();		  
          // 查询mail 服务器的会话
          javax.mail.Session session = (javax.mail.Session)	initial.lookup("java:comp/env/MailSession");
          //创建一个新的mail对象
          javax.mail.Message msg = new MimeMessage(session);		  
          //设置mail的属性 
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
          //发送mail消息
          Transport.send(msg);		  
        } catch(Exception e) {
          throw new EJBException(e.getMessage());
        }
    }
    public void ejbCreate() {}
    public void ejbRemove() {}
}

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;
//实现处理消息请求的响应者
public class Replier implements MessageListener {

	private Session session;
	private MessageProducer invalidProducer;

	protected Replier() {
		super();
	}
	//创建和初始化一个响应者对象Replier
	public static Replier newReplier(Connection connection, String requestQueueName, String invalidQueueName)
		throws JMSException, NamingException {

		Replier replier = new Replier();
		replier.initialize(connection, requestQueueName, invalidQueueName);
		return replier;
	}
	//初始化响应者对象Replier
	protected void initialize(Connection connection, String requestQueueName, String invalidQueueName)
		throws NamingException, JMSException {
		//创建一个会话
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination requestQueue = JndiUtil.getDestination(requestQueueName);
		Destination invalidQueue = JndiUtil.getDestination(invalidQueueName);

		MessageConsumer requestConsumer = session.createConsumer(requestQueue);
		MessageListener listener = this;
		requestConsumer.setMessageListener(listener);

		invalidProducer = session.createProducer(invalidQueue);
	}

	public void onMessage(Message message) {
		try {
			//接收request请求产生相应的响应信息
			if ((message instanceof TextMessage) && (message.getJMSReplyTo() != null)) {
				TextMessage requestMessage = (TextMessage) message;
				//输出接收到request的打印信息
				System.out.println("Received request");
				System.out.println("\tTime:       " + System.currentTimeMillis() + " ms");
				System.out.println("\tMessage ID: " + requestMessage.getJMSMessageID());
				System.out.println("\tCorrel. ID: " + requestMessage.getJMSCorrelationID());
				System.out.println("\tReply to:   " + requestMessage.getJMSReplyTo());
				System.out.println("\tContents:   " + requestMessage.getText());
				//响应request产生要输出的信息
				String contents = requestMessage.getText();
				Destination replyDestination = message.getJMSReplyTo();
				MessageProducer replyProducer = session.createProducer(replyDestination);
				
				TextMessage replyMessage = session.createTextMessage();
				replyMessage.setText(contents);
				replyMessage.setJMSCorrelationID(requestMessage.getJMSMessageID());
				replyProducer.send(replyMessage);
				//输出响应信息
				System.out.println("Sent reply");
				System.out.println("\tTime:       " + System.currentTimeMillis() + " ms");
				System.out.println("\tMessage ID: " + replyMessage.getJMSMessageID());
				System.out.println("\tCorrel. ID: " + replyMessage.getJMSCorrelationID());
				System.out.println("\tReply to:   " + replyMessage.getJMSReplyTo());
				System.out.println("\tContents:   " + replyMessage.getText());
			} else {
				//处理无效的请求
				System.out.println("Invalid message detected");
				System.out.println("\tType:       " + message.getClass().getName());
				System.out.println("\tTime:       " + System.currentTimeMillis() + " ms");
				System.out.println("\tMessage ID: " + message.getJMSMessageID());
				System.out.println("\tCorrel. ID: " + message.getJMSCorrelationID());
				System.out.println("\tReply to:   " + message.getJMSReplyTo());

				message.setJMSCorrelationID(message.getJMSMessageID());
				invalidProducer.send(message);

				System.out.println("Sent to invalid message queue");
				System.out.println("\tType:       " + message.getClass().getName());
				System.out.println("\tTime:       " + System.currentTimeMillis() + " ms");
				System.out.println("\tMessage ID: " + message.getJMSMessageID());
				System.out.println("\tCorrel. ID: " + message.getJMSCorrelationID());
				System.out.println("\tReply to:   " + message.getJMSReplyTo());
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}


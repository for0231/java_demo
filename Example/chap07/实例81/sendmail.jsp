<! ʹ��javamail����>
<%@ page contentType="text/html;charSet=gb2312"%>
<!����javamail��>
<%@ page 
import=" javax.mail.*�� javax.mail.internet.*�� javax.activation.*��java.util.*" 
%> 
<html> 
<head> 
<TITLE>JSP  USE JavaMail </TITLE> 
</HEAD> 
<BODY> 
<% 
try{ 
Properties props = new Properties(); 
Session sendMailSession; 
Store store; 
Transport transport; 
<! ���еĻ���JavaMail�ĳ���������Ҫһ����ȫ���ĶԻ�Ŀ��>
sendMailSession = Session.getInstance(props�� null); 
<! JavaMail��Ҫ����һ����ʽΪ"mail.smtp.host"���ļ�����������Ϣ>
props.put("mail.smtp.host"�� "smtp.jspinsider.com"); 
<! ��Ϣ���󽫰��������͵��ʼ���ʵ�ķ�ӳ����>
Message newMessage = new MimeMessage(sendMailSession); 
< !���email����ҳ�����Ϣ>
newMessage.setFrom(new InternetAddress(request.getParameter("from"))); 
newMessage.setRecipient(Message.RecipientType.TO�� new InternetAddress(request.getParameter("to"))); 
newMessage.setSubject(request.getParameter("subject")); 
newMessage.setSentDate(new Date()); 
newMessage.setText(request.getParameter("text")); 
transport = sendMailSession.getTransport("smtp"); 
<!��ʼ������Ϣ>
transport.send(newMessage); 
%> 
<P>���ͳɹ�.</P> 
<% 
} 
catch(MessagingException m) 
{ 
out.println(m.toString()); 
} 
%> 
</BODY> 
</HTML>

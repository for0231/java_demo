<! 使用javamail工具>
<%@ page contentType="text/html;charSet=gb2312"%>
<!导入javamail包>
<%@ page 
import=" javax.mail.*， javax.mail.internet.*， javax.activation.*，java.util.*" 
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
<! 所有的基于JavaMail的程序都至少需要一个或全部的对话目标>
sendMailSession = Session.getInstance(props， null); 
<! JavaMail需要创建一个格式为"mail.smtp.host"的文件用来发送信息>
props.put("mail.smtp.host"， "smtp.jspinsider.com"); 
<! 信息对象将把你所发送的邮件真实的反映出来>
Message newMessage = new MimeMessage(sendMailSession); 
< !获得email发送页面的信息>
newMessage.setFrom(new InternetAddress(request.getParameter("from"))); 
newMessage.setRecipient(Message.RecipientType.TO， new InternetAddress(request.getParameter("to"))); 
newMessage.setSubject(request.getParameter("subject")); 
newMessage.setSentDate(new Date()); 
newMessage.setText(request.getParameter("text")); 
transport = sendMailSession.getTransport("smtp"); 
<!开始发送信息>
transport.send(newMessage); 
%> 
<P>发送成功.</P> 
<% 
} 
catch(MessagingException m) 
{ 
out.println(m.toString()); 
} 
%> 
</BODY> 
</HTML>

<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="javax.naming.Context"%>
<%@ page import="javax.naming.InitialContext"%>
<%@ page import="bsejb.*"%>
<%@ page import="javax.rmi.PortableRemoteObject"%>
<%@ page import="java.util.Properties"%>
<html>
<head>
<title>
BSBrowser
</title>
</head>
<body>
<h1>
<%="This is BS EJB example!"%><br>
<%
try{
	//Ϊ��ʼ�������Ļ��һ��ϵͳ���Զ���
  Properties pro=System.getProperties();
  //��ʼ�����õ�һ��������
  Context ctx = new InitialContext(pro);
  //��ѯjndi����ͨ��ǿ��ת�͵õ�Home�ӿ�
  theServerHome home = (theServerHome)javax.rmi.PortableRemoteObject.narrow(ctx.lookup("theServer"), theServer.class);
  //�õ�Զ�̽ӿڵ�����
  theServer theServer=home.create();
  out.write(theServer.BServer());
}
catch(Exception e)
{
  out.println(e.toString());
}
%>
<%="This is Browser speaking."%><br>
<%="The example is over!"%><br>

</h1>
</body>
</html>

package javawebservice;
import java.net.*;
import java.util.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
public class RPCClient
{
	public static void main(String[] args) throws Exception
	{
		//����Զ�̺���
		Call call = new Call();
		//����Ŀ��URI
		call.setTargetObjectURI("urn:javawebservice.RPC.Service");
		//����Զ�̵��÷���
		call.setMethodName("getInfo");
		//�����ַ�����
		call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
		//����Զ�̷�������
		Vector params = new Vector();
		params.addElement(
			new Parameter("str", String.class, "SUN", null));
		call.setParams(params);
		//������ӦURL
		Response resp = call.invoke(new URL(
			"http://127.0.01:8080/soap/servlet/rpcrouter"),"");
		//�õ���Ӧ����
		if (resp.generatedFault())
		{
			Fault fault = resp.getFault();
			System.err.println("Generated fault: " + fault);
		}
		else
		{
			Parameter result = resp.getReturnValue();
			System.out.println(result.getValue());
		}
	}
}

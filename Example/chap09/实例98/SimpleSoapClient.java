package javawebservice;
import java.net.*;
import java.util.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
public class SimpleSoapClient
{
	public static void main(String[] args)
	{
		System.out.println("SOAP calling test beginning...");
		SimpleSoapClient client=new SimpleSoapClient();
		System.out.println("the upper case string is:"+
			client.doRequest(args[0]));
	}
	public String doRequest(String str)
	{
		try
		{
			//����Զ�̺���
			Call call=new Call();
			//����Զ�̶���URI�������벿��ʱ�ִ�һ��
			call.setTargetObjectURI(
				"urn:javawebservice.string.touppercase");
			//����Զ�̷���
			call.setMethodName("doChange");
			//���ñ�����
			call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
			//����Զ�̷���������������в�������Vector��
			Vector params=new Vector();
			params.addElement(new Parameter(
				"str",String.class,new String(str),null));
			call.setParams(params);
			//����RPC����
			Response resp=call.invoke(new URL(
				"http://127.0.01:8080/soap/servlet/rpcrouter"),"");
			//�������
			if(resp.generatedFault())
			{
				Fault fault=resp.getFault();
				System.out.println("call faild:");
				System.out.println("Fault Code="+
					fault.getFaultCode());
				System.out.println("Fault String"+
					fault.getFaultString());
				return "Something wrong!";
			}
			else
			{
				//�õ����������ؽ��
				Parameter result=resp.getReturnValue();
				//����ת��
				return (String)result.getValue().toString();
			}
		}
		catch(Exception e)
		{
			return e.toString();
		}
	}
}

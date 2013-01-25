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
			//建立远程呼叫
			Call call=new Call();
			//设置远程对象URI，必须与部属时字串一致
			call.setTargetObjectURI(
				"urn:javawebservice.string.touppercase");
			//设置远程方法
			call.setMethodName("doChange");
			//设置编码风格
			call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
			//设置远程方法所需参数，所有参数置于Vector中
			Vector params=new Vector();
			params.addElement(new Parameter(
				"str",String.class,new String(str),null));
			call.setParams(params);
			//发送RPC请求
			Response resp=call.invoke(new URL(
				"http://127.0.01:8080/soap/servlet/rpcrouter"),"");
			//结果处理
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
				//得到服务器返回结果
				Parameter result=resp.getReturnValue();
				//代码转换
				return (String)result.getValue().toString();
			}
		}
		catch(Exception e)
		{
			return e.toString();
		}
	}
}

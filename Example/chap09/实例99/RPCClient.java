package javawebservice;
import java.net.*;
import java.util.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
public class RPCClient
{
	public static void main(String[] args) throws Exception
	{
		//建立远程呼叫
		Call call = new Call();
		//设置目标URI
		call.setTargetObjectURI("urn:javawebservice.RPC.Service");
		//设置远程调用方法
		call.setMethodName("getInfo");
		//设置字符编码
		call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
		//设置远程方法参数
		Vector params = new Vector();
		params.addElement(
			new Parameter("str", String.class, "SUN", null));
		call.setParams(params);
		//设置响应URL
		Response resp = call.invoke(new URL(
			"http://127.0.01:8080/soap/servlet/rpcrouter"),"");
		//得到相应数据
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

package javawebservice;
import java.io.*;
import java.net.*;
import java.util.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
public class EjbTest
{
	public static void main(String[] args) throws Exception
	{
		if (args.length != 1)
		{
			System.err.println(
				"Usage: java " +
				ejbtest.class.getName() + 
				" SOAP-router-URL");
			System.exit(1);
		}
		URL url = new URL(args[0]);
		// 建立远程呼叫连接
		Call call = new Call();
		call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
		call.setTargetObjectURI("urn:ejbhello");
		// 设置远程方法
		// 注意，这里调用的是hello()，而不是配置文件中的create()
		call.setMethodName("hello");
		Vector params = new Vector();
		params.addElement(
			new Parameter("phrase", String.class, 
			"what's your name?", null));
		call.setParams(params);
		Response resp = call.invoke(url, "");
		if (resp.generatedFault())
		{
			Fault fault = resp.getFault();
			System.err.println("Generated fault: " + fault);
		}
		else
		{
			Parameter result = resp.getReturnValue();
			System.out.println("Done. result=" + result.getValue());
		}
	}
}

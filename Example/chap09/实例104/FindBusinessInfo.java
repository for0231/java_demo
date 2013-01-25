	package javawebservice;
import com.ibm.uddi.*;
import com.ibm.uddi.response.*;
import com.ibm.uddi.client.*;
import com.ibm.uddi.datatype.business.*;
import org.w3c.dom.*;
import java.util.*;

public class FindBusinessInfo
{
	public static void main(String[] args)
	{
		FindBusinessInfo demo=new FindBusinessInfo();
		demo.run();
		System.exit(0);
	}
	public void run()
	{
		// 建立UDDI代理
		UDDIProxy proxy=new UDDIProxy();
		try
		{
			// 设置请求页面
			proxy.setInquiryURL
	("http://www-3.ibm.com/services/uddi/testregistry/inquiryapi");
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		try
		{
			// 获得所有以A开头的业务信息
			BusinessList list=proxy.find_business("A",null,0);
			Vector info=
list.getBusinessInfos().getBusinessInfoVector();
			for(int i=0;i<info.size();i++)
			{
				BusinessInfo business=(BusinessInfo)info.elementAt(i);
				System.out.println(business.getNameString());
			}
		}
		catch(UDDIException ue)
		{
			System.out.println(ue.getDispositionReport());
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}			
	}
}

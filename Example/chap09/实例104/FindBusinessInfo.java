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
		// ����UDDI����
		UDDIProxy proxy=new UDDIProxy();
		try
		{
			// ��������ҳ��
			proxy.setInquiryURL
	("http://www-3.ibm.com/services/uddi/testregistry/inquiryapi");
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		try
		{
			// ���������A��ͷ��ҵ����Ϣ
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

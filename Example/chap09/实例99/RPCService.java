package javawebservice;
import java.net.URL;
import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.apache.soap.util.xml.*;
public class RPCService
{
	//����DocumentBuilder
	DocumentBuilder xdb = XMLParserUtils.getXMLDocBuilder();
	public Hashtable getInfo(String str) throws Exception
	{
		//������ѯ�����
		Hashtable result=new Hashtable();
		try
		{
			//����URL
			URL url =
				new URL(
					"http://www.xmltoday.com/examples/stockquote"+
						"/getxmlquote.vep?s="+str);
			//�����Ӧ��
			InputStream is = url.openStream();
			//������Ӧ��XML��
			Document doc = xdb.parse(is);
			//�ڵ�ϼ�
			Element element = doc.getDocumentElement();
			//�õ�price��ǩ�ϼ�
			NodeList list = element.getElementsByTagName("price");
			//�����ϼ�
			for(int i=0;i<list.getLength();i++)
			{
				element = (Element) list.item(i);
				String info = element.getAttribute("value");
				switch(i)
				{
					case 0:
						result.put("ask",info);
						break;
					case 1:
						result.put("open",info);
						break;
					case 2:
						result.put("dayhigh",info);
						break;
					default:
						result.put("daylow",info);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		//���ؽ�����Ľ���ϼ�
		return result;
	}
}

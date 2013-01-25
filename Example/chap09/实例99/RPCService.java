package javawebservice;
import java.net.URL;
import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.apache.soap.util.xml.*;
public class RPCService
{
	//建立DocumentBuilder
	DocumentBuilder xdb = XMLParserUtils.getXMLDocBuilder();
	public Hashtable getInfo(String str) throws Exception
	{
		//建立查询结果集
		Hashtable result=new Hashtable();
		try
		{
			//请求URL
			URL url =
				new URL(
					"http://www.xmltoday.com/examples/stockquote"+
						"/getxmlquote.vep?s="+str);
			//请求回应流
			InputStream is = url.openStream();
			//解析回应的XML流
			Document doc = xdb.parse(is);
			//节点合集
			Element element = doc.getDocumentElement();
			//得到price标签合集
			NodeList list = element.getElementsByTagName("price");
			//遍历合集
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
		//返回解析后的结果合集
		return result;
	}
}

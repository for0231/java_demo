package javawebservice;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import org.apache.xerces.parsers.*;
public class SAXDemo extends DefaultHandler
{
	public static void main(String[] args)
	{
		try
		{
			//生成SAXParser
			SAXParser parser=(SAXParser)Class.
				forName("org.apache.xerces.parsers.SAXParser").
					newInstance();
			SAXDemo handler=new SAXDemo();
			//生成Handler实例
			FilterHandler filter=new FilterHandler();
			parser.setContentHandler(handler);
			System.out.println("\nNow the parser is working...\n");
			//解析文件
			parser.parse("TestXerces.xml");
			System.out.println("\nNow the filter is working ...\n");
			//为过滤器设置解析器
			filter.setParent(parser);
			//为过滤器设置处理器
			filter.setContentHandler(handler);
			//解析文件
			filter.parse("TestXerces.xml");
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}

	}
	public void startElement
		(String uri,String localName,String qName,Attributes attr)
	{
		System.out.println("start of a Element --> uri: "+
			uri+" localName: "+localName+" qName: "+qName);
		//打印该元素节点所有属性
		if(attr.getLength()>0)
		{
			for(int i=0;i<attr.getLength();i++)
			{
				System.out.println("\t...with attribute --> "+
					attr.getQName(i)+"="+attr.getValue(i));
			}
		}
	}
	public void endElement
		(String uri,String localName,String qName)
	{
		System.out.println("end of a Element --> uri: "+uri+
			" localName: "+localName+" qName: "+qName);
	}
	static class FilterHandler extends XMLFilterImpl
	{
		int index=0;
		public void startElement
			(String uri,String localName,String qName,Attributes attr)
				 throws SAXException
		{
			AttributesImpl attrImpl=new AttributesImpl(attr);
			for (int i=0;i<attrImpl.getLength();i++)
			{
				//检查是否有ID属性
				if(attrImpl.getQName(i).toUpperCase().equals("ID"))
				{
					attrImpl.removeAttribute(i);
					break;
				}
			}
			//新建ID属性
			attrImpl.addAttribute
				("","id","id","ID",String.valueOf(index++));
			super.startElement(uri,localName,qName,attrImpl);
		}
		public void endElement
			(String uri,String localName,String qName)
				 throws SAXException
		{
			super.endElement(uri,localName,qName);
		}
		public void characters(char[] ch,int iStart,int iLength) 
			throws SAXException
		{
			super.characters(ch,iStart,iLength);
		}
	}
}

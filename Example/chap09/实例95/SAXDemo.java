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
			//����SAXParser
			SAXParser parser=(SAXParser)Class.
				forName("org.apache.xerces.parsers.SAXParser").
					newInstance();
			SAXDemo handler=new SAXDemo();
			//����Handlerʵ��
			FilterHandler filter=new FilterHandler();
			parser.setContentHandler(handler);
			System.out.println("\nNow the parser is working...\n");
			//�����ļ�
			parser.parse("TestXerces.xml");
			System.out.println("\nNow the filter is working ...\n");
			//Ϊ���������ý�����
			filter.setParent(parser);
			//Ϊ���������ô�����
			filter.setContentHandler(handler);
			//�����ļ�
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
		//��ӡ��Ԫ�ؽڵ���������
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
				//����Ƿ���ID����
				if(attrImpl.getQName(i).toUpperCase().equals("ID"))
				{
					attrImpl.removeAttribute(i);
					break;
				}
			}
			//�½�ID����
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

package javawebserivce;
import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
public class JAXPDemo extends DefaultHandler
{
	private Hashtable tags;
	public void startDocument() throws SAXException
	{
		tags=new Hashtable();
	}
	public void startElement
		(String namespaceURI,String localName,
			String keyName,Attributes atts) throws SAXException
	{
		String key=keyName;
		Object value=tags.get(key);
		//����ϣ����û�д�Ԫ������Ӵ�Ԫ��
		if(value==null)
		{
			tags.put(key,new Integer(1));
		}
		//���Ѿ��д�Ԫ����Ԫ����������
		else
		{
			int count=((Integer)value).intValue();
			count++;
			tags.put(key,new Integer(count));
		}
	}
	public void endDocument() throws SAXException
	{
		Enumeration keys=tags.keys();
		//XML�ĵ���������ʱ��ӡ���б�ǩ���ִ���
		while(keys.hasMoreElements())
		{
			String tag=(String)keys.nextElement();
			int count=((Integer)tags.get(tag)).intValue();
			System.out.println("Tag<"+tag+"> occurs "+count+" times ");
		}
	}
	//��Fileת����URL
	private static String convertToFileURL(String fileName)
	{
		String path=new File(fileName).getAbsolutePath();
		if(File.separatorChar!='/')
		{
			path=path.replace(File.separatorChar,'/');
		}
		if(!path.startsWith("/"))
		{
			path="/"+path;
		}
		return "file:"+path;
	}
	private static void usage()
	{
		System.out.println("Usage:JAXPDemo [-v] <filename>");
		System.out.println("		-v=validation");
		System.exit(1);
	}
	public static void main(String[] args)
	{
		String fileName=null;
		boolean validation=false;
		for(int i=0;i<args.length;i++)
		{
			if(args[i].equals("-v"))
			{
				validation=true;
			}
			else
			{
				fileName=args[i];
				if(i!=args.length-1)
				{
					usage();
				}
			}
			if(fileName==null)
			{
				usage();
			}
			//����SAXParserFactoryʵ��
			SAXParserFactory factory=SAXParserFactory.newInstance();
			factory.setValidating(validation);
			//����XMLReaderʵ��
			XMLReader xmlReader=null;
			try
			{
				SAXParser saxParser=factory.newSAXParser();
				xmlReader=saxParser.getXMLReader();
			}
			catch(Exception e)
			{
				System.out.println(e.toString());
				System.exit(1);
			}
			//����XMLReader�����ݴ���
			xmlReader.setContentHandler(new JAXPDemo());
			//����XMLReader�Ĵ�����
			xmlReader.setErrorHandler(new MyErrorHandler(System.out));
			try
			{
				//����XML�ļ�
				xmlReader.parse(convertToFileURL(fileName));
			}
			catch(SAXException se)
			{
				System.out.println(se.toString());
				System.exit(1);
			}
			catch(IOException ioe)
			{
				System.out.println(ioe.toString());
				System.exit(1);
			}
		}
	}
	private static class MyErrorHandler implements ErrorHandler
	{
		private PrintStream out;
		MyErrorHandler(PrintStream out)
		{
			this.out=out;
		}
		private String getParseExceptionInfo(SAXParseException spe)
		{
			String systemID=spe.getSystemId();
			if(systemID==null)
			{
				systemID="null";
			}
			String info="URI="+systemID+"Line="+spe.getLineNumber()+
				":"+spe.getMessage();
			return info;
		}
		public void warning(SAXParseException spe) throws SAXException
		{
			out.println("Waring:"+getParseExceptionInfo(spe));
		}
		public void error(SAXParseException spe) throws SAXException
		{
			String message="Error:"+getParseExceptionInfo(spe);
			throw new SAXException(message);
		}
		public void fatalError(SAXParseException spe) throws SAXException
		{
			String message="Fatal Error:"+getParseExceptionInfo(spe);
			throw new SAXException(message);
		}
	}
}

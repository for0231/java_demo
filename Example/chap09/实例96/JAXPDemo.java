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
		//若哈希表中没有此元素则添加此元素
		if(value==null)
		{
			tags.put(key,new Integer(1));
		}
		//若已经有此元素则元素数量自增
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
		//XML文档解析结束时打印所有标签出现次数
		while(keys.hasMoreElements())
		{
			String tag=(String)keys.nextElement();
			int count=((Integer)tags.get(tag)).intValue();
			System.out.println("Tag<"+tag+"> occurs "+count+" times ");
		}
	}
	//将File转化成URL
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
			//创建SAXParserFactory实例
			SAXParserFactory factory=SAXParserFactory.newInstance();
			factory.setValidating(validation);
			//创建XMLReader实例
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
			//设置XMLReader的内容处理
			xmlReader.setContentHandler(new JAXPDemo());
			//设置XMLReader的错误处理
			xmlReader.setErrorHandler(new MyErrorHandler(System.out));
			try
			{
				//解析XML文件
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

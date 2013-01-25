package javawebservice;
import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
public class JDOMDemo
{
	public static Document readDocument()
	{
		try
		{
			//新建SAXBuilder
			SAXBuilder builder = new SAXBuilder();
			//得到模板XML文件
			Document anotherDocument =
				builder.build(new File("xml/sample.xml"));
			return anotherDocument;
		}
		catch (JDOMException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public static Document createDocument()
	{
		//建立根节点
		Element carElement = new Element("car");
		//建立文档
		Document myDocument = new Document(carElement);
		//建立注释节点
		carElement.addContent(
			new Comment("Description of a car"));
		Element make = new Element("make");
		make.addContent("Toyota");
		carElement.addContent(make);
		//建立更多的元素节点
		carElement.addContent(
			new Element("model").addContent("Celica"));
		carElement.addContent(
			new Element("year").addContent("1997"));
		carElement.addContent(
			new Element("color").addContent("green"));
		return myDocument;
	}
	public static void accessChildElement(Document myDocument)
	{
		//根节点的建立
		Element carElement = myDocument.getRootElement();
		//与子节点进行数据通讯
		Element yearElement = carElement.getChild("year");
		if (yearElement != null)
		{
			System.out.println(
				"Here is the element we found: "
					+ yearElement.getName()
					+ ".  Its content: "
					+ yearElement.getText()
					+ "\n");
		}
		else
		{
			System.out.println(
				"Something is wrong."+
					"  We did not find a year Element");
		}
	}
	public static void removeChildElement(Document myDocument)
	{
		System.out.println(
			"About to remove the year element.\n"+
				"The current document:");
		outputDocument(myDocument);
		Element carElement = myDocument.getRootElement();
		//删除节点
		boolean removed = carElement.removeChild("year");
		if (removed)
		{
			System.out.println(
				"Here is the modified document without year:");
			outputDocument(myDocument);
		}
		else
		{
			System.out.println(
				"Something happened."+
					"We were unable to remove the year element.");
		}
	}
	public static void outputDocument(Document myDocument)
	{
		try
		{
			XMLOutputter outputter = new XMLOutputter("  ", true);
			outputter.output(myDocument, System.out);
		}
		catch (java.io.IOException e)
		{
			e.printStackTrace();
		}
	}
	public static void outputDocumentToFile(Document myDocument)
	{
		try
		{
			XMLOutputter outputter = new XMLOutputter("  ", true);
			//建立一个新的XML文件
			FileWriter writer = new FileWriter("xml/myFile.xml");
			outputter.output(myDocument, writer);
			writer.close();
		}
		catch (java.io.IOException e)
		{
			e.printStackTrace();
		}
	}
	public static void executeXSL(Document myDocument)
	{
		try
		{
			TransformerFactory tFactory = 
				TransformerFactory.newInstance();
			org.jdom.output.DOMOutputter outputter =
				new org.jdom.output.DOMOutputter();
			org.w3c.dom.Document domDocument = 
				outputter.output(myDocument);
			javax.xml.transform.Source xmlSource =
				new javax.xml.transform.dom.DOMSource(domDocument);
			StreamSource xsltSource =
				new StreamSource(
					new FileInputStream("xml/car.xsl"));
			StreamResult xmlResult = new StreamResult(System.out);
			Transformer transformer = 
				tFactory.newTransformer(xsltSource);
			
			transformer.transform(xmlSource, xmlResult);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (TransformerConfigurationException e)
		{
			e.printStackTrace();
		}
		catch (TransformerException e)
		{
			e.printStackTrace();
		}
		catch (org.jdom.JDOMException e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String argv[])
	{
		if (argv.length == 1)
		{
			String command = argv[0];
			if (command.equals("create"))
				outputDocument(createDocument());
			else if (command.equals("access"))
				accessChildElement(createDocument());
			else if (command.equals("remove"))
				removeChildElement(createDocument());
			else if (command.equals("output"))
				outputDocument(createDocument());
			else if (command.equals("file"))
				outputDocumentToFile(createDocument());
			else if (command.equals("read"))
				outputDocument(readDocument());
			else if (command.equals("xsl"))
				executeXSL(createDocument());
			else
			{
				System.out.println(
					command + " is not a valid option.");
				printUsage();
			}
		}
		else
		{
			printUsage();
		}
	}
	public static void printUsage()
	{
		System.out.println(
"Usage: Article [option] \n where option is one of the following:");
		System.out.println(
"  create - create a document as shown in Listing 2");
		System.out.println(
"  access - access a child element as shown in Listing 3");
		System.out.println(
"  remove - remove a child element as shown in Listing 4");
		System.out.println(
"  output - output a document to the console as shown in Listing 5");
		System.out.println(
"  file   - output a document to xml/myFile.xml as shown in Listing 6");
		System.out.println(
"  read   - parse a document from xml/sample.xml as shown in Listing 7");
		System.out.println(
"  xsl    - transform a document as shown in Listing 9");
	}
}

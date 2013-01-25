package javawebservice;
import java.io.*;
import javax.xml.parsers.*;	
import org.xml.sax.*;
import org.w3c.dom.*;
public class DOMDemo
{
	//������������ʽ
	static final String outputEncoding="UTF-8";
	private PrintWriter out;
	DOMDemo(PrintWriter out)
	{
		this.out=out;
	}
	private void print(Node node)
	{
		//��ӡ�ڵ�����
		out.println("NodeName=\""+node.getNodeName()+"\"");
		//��ӡ�ڵ�URI
		String value=node.getNamespaceURI();
		if(value!=null)
		{
			out.println("URL=\""+value+"\"");
		}
		//��ӡ�ڵ�ǰ׺
		value=node.getPrefix();
		if(value!=null)
		{
			out.println("Prefix=\""+value+"\"");
		}
		//��ӡ�ڵ㱾������
		value=node.getLocalName();
		if(value!=null)
		{
			out.println("LocalName=\""+value+"\"");
		}
		//��ӡ�ڵ�ֵ
		value=node.getNodeValue();
		if(value!=null)
		{
			out.println("NodeValue=");
			if(value.trim().equals(""))
			{
				out.println("[WS]");
			}
			else
			{
				out.println("\""+node.getNodeValue()+"\"");
			}
		}
	}
	private void echo(Node node)
	{
		int type=node.getNodeType();
		switch(type)
		{
			//���Խڵ�
			case Node.ATTRIBUTE_NODE:
				out.println("ATTR");
				print(node);
				break;
			//CDATA
			case Node.CDATA_SECTION_NODE:
				out.println("CDATA");
				print(node);
				break;
			//ע��
			case Node.COMMENT_NODE:
				out.println("COMMENT");
				print(node);
				break;
			//����ڵ�
			case Node.DOCUMENT_FRAGMENT_NODE:
				out.println("DOC_FRAGMENT");
				print(node);
				break;
			//�ĵ��ڵ�
			case Node.DOCUMENT_NODE:
				out.println("DOC");
				print(node);
				break;
			//�ĵ����ͽڵ�
			case Node.DOCUMENT_TYPE_NODE:
				out.println("DOC_TYPE");
				print(node);
				break;
			//Ԫ�ؽڵ�
			case Node.ELEMENT_NODE:
				out.println("ELEMENT");
				print(node);
				//�õ��ýڵ���������
				NamedNodeMap atts=node.getAttributes();
				for(int i=0;i<atts.getLength();i++)
				{
					Node att=atts.item(i);
					//�ݹ����
					echo(att);
				}
				break;
			//ʵ��ڵ�
			case Node.ENTITY_NODE:
				out.println("ENTITY");
				print(node);
				break;
			//ʵ�����ýڵ�
			case Node.ENTITY_REFERENCE_NODE:
				out.println("ENTITY_REF");
				print(node);
				break;
			//����˵���ڵ�
			case Node.NOTATION_NODE:
				out.println("NOTATION");
				print(node);
				break;
			//Ԥ����ڵ�
			case Node.PROCESSING_INSTRUCTION_NODE:
				out.println("PROC_INST");
				print(node);
				break;
			//�ı��ڵ�
			case Node.TEXT_NODE:
				out.println("TEXT");
				print(node);
				break;
			//δ֪�ڵ�
			default:
				out.println("UNKNOWN NODE:"+type);
				print(node);
		}
		//�����ýڵ��ӽڵ�
		for(Node child=node.getFirstChild();
			child!=null;child=child.getNextSibling())
		{
			echo(child);
		}		
	}
	public static void main(String[] args)
	{
		//����һ��DocumentBuilderFactory
		DocumentBuilderFactory dbf=
			DocumentBuilderFactory.newInstance();
		//����һ��DocumentBuilder
		DocumentBuilder db=null;
		try
		{
			db=dbf.newDocumentBuilder();
		}
		catch(ParserConfigurationException pce)
		{
			System.out.println(pce.toString());
		}
		String fileName=args[0];
		//�����ĵ�
		Document doc=null;
		try
		{
			//�����ĵ�
			doc=db.parse(new File(fileName));
		}
		catch(IOException ioe)
		{
			System.out.println(ioe.toString());
		}
		catch(SAXException se)
		{
			System.out.println(se.toString());
		}
		try
		{
			//��ʾDOM��
			OutputStreamWriter outWriter=
				new OutputStreamWriter(System.out,outputEncoding);
			new DOMDemo(new PrintWriter(outWriter,true)).echo(doc);
		}
		catch(UnsupportedEncodingException uee)
		{
			System.out.println(uee.toString());
		}
	}
}

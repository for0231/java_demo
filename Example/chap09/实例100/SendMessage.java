package javawebservice;
import java.io.*;
import java.net.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.apache.soap.*;
import org.apache.soap.messaging.*;
import org.apache.soap.transport.*;
import org.apache.soap.util.xml.*;
/**
 * This class sends a message by taking in an XML file that has a full
 * SOAP envelope in it and dumping it to the given message router URL.
 * Any content from the response is dumped to stdout.
 *
 * @author Sanjiva Weerawarana <sanjiva@watson.ibm.com>
 */
public class SendMessage
{
	public static void main(String[] args) throws Exception
	{
		if (args.length != 2)
		{
			System.err.println("Usage: java "
					+ SendMessage.class.getName()
					+ " SOAP-router-URL envelope-file");
			System.exit(1);
		}
		// 得到发送给服务器端的信息文件
		FileReader fr = new FileReader(args[1]);
		DocumentBuilder xdb = XMLParserUtils.getXMLDocBuilder();
		Document doc = xdb.parse(new InputSource(fr));
		if (doc == null)
		{
			throw new SOAPException(Constants.FAULT_CODE_CLIENT,
				"parsing error");
		}
		Envelope msgEnv=envelope.unmarshall(doc.getDocumentElement());
		// 发送信息
		Message msg = new Message();
		msg.send(new URL
			(args[0]), "urn:this-is-the-action-uri", msgEnv);
		// 得到服务器返回信息并显示
		System.out.println("RESPONSE:");
		System.out.println("--------");
		SOAPTransport st = msg.getSOAPTransport();
		BufferedReader br = st.receive();
		String line;
		while ((line = br.readLine()) != null)
		{
			System.out.println(line);
		}
	}
}

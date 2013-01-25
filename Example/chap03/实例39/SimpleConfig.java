package javaio;
import java.io.*;
import java.util.*;
public class SimpleConfig
{
	public static void main(String[] args) 
	{
		try
		{
			String fileName=
			"D:/Eclipse/eclipse/workspace/config/javaio/Example.conf";
			FileInputStream in = new FileInputStream(new File(fileName));
			//���ļ�û�н���ʱÿ�ζ�ȡһ���ֽ���ʾ
			String name="";
			String value="";
			String type="name";
			//������Hash��
			Hashtable result=new Hashtable();
			while (in.available()>0)
			{
				char c=(char)in.read();
				switch(c)
				{
					case '=':	
						type="value"; 
						break;
					case '\n':
						type="name"; 
						result.put(name,value);
						name="";
						value="";
						break;	
					default:
						if(type.equals("name"))
							name+=c;
						else
							value+=c;						
				}
			}
			//��ӡhash��
			System.out.println(result);
			in.close();
		}
		catch (IOException e)
		{
			System.out.println(e.toString());
		}
	}
}

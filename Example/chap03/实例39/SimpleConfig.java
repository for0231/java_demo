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
			//当文件没有结束时每次读取一个字节显示
			String name="";
			String value="";
			String type="name";
			//定义结果Hash表
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
			//打印hash表
			System.out.println(result);
			in.close();
		}
		catch (IOException e)
		{
			System.out.println(e.toString());
		}
	}
}

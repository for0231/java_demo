//Chapter 03, sample 35
//ReadFileExample.java
//Read a file.
package javaio;
import java.io.*;
public class ReadFileExample
{
	public static void main(String[] args) 
	{
		try
		{
			ReadFileExample example=new ReadFileExample();
			//得到当前源程序的文件名
			String fileName=example.getClass().
				getResource("ReadFileExample.java").toString();
			FileInputStream in = new FileInputStream(new File(fileName));
			//当文件没有结束时每次读取一个字节显示
			while (in.available()>0)
			{
				System.out.print((char)in.read());
			}
			in.close();
		}
		catch (IOException e)
		{
			System.out.println(e.toString());
		}
	}
}

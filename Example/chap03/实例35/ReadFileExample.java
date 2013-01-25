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
			//�õ���ǰԴ������ļ���
			String fileName=example.getClass().
				getResource("ReadFileExample.java").toString();
			FileInputStream in = new FileInputStream(new File(fileName));
			//���ļ�û�н���ʱÿ�ζ�ȡһ���ֽ���ʾ
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

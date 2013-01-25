package javaio;
import java.io.*;
public class WriteFileExample
{
	public static void main(String[] args) 
	{
		try
		{
			File aFile=new File("WriteExample.txt");	//指定文件名
			//建立输出流
		FileOutputStream out= new FileOutputStream(aFile);					byte[] b=new byte[1024];
			String str="This is a test file";
			b=str.getBytes();	//进行String到byte[]的转化
			out.write(b);		//写入文本内容
		}
		catch (IOException e)
		{
			System.out.println(e.toString());
		}
	}
}

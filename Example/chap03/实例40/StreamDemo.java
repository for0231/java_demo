package javaio;
import java.io.*;
public class StreamDemo
{
	public static void main(String[] args)
	{
		try
		{
			//建立输出流
			DataOutputStream out=
				new DataOutputStream(
					new BufferedOutputStream(
						new FileOutputStream(
							"Example.txt")));
			//写入一个double类型数据
			out.writeDouble(3.1425926);
			//写入一行文本
			out.writeBytes("This was pi");
			out.close();
			//建立输入流
			DataInputStream in=
				new DataInputStream(
					new BufferedInputStream(
						new FileInputStream(
							"Example.txt")));
			//建立输入Reader
			BufferedReader reader=
				new BufferedReader(
					new InputStreamReader(in));
			//读入一个double类型数据
			System.out.println(in.readDouble());
			//读入一行文本
			System.out.println(reader.readLine());
		}
		catch(IOException e)
		{
			System.out.println(e.toString());
		}
	}
}

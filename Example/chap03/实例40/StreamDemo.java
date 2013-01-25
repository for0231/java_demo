package javaio;
import java.io.*;
public class StreamDemo
{
	public static void main(String[] args)
	{
		try
		{
			//���������
			DataOutputStream out=
				new DataOutputStream(
					new BufferedOutputStream(
						new FileOutputStream(
							"Example.txt")));
			//д��һ��double��������
			out.writeDouble(3.1425926);
			//д��һ���ı�
			out.writeBytes("This was pi");
			out.close();
			//����������
			DataInputStream in=
				new DataInputStream(
					new BufferedInputStream(
						new FileInputStream(
							"Example.txt")));
			//��������Reader
			BufferedReader reader=
				new BufferedReader(
					new InputStreamReader(in));
			//����һ��double��������
			System.out.println(in.readDouble());
			//����һ���ı�
			System.out.println(reader.readLine());
		}
		catch(IOException e)
		{
			System.out.println(e.toString());
		}
	}
}

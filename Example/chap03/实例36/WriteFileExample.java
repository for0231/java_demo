package javaio;
import java.io.*;
public class WriteFileExample
{
	public static void main(String[] args) 
	{
		try
		{
			File aFile=new File("WriteExample.txt");	//ָ���ļ���
			//���������
		FileOutputStream out= new FileOutputStream(aFile);					byte[] b=new byte[1024];
			String str="This is a test file";
			b=str.getBytes();	//����String��byte[]��ת��
			out.write(b);		//д���ı�����
		}
		catch (IOException e)
		{
			System.out.println(e.toString());
		}
	}
}

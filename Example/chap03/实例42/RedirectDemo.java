package javaio;
import java.io.*;
public class RedirectDemo
{
	public static void main(String[] args)
	{
		try
		{
			BufferedInputStream in=
				new BufferedInputStream(
					new FileInputStream(
						"RedirectDemo.java"));
			PrintStream out=
				new PrintStream(
					new BufferedOutputStream(
						new FileOutputStream(
							"out.txt")));
			//�ض����׼����
			System.setIn(in);
			//�ض����׼���
			System.setOut(out);
			//�ض����׼����IO
			System.setErr(out);
			
			BufferedReader reader=
				new BufferedReader(
					new InputStreamReader(System.in));
			String str;
			while((str=reader.readLine())!=null)
				System.out.println(str);
			//�ر�������ͷ���Դ
			out.close();
		}
		catch(IOException e)
		{
			System.out.println(e.toString());
		}
	}
}

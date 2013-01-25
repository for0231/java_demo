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
			//重定向标准输入
			System.setIn(in);
			//重定向标准输出
			System.setOut(out);
			//重定向标准错误IO
			System.setErr(out);
			
			BufferedReader reader=
				new BufferedReader(
					new InputStreamReader(System.in));
			String str;
			while((str=reader.readLine())!=null)
				System.out.println(str);
			//关闭输出，释放资源
			out.close();
		}
		catch(IOException e)
		{
			System.out.println(e.toString());
		}
	}
}

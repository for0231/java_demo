package javaio;
import java.io.*;
public class FilterDemo
{
	public static void main(String[] args)
	{
		try
		{
			//设置当前路径
			File path=new File(".");
			String[] list;
			if(args.length==0)
				list=path.list();
			else
				//若有输入参数则将输入参数作为过滤因子
				list=path.list(new DirFilter(args[0]));
			for(int i=0;i<list.length;i++)
			{
				System.out.println(list[i]);
			}
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	//建立静态内部类
	static class DirFilter implements FilenameFilter
	{
		String fileName;
		DirFilter(String fileName)
		{
			this.fileName=fileName;
		}
		//继承accept方法，如果不符合过滤要求则过滤掉
		public boolean accept(File dir,String name)
		{
			String file=new File(name).getName();
			return file.indexOf(fileName) != -1;
		}
	}
}

package javaio;
import java.io.*;
public class FilterDemo
{
	public static void main(String[] args)
	{
		try
		{
			//���õ�ǰ·��
			File path=new File(".");
			String[] list;
			if(args.length==0)
				list=path.list();
			else
				//����������������������Ϊ��������
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
	//������̬�ڲ���
	static class DirFilter implements FilenameFilter
	{
		String fileName;
		DirFilter(String fileName)
		{
			this.fileName=fileName;
		}
		//�̳�accept��������������Ϲ���Ҫ������˵�
		public boolean accept(File dir,String name)
		{
			String file=new File(name).getName();
			return file.indexOf(fileName) != -1;
		}
	}
}

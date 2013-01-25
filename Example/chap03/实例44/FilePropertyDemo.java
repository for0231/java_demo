package javaio;
import java.io.*;
public class FilePropertyDemo
{
	public static void main(String[] args)
	{
		File aFile=new File(
			"D:/Eclipse/eclipse/workspace/Property/javaio/"+
				"FilepropertyDemo.java");
		System.out.println(
			//文件绝对路径
			"Absolute path: "+aFile.getAbsolutePath()+
			//是否可读
			"\n Can read: "+aFile.canRead()+
			//是否可写
			"\n Can write: "+aFile.canWrite()+
			//文件名
			"\n Name: "+aFile.getName()+
			//文件所在上级目录
			"\n Parent: "+aFile.getParent()+
			//文件所在目录（包括文件名）
			"\n Path: "+aFile.getPath()+
			//文件长度
			"\n Length: "+aFile.length()+
			//文件上次修改时间
			//从00:00:00 GMT, January 1, 1970开始的长整型数
			"\n Last Modified: "+aFile.lastModified()+
			//文件是否被隐藏
			"\n Hidden: "+aFile.isHidden());
		//文件是否为"文件"类型
		if(aFile.isFile())
		{
			System.out.println("It's a file");
		}
		//文件是否为"目录"类型
		else if(aFile.isDirectory())
		{
			System.out.println("It's a directory");
		}
	}
}

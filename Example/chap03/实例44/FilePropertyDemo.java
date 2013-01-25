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
			//�ļ�����·��
			"Absolute path: "+aFile.getAbsolutePath()+
			//�Ƿ�ɶ�
			"\n Can read: "+aFile.canRead()+
			//�Ƿ��д
			"\n Can write: "+aFile.canWrite()+
			//�ļ���
			"\n Name: "+aFile.getName()+
			//�ļ������ϼ�Ŀ¼
			"\n Parent: "+aFile.getParent()+
			//�ļ�����Ŀ¼�������ļ�����
			"\n Path: "+aFile.getPath()+
			//�ļ�����
			"\n Length: "+aFile.length()+
			//�ļ��ϴ��޸�ʱ��
			//��00:00:00 GMT, January 1, 1970��ʼ�ĳ�������
			"\n Last Modified: "+aFile.lastModified()+
			//�ļ��Ƿ�����
			"\n Hidden: "+aFile.isHidden());
		//�ļ��Ƿ�Ϊ"�ļ�"����
		if(aFile.isFile())
		{
			System.out.println("It's a file");
		}
		//�ļ��Ƿ�Ϊ"Ŀ¼"����
		else if(aFile.isDirectory())
		{
			System.out.println("It's a directory");
		}
	}
}

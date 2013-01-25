package javaio;
import java.io.*;
import java.util.zip.*;
public class ZipCompress
{
	public static void main(String[] args) 
	{
		try
		{
			//��������ļ���
			FileOutputStream fileOut=
				new FileOutputStream("Example.zip");
			//����������֤��
			CheckedOutputStream checkedOut=
				new CheckedOutputStream(fileOut,new CRC32());
			//����Zip��
			ZipOutputStream zipOut=
				new ZipOutputStream(
					new BufferedOutputStream(checkedOut));
			//����ע������
			zipOut.setComment("This is a java zipping test file");
			//�ļ���
			String fileName=
				"D:/Eclipse/eclipse/workspace/Zip/javaio/"+
					"ZipCompress.java";
			//��ȡ��ѹ���ļ���
			BufferedReader in=
				new BufferedReader(new FileReader(fileName));
			//����ѹ��ʵ��
			zipOut.putNextEntry(new ZipEntry(fileName));
			int ch;
			//����ѹ���ļ�û�н���ʱ������д
			while ((ch=in.read())!=-1)
			{
				zipOut.write(ch);
			}
			//�ر��ļ������ͷ���Դ
			in.close();
			zipOut.close();
		}
		catch (IOException e)
		{
			System.out.println(e.toString());
		}
	}
}

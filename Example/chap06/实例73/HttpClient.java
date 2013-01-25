import java.io.*;
import java.net.*;
/**
 * �������������ӵ�һ����������������ܹ����������������ָ����URL����
 * ������ֱ��ʹ��HTTPЭ��
 */
public class HttpClient {
	public static void main(String[] args) {
		try {
			// ���Բ���
			if ((args.length != 1) && (args.length != 2))
				throw new IllegalArgumentException("Wrong number of args");
			// ����һ������������ص�URL�����ݽ�����д�������
			OutputStream to_file;
			if (args.length == 2) to_file = new FileOutputStream(args[1]);
			else to_file = System.out;
			// ����ʹ��URL�������û�ָ����URL�����ɼ�������
			URL url = new URL(args[0]);
			String protocol = url.getProtocol();
			if (!protocol.equals("http")) // �����Ƿ���������֧�ֵ�Э��
				throw new IllegalArgumentException("Must use 'http:' protocol");
			String host = url.getHost();
			int port = url.getPort();
			if (port == -1) port = 80; //���û��ָ���˿ڣ�������Ĭ�ϵö˿�
			String filename = url.getFile();
			// ��һ�����ӵ�ָ�������Ͷ˿ڵ�����socket����
			Socket socket = new Socket(host, port);
			// ͨ��socket���������������
			InputStream from_server = socket.getInputStream();
			PrintWriter to_server = new PrintWriter(socket.getOutputStream());
			
			// ����HTTP GET����������������ָ��Ҫ���ص��ļ�
			// ����ʹ����һ���ϵİ汾�ǳ��򵥵�HTTPЭ��
			to_server.print("GET " + filename + "\n\n");
			to_server.flush(); // ��������
			// ���ڶ�ȡ����������Ӧ���ѽ��յ�������д���ļ�
			byte[] buffer = new byte[4096];
			int bytes_read;
			while((bytes_read = from_server.read(buffer)) != -1)
				to_file.write(buffer, 0, bytes_read);
			// ���������ر�����ʱ������Ҳ�ر����ǵ�stuff
			socket.close();
			to_file.close();
		}
		catch (Exception e) { // ��������Ĵ���
			System.err.println(e);
			System.err.println("Usage: java HttpClient <URL> [<filename>]");
		}
	}
}
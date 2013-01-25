import java.io.*;
import java.net.*;
/**
 * ���������һ���ǳ��򵥵�Web Server���������ܵ�һ��HTTP�����ʱ��������
 * ����������������ΪӦ�𡣵����뿴һ��Web�ͻ����Ƿ���������ʱ���⽫�Ǻ���
 * ��Ȥ�ġ�
 */
public class HttpMirror {
	public static void main(String args[]) {
		try {
			// ��ȡҪ�����Ķ˿�
			int port = Integer.parseInt(args[0]);
			// ����һ��ServerSocket�������˿�
			ServerSocket ss = new ServerSocket(port);
			// ���ڽ���һ������ѭ�����ȴ����ߴ�������
			for(;;) {
				// �ȴ�һ���ͻ������ӡ������������������
				// ��������ʱ�����socket�������ӵ��ͻ���
				Socket client = ss.accept();
				// ��ȡ������������ͬ�ͻ��˽���
				BufferedReader in = new BufferedReader(
				new InputStreamReader(client.getInputStream()));
				PrintWriter out = new PrintWriter(client.getOutputStream());
				// ��ʼ�������ǵ�Ӧ��ʹ��HTTP1.0Э��
				out.print("HTTP/1.0 200 \n"); // �汾��״̬���
				out.print("Content-Type: text/plain\n"); // ��������
				out.print("\n"); // ͷ�ĵײ�
				// ���ڣ��ӿͻ��˶�ȡHTTP�����ٰ���������ͻؿͻ�����Ϊ
				// ����Ӧ���һ���֡��ͻ��˲�û��ȡ�����ӣ�����������Զ����
				// �õ�EOF���ڱ�ͷ���������һ�����У����Ե����ǿ������У�
				// ��ֹͣ��ȡ������ζ�����ǲ��ܷ�ӳPOST��������ݡ�ע�⣬���
				// readline()�ܹ���unix, windows �� Mac�ն�������������
				String line;
				while((line = in.readLine()) != null) {
					if (line.length() == 0) break;
					out.print(line + "\n");
				}
				// �ر�socket���ж���ͻ��˵����ӣ��ر�����������
				out.close(); 
				in.close(); 
				client.close(); 
			} 
		}
		// ����д���������ô��ʾ������Ϣ
		catch (Exception e) {
			System.err.println(e);
			System.err.println("Usage: java HttpMirror <port>");
		}
	}
}
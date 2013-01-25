import java.io.*;
import java.net.*;
/**
 * �����ʵ����һ���򵥵ĵ��̵߳Ĵ��������
 */
public class SimpleProxyServer {
	/** �����������������Ѳ������ݸ�runServer */
	public static void main(String[] args) throws IOException {
		try {
			// �������ĸ���
			if (args.length != 3)
				throw new IllegalArgumentException("Wrong number of args.");
			// ��������в���: ����Ҫ����������Ͷ˿�
			String host = args[0];
			int remoteport = Integer.parseInt(args[1]);
			int localport = Integer.parseInt(args[2]);
			// ��ʾ��ʼ��Ϣ
			System.out.println("Starting proxy for " + host + ":" +
				remoteport + " on port " + localport);
			// ��ʼ�������������
			runServer(host, remoteport, localport); // ��������
		}
		catch (Exception e) {
			System.err.println(e);
			System.err.println("Usage: java SimpleProxyServer " +
				"<host> <remoteport> <localport>");
		}
	}

	/**
	 * �����������һ�����̵߳Ĵ��������������ָ���ı��ض˿���
	 * ����һ�������ϵ�Զ�̶˿ڡ����ò����ء�
	 */
	public static void runServer(String host, int remoteport, int localport)
		throws IOException {
		// ����һ��ServerSocket����������
		ServerSocket ss = new ServerSocket(localport);
		// Ϊ�ͻ��˵��������ͷ��������ͻ��˴���������
		// ����ָ��������Ϊfinal��ʹ���ܹ�����������������ܹ���ʹ��
		// ע����ÿ�������Ͻ������ϵļٶ�
		final byte[] request = new byte[1024];
		byte[] reply = new byte[4096];
		// ����һ���ò����صķ��������ʽ���һ������ѭ��
		while(true) {
			// ������ͻ��˺�����������ӵ�socket�ı���
			Socket client = null, server = null;
			try {
				// �ȴ�һ�����ض˿ڵ�����
				client = ss.accept();
				// ��ȡ�ͻ����������Ƕ����finalʹ�����������������
				// �����ܹ���ʹ��
				final InputStream from_client = client.getInputStream();
				final OutputStream to_client = client.getOutputStream();
				// ����һ������ʵ������������
				// ������ǲ������Ϸ�����������һ��������ͻ���
				// ȡ�����ӣ��ȴ��´�����
				try { server = new Socket(host, remoteport); }
				catch (IOException e) {
					PrintWriter out = new PrintWriter(to_client);
					out.print("Proxy server cannot connect to " + host + ":"+
						remoteport + ":\n" + e + "\n");
					out.flush();
					client.close();
					continue;
				}
				// ��÷���������
				final InputStream from_server = server.getInputStream();
				final OutputStream to_server = server.getOutputStream();
				// ����һ���̶߳�ȡ�ͻ��˵����󣬰����󴫵ݸ������������ǲ��ò�
				// ʹ��һ���������̣߳���Ϊ�������Ӧ���첽��
				Thread t = new Thread() {
					public void run() {
						int bytes_read;
						try {
							while((bytes_read=from_client.read(request))!=-1) {
								to_server.write(request, 0, bytes_read);
								to_server.flush();
							}
						}
						catch (IOException e) {}
						// �ͻ��˹ر���ͬ���ǵ����ӣ���ôҲ�͹ر����������������
						// ���ӡ��⽫������߳��з�������ͻ���֮���ѭ���˳�
						try {to_server.close();} catch (IOException e) {}
					}
				};
				// ��ʼ�ͻ��˵������������̵߳�����
				t.start();
				// ͬʱ�������߳��У���ȡ����������Ӧ���������Ǵ��ݸ��ͻ��ˡ��⽫
				// �������ᵽ�Ŀͻ��˵��������������̲߳���ִ��
				int bytes_read;
				try {
					while((bytes_read = from_server.read(reply)) != -1) {
						to_client.write(reply, 0, bytes_read);
						to_client.flush();
					}
				}
				catch(IOException e) {}
				// �������ر�ͬ���ǵ����ӣ���ô���ǹر���ͻ��˵�����
				// �⽫ʹ����һ���߳��˳�
				to_client.close();
			}
			catch (IOException e) { System.err.println(e); }
			finally { // ���ܷ���ʲô�ر��߳�
				try {
					if (server != null) server.close();
					if (client != null) client.close();
				}
				catch(IOException e) {}
			}
		}
	}
}

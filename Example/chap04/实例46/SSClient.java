import java.io.*;
import java.net.*;
class SSClient
{
  public static void main (String [] args){
    String host = "localhost";
    // �������в������localhost��
    if (args.length == 1)
      host = args [0];
    BufferedReader br = null;
    PrintWriter pw = null;
    Socket s = null;
    try{
          // ����һ���������ӵ�server���׽���
          // �趨�˿�10000
          s = new Socket (host, 10000);
          // ����һ��������reader��������reader�Ѵ��׽����ж������ֽ�ת�����ַ���
          // ���ת���ǻ���ƽ̨Ĭ���ַ���֮�ϵ�
          InputStreamReader isr;
          isr = new InputStreamReader (s.getInputStream ());
          // ����һ�� buffered reader�����ṩ�˶�ȡ�����ı��ķ��㷽��
          br = new BufferedReader (isr);
          // ����һ�� print writer �����ѷ��͸��׽��ֵ��ַ�ת�����ֽ�
          pw = new PrintWriter (s.getOutputStream (), true);
          // ����DATE ���server.
          pw.println ("DATE");
          // ��ȡ����ӡ��ǰ�����ں�ʱ��
          System.out.println (br.readLine ());
          // ����PAUSE ���server.
          pw.println ("PAUSE");
          // ����DOW���server.
          pw.println ("DOW");
          // ��ȡ����ӡ��ǰ�����ڼ�.
          System.out.println (br.readLine ());
          //����DOW���server.
          pw.println ("DOM");
          // ��ȡ����ӡ��ǰ�·�
          System.out.println (br.readLine ());
          // ����DOY���server.
          pw.println ("DOY");
          // ��ȡ����ӡ��ǰ���
          System.out.println (br.readLine ());
        }catch (IOException e){
          System.out.println (e.toString ());
        }
        finally{
          try{
            if (br != null)
              br.close ();
            if (pw != null)
              pw.close ();
            if (s != null)
              s.close ();
          }catch (IOException e){
          }
        }
      }
}
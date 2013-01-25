import java.net.*;
import java.io.*;
public class ReadURL {
public static void main(String argv[]) throws Exception { // 主函数
if(argv.length != 1) {
System.out.println("Usage: java ReadURL <url>");
System.exit(0);
}
URL url = new URL(argv[0]); // 从命令行参数中获得地址
BufferedReader in= new BufferedReader(new InputStreamReader(url.openStream()));
String line;
StringBuffer sb = new StringBuffer();
while ((line = in.readLine()) != null) {
sb.append(line);
}
in.close();
System.out.println(sb.toString());
}
}

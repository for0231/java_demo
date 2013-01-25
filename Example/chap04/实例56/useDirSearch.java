import com.macfaq.net.*; 
import java.net.*; 
import java.io.*; 
public class useDirSearch 
{
	public static void main(String[] args) {
		String target = "";
		for (int i = 0; i < args.length; i++) {
			target += args[i] + " ";
		}
		target = target.trim( ); 
		QueryString query = new QueryString("search", target); 
		try {
			// 新建URL对象
			URL u = new URL("http://search.dmoz.org/cgi-bin/search?" +query); 
			InputStream in = new BufferedInputStream(u.openStream( )); 
			InputStreamReader theHTML = new InputStreamReader(in); 
			int c; 
			while ((c = theHTML.read( )) != -1) 	{
				System.out.print((char) c); 
			}
		}
		// 处理异常
		catch (MalformedURLException e) {
			System.err.println(e); 
		}
		catch (IOException e) 	{
			System.err.println(e); 
		}
	}
}

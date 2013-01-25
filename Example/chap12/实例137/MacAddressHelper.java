import java.io.*; 
import java.net.URL; 
public class MacAddressHelper {
	public MacAddressHelper() { 
	}
	private static String getMyMac() {
		String s = "";
		try {
			String s1 = "ipconfig /all";
			// 相当于在命令行下直接使用ipconfig /all
			Process process = Runtime.getRuntime().exec(s1); 
			BufferedReader bufferedreader = new BufferedReader(new 
InputStreamReader(process.getInputStream()));
			String line = bufferedreader.readLine();
			// 筛选出mac地址
			for(;line != null;) {
				String nextLine = bufferedreader.readLine();
				if(line.indexOf("Physical Address") > 0){ 
					int i = line.indexOf("Physical Address") + 36; 
					s = line.substring(i); 
					break; 
				}
				line = nextLine; 
			}
			bufferedreader.close();
			process.waitFor();
		} catch(Exception exception) {
			s = "";
		}
		return s.trim();
	}
	public static void main(String[] args){ 
		// 显示Mac地址在命令行界面
		System.out.println(MacAddressHelper.getMyMac());
	}
}

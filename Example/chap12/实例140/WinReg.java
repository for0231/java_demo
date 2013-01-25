import java.util.*; 
// WinReg对象可以用来获取和设置 windows注册表的键值. 
public class WinReg{
/**
构建一个注册表键对象. 
@变量theRoot是 HKEY_CLASSES_ROOT, HKEY_CURRENT_USER, 
HKEY_LOCAL_MACHINE, HKEY_USERS, HKEY_CURRENT_CONFIG, 
HKEY_DYN_DATA中的一个
@变量 thePath 是注册表键的路径
*/
	public WinReg(int theRoot, String thePath) { 
		root = theRoot; 
		path = thePath; 
	}
	/**
	把有对象给定的路径下所有注册表项的名字列举出来
	@返回一个枚举型的包含所有项名的量
	*/
	public Enumeration names() {  
		return new WinRegNameEnumeration(root, path); 
	}
	// 获得注册表键值 
	public native Object getValue(String name); 
	// 设置注册表键值
	public native void setValue(String name, Object value); 
	public static final int HKEY_CLASSES_ROOT = 0x80000000; 
	public static final int HKEY_CURRENT_USER = 0x80000001; 
	public static final int HKEY_LOCAL_MACHINE = 0x80000002; 
	public static final int HKEY_USERS = 0x80000003; 
	public static final int HKEY_CURRENT_CONFIG = 0x80000005; 
	public static final int HKEY_DYN_DATA = 0x80000006; 
	private int root; 
	private String path; 
	static { 
		System.loadLibrary("WinReg");   // 载入本地方法
	}
}

class WinRegNameEnumeration implements Enumeration
{  
	WinRegNameEnumeration(int theRoot, String thePath) {  
		root = theRoot; 
		path = thePath; 
	}
	public native Object nextElement();
	public native boolean hasMoreElements();
	private int root; 
	private String path; 
	private int index = -1; 
	private int hkey = 0; 
	private int maxsize; 
	private int count; 
}

class WinRegException extends RuntimeException{  
	public WinRegException() {}
	public WinRegException(String why) {  
		super(why); 
	}
}

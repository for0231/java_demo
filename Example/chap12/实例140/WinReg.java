import java.util.*; 
// WinReg�������������ȡ������ windowsע���ļ�ֵ. 
public class WinReg{
/**
����һ��ע��������. 
@����theRoot�� HKEY_CLASSES_ROOT, HKEY_CURRENT_USER, 
HKEY_LOCAL_MACHINE, HKEY_USERS, HKEY_CURRENT_CONFIG, 
HKEY_DYN_DATA�е�һ��
@���� thePath ��ע������·��
*/
	public WinReg(int theRoot, String thePath) { 
		root = theRoot; 
		path = thePath; 
	}
	/**
	���ж��������·��������ע�����������оٳ���
	@����һ��ö���͵İ���������������
	*/
	public Enumeration names() {  
		return new WinRegNameEnumeration(root, path); 
	}
	// ���ע����ֵ 
	public native Object getValue(String name); 
	// ����ע����ֵ
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
		System.loadLibrary("WinReg");   // ���뱾�ط���
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

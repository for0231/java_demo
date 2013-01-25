import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;
class Lookup {
    public static void main(String[] args) {
	if (args.length != 1) {
	    System.err.println("格式: java Lookup <filename>");
	    System.exit(-1);
	}
	String name = args[0];
	// 指定使用sun的文件系统
	Hashtable env = new Hashtable(11);
	env.put(Context.INITIAL_CONTEXT_FACTORY, 
	    "com.sun.jndi.fscontext.RefFSContextFactory");
	try {
	    // 初始化Context,它是连接JNDI树的起始点
	    Context ctx = new InitialContext(env);
	    // 查找你要的对象
	    Object obj = ctx.lookup(name);
	    // 打印找到的对象
	    System.out.println(name + "邦定在: " + obj);
	    // 关闭Context
	    ctx.close();
	} catch (NamingException e) {
	    System.err.println("Problem looking up " + name + ": " + e);
	}
    }
}

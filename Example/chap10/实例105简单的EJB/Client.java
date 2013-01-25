import javax.naming.*;
import javax.rmi.PortableRemoteObject;
import java.util.Properties;
import com.javapro.ejb.StringProcessor;
import com.javapro.ejb.StringProcessorHome;
//通过Client触发调用Bean方法
public class Client 
{
  public static void main(String[] args)
 {
//为JNDI初始化取得系统属性
    Properties properties = new Properties();
    try {
       //取得初始化属性jndiContext，它是连接JNDI树的起始点
InitialContext jndiContext = new InitialContext(properties);
       //取得Home对象的引用
         Object ref  = jndiContext.lookup("HelloWorldHome");
         HelloWorldHome home = (HelloWorldHome)
PortableRemoteObject.narrow (ref, HelloWorldHome.class);
      //用EJB生成库Home生成EJB对象
         HelloWorld hello= home.create();
      //调用EJB对象方法printHelloWorld（）,EJB对象把调用委派给Bean
         System.out.println (hello.printHelloWorld());
}
    catch(Exception e) {
      System.out.println(e.toString());
    }
  }
}

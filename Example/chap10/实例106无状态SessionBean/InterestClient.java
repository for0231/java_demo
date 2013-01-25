package Interest;
import java.util.Properties;
import javax.rmi.PortableRemoteObject; 
import javax.naming.*;
class InterestClient
{
   public static void main(String[] args)
{ 
       try
       {   //获取系统属性，初始化JNDI
 Properties props = new Properties();
// 取得Home对象的引用
          InitialContext jndiContext = new InitialContext(props);
          Object ref  = jndiContext.lookup("interest/Interest");                      InterestHome home = (InterestHome) 
          PortableRemoteObject.narrow (ref, InterestHome.class);
         // 创建EJB对象
          Interest interest = home.create();
	// 计算利息
        System.out.println("利息是：");
         System.out.println(interest.calculateCompoundInterest (1000, 0.10, 2));
       }
       catch(Exception e)
       {
          System.out.println(e.toString());
       }
     }

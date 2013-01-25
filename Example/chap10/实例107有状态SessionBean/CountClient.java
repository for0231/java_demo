package Count;
import javax.naming.*;
import java.util.Properties;
import javax.rmi.PortableRemoteObject;
// 这个代码触发调用一个状态Bean中的方法
public class CountClient{
 public static void main(String[] args)
{
  try{
 //获取系统属性，初始化JNDI
  Properties props = System.getProperties();
  // 取得Home对象的引用
 Context ctx = new InitialContext(props);
 CountHome Home =(CountHome);
 Javax.rmi.PortableRemoteObject.narrow
(ctx.lookup("CountHome"),CountHome.class);
 //具有3个Count EJB对象的数组
Count count[] = new Count[3];
 int countVal = 0;
 for (int i=0; i<3; i++)
  { //创建EJB对象，并将当前的计数器初始化
count[i]=Home.create(countVal);
countVal = count[i].count();
    System.out.pringln("countVal");
}
     for (int i=0; i<3; i++)
      { // 调用每一个EJB对象的count()方法，保证Bean正常被激活和钝化
         countVal = count[i].count();
         System.out.println(countVal);
}
for (int i=0; i<3; i++)
{//EJB对象是用完毕，从内存中清除
  count[i].remove();
}
}
  catch(Exception e){
   e.printStackTrace();
}
}
}

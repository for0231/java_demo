package Count;
import javax.naming.*;
import java.util.Properties;
import javax.rmi.PortableRemoteObject;
// ������봥������һ��״̬Bean�еķ���
public class CountClient{
 public static void main(String[] args)
{
  try{
 //��ȡϵͳ���ԣ���ʼ��JNDI
  Properties props = System.getProperties();
  // ȡ��Home���������
 Context ctx = new InitialContext(props);
 CountHome Home =(CountHome);
 Javax.rmi.PortableRemoteObject.narrow
(ctx.lookup("CountHome"),CountHome.class);
 //����3��Count EJB���������
Count count[] = new Count[3];
 int countVal = 0;
 for (int i=0; i<3; i++)
  { //����EJB���󣬲�����ǰ�ļ�������ʼ��
count[i]=Home.create(countVal);
countVal = count[i].count();
    System.out.pringln("countVal");
}
     for (int i=0; i<3; i++)
      { // ����ÿһ��EJB�����count()��������֤Bean����������Ͷۻ�
         countVal = count[i].count();
         System.out.println(countVal);
}
for (int i=0; i<3; i++)
{//EJB����������ϣ����ڴ������
  count[i].remove();
}
}
  catch(Exception e){
   e.printStackTrace();
}
}
}

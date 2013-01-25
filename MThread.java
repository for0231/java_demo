public class MThread
{
  public static void main(String[] args)
  {
    System.out.println("Hello World!");
    thread2 t1 = new thread2("线程实例1");
    t1.start(); //调用
    thread2 t2 = new thread2("线程实例2");
    t2.start();
    thread2 t3 = new thread2("线程实例3");
    t3.start();
  }
}

// 自定义线程类
class thread2 extends Thread
{
  Thread thread; // 定义线程实例
  String str;
  // 构造函数
  public thread2(String str)
  {
    this.str = str;
  }

  // 启动线程
  public void start()
  {
    thread = new Thread(this);
    thread.start();
  }

  public void run()
  {
    int i = 0;
    while(thread != null)
    {
      try {
        // 计数到5时睡眠10秒
        if (i == 5) 
          sleep(10000);
      }
      catch (Exception e)
      {
        System.out.println(e.getMessage());
      }
      System.out.println(str);
      i++;
    }
  }
}

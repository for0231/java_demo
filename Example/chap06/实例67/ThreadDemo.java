
/** 主方法main()是java解释器调用的初始方法，它定义和同时启动了第二个
 *  和第三个进程。类ThreadDemo继承了类Thread，重载了类Thread的方法run()
 */
public class ThreadDemo extends Thread {
	/**　这里重载了类Thread的方法run()，它定义了这个线程的执行代码
	 */
	public void run() {
		for (int i = 0; i < 5; ++i) 
			compute();
	}

	/** 除了解释器调用main()时创建的初始进程外，main()创建和启动了
	 *  两个进程
	 */
	public static void main(String[] args) {
		// 创建第一个线程：一个这个类的实例。上面定义的方法run()
		// 定义了这个线程的执行代码
		ThreadDemo thread1 = new ThreadDemo();
		
		// 通过传递一个Runnable类的实例给类Thread的构造函数来
		// 创建第二个线程。下面类Runnable的匿名实例的方法run()
		// 定义了这个线程的执行代码
		Thread thread2 = new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 5; ++i) compute();
			}
		});

		// 通过main()的参数对线程的优先级进行设定
		if (args.length >= 1)
			thread1.setPriority(Integer.parseInt(args[0]));
		if (args.length >= 2)
			thread2.setPriority(Integer.parseInt(args[1]));

		// 启动这两个线程
		thread1.start();
		thread2.start();
		
		// main()运行在解释器创建的初始线程中，现在，这个线程
		// 也做一些工作
		for (int i = 0; i < 5; ++i) 
			compute();
		
		// 运行到下面的代码，我们将等待着线程的运行结束。但是
		// 这些代码并不是必要的，所以我们将它们注释掉了。
		// try {
		//	thread1.join();
		//	thread2.join();
		// }
		// catch (InterruptedException e) {}

		// 仅仅当所有的线程都停止运行，主方法main()返回时，
		// java虚拟机才退出。
	}

	// 类ThreadLocal的实例用来记录一个数值，这个数值可以通过
	// 方法get()和set()来获得和设置。但是对不同的线程，它们
	// 记录一个不同的数值。这个实例跟踪每个线程调用方法compute()
	// 的次数
	static ThreadLocal numcalls = new ThreadLocal();

	/** 这是我们所有的线程都调用的模型函数 */
	static synchronized void compute() {
		// 计算出已经被当前线程调用的次数
		Integer n = (Integer)numcalls.get();
		
		if (n == null)
			n = new Integer(1);
		else 
			n = new Integer(n.intValue()+1);
		numcalls.set(n);
		
		// 显示线程的名字和调用的次数
		System.out.println(Thread.currentThread().getName()+": "+n);
		
		// 做一个长时间的计算，模拟一个有限次计算的线程
		for (int i = 0, j = 0; i < 1000000; ++i)
			j += i;
		
		// 同样的，我们可以通过使线程休眠一段任意的时间来模拟一个
		// 线程因为网络或者I/O操作的延迟
		try {
			Thread.sleep((int)(Math.random()*100+1));
		}
		catch(InterruptedException e) {}

		// 每一个线程都客气的给其它线程提供运行的机会，这是很
		// 重要的，可以保证一个线程不会使同等优先级的线程饿死
		Thread.yield();
	}
}
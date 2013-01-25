
/** ������main()��java���������õĳ�ʼ�������������ͬʱ�����˵ڶ���
 *  �͵��������̡���ThreadDemo�̳�����Thread����������Thread�ķ���run()
 */
public class ThreadDemo extends Thread {
	/**��������������Thread�ķ���run()��������������̵߳�ִ�д���
	 */
	public void run() {
		for (int i = 0; i < 5; ++i) 
			compute();
	}

	/** ���˽���������main()ʱ�����ĳ�ʼ�����⣬main()������������
	 *  ��������
	 */
	public static void main(String[] args) {
		// ������һ���̣߳�һ��������ʵ�������涨��ķ���run()
		// ����������̵߳�ִ�д���
		ThreadDemo thread1 = new ThreadDemo();
		
		// ͨ������һ��Runnable���ʵ������Thread�Ĺ��캯����
		// �����ڶ����̡߳�������Runnable������ʵ���ķ���run()
		// ����������̵߳�ִ�д���
		Thread thread2 = new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 5; ++i) compute();
			}
		});

		// ͨ��main()�Ĳ������̵߳����ȼ������趨
		if (args.length >= 1)
			thread1.setPriority(Integer.parseInt(args[0]));
		if (args.length >= 2)
			thread2.setPriority(Integer.parseInt(args[1]));

		// �����������߳�
		thread1.start();
		thread2.start();
		
		// main()�����ڽ����������ĳ�ʼ�߳��У����ڣ�����߳�
		// Ҳ��һЩ����
		for (int i = 0; i < 5; ++i) 
			compute();
		
		// ���е�����Ĵ��룬���ǽ��ȴ����̵߳����н���������
		// ��Щ���벢���Ǳ�Ҫ�ģ��������ǽ�����ע�͵��ˡ�
		// try {
		//	thread1.join();
		//	thread2.join();
		// }
		// catch (InterruptedException e) {}

		// ���������е��̶߳�ֹͣ���У�������main()����ʱ��
		// java��������˳���
	}

	// ��ThreadLocal��ʵ��������¼һ����ֵ�������ֵ����ͨ��
	// ����get()��set()����ú����á����ǶԲ�ͬ���̣߳�����
	// ��¼һ����ͬ����ֵ�����ʵ������ÿ���̵߳��÷���compute()
	// �Ĵ���
	static ThreadLocal numcalls = new ThreadLocal();

	/** �����������е��̶߳����õ�ģ�ͺ��� */
	static synchronized void compute() {
		// ������Ѿ�����ǰ�̵߳��õĴ���
		Integer n = (Integer)numcalls.get();
		
		if (n == null)
			n = new Integer(1);
		else 
			n = new Integer(n.intValue()+1);
		numcalls.set(n);
		
		// ��ʾ�̵߳����ֺ͵��õĴ���
		System.out.println(Thread.currentThread().getName()+": "+n);
		
		// ��һ����ʱ��ļ��㣬ģ��һ�����޴μ�����߳�
		for (int i = 0, j = 0; i < 1000000; ++i)
			j += i;
		
		// ͬ���ģ����ǿ���ͨ��ʹ�߳�����һ�������ʱ����ģ��һ��
		// �߳���Ϊ�������I/O�������ӳ�
		try {
			Thread.sleep((int)(Math.random()*100+1));
		}
		catch(InterruptedException e) {}

		// ÿһ���̶߳������ĸ������߳��ṩ���еĻ��ᣬ���Ǻ�
		// ��Ҫ�ģ����Ա�֤һ���̲߳���ʹͬ�����ȼ����̶߳���
		Thread.yield();
	}
}
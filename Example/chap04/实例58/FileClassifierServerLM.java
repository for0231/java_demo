import java.rmi.Naming; 
import java.net.InetAddress; 
import net.jini.discovery.LookupDiscovery; 
import net.jini.discovery.DiscoveryListener; 
import net.jini.discovery.DiscoveryEvent; 
import net.jini.core.lookup.ServiceRegistrar; 
import net.jini.core.lookup.ServiceItem; 
import net.jini.core.lookup.ServiceRegistration; 
import net.jini.core.lease.Lease; 
import java.rmi.RMISecurityManager; 
import com.sun.jini.lease.LeaseRenewalManager; 
import com.sun.jini.lease.LeaseListener; 
import com.sun.jini.lease.LeaseRenewalEvent; 
public class FileClassifierServerLM implements DiscoveryListener, LeaseListener {
	// 这只是一个名字，可以是任何其它名字
	// impl对象强制搜索 Stub
	static final String serviceName = "FileClassifier";
	protected FileClassifierImpl impl;  
	protected FileClassifierProxy proxy; 
	protected LeaseRenewalManager leaseManager = new LeaseRenewalManager();
	public static void main(String argv[]) {
		new FileClassifierServerLM();
	}
	public FileClassifierServerLM() {
		try {
			impl = new FileClassifierImpl();
		} catch(Exception e) {
			System.err.println("New impl: " + e.toString());
			System.exit(1); 
		}
		// 在 RMI registry中注册
		System.setSecurityManager(new RMISecurityManager());
		try {
			Naming.rebind(serviceName, impl); 
		} catch(java.net.MalformedURLException e) {
			System.err.println("Binding: " + e.toString());
			System.exit(1); 
		} catch(java.rmi.RemoteException e) {
			System.err.println("Binding: " + e.toString());
			System.exit(1); 
		}
		System.out.println("bound");
		// 找到当前运行地址
		String address = null; 
		try {
			address = InetAddress.getLocalHost().getHostName();
		} catch(java.net.UnknownHostException e) {
			System.err.println("Address: " + e.toString());
			System.exit(1);        }
		String registeredName = "//" + address + "/" + serviceName; 
		//建立一个知道服务地址的代理
		proxy = new FileClassifierProxy(registeredName); 
		// 接下来像以前一样继续
		LookupDiscovery discover = null; 
		try {
			discover = new LookupDiscovery(LookupDiscovery.ALL_GROUPS); 
		} catch(Exception e) {
			System.err.println(e.toString());
			System.exit(1); 
		}
		discover.addDiscoveryListener(this); 
		System.out.println("waiting..."); 
		// 等待足够长的时间来接收回复
		try {
			Thread.currentThread().sleep(10000000L); 
		} catch(java.lang.InterruptedException e) { 
			// 什么也不做
		} 
	}
	public void discovered(DiscoveryEvent evt) {
		ServiceRegistrar[] registrars = evt.getRegistrars();
		for (int n = 0; n < registrars.length; n++) {
			ServiceRegistrar registrar = registrars[n]; 
			// 导出代理服务
			ServiceItem item = new ServiceItem(null, proxy, null);	     
			ServiceRegistration reg = null; 
			try {
				reg = registrar.register(item, 20000L); 
			} catch(java.rmi.RemoteException e) {
				System.err.println("Register exception: " + e.toString());	
			}
			try {
				System.out.println("Service registered at " +
					registrar.getLocator().getHost());
				System.out.println("Lease granted for " +
					(reg.getLease().getExpiration()
					- System.currentTimeMillis()));
			} catch(Exception e) {	
			}
			leaseManager.renewFor(reg.getLease(), 1000000L, this); 
		}
	}
	public void discarded(DiscoveryEvent evt) { }
	public void notify(LeaseRenewalEvent evt) {
		System.out.println("Expired " + evt.toString());
	}   
}

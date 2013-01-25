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
	// ��ֻ��һ�����֣��������κ���������
	// impl����ǿ������ Stub
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
		// �� RMI registry��ע��
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
		// �ҵ���ǰ���е�ַ
		String address = null; 
		try {
			address = InetAddress.getLocalHost().getHostName();
		} catch(java.net.UnknownHostException e) {
			System.err.println("Address: " + e.toString());
			System.exit(1);        }
		String registeredName = "//" + address + "/" + serviceName; 
		//����һ��֪�������ַ�Ĵ���
		proxy = new FileClassifierProxy(registeredName); 
		// ����������ǰһ������
		LookupDiscovery discover = null; 
		try {
			discover = new LookupDiscovery(LookupDiscovery.ALL_GROUPS); 
		} catch(Exception e) {
			System.err.println(e.toString());
			System.exit(1); 
		}
		discover.addDiscoveryListener(this); 
		System.out.println("waiting..."); 
		// �ȴ��㹻����ʱ�������ջظ�
		try {
			Thread.currentThread().sleep(10000000L); 
		} catch(java.lang.InterruptedException e) { 
			// ʲôҲ����
		} 
	}
	public void discovered(DiscoveryEvent evt) {
		ServiceRegistrar[] registrars = evt.getRegistrars();
		for (int n = 0; n < registrars.length; n++) {
			ServiceRegistrar registrar = registrars[n]; 
			// �����������
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

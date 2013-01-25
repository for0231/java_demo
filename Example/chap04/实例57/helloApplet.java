import java.awt.*; 
import java.awt.event.*; 
import java.applet.*; 
import java.rmi.*; 
public class helloApplet extends Applet {
	boolean isStandalone = false; 
	String message = "blank";
	private Button button1 = new Button();
	private Label label1 = new Label();  //��ñ���ֵ
	public String getParameter(String key, String def) {
		return isStandalone ? System.getProperty(key, def) : 
		(getParameter(key) != null ? getParameter(key) : def); 
	}
	//���� applet
	public helloApplet() {
	}
	//��ʼ��applet
	public void init() {
		try {
			jbInit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	//�����ʼ��
	private void jbInit() throws Exception {
		button1.setLabel("Click me");                        
		button1.setBounds(new Rectangle(120, 143, 91, 29)); 
		button1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button1_actionPerformed(e); 
			}
		});
		this.setLayout(null); 
		label1.setBounds(new Rectangle(105, 90, 395, 31)); 
		this.add(button1, null); 
		this.add(label1, null); 
	}
	public void paint(Graphics g){ 
		g.drawString("", 25, 50); 
	}
	//���Applet����Ϣ
	public String getAppletInfo() {
		return "Applet Information";
	}
	//��� parameter ����Ϣ
	public String[][] getParameterInfo() {
		return null; 
	}
	void button1_actionPerformed(ActionEvent e) {
		try {
			label1.setText("��������RMI����..."); 
			//��RMI server��������ط��� 
			hello obj = (hello)Naming.lookup("rmi://127.0.0.1/HelloWorld"); 
			message = obj.sayHello();             
			label1.setText(message);              
		}
		catch (Exception e1) {
			label1.setText("Error: " + e1);      
		}
	}
}

package javawebservice;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
public class Calculator extends Frame
{
	String encodingStyleURI;
	URL url;
	TextField ef = new TextField();
	int state = 0; 
	double arg1 = Double.NaN;
	boolean dotpressed = false;
	String lastop;
	Label status;
	boolean freshstart = true;
	public Calculator(String title)
	{
		super(title);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		ef.setEditable(false);
		add("North", ef);
		Panel p = new Panel();
		p.setLayout(new GridLayout(-1, 4, 5, 5));
		String bs[] ={	
						"7","8","9","/",
						"4","5","6","*",
						"1","2","3","-",
						"0","+/-",".","+" 
					 };
		for (int i = 0; i < bs.length; i++)
		{
			Button b = new Button(" " + bs[i] + " ");
			ActionListener al = null;
			if ((i != 3)&& (i != 7)&& (i != 11)
				&& (i != 13) && (i != 14) && (i != 15))
			{
				// 数字被按下
				b.setActionCommand(bs[i]);
				al = new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (freshstart)
						{
							ef.setText("");
							freshstart = false;
						}
						Button bb = (Button) e.getSource();
						ef.setText(ef.getText() + 
							bb.getActionCommand());
						status.setText("");
					}
				};
			}
			else if (i == 14)
			{
				// 按下小数点"."
				al = new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (freshstart)
						{
							ef.setText("");
							freshstart = false;
						}
						status.setText("");
						if (dotpressed)
						{
							return;
						}
						else
						{
							dotpressed = true;
							ef.setText(ef.getText() + '.');
						}
					}
				};
			}
			else if (i == 13)
			{
				// 正负号被按下"+/-"
				al = new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (freshstart)
						{
							ef.setText("");
							freshstart = false;
						}
						String t = ef.getText();
						if (t.charAt(0) == '-')
						{
							ef.setText(t.substring(1));
						}
						else
						{
							ef.setText('-' + t);
						}
						status.setText("");
					}
				};
			}
			else
			{
				// 运算操作按钮被按下
				String ac = null;
				if (bs[i].equals("/"))
				{
					ac = "divide";
				}
				else if (bs[i].equals("*"))
				{
					ac = "times";
				}
				else if (bs[i].equals("-"))
				{
					ac = "minus";
				}
				else
				{
					ac = "plus";
				}
				b.setActionCommand(ac);
				al = new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						Button bb = (Button) e.getSource();
						double arg = stringToDouble(ef.getText());
						if (state == 0)
						{
							arg1 = arg;
							lastop = bb.getActionCommand();
							freshstart = true;
							state = 1;
						}
						else
						{
							try
							{
								status.setText("Working ..");
								arg1 = doOp(lastop, arg1, arg);
								lastop = bb.getActionCommand();
								ef.setText("" + arg1);
								freshstart = true;
								status.setText("");
							}
							catch (SOAPException e2)
							{
								status.setText(
									"Ouch, excepted: "+e2.getMessage());
								e2.printStackTrace();
							}
						}
					}
				};
			}
			b.addActionListener(al);
			p.add(b);
		}
		add("Center", p);
		add("South", status = new Label("Ready .."));
		pack();
		show();
	}
	private double doOp(String op, double arg1, double arg2)
		throws SOAPException
	{
		// 建立远程连接
		Call call = new Call();
		// 设置远程目标URI
		call.setTargetObjectURI("urn:xml-soap-demo-calculator");
		// 设置调用方法
		call.setMethodName(op);
		// 设置编码方式
		call.setEncodingStyleURI(encodingStyleURI);
		// 设置远程方法参数
		Vector params = new Vector();
		params.addElement(
			new Parameter("arg1", double.class, new Double(arg1), null));
		params.addElement(
			new Parameter("arg2", double.class, new Double(arg2), null));
		call.setParams(params);
		// 得到返回信息
		Response resp = call.invoke(url,"");
		// 检查返回信息
		if (resp.generatedFault())
		{
			Fault fault = resp.getFault();
			System.err.println("Generated fault: " + fault);
			return Double.NaN;
		}
		else
		{
			Parameter result = resp.getReturnValue();
			return ((Double) result.getValue()).doubleValue();
		}
	}
	private double stringToDouble(String s)
	{
		try
		{
			return Double.valueOf(s).doubleValue();
		}
		catch (NumberFormatException e1)
		{
			try
			{
				return Float.valueOf(s).floatValue() * 1.0;
			}
			catch (NumberFormatException e2)
			{
				if (s.indexOf(".") == -1)
				{
					return stringToDouble(s + '.' + '0');
				}
				else
				{
					return Double.NaN;
				}
			}
		}
	}
	public static void main(String[] args) throws Exception
	{
		int maxargs = 2;
		if (args.length != (maxargs - 1)
			&& (args.length != maxargs || !args[0].startsWith("-")))
		{
			System.err.println(
				"Usage: java "
					+ Calculator.class.getName()
					+ " [-encodingStyleURI] SOAP-router-URL");
			System.exit(1);
		}
		Calculator c = new Calculator("XML-SOAP Calculator");
		int offset = maxargs - args.length;
		c.encodingStyleURI =
			(args.length == maxargs)
				? args[0].substring(1)
				: Constants.NS_URI_SOAP_ENC;
		c.url = new URL(args[1 - offset]);
	}
}

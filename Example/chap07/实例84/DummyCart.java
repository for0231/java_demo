import javax.servlet.http.*;
import javax.servlet.*;
import java.util.*;
public class DummyCart
{
	Vector v = new Vector();
	String submit = null;
	String item = null;
	private void addItem(String name)
	{
	 v.addElement(name);
	}
	private void removeItem(String name)
	{
		v.removeElement(name);
	}
	public void setItem(String name)
	{
		item = name;
	}
	public void setSubmit(String s)
	{
		submit = s;
	}
	public String[] getItems()
	{
		String[] s = new String[v.size()];
		v.copyInto(s);
		return s;
	}
	
	public void processRequest(HttpServletRequest request)
	{
		//判断submit的值
		submit = (String)request.getParameter("submit");
	//	System.out.println(submit);
		item = (String)request.getParameter("item");
		if(submit.equals("null"))
		 addItem(item);
		if(submit.equals("add"))
		  addItem(item);
		else if(submit.equals("remove"))
		  removeItem(item);
		// 重置submit的值
		reset();
	}
	
	private void reset()
	{
		submit = null;
		item = null;
  }
}